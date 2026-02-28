package com.cgms.minecraft.ai.jms;

import jakarta.jms.JMSException;
import jakarta.jms.Message;
import jakarta.jms.TextMessage;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class HeartbeatQueueRequestEventListener
{
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger( HeartbeatQueueRequestEventListener.class );

    @Autowired
    private HeartbeatQueueResponseEventSender heartbeatQueueResponseEventSender;

    @JmsListener (destination = "ai.heartbeat.request.queue" )
    public void receiveMessage( Message heartbeatRequestEventMessage )
    {
        try
        {
            if ( heartbeatRequestEventMessage instanceof TextMessage )
            {
                LOGGER.info( "Received AI heartbeat request event: {}", ((TextMessage) heartbeatRequestEventMessage).getText() );

                HeartbeatResponseEvent heartbeatResponseEvent = new HeartbeatResponseEvent( "Im Alive. Buzz off." );
                this.heartbeatQueueResponseEventSender.sendMessage( heartbeatResponseEvent, heartbeatRequestEventMessage.getJMSCorrelationID() );

                LOGGER.info( "Sent heartbeat response event: {}", heartbeatResponseEvent );
            } else
            {
                LOGGER.warn( "Received heartbeat non-text message of type: {}", heartbeatRequestEventMessage.getClass().getName() );
            }

        } catch ( JMSException e )
        {
            LOGGER.error( "Error processing heartbeat request message", e );
        }
    }
}
