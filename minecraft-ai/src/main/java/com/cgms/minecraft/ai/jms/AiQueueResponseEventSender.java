package com.cgms.minecraft.ai.jms;

import lombok.NonNull;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsClient;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class AiQueueResponseEventSender
{
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger( AiQueueResponseEventSender.class );

    @Autowired
    private JmsClient jmsClient;

    public void sendMessage( @NonNull AiResponseEvent  aiResponseEvent )
    {
        LOGGER.debug( "Sending AI response event: {}", aiResponseEvent );

        this.jmsClient.destination( "ai.response.queue" ).send( aiResponseEvent );
    }
}
