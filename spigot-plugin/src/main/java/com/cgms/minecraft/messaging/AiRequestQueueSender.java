package com.cgms.minecraft.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AiRequestQueueSender
{
    private static final Logger LOGGER = LoggerFactory.getLogger( AiRequestQueueSender.class );
    private static final String AI_REQUEST_QUEUE = "ai.request.queue";

    private final ObjectMapper objectMapper;
    private final MessageProducer aiRequestProducer;
    private final TextMessage textMessage;


    protected AiRequestQueueSender( @NonNull Session session )
    {
        try
        {
            // Initialize reusable JSON mapper
            this.objectMapper = new ObjectMapper();

            // Initialize reusable producer
            this.aiRequestProducer = session.createProducer( session.createQueue( AI_REQUEST_QUEUE ) );

            // Initialize a reusable message
            this.textMessage = session.createTextMessage();

            LOGGER.info( "Created producer for AI request queue: {}", AI_REQUEST_QUEUE );

        } catch ( JMSException e )
        {
            throw new RuntimeException( "Failed to create producer for AI request queue", e );
        }

    }


    public void sendMessage( @NonNull AiRequestEvent aiRequestEvent )
    {
        try
        {
            // Serialize the event to JSON
            String json = this.objectMapper.writeValueAsString( aiRequestEvent );

            // Set the message text
            this.textMessage.setText( json );

            // Send the message
            this.aiRequestProducer.send( this.textMessage );

            LOGGER.debug( "Sent AI request event to {}: {}", AI_REQUEST_QUEUE, json );

        } catch ( JMSException | JsonProcessingException e )
        {
            LOGGER.error( "Failed to send AI request message", e );
        }
    }
}