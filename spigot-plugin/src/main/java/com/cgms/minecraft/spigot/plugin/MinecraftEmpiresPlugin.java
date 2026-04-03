package com.cgms.minecraft.spigot.plugin;

import com.cgms.minecraft.messaging.ActiveMqConnectionManager;
import com.cgms.minecraft.spigot.command.MagicItemsGuiCommand;
import com.cgms.minecraft.spigot.command.NpcGuiCommand;
import com.cgms.minecraft.spigot.listener.*;
import com.cgms.minecraft.spigot.schedule.job.AiServerHeartbeatJob;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;

public class MinecraftEmpiresPlugin extends JavaPlugin implements Listener
{
    private static final Logger LOGGER = LoggerFactory.getLogger( MinecraftEmpiresPlugin.class.getName() );
    private ActiveMqConnectionManager activeMqConnectionManager;

    @Override
    public void onEnable()
    {
        try
        {
            PluginManager pluginManager = getServer().getPluginManager();
            pluginManager.registerEvents( new NpcNavigationCompleteListener(), this );
            pluginManager.registerEvents( new NpcSpawnItemDroppedListener(), this );
            pluginManager.registerEvents( new BellOfTeleportationBlockBreakListener( this ), this );
            pluginManager.registerEvents( new BellOfTeleportationBlockPlacedListener( this ), this );
            pluginManager.registerEvents( new BellOfTeleportationRingListener( this ), this );
            pluginManager.registerEvents( new BellOfTeleportationPickedUpListener( this ), this );
            pluginManager.registerEvents( new BellOfTeleportationOnBlockDropListener( this ), this );
            pluginManager.registerEvents( new GuiListener(), this );
            pluginManager.registerEvents( new NpcSpawnListener(), this );


            this.getCommand( "npc-gui" ).setExecutor( new NpcGuiCommand() );
            this.getCommand( "magic-gui" ).setExecutor( new MagicItemsGuiCommand( this ) );

            // Start the ActiveMQ connection manager
            this.activeMqConnectionManager = ActiveMqConnectionManager.getInstance();
            this.activeMqConnectionManager.start();

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
