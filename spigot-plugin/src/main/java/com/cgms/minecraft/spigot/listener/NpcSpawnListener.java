package com.cgms.minecraft.spigot.listener;

import com.cgms.minecraft.spigot.util.MinecraftAiConstants;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Equipment;
import net.citizensnpcs.api.trait.trait.Inventory;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;

public class NpcSpawnListener implements Listener
{
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger( NpcSpawnListener.class );
    @EventHandler
    public void onNpcSpawn( NPCSpawnEvent event)
    {
        LOGGER.info("*** NPC Spawned ***");
        NPC npc = event.getNPC();

        if ( npc.getName() != null )
        {
            if( npc.getName().contains( MinecraftAiConstants.ARMORED_KNIGHT ) )
            {
                LOGGER.info( "Found Knight...Setting equipment." );
                this.setNpcKnightEquipment( npc );
            }

            if( npc.getName().contains( MinecraftAiConstants.FOOT_SOLDIER ) )
            {
                LOGGER.info( "Found Foot Soldier...Setting equipment." );
                this.setNpcFootSoldierEquipment( npc );
            }

            if( npc.getName().contains( MinecraftAiConstants.BODYGUARD ) )
            {
                LOGGER.info( "Found Bodyguard...Setting equipment." );
                this.setNpcBodyguardEquipment( npc );
            }

            if( npc.getName().contains( MinecraftAiConstants.ARCHER ) )
            {
                LOGGER.info( "Found Archer...Setting equipment." );
                this.setNpcArcherEquipment( npc );
            }
        }
    }

    private void setNpcKnightEquipment( NPC npc )
    {
        Equipment equipment = npc.getOrAddTrait( Equipment.class );
        Inventory npcInventoryTrait = npc.getOrAddTrait( Inventory.class );

        equipment.set( Equipment.EquipmentSlot.HAND, new ItemStack( Material.IRON_SWORD ) );
        equipment.set( Equipment.EquipmentSlot.OFF_HAND, new ItemStack( Material.SHIELD ) );
        equipment.set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.IRON_BOOTS ) );
        equipment.set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.IRON_HELMET ) );
        equipment.set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.IRON_CHESTPLATE ) );
        equipment.set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.IRON_LEGGINGS ) );
    }

    private void setNpcArcherEquipment( NPC npc )
    {
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

    private void setNpcBodyguardEquipment( NPC npc )
    {
        Equipment equipment = npc.getOrAddTrait( Equipment.class );

        equipment.set( Equipment.EquipmentSlot.HAND, new ItemStack( Material.IRON_SWORD ) );
        equipment.set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.IRON_BOOTS ) );
        equipment.set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.GOLDEN_HELMET ) );
        equipment.set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.GOLDEN_CHESTPLATE ) );
        equipment.set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.IRON_LEGGINGS ) );
    }

    private void setNpcFootSoldierEquipment( NPC npc )
    {
        Equipment equipment = npc.getOrAddTrait( Equipment.class );

        equipment.set( Equipment.EquipmentSlot.HAND, new ItemStack( Material.IRON_SWORD ) );
        equipment.set( Equipment.EquipmentSlot.BOOTS, new ItemStack( Material.IRON_BOOTS ) );
        equipment.set( Equipment.EquipmentSlot.HELMET, new ItemStack( Material.COPPER_HELMET ) );
        equipment.set( Equipment.EquipmentSlot.CHESTPLATE, new ItemStack( Material.COPPER_CHESTPLATE ) );
        equipment.set( Equipment.EquipmentSlot.LEGGINGS, new ItemStack( Material.IRON_LEGGINGS ) );
    }
}
