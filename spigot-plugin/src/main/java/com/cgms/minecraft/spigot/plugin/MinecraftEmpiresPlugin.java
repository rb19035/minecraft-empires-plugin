package com.cgms.minecraft.spigot.plugin;

import com.cgms.minecraft.messaging.ActiveMqConnectionManager;
import com.cgms.minecraft.messaging.AiRequestEvent;
import com.cgms.minecraft.messaging.AiRequestQueueSender;
import com.cgms.minecraft.spigot.command.NpcGuiCommand;
import com.cgms.minecraft.spigot.listener.GuiListener;
import com.cgms.minecraft.spigot.listener.NpcNavigationCompleteListener;
import com.cgms.minecraft.spigot.listener.NpcSpawnItemDroppedListener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MinecraftEmpiresPlugin extends JavaPlugin
{
    private static final Logger LOGGER = LoggerFactory.getLogger( MinecraftEmpiresPlugin.class.getName() );

    private ActiveMqConnectionManager activeMqConnectionManager;

    @Override
    public void onEnable()
    {
        LOGGER.info("Minecraft Empires Plugin has been enabled!");

        // Initialization logic here
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents( new NpcNavigationCompleteListener(), this);
        pluginManager.registerEvents( new NpcSpawnItemDroppedListener(), this);
        pluginManager.registerEvents( new GuiListener(), this );

        this.getCommand( "npc-gui" ).setExecutor( new NpcGuiCommand() );

        startActiveMqListener();
        AiRequestQueueSender requestSender = activeMqConnectionManager.getRequestSender();
        requestSender.sendMessage( new AiRequestEvent( "Hello World" ) );

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

    private void startActiveMqListener()
    {
        try
        {
            activeMqConnectionManager = new ActiveMqConnectionManager(
                    "tcp://localhost:61616", "admin", "admin" );
            activeMqConnectionManager.start();
        }
        catch ( Exception e )
        {
            LOGGER.error( "Failed to connect to ActiveMQ", e );
        }
    }
}
