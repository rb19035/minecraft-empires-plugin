package com.cgms.minecraft.spigot.schedule.job;

import com.cgms.minecraft.messaging.*;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AiServerHeartbeatJob implements Job
{
    private static final Logger LOGGER = LoggerFactory.getLogger( AiServerHeartbeatJob.class );

    public void execute( JobExecutionContext jobExecutionContext ) throws JobExecutionException
    {
        LOGGER.debug( "Executing server heartbeat job." );

        HeartbeatRequestEvent heartbeatRequestEvent = new HeartbeatRequestEvent( "Alive?" );
        HeartbeatQueueManager.getInstance().sendHeartbeatMessage( heartbeatRequestEvent );

        LOGGER.debug( "Sent heartbeat event to server." );
    }
}
