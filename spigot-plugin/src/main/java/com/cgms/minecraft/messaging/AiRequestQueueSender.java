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

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Session session;

    public AiRequestQueueSender( @NonNull Session session )
    {
        this.session = session;
    }

    public void sendMessage( @NonNull AiRequestEvent aiRequestEvent )
    {
        try
        {
            String json = objectMapper.writeValueAsString( aiRequestEvent );
            TextMessage textMessage = session.createTextMessage( json );
            MessageProducer producer = session.createProducer( session.createQueue( AI_REQUEST_QUEUE ) );
            producer.send( textMessage );
            producer.close();
            LOGGER.debug( "Sent AI request event to {}: {}", AI_REQUEST_QUEUE, json );
        }
        catch ( JMSException | JsonProcessingException e )
        {
            LOGGER.error( "Failed to send AI request message", e );
        }
    }
}