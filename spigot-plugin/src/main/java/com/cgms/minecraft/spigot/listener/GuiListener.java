package com.cgms.minecraft.spigot.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

import java.util.logging.Logger;

public class GuiListener implements Listener
{
    private static Logger LOGGER = Logger.getLogger( GuiListener.class.getName() );

    @EventHandler
    public void onClick( InventoryClickEvent inventoryClickEvent )
    {
        LOGGER.info( "Received inventory click event." );
    }

    @EventHandler
    public void onClose( InventoryCloseEvent inventoryCloseEvent )
    {
        Player player = (Player) inventoryCloseEvent.getPlayer();
        LOGGER.info( "Received inventory close event." );
    }
}
