/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 */

package com.cgms.minecraft.ai.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsClient;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class HeartbeatQueueResponseEventSender
{
    private static final Logger LOGGER = LoggerFactory.getLogger( HeartbeatQueueResponseEventSender.class );
    private static final String AI_HEARTBEAT_RESPONSE_QUEUE = "ai.heartbeat.response.queue";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private JmsClient jmsClient;

    public void sendMessage( @NonNull HeartbeatResponseEvent heartbeatResponseEvent, String correlationId )
    {
        LOGGER.info( "Sending AI heartbeat response event: {}", heartbeatResponseEvent );
        LOGGER.info( "Sending AI heartbeat response with correlationId: {}", correlationId );

        try
        {
            Map<String, Object> headers = Map.of( "JMSCorrelationID", correlationId );
            this.jmsClient.destination( AI_HEARTBEAT_RESPONSE_QUEUE ).send( this.objectMapper.writeValueAsString( heartbeatResponseEvent ), headers );

        } catch ( JsonProcessingException e )
        {
            LOGGER.error( "Failed to send AI heartbeat response event", e );
        }
    }
}
