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
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.Bukkit;
import org.bukkit.Location;

public class EmpireNPC
{
    private int id;
    private String name;
    private EntityType entityType;
    private NpcType npcType;
    private Empire empire;

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
        this.npc = npc;
    }

    public EmpireNPC()
    {
    }

    public void spawn( Location location )
    {
        this.npc = this.deriveNPC();
        this.npc.spawn( location );
        this.npc.setFlyable( false );
        this.npc.setProtected( false );

        Owner ownerTrait = npc.getOrAddTrait( Owner.class );
        ownerTrait.setOwner( Bukkit.getPlayer( this.empire.getLeaderUUID() ) );
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
}
