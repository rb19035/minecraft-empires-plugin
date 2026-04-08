/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class HeartbeatQueueManager implements MessageListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger( HeartbeatQueueManager.class );

    private static final String AI_HEARTBEAT_QUEUE = "ai.heartbeat.request.queue";
    private static final String HEARTBEAT_RESPONSE_QUEUE = "ai.heartbeat.response.queue";

    private static HeartbeatQueueManager INSTANCE;
    private final ObjectMapper objectMapper;
    private final MessageProducer heartbeatMessageProducer;
    private final TextMessage heartbeatMessage;
    private final Map<String, Boolean> heartbeatResponseMap;


    private HeartbeatQueueManager()
    {
        try
        {
            ActiveMqConnectionManager activeMqConnectionManager = ActiveMqConnectionManager.getInstance();
            Session jmsSession = activeMqConnectionManager.getJmsSession();

            // Init the heartbeat queue message listener/consumer
            Queue heartbeatQueue = jmsSession.createQueue( HEARTBEAT_RESPONSE_QUEUE );
            MessageConsumer heartbeatResponseConsumer = jmsSession.createConsumer( heartbeatQueue );
            heartbeatResponseConsumer.setMessageListener( this );

            // Init the Heartbeat queue message sender/producer
            this.objectMapper = new ObjectMapper();
            this.heartbeatMessageProducer = jmsSession.createProducer( jmsSession.createQueue( AI_HEARTBEAT_QUEUE ) );
            this.heartbeatMessage = jmsSession.createTextMessage();

            this.heartbeatResponseMap = new ConcurrentHashMap<>();

            LOGGER.info( "Created producer for AI heartbeat queue: {}", AI_HEARTBEAT_QUEUE );

        } catch ( JMSException jmsException )
        {
            LOGGER.error( "Failed to create heartbeat response consumer", jmsException );
            throw new RuntimeException( "Failed to create heartbeat response consumer", jmsException );
        }
    }

    public static HeartbeatQueueManager getInstance()
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new HeartbeatQueueManager();
        }

        return INSTANCE;
    }

    public void sendHeartbeatMessage( HeartbeatRequestEvent event)
    {
        try
        {
            // Serialize the event to JSON
            String json = this.objectMapper.writeValueAsString( event );

            // Set the message text
            this.heartbeatMessage.setText( json );
            this.heartbeatMessage.setJMSCorrelationID( UUID.randomUUID().toString() );

            this.heartbeatResponseMap.put( this.heartbeatMessage.getJMSCorrelationID(), false );

            // Send the message
            this.heartbeatMessageProducer.send( this.heartbeatMessage );

            LOGGER.info( "Sent heartbeat event to {}: {} : {}", AI_HEARTBEAT_QUEUE, json, this.heartbeatMessage.getJMSCorrelationID() );



        } catch ( JMSException | JsonProcessingException e)
        {
            LOGGER.error( "Failed to send heartbeat message", e );
        }

    }

    @Override
    public void onMessage( Message message )
    {
        try
        {
            if ( message instanceof TextMessage textMessage )
            {
                String json = textMessage.getText();

                if( this.heartbeatResponseMap.containsKey( textMessage.getJMSCorrelationID() ) )
                {
                    this.heartbeatResponseMap.remove( textMessage.getJMSCorrelationID() );
                } else
                {
                    LOGGER.error( "Received heartbeat response for unknown correlation ID: {}", textMessage.getJMSCorrelationID() );
                }

                if ( this.heartbeatResponseMap.size() > 1 )
                {
                    LOGGER.error( "Heartbeats with AI Server may be out of sync. Heartbeat Responses Map Size: {}", this.heartbeatResponseMap.size() );
                }

                LOGGER.debug( "Received heartbeat response event: {}", json );
            }
            else
            {
                LOGGER.warn( "Received heartbeat non-text message of type: {}", message.getClass().getName() );
            }

        } catch ( Exception e )
        {
            LOGGER.error( "Failed to process heartbeat response message", e );
        }
    }
}
