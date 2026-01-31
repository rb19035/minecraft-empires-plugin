package com.cgms.minecraft.spigot.command;

import com.cgms.minecraft.spigot.util.MinecraftAiConstants;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NpcGuiCommand implements CommandExecutor
{
    private static final Logger LOGGER = LoggerFactory.getLogger( NpcGuiCommand.class.getName() );

    @Override
    public boolean onCommand( CommandSender commandSender, Command command, String s, String[] strings )
    {
        LOGGER.debug( "GUI Command fired." );

        if( ! (commandSender instanceof Player) )
        {
            commandSender.sendMessage( "Only players can use this command." );
            return true;
        }

        Player player = (Player) commandSender;
        Inventory inventory = Bukkit.createInventory( player, 9*3, "Empires NPC Menu" );

        ItemStack npcButton = new ItemStack( Material.PLAYER_HEAD );
        ItemMeta npcButtonMeta = npcButton.getItemMeta();
        npcButtonMeta.setDisplayName( MinecraftAiConstants.ARMORED_KNIGHT );
        npcButton.setItemMeta( npcButtonMeta );

        inventory.setItem( 1, npcButton );

        npcButton = new ItemStack( Material.PLAYER_HEAD );
        npcButtonMeta = npcButton.getItemMeta();
        npcButtonMeta.setDisplayName( MinecraftAiConstants.FOOT_SOLDIER );
        npcButton.setItemMeta( npcButtonMeta );

        inventory.setItem( 2, npcButton );

        npcButton = new ItemStack( Material.PLAYER_HEAD );
        npcButtonMeta = npcButton.getItemMeta();
        npcButtonMeta.setDisplayName( MinecraftAiConstants.BODYGUARD );
        npcButton.setItemMeta( npcButtonMeta );

        inventory.setItem( 3, npcButton );

        npcButton = new ItemStack( Material.PLAYER_HEAD );
        npcButtonMeta = npcButton.getItemMeta();
        npcButtonMeta.setDisplayName( MinecraftAiConstants.ARCHER );
        npcButton.setItemMeta( npcButtonMeta );

        inventory.setItem( 4, npcButton );
        player.openInventory( inventory );

        return true;
    }
}
