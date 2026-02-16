package com.cgms.minecraft.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class HeartbeatResponseQueueListener implements MessageListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger( HeartbeatResponseQueueListener.class );
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onMessage( Message message )
    {
        try
        {
            if ( message instanceof TextMessage textMessage )
            {
                String json = textMessage.getText();
                HeartbeatResponseEvent event = objectMapper.readValue( json, HeartbeatResponseEvent.class );
                handleResponse( event );

                LOGGER.debug( "Received heartbeat response event: {}", json );
            }
            else
            {
                LOGGER.warn( "Received heartbeat non-text message of type: {}", message.getClass().getName() );
            }
        }
        catch ( Exception e )
        {
            LOGGER.error( "Failed to process heartbeat response message", e );
        }
    }

    private void handleResponse( HeartbeatResponseEvent event )
    {
        LOGGER.info( "Processing heartbeat response event" );
    }
}