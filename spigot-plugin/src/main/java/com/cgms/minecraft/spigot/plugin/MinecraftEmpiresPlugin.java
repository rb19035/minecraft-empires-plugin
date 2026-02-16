package com.cgms.minecraft.spigot.plugin;

import com.cgms.minecraft.messaging.ActiveMqConnectionManager;
import com.cgms.minecraft.messaging.AiRequestQueueSender;
import com.cgms.minecraft.spigot.command.NpcGuiCommand;
import com.cgms.minecraft.spigot.listener.GuiListener;
import com.cgms.minecraft.spigot.listener.NpcNavigationCompleteListener;
import com.cgms.minecraft.spigot.listener.NpcSpawnItemDroppedListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class MinecraftEmpiresPlugin extends JavaPlugin
{
    private static final Logger LOGGER = LoggerFactory.getLogger( MinecraftEmpiresPlugin.class.getName() );

    private static final String CONFIG_FILE_PATH = "./config.properties";
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

    private void loadAiPropertiesFile()
    {
        Properties properties = new Properties();

        try ( FileInputStream input = new FileInputStream( CONFIG_FILE_PATH ) )
        {
            properties.load( input );

            LOGGER.debug( "Loaded AI properties file." );

        } catch ( IOException e)
        {
            LOGGER.error( "Failed to load AI properties file. Will continue with default settings.", e );
        }

        this.mqttBrokerUrl = properties.getProperty( "mqtt.broker.url", "tcp://localhost:61616" );
        this.mqttUsername = properties.getProperty( "mqtt.username", "admin" );
        this.mqttPassword = properties.getProperty( "mqtt.password", "admin" );
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
