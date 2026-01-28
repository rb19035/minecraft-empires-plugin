package com.cgms.minecraft.spigot.util;


import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Inventory;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.mcmonkey.sentinel.SentinelTrait;

import java.util.logging.Logger;

public class NpcFactory
{
    private static final Logger LOGGER = Logger.getLogger( NpcFactory.class.getName() );

    private NpcFactory() {}

    public static NPC spawnNPC( Location location, String npcType, Player player, String name )
    {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC( EntityType.PLAYER, name);

        npc.setFlyable( false );
        npc.setProtected( false );

        SentinelTrait sentry = npc.getOrAddTrait(SentinelTrait.class);
        npc.spawn( location );
        sentry.fightback = true;

        if( npcType.equalsIgnoreCase( MinecraftAiConstants.ARMORED_KNIGHT ) )
        {
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.IRON_BOOTS, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.IRON_HELMET, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.IRON_CHESTPLATE, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.IRON_LEGGINGS, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.HAND, new ItemStack( Material.IRON_SWORD, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.OFF_HAND, new ItemStack( Material.SHIELD, 1 ) );

            LOGGER.fine( "Spawned NPC Type:" + MinecraftAiConstants.ARMORED_KNIGHT );

        } else if ( npcType.equalsIgnoreCase( MinecraftAiConstants.ARCHER ) )
        {
            // Added standard archer equipment
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.LEATHER_BOOTS, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.CHAINMAIL_HELMET, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.LEATHER_CHESTPLATE, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.LEATHER_LEGGINGS, 1 ) );

            // Add arrows to the NPC's inventory.
            Inventory npcInventoryTrait = npc.getOrAddTrait(Inventory.class);
            org.bukkit.inventory.Inventory bukkitInventory = npcInventoryTrait.getInventoryView();
            for ( int i = 9; i < 18; i++ )
            {
                bukkitInventory.setItem( i, new ItemStack( Material.ARROW, 64) );
            }

            LOGGER.fine( "Spawned NPC Type:" + MinecraftAiConstants.ARCHER );

        } else if ( npcType.equalsIgnoreCase( MinecraftAiConstants.BODYGUARD ) )
        {
            // Set NPC to guard player that created it.
            sentry.setGuarding( player.getUniqueId() );

            // Added standard bodyguard equipment
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.CHAINMAIL_BOOTS, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.CHAINMAIL_HELMET, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.GOLDEN_CHESTPLATE, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.CHAINMAIL_LEGGINGS, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.HAND, new ItemStack( Material.BOW, 1 ) );

            LOGGER.fine( "Spawned NPC Type:" + MinecraftAiConstants.BODYGUARD );
            LOGGER.info( "Guarding player ID: " + player.getUniqueId() );

        } else if ( npcType.equalsIgnoreCase( MinecraftAiConstants.FOOT_SOLDIER ) )
        {
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.CHAINMAIL_BOOTS, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.CHAINMAIL_HELMET, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.CHAINMAIL_CHESTPLATE, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.CHAINMAIL_LEGGINGS, 1 ) );
            npc.getOrAddTrait( Equipment.class ).set( Equipment.EquipmentSlot.HAND, new ItemStack( Material.IRON_SWORD, 1 ) );

            LOGGER.fine( "Spawned NPC Type:" + MinecraftAiConstants.FOOT_SOLDIER );
        }

        // Get the Owner trait and set the owner
        Owner ownerTrait = npc.getOrAddTrait( Owner.class );
        ownerTrait.setOwner( player.getUniqueId() );

        return npc;
    }
}
