/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.plugin;


import lombok.NonNull;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NpcFactory
{
    private static final Logger LOGGER = LoggerFactory.getLogger( NpcFactory.class.getName() );

    private NpcFactory() {}

    public static NPC spawnNPC( @NonNull String entityType, @NonNull Location location, @NonNull String npcType, @NonNull Player player, String name )
    {
        NPC npc = deriveNPC( entityType, name );
        npc.spawn( location );
        npc.setFlyable( false );
        npc.setProtected( false );

        // Get the Owner trait and set the owner
        Owner ownerTrait = npc.getOrAddTrait( Owner.class );
        ownerTrait.setOwner( player.getUniqueId() );

        Inventory npcInventoryTrait = npc.getOrAddTrait(Inventory.class);
        SentinelTrait sentinelTrait = npc.getOrAddTrait( SentinelTrait.class );
        sentinelTrait.fightback = true;
        sentinelTrait.invincible = false;
        sentinelTrait.allowKnockback = true;
        sentinelTrait.health = 20.0;
        sentinelTrait.damage = -1.0;


        if( npcType.equalsIgnoreCase( MinecraftEmpiresConstants.NPC_ARMORED_KNIGHT_TYPE ) )
        {
            npc.setName( MinecraftEmpiresConstants.NPC_ARMORED_KNIGHT_TYPE + " For "+ player.getName() );
            setNpcKnightEquipment( npc );

        } else if ( npcType.equalsIgnoreCase( MinecraftEmpiresConstants.NPC_ARCHER_TYPE ) )
        {
            npc.setName( MinecraftEmpiresConstants.NPC_ARCHER_TYPE + " For "+ player.getName() );
            setNpcArcherEquipment( npc );

            // Add arrows to the NPC's inventory
            for ( int i = 9; i < 18; i++ )
            {
                npcInventoryTrait.setItem( i, new ItemStack( Material.ARROW, 64) );
            }

            LOGGER.debug( "Creating NPC Type:" + MinecraftEmpiresConstants.NPC_ARCHER_TYPE );

        } else if ( npcType.equalsIgnoreCase( MinecraftEmpiresConstants.NPC_BODYGUARD_TYPE ) )
        {
            // Set NPC to guard player that created it.
            sentinelTrait.setGuarding( player.getUniqueId() );

            npc.setName( MinecraftEmpiresConstants.NPC_BODYGUARD_TYPE + " For "+ player.getName() );
            setNpcBodyguardEquipment( npc );

        } else if ( npcType.equalsIgnoreCase( MinecraftEmpiresConstants.NPC_FOOT_SOLDIER_TYPE ) )
        {
            npc.setName( MinecraftEmpiresConstants.NPC_FOOT_SOLDIER_TYPE + " For "+ player.getName() );
            setNpcFootSoldierEquipment( npc );
        }

        return npc;
    }

    public static void setNpcKnightEquipment( NPC npc )
    {
        npc.setProtected( false );
        Equipment equipment = npc.getOrAddTrait( Equipment.class );
        Inventory npcInventoryTrait = npc.getOrAddTrait( Inventory.class );

        equipment.set( Equipment.EquipmentSlot.HAND, new ItemStack( Material.IRON_SWORD ) );
        equipment.set( Equipment.EquipmentSlot.OFF_HAND, new ItemStack( Material.SHIELD ) );
        equipment.set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.IRON_BOOTS ) );
        equipment.set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.IRON_HELMET ) );
        equipment.set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.IRON_CHESTPLATE ) );
        equipment.set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.IRON_LEGGINGS ) );
    }

    public static void setNpcArcherEquipment( NPC npc )
    {
        npc.setProtected( false );
        Equipment equipment = npc.getOrAddTrait( Equipment.class );
        Inventory npcInventoryTrait = npc.getOrAddTrait( Inventory.class );

        equipment.set( Equipment.EquipmentSlot.HAND, new ItemStack( Material.BOW ) );
        equipment.set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.LEATHER_BOOTS ) );
        equipment.set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.CHAINMAIL_HELMET ) );
        equipment.set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.LEATHER_CHESTPLATE ) );
        equipment.set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.LEATHER_LEGGINGS ) );

        // Add arrows to the NPC's inventory
        for ( int i = 9; i < 18; i++ )
        {
            npcInventoryTrait.setItem( i, new ItemStack( Material.ARROW, 64) );
        }
    }

    public static void setNpcBodyguardEquipment( NPC npc )
    {
        npc.setProtected( false );
        Equipment equipment = npc.getOrAddTrait( Equipment.class );

        equipment.set( Equipment.EquipmentSlot.HAND, new ItemStack( Material.IRON_SWORD ) );
        equipment.set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.IRON_BOOTS ) );
        equipment.set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.GOLDEN_HELMET ) );
        equipment.set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.GOLDEN_CHESTPLATE ) );
        equipment.set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.IRON_LEGGINGS ) );
    }

    public static void setNpcFootSoldierEquipment( NPC npc )
    {
        npc.setProtected( false );

        Equipment equipment = npc.getOrAddTrait( Equipment.class );

        equipment.set( Equipment.EquipmentSlot.HAND, new ItemStack( Material.IRON_SWORD ) );
        equipment.set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.IRON_BOOTS ) );
        equipment.set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.COPPER_HELMET ) );
        equipment.set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.COPPER_CHESTPLATE ) );
        equipment.set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.IRON_LEGGINGS ) );
    }

    private static NPC deriveNPC( @NonNull String entityType, @NonNull String name )
    {
        NPC npc = null;

        if( entityType.equalsIgnoreCase( MinecraftEmpiresConstants.ENTITY_PLAYER_TYPE ) )
        {
            npc = CitizensAPI.getNPCRegistry().createNPC( EntityType.PLAYER, name);

        } else if( entityType.equalsIgnoreCase( MinecraftEmpiresConstants.ENTITY_ZOMBIE_TYPE ) )
        {
            npc = CitizensAPI.getNPCRegistry().createNPC( EntityType.ZOMBIE, name);

        } else if( entityType.equalsIgnoreCase( MinecraftEmpiresConstants.ENTITY_SKELETON_TYPE ) )
        {
            npc = CitizensAPI.getNPCRegistry().createNPC( EntityType.SKELETON, name);
        }

        return npc;
    }

    private static void deriveEnemyTargets( SentinelTrait sentinelTrait, boolean isEvil)
    {
        if( isEvil )
        {
            sentinelTrait.addTarget( "npc:" + MinecraftEmpiresConstants.NPC_ARMORED_KNIGHT_TYPE + " For Enemy");

        } else
        {
            sentinelTrait.addTarget( "monsters" );
            sentinelTrait.addTarget( "entity:pillager" );
        }
    }
}
