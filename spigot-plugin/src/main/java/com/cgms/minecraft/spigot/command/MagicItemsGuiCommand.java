package com.cgms.minecraft.spigot.command;

import com.cgms.minecraft.spigot.item.BellOfTeleportation;
import com.cgms.minecraft.spigot.util.BellOfTeleportationUtil;
import com.cgms.minecraft.spigot.util.MinecraftAiConstants;
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


public class MagicItemsGuiCommand implements CommandExecutor
{
    private static final Logger LOGGER = LoggerFactory.getLogger( MagicItemsGuiCommand.class.getName() );
    private final Plugin plugin;

    public MagicItemsGuiCommand( Plugin plugin )
    {
        this.plugin = plugin;
    }



    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String s, String[] strings )
    {
        LOGGER.debug( "MagicItemsGuiCommand fired." );

        if( ! (commandSender instanceof Player) )
        {
            commandSender.sendMessage( "Only players can use this command." );
            LOGGER.debug( "Command sender is not a player." );
            return false;
        }

        // Get the player that sent the command
        Player player = (Player) commandSender;

        // Create the inventory for the GUI
        Inventory inventory = Bukkit.createInventory( player, 9*3, "Empires Magic Items Menu" );

        // Create the Bell of Teleportation ItemStacks for the GUI menu
        ItemStack bellOfTeleportationItemStack = new ItemStack( Material.BELL );

        // Create item meta data for the Bell of Teleportation ItemStack
        ItemMeta bellOfTeleportationButtonMeta = bellOfTeleportationItemStack.getItemMeta();

        // Create BellOfTeleportation object to track usage, entanglement, and other stuff
        BellOfTeleportation bellOfTeleportation = new BellOfTeleportation();
        bellOfTeleportation.setBellOfTeleportationItemStack( bellOfTeleportationItemStack );

        // Set the display name of the Bell of Teleportation ItemStack
        bellOfTeleportationButtonMeta.setDisplayName( MinecraftAiConstants.BELLS_OF_TELEPORTATION );
        bellOfTeleportationButtonMeta.getPersistentDataContainer().set( NamespacedKey.minecraft( MinecraftAiConstants.BELLS_OF_TELEPORTATION_UUID_FIELD ),
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
        entangledBellOfTeleportation.setBellOfTeleportationItemStack( entangledBellOfTeleportationItemStack );

        LOGGER.debug( "Creating entangled Bell of Teleportation with UUID {}.", entangledBellOfTeleportation.getUuid() );

        // Set the display name of the entangled Bell of Teleportation ItemStack
        entangledBellOfTeleportationButtonMeta.setDisplayName( "Entangled-" + MinecraftAiConstants.BELLS_OF_TELEPORTATION );
        entangledBellOfTeleportationButtonMeta.getPersistentDataContainer().set( NamespacedKey.minecraft( MinecraftAiConstants.BELLS_OF_TELEPORTATION_UUID_FIELD ),
                PersistentDataType.STRING, entangledBellOfTeleportation.getUuid()
        );

        // Set metadata for the entangled Bell of Teleportation ItemStack
        entangledBellOfTeleportationItemStack.setItemMeta( entangledBellOfTeleportationButtonMeta );

        // Entangle the Bell of Teleportation objects to enable teleportation
        entangledBellOfTeleportation.setEntangledBellOfTeleportation( bellOfTeleportation );
        bellOfTeleportation.setEntangledBellOfTeleportation( entangledBellOfTeleportation );

        LOGGER.debug( "Setting entangled relationship between Bell of Teleportation {} and {}.", bellOfTeleportation.getUuid(), entangledBellOfTeleportation.getUuid() );

        // Add the bells to the BellOfTeleportationUtil for tracking and easy lookup
        BellOfTeleportationUtil.getInstance().updateBellOfTeleportationMappings( bellOfTeleportation, entangledBellOfTeleportation );

        //Set the GUI menus inventory
        inventory.setItem( 1, bellOfTeleportationItemStack );
        inventory.setItem( 2, entangledBellOfTeleportationItemStack );

        // Open the inventory and display on screen
        player.openInventory( inventory );

        return true;
    }
}
