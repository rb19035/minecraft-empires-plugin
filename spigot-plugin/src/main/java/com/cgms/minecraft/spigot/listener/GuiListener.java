package com.cgms.minecraft.spigot.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GuiListener implements Listener
{
    private static final Logger LOGGER = LoggerFactory.getLogger( GuiListener.class.getName() );

    @EventHandler
    public void onClick( InventoryClickEvent inventoryClickEvent )
    {
        LOGGER.debug( "Received inventory click event." );
    }

    @EventHandler
    public void onClose( InventoryCloseEvent inventoryCloseEvent )
    {
        LOGGER.debug( "Received inventory close event." );

        Player player = (Player) inventoryCloseEvent.getPlayer();
    }
}
