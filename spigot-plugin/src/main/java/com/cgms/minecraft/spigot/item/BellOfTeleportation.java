/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.item;

import com.cgms.minecraft.spigot.plugin.MinecraftEmpiresConstants;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@JsonIdentityInfo (
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "ObjectID")
public class BellOfTeleportation implements Serializable
{
    private static final long serialVersionUID = 1L;

    private String uuid;
    private BellOfTeleportation entangledBellOfTeleportation;
    private BellOfTeleportationBlock bellOfTeleportationBlock;


    public BellOfTeleportation()
    {
        this.uuid = UUID.randomUUID().toString();
    }

    public BellOfTeleportation( String uuid)
    {
        this.uuid = uuid;
    }



    public boolean canTeleport()
    {
        if( this.entangledBellOfTeleportation != null )
        {
            if ( this.getEntangledBellOfTeleportation().getBellOfTeleportationBlock() != null )
            {
                return true;
            }
        }

        return false;
    }

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid( @NonNull String uuid )
    {
        this.uuid = uuid;
    }

    public Location determineSpigotLocationOfPlacedBell()
    {
        if( this.bellOfTeleportationBlock != null)
        {
            return new Location( Bukkit.getWorld( this.bellOfTeleportationBlock.getWorldName() ), this.bellOfTeleportationBlock.getLocationX(),
                                 this.bellOfTeleportationBlock.getLocationY(), this.bellOfTeleportationBlock.getLocationZ()
            );
        }

        return null;
    }


    public void clearLocationOfPlacedBell( )
    {
        this.bellOfTeleportationBlock = null;
    }


    public BellOfTeleportation getEntangledBellOfTeleportation()
    {
        return entangledBellOfTeleportation;
    }


    public void setEntangledBellOfTeleportation( BellOfTeleportation entangledBellOfTeleportation )
    {
        this.entangledBellOfTeleportation = entangledBellOfTeleportation;
    }

    public BellOfTeleportationBlock getBellOfTeleportationBlock()
    {
        return bellOfTeleportationBlock;
    }

    public void setBellOfTeleportationBlock( BellOfTeleportationBlock bellOfTeleportationBlock )
    {
        this.bellOfTeleportationBlock = bellOfTeleportationBlock;
    }

    public void configureBellOfTeleportationBlockFromSpigotBlock( Block bellOfTeleportationBlock )
    {
        this.bellOfTeleportationBlock = new BellOfTeleportationBlock( bellOfTeleportationBlock );
    }

    @Transient
    public ItemStack getSpigotItemStack()
    {
        ItemStack itemStack = new ItemStack( Material.BELL, 1 );

        ItemMeta bellMeta = itemStack.getItemMeta();
        bellMeta.setItemName( MinecraftEmpiresConstants.BELLS_OF_TELEPORTATION );
        bellMeta.setDisplayName( MinecraftEmpiresConstants.BELLS_OF_TELEPORTATION );

        return itemStack;
    }

    @Override
    public boolean equals( Object o )
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BellOfTeleportation that = (BellOfTeleportation) o;

        return Objects.equals( uuid, that.uuid ) && Objects.equals( entangledBellOfTeleportation, that.entangledBellOfTeleportation ) &&
                Objects.equals( bellOfTeleportationBlock, that.bellOfTeleportationBlock );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( uuid, entangledBellOfTeleportation, bellOfTeleportationBlock );
    }
}
