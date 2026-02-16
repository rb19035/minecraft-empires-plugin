package com.cgms.minecraft.ai.jms;

import org.slf4j.Logger;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class AiQueueRequestEventListener
{
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger( AiQueueRequestEventListener.class );

    @JmsListener (destination = "ai.request.queue" )
    public void receiveMessage( AiRequestEvent aiRequestEvent )
    {
        LOGGER.debug( "Received AI request event: {}", aiRequestEvent );
    }
}
