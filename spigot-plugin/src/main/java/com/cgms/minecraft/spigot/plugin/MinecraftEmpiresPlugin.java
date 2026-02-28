package com.cgms.minecraft.spigot.plugin;

import com.cgms.minecraft.messaging.ActiveMqConnectionManager;
import com.cgms.minecraft.messaging.AiRequestQueueSender;
import com.cgms.minecraft.spigot.command.NpcGuiCommand;
import com.cgms.minecraft.spigot.listener.GuiListener;
import com.cgms.minecraft.spigot.listener.NpcNavigationCompleteListener;
import com.cgms.minecraft.spigot.listener.NpcSpawnItemDroppedListener;
import com.cgms.minecraft.spigot.schedule.job.AiServerHeartbeatJob;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MinecraftEmpiresPlugin extends JavaPlugin
{
    private static final Logger LOGGER = LoggerFactory.getLogger( MinecraftEmpiresPlugin.class.getName() );


    private String mqttBrokerUrl;
    private String mqttUsername;
    private String mqttPassword;

    private ActiveMqConnectionManager activeMqConnectionManager;

    @Override
    public void onEnable()
    {
        try
        {
            // Initialization logic here
            PluginManager pluginManager = getServer().getPluginManager();
            pluginManager.registerEvents( new NpcNavigationCompleteListener(), this );
            pluginManager.registerEvents( new NpcSpawnItemDroppedListener(), this );
            pluginManager.registerEvents( new GuiListener(), this );

            this.getCommand( "npc-gui" ).setExecutor( new NpcGuiCommand() );

            // Start the ActiveMQ connection manager
            ActiveMqConnectionManager.getInstance().start();

            SchedulerFactory sf = new StdSchedulerFactory();
            Scheduler scheduler = sf.getScheduler();

            // Init AI server heartbeat job
            JobDetail job = JobBuilder.newJob( AiServerHeartbeatJob.class )
                    .withIdentity("aiServerHeartbeatJob", "heartbeatGroup" )
                    .build();

            Trigger trigger = TriggerBuilder.newTrigger()
                    .withIdentity("aiServerHeartbeatJobTrigger", "heartbeatGroup")
                    .startNow()
                    .withSchedule(CronScheduleBuilder.cronSchedule("0 0/5 * * * ?")) // Run every 5 minutes
                    .build();

            scheduler.scheduleJob(job, trigger);
            scheduler.start();

            LOGGER.info( "Minecraft Empires Plugin has been enabled!" );

        } catch ( JMSException jmse )
        {
            LOGGER.error( "Failed to start ActiveMQ connection and cannot continue plugin initialization.", jmse );
            throw new RuntimeException( jmse );

        } catch ( Exception e )
        {
            LOGGER.error( "Minecraft Empires Plugin failed to load!", e );
            throw new RuntimeException( e );
        }
    }



    @Override
    public void onDisable()
    {
        if ( activeMqConnectionManager != null )
        {
            activeMqConnectionManager.stop();
        }
        LOGGER.info("Minecraft Empires Plugin has been disabled!");
    }
}
