/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.empire;


import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Inventory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class NpcEquipmentUtility
{
    private static final Logger LOGGER = LoggerFactory.getLogger( NpcEquipmentUtility.class.getName() );

    private NpcEquipmentUtility() {}

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
}
