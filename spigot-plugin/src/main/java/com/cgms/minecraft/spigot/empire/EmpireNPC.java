/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.empire;

import com.cgms.minecraft.spigot.plugin.EntityType;
import com.cgms.minecraft.spigot.plugin.NpcType;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.NonNull;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.trait.trait.Owner;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Objects;

public class EmpireNPC
{
    @JsonProperty
    private final EntityType entityType;

    @JsonProperty
    private final NpcType npcType;

    @JsonProperty
    private EmpireUnitLocation location;

    @JsonProperty
    private final String playerUUID;

    @JsonProperty
    private final String playerName;

    @JsonProperty
    private final String unitName;

    private NPC npc;

    @JsonCreator
    public EmpireNPC( @JsonProperty @NonNull EntityType entityType,
                      @JsonProperty @NonNull NpcType npcType,
                      @JsonProperty @NonNull EmpireUnitLocation location,
                      @JsonProperty @NonNull String playerUUID,
                      @JsonProperty @NonNull String playerName,
                      @JsonProperty @NonNull String unitName )
    {
        this.entityType = entityType;
        this.npcType = npcType;
        this.location = location;
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.unitName = unitName;

    }

    public EmpireNPC( @NonNull EntityType entityType,
                      @NonNull NpcType npcType,
                      @NonNull Location location,
                      @NonNull String playerUUID,
                      @NonNull String playerName,
                      @NonNull String unitName )
    {
        this.entityType = entityType;
        this.npcType = npcType;
        this.location = new EmpireUnitLocation( location );
        this.playerUUID = playerUUID;
        this.playerName = playerName;
        this.unitName = unitName;
    }

    public void spawnNPC()
    {
        this.npc = this.deriveNPC();
        this.npc.spawn( new Location( Bukkit.getWorld( this.location.getWorldName() ),
                this.location.getLocationX(),
                this.location.getLocationY(),
                this.location.getLocationZ() )
        );

        this.npc.setFlyable( false );
        this.npc.setProtected( false );

        Owner ownerTrait = npc.getOrAddTrait( Owner.class );
        ownerTrait.setOwner( Bukkit.getPlayer( this.playerUUID ) );
    }

    public NPC getNPC()
    {
        return npc;
    }


    public EntityType getEntityType()
    {
        return entityType;
    }

    public NpcType getNpcType()
    {
        return npcType;
    }

    public EmpireUnitLocation getLocation()
    {
        return location;
    }

    public String getPlayerUUID()
    {
        return playerUUID;
    }

    public String getPlayerName()
    {
        return playerName;
    }

    public String getUnitName()
    {
        return unitName;
    }

    public void setLocation( EmpireUnitLocation location )
    {
        this.location = location;
    }

    public void setLocation( double x, double y, double z, String worldName )
    {
        this.location = new EmpireUnitLocation( worldName, x, y, z );
    }

    public String toString()
    {
        return "EmpireUnit [entityType=" + entityType + ", npcType=" + npcType + ", location=" + location + ", playerUUID=" + playerUUID + ", playerName=" + playerName + ", unitName=" + unitName + "]";
    }

    @Override
    public boolean equals( Object o )
    {
        if ( !(o instanceof EmpireNPC empireNPC) )
        {
            return false;
        }
        return entityType == empireNPC.entityType && npcType == empireNPC.npcType && Objects.equals( location, empireNPC.location ) && Objects.equals( playerUUID, empireNPC.playerUUID ) && Objects.equals( playerName, empireNPC.playerName ) && Objects.equals( unitName, empireNPC.unitName ) && Objects.equals( npc, empireNPC.npc );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( entityType, npcType, location, playerUUID, playerName, unitName, npc );
    }

    private NPC deriveNPC()
    {
        NPC npc = null;

        if( this.entityType.equals( EntityType.PLAYER ) )
        {
            npc = CitizensAPI.getNPCRegistry().createNPC( org.bukkit.entity.EntityType.PLAYER, this.unitName );

        } else if( entityType.equals( EntityType.ZOMBIE ) )
        {
            npc = CitizensAPI.getNPCRegistry().createNPC( org.bukkit.entity.EntityType.ZOMBIE, this.unitName);

        } else if( entityType.equals( EntityType.SKELETON ) )
        {
            npc = CitizensAPI.getNPCRegistry().createNPC( org.bukkit.entity.EntityType.SKELETON, this.unitName );

        } else if( entityType.equals( EntityType.VILLAGER ) )
        {
            npc = CitizensAPI.getNPCRegistry().createNPC( org.bukkit.entity.EntityType.VILLAGER, this.unitName );
        }

        return npc;
    }
}
