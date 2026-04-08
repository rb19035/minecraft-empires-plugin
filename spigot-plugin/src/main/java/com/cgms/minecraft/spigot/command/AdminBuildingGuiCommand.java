/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.command;

import com.cgms.minecraft.spigot.item.BellOfTeleportation;
import com.cgms.minecraft.spigot.item.BellOfTeleportationManager;
import com.cgms.minecraft.spigot.item.Building;
import com.cgms.minecraft.spigot.plugin.MinecraftEmpiresConstants;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class AdminBuildingGuiCommand implements CommandExecutor
{
    private static final Logger LOGGER = LoggerFactory.getLogger( AdminBuildingGuiCommand.class.getName() );
    private static final String INVENTORY_MENU_TITLE = "Admin Building Menu";
    private final Plugin plugin;

    public AdminBuildingGuiCommand( Plugin plugin )
    {
        this.plugin = plugin;
    }


    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String s, String[] strings )
    {
        LOGGER.debug( "AdminBuildingGuiCommand fired." );

        if( ! (commandSender instanceof Player) )
        {
            commandSender.sendMessage( "Only players can use this command." );
            LOGGER.debug( "Command sender is not a player." );
            return false;
        }


        Player player = (Player) commandSender;

        Inventory inventory = Bukkit.createInventory( player, 9*3, INVENTORY_MENU_TITLE );
        ItemStack buildingItemStack = new ItemStack( Material.WHITE_BANNER );

        Building building = new Building();

        ItemMeta buildingButtonMeta = buildingItemStack.getItemMeta();
        buildingButtonMeta.setDisplayName( MinecraftEmpiresConstants.BELLS_OF_TELEPORTATION );
        buildingButtonMeta.getPersistentDataContainer().set( NamespacedKey.minecraft( MinecraftEmpiresConstants.BELLS_OF_TELEPORTATION_UUID_FIELD ),
                PersistentDataType.STRING, building.getUuid()
        );

        // Create BellOfTeleportation object to track usage, entanglement, and other stuff
        BellOfTeleportation bellOfTeleportation = new BellOfTeleportation();


        buildingButtonMeta.setDisplayName( MinecraftEmpiresConstants.BELLS_OF_TELEPORTATION );
        buildingButtonMeta.getPersistentDataContainer().set( NamespacedKey.minecraft( MinecraftEmpiresConstants.BELLS_OF_TELEPORTATION_UUID_FIELD ),
                PersistentDataType.STRING, bellOfTeleportation.getUuid()
        );

        // Set metadata for the Bell of Teleportation ItemStack
        bellOfTeleportationItemStack.setItemMeta( bellOfTeleportationButtonMeta );

        LOGGER.debug( "Creating Bell of Teleportation with UUID {}.", bellOfTeleportation.getUuid() );

        // Create the entangled Bell of Teleportation ItemStack
        ItemStack entangledBellOfTeleportationItemStack = new ItemStack( Material.BELL );

        // Create item metadata for the entangled Bell of Teleportation ItemStack
        ItemMeta entangledBellOfTeleportationButtonMeta = entangledBellOfTeleportationItemStack.getItemMeta();

        // Create BellOfTeleportation object for the entangled bell to track usage, entanglement, and other stuff
        BellOfTeleportation entangledBellOfTeleportation = new BellOfTeleportation();

        LOGGER.debug( "Creating entangled Bell of Teleportation with UUID {}.", entangledBellOfTeleportation.getUuid() );

        // Set the display name of the entangled Bell of Teleportation ItemStack
        entangledBellOfTeleportationButtonMeta.setDisplayName( "Entangled-" + MinecraftEmpiresConstants.BELLS_OF_TELEPORTATION );
        entangledBellOfTeleportationButtonMeta.getPersistentDataContainer().set( NamespacedKey.minecraft( MinecraftEmpiresConstants.BELLS_OF_TELEPORTATION_UUID_FIELD ),
                PersistentDataType.STRING, entangledBellOfTeleportation.getUuid()
        );

        // Set metadata for the entangled Bell of Teleportation ItemStack
        entangledBellOfTeleportationItemStack.setItemMeta( entangledBellOfTeleportationButtonMeta );

        // Entangle the Bell of Teleportation objects to enable teleportation
        entangledBellOfTeleportation.setEntangledBellOfTeleportation( bellOfTeleportation );
        bellOfTeleportation.setEntangledBellOfTeleportation( entangledBellOfTeleportation );

        LOGGER.debug( "Setting entangled relationship between Bell of Teleportation {} and {}.", bellOfTeleportation.getUuid(), entangledBellOfTeleportation.getUuid() );

        // Add the bells to the BellOfTeleportationUtil for tracking and easy lookup
        BellOfTeleportationManager.getInstance().updateBellOfTeleportationMappings( bellOfTeleportation, entangledBellOfTeleportation );

        //Set the GUI menus inventory
        inventory.setItem( 1, bellOfTeleportationItemStack );
        inventory.setItem( 2, entangledBellOfTeleportationItemStack );

        // Create a Building Spawner ItemStacks for the GUI menu
        ItemStack buildingSpawnerItemStack = new ItemStack( Material.SPAWNER );

        // Create item meta data for the Bell of Teleportation ItemStack
        ItemMeta buildingSpawnerButtonMeta = buildingSpawnerItemStack.getItemMeta();
        buildingSpawnerButtonMeta.setDisplayName( "BUILDING" );
        buildingSpawnerItemStack.setItemMeta( buildingSpawnerButtonMeta );

        inventory.setItem( 9, buildingSpawnerItemStack );

        // Open the inventory and display on screen
        player.openInventory( inventory );

        return true;
    }
}
