/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.listener;

import com.cgms.minecraft.spigot.item.BellOfTeleportation;
import com.cgms.minecraft.spigot.item.BellOfTeleportationManager;
import com.cgms.minecraft.spigot.plugin.MinecraftEmpiresConstants;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BellOfTeleportationPickedUpListener implements Listener
{
    private static final Logger LOGGER = LoggerFactory.getLogger( BellOfTeleportationPickedUpListener.class.getName() );
    private final Plugin plugin;


    public BellOfTeleportationPickedUpListener( Plugin plugin )
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPickup( EntityPickupItemEvent event)
    {
        if (event.getItem().getItemStack().getType() == Material.BELL)
        {
            LOGGER.debug( "Bell item picked up caught. Checking to see if it a Bell of Teleportation." );

            ItemStack itemStack = event.getItem().getItemStack();

            BellOfTeleportationManager bellOfTeleportationManager = BellOfTeleportationManager.getInstance();
            BellOfTeleportation bellOfTeleportation = bellOfTeleportationManager.retrieveBellOfTeleportationUUIDFromPersistentDataContainer( itemStack.getItemMeta().getPersistentDataContainer() );

            if( bellOfTeleportation != null )
            {
                itemStack.getItemMeta().getPersistentDataContainer().set( NamespacedKey.minecraft( MinecraftEmpiresConstants.BELLS_OF_TELEPORTATION_UUID_FIELD ),
                        PersistentDataType.STRING, bellOfTeleportation.getUuid()
                );

                itemStack.getItemMeta().setItemName( MinecraftEmpiresConstants.BELLS_OF_TELEPORTATION );
                itemStack.getItemMeta().setDisplayName( MinecraftEmpiresConstants.BELLS_OF_TELEPORTATION );

                bellOfTeleportationManager.updateBellOfTeleportationMappings( bellOfTeleportation );
            }
        }
    }
}
