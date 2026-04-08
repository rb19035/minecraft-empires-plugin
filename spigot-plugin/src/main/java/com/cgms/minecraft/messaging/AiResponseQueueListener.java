/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.messaging;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

public class AiResponseQueueListener implements MessageListener
{
    private static final Logger LOGGER = LoggerFactory.getLogger( AiResponseQueueListener.class );
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void onMessage( Message message )
    {
        try
        {
            if ( message instanceof TextMessage textMessage )
            {
                String json = textMessage.getText();
                AiResponseEvent event = objectMapper.readValue( json, AiResponseEvent.class );
                LOGGER.debug( "Received AI response event: {}", json );
                handleResponse( event );
            }
            else
            {
                LOGGER.warn( "Received non-text message of type: {}", message.getClass().getName() );
            }
        }
        catch ( Exception e )
        {
            LOGGER.error( "Failed to process AI response message", e );
        }
    }

    private void handleResponse( AiResponseEvent event )
    {
        LOGGER.info( "Processing AI response event" );
    }
}