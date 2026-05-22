/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.empire;

import com.cgms.minecraft.spigot.plugin.EntityType;
import com.cgms.minecraft.spigot.plugin.NpcType;
import lombok.NonNull;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Inventory;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;
import org.mcmonkey.sentinel.SentinelTrait;

import java.util.UUID;

public class EmpireNPC
{
    private int id;
    private String name;
    private EntityType entityType;
    private NpcType npcType;
    private Empire empire;
    private String uuid;
    private NPC npc;


    public EmpireNPC(
            @NonNull String name,
            @NonNull EntityType entityType,
            @NonNull NpcType npcType,
            @NonNull Empire empire
    )
    {
        this.name = name;
        this.entityType = entityType;
        this.npcType = npcType;
        this.empire = empire;

        this.npc = this.deriveNPC();
        this.uuid = npc.getUniqueId().toString();
    }

    public EmpireNPC(
            @NonNull String name,
            @NonNull EntityType entityType,
            @NonNull NpcType npcType,
            @NonNull Empire empire,
            @NonNull NPC npc
    )
    {
        this.name = name;
        this.entityType = entityType;
        this.npcType = npcType;
        this.empire = empire;
        this.uuid = npc.getUniqueId().toString();
        this.npc = npc;
    }

    public EmpireNPC()
    {
    }

    public void spawn( Location location )
    {
        this.npc.spawn( location );
        this.npc.setFlyable( false );
        this.npc.setProtected( false );
        this.npc.setName( this.getName() );

        Owner ownerTrait = npc.getOrAddTrait( Owner.class );
        ownerTrait.setOwner( Bukkit.getPlayer( this.empire.getLeaderUUID() ) );

        Inventory npcInventoryTrait = this.npc.getOrAddTrait(Inventory.class);

        if ( this.npcType.equals( NpcType.VILLAGER ) )
        {
            Villager villager = (Villager) this.npc.getEntity();
            villager.setProfession( Villager.Profession.FARMER );
            villager.setVillagerLevel( 2 );
            villager.setAI( true );
            villager.setCanPickupItems( true );
            villager.setBreed( true );
            this.npc.data().set( NPC.Metadata.USE_MINECRAFT_AI, true );

        } else
        {
            SentinelTrait sentinelTrait = npc.getOrAddTrait( SentinelTrait.class );
            sentinelTrait.fightback = true;
            sentinelTrait.invincible = false;
            sentinelTrait.allowKnockback = true;
            sentinelTrait.health = 20.0;
            sentinelTrait.damage = -1.0;
            this.deriveEnemyTargets( sentinelTrait, false );

            if( this.npcType.equals( NpcType.ARMORED_KNIGHT ) )
            {
                NpcEquipmentUtility.setNpcKnightEquipment( this.npc );

            } else if ( this.npcType.equals( NpcType.ARCHER ) )
            {
                NpcEquipmentUtility.setNpcArcherEquipment( this.npc );

                // Add arrows to the NPC's inventory
                for ( int i = 9; i < 18; i++ )
                {
                    npcInventoryTrait.setItem( i, new ItemStack( Material.ARROW, 64) );
                }

            } else if ( this.npcType.equals( NpcType.BODY_GUARD ) )
            {
                // Set NPC to guard player that created it.
                sentinelTrait.setGuarding( UUID.fromString( this.empire.getLeaderUUID() ) );
                NpcEquipmentUtility.setNpcBodyguardEquipment( npc );

            } else if ( this.npcType.equals( NpcType.FOOT_SOLDIER ) )
            {
                NpcEquipmentUtility.setNpcFootSoldierEquipment( npc );
            }
        }
    }

    public int getId()
    {
        return id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public EntityType getEntityType()
    {
        return entityType;
    }

    public void setEntityType( EntityType entityType )
    {
        this.entityType = entityType;
    }

    public NpcType getNpcType()
    {
        return npcType;
    }

    public void setNpcType( NpcType npcType )
    {
        this.npcType = npcType;
    }

    public Empire getEmpire()
    {
        return empire;
    }

    public void setEmpire( Empire empire )
    {
        this.empire = empire;
    }

    public NPC getNpc()
    {
        return npc;
    }

    public void setNpc( NPC npc )
    {
        this.npc = npc;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid( String uuid )
    {
        this.uuid = uuid;
    }

    private NPC deriveNPC()
    {
        NPC npc = null;

        if( this.entityType.equals( EntityType.PLAYER ) )
        {
            npc = CitizensAPI.getNPCRegistry().createNPC( org.bukkit.entity.EntityType.PLAYER, this.name );

        } else if( entityType.equals( EntityType.ZOMBIE ) )
        {
            npc = CitizensAPI.getNPCRegistry().createNPC( org.bukkit.entity.EntityType.ZOMBIE, this.name );

        } else if( entityType.equals( EntityType.SKELETON ) )
        {
            npc = CitizensAPI.getNPCRegistry().createNPC( org.bukkit.entity.EntityType.SKELETON, this.name );

        } else if( entityType.equals( EntityType.VILLAGER ) )
        {
            npc = CitizensAPI.getNPCRegistry().createNPC( org.bukkit.entity.EntityType.VILLAGER, this.name );
        }

        return npc;
    }

    private void deriveEnemyTargets( @NonNull SentinelTrait sentinelTrait, boolean isEvil)
    {
        for ( Empire enemy : this.empire.getEnemyList() )
        {
            sentinelTrait.addTarget( "uuid:" + empire.getLeaderUUID() );
            sentinelTrait.addTarget( this.empire.getLeaderName() + ": Villager" );
            sentinelTrait.addTarget( this.empire.getLeaderName() + ": Knight" );
            sentinelTrait.addTarget( this.empire.getLeaderName() + ": Archer" );
            sentinelTrait.addTarget( this.empire.getLeaderName() + ": Bodyguard" );
            sentinelTrait.addTarget( this.empire.getLeaderName() + ": Soldier" );
        }

        sentinelTrait.addTarget( "monsters" );
        sentinelTrait.addTarget( "entity:pillager" );
    }
}
