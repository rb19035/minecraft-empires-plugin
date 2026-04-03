package com.cgms.minecraft.spigot.util;


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

    public static NPC spawnNPC( @NonNull Location location, @NonNull String npcType, @NonNull Player player, String name )
    {
        NPC npc = CitizensAPI.getNPCRegistry().createNPC( EntityType.PLAYER, name);
        npc.spawn( location );

        npc.setFlyable( false );
        npc.setProtected( false );

        Equipment equipment = npc.getOrAddTrait( Equipment.class );
        Inventory npcInventoryTrait = npc.getOrAddTrait(Inventory.class);
        SentinelTrait sentinelTrait = npc.getOrAddTrait( SentinelTrait.class );
        sentinelTrait.fightback = true;
        sentinelTrait.invincible = false;
        sentinelTrait.allowKnockback = true;
        sentinelTrait.addTarget( "monsters" );
        sentinelTrait.addTarget( "entity:pillager" );

        if( npcType.equalsIgnoreCase( MinecraftAiConstants.ARMORED_KNIGHT ) )
        {
            npc.setName( MinecraftAiConstants.ARMORED_KNIGHT + " For "+ player.getName() );

            equipment.set( Equipment.EquipmentSlot.HAND, new ItemStack( Material.IRON_SWORD ) );
            equipment.set( Equipment.EquipmentSlot.OFF_HAND, new ItemStack( Material.SHIELD ) );
            equipment.set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.IRON_BOOTS ) );
            equipment.set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.IRON_HELMET ) );
            equipment.set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.IRON_CHESTPLATE ) );
            equipment.set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.IRON_LEGGINGS ) );

            LOGGER.debug( "Creating NPC Type:" + MinecraftAiConstants.ARMORED_KNIGHT );

        } else if ( npcType.equalsIgnoreCase( MinecraftAiConstants.ARCHER ) )
        {
            npc.setName( MinecraftAiConstants.ARCHER + " For "+ player.getName() );
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

            LOGGER.debug( "Creating NPC Type:" + MinecraftAiConstants.ARCHER );

        } else if ( npcType.equalsIgnoreCase( MinecraftAiConstants.BODYGUARD ) )
        {
            // Set NPC to guard player that created it.
            sentinelTrait.setGuarding( player.getUniqueId() );
            npc.setName( MinecraftAiConstants.BODYGUARD + " For "+ player.getName() );
            equipment.set( Equipment.EquipmentSlot.HAND, new ItemStack( Material.IRON_SWORD ) );
            equipment.set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.IRON_BOOTS ) );
            equipment.set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.GOLDEN_HELMET ) );
            equipment.set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.GOLDEN_CHESTPLATE ) );
            equipment.set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.IRON_LEGGINGS ) );

            LOGGER.debug( "Creating NPC Type:" + MinecraftAiConstants.BODYGUARD );

        } else if ( npcType.equalsIgnoreCase( MinecraftAiConstants.FOOT_SOLDIER ) )
        {
            npc.setName( MinecraftAiConstants.FOOT_SOLDIER + " For "+ player.getName() );

            equipment.set( Equipment.EquipmentSlot.HAND, new ItemStack( Material.IRON_SWORD ) );
            equipment.set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.IRON_BOOTS ) );
            equipment.set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.COPPER_HELMET ) );
            equipment.set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.COPPER_CHESTPLATE ) );
            equipment.set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.IRON_LEGGINGS ) );

            LOGGER.debug( "Creating NPC Type:" + MinecraftAiConstants.FOOT_SOLDIER );
        }

        // Get the Owner trait and set the owner
        Owner ownerTrait = npc.getOrAddTrait( Owner.class );
        ownerTrait.setOwner( player.getUniqueId() );

        return npc;
    }
}
