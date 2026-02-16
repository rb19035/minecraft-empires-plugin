package com.cgms.minecraft.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;

public class HeartbeatRequestQueueSender
{
    private static final Logger LOGGER = LoggerFactory.getLogger( HeartbeatRequestQueueSender.class );
    private static final String AI_HEARTBEAT_QUEUE = "ai.heartbeat.request.queue";

    private final ObjectMapper objectMapper;
    private final MessageProducer heartbeatMessageProducer;
    private final TextMessage heartbeatMessage;


    protected HeartbeatRequestQueueSender( @NonNull Session session )
    {
        try
        {
            // Initialize reusable JSON mapper
            this.objectMapper = new ObjectMapper();

            // Initialize reusable producer
            this.heartbeatMessageProducer = session.createProducer( session.createQueue( AI_HEARTBEAT_QUEUE ) );

            // Initialize a reusable message
            this.heartbeatMessage = session.createTextMessage();

            LOGGER.info( "Created producer for AI heartbeat queue: {}", AI_HEARTBEAT_QUEUE );

        } catch ( JMSException e )
        {
            throw new RuntimeException( "Failed to create producer for AI heartbeat queue", e );
        }

    }


    public void sendMessage( @NonNull AiRequestEvent aiRequestEvent )
    {
        try
        {
            // Serialize the event to JSON
            String json = this.objectMapper.writeValueAsString( aiRequestEvent );

            // Set the message text
            this.heartbeatMessage.setText( json );

            // Send the message
            this.heartbeatMessageProducer.send( this.heartbeatMessage );

            LOGGER.debug( "Sent AI heartbeat event to {}: {}", AI_HEARTBEAT_QUEUE, json );

        } catch ( JMSException | JsonProcessingException e )
        {
            LOGGER.error( "Failed to send AI heartbeat message", e );
        }
    }
}