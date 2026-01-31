package com.cgms.minecraft.spigot.plugin;

import com.cgms.minecraft.spigot.command.NpcGuiCommand;
import com.cgms.minecraft.spigot.listener.GuiListener;
import com.cgms.minecraft.spigot.listener.NpcNavigationCompleteListener;
import com.cgms.minecraft.spigot.listener.NpcSpawnItemDroppedListener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class MinecraftEmpiresPlugin extends JavaPlugin
{
    private static final Logger LOGGER = Logger.getLogger( MinecraftEmpiresPlugin.class.getName() );

    @Override
    public void onEnable()
    {
        LOGGER.info("Minecraft Empires Plugin has been enabled!");

        // Initialization logic here
        getServer().getPluginManager().registerEvents( new NpcNavigationCompleteListener(), this);
        getServer().getPluginManager().registerEvents( new NpcSpawnItemDroppedListener(), this);
        getServer().getPluginManager().registerEvents( new GuiListener(), this );

        getCommand( "npc-gui" ).setExecutor( new NpcGuiCommand() );
    }

    @Override
    public void onDisable()
    {
        LOGGER.info("Minecraft Empires Plugin has been disabled!");
    }
}
