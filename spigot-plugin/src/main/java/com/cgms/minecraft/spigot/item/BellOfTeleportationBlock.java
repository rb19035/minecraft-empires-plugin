/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.item;

import org.bukkit.Location;
import org.bukkit.block.Block;

import java.io.Serializable;
import java.util.Objects;

public class BellOfTeleportationBlock implements Serializable
{
    private double locationX;
    private double locationY;
    private double locationZ;
    private String worldName;


    public BellOfTeleportationBlock()
    {
    }

    public BellOfTeleportationBlock( String worldName, double x, double y, double z )
    {
        this.locationX = x;
        this.locationY = y;
        this.locationZ = z;
        this.worldName = worldName;
    }

    public BellOfTeleportationBlock( Block block )
    {
        Location location = block.getLocation();

        this.locationX = location.getX();
        this.locationY = location.getY();
        this.locationZ = location.getZ();

        this.worldName = location.getWorld().getName();
    }

    public double getLocationX()
    {
        return locationX;
    }

    public void setLocationX( double locationX )
    {
        this.locationX = locationX;
    }

    public double getLocationY()
    {
        return locationY;
    }

    public void setLocationY( double locationY )
    {
        this.locationY = locationY;
    }

    public double getLocationZ()
    {
        return locationZ;
    }

    public void setLocationZ( double locationZ )
    {
        this.locationZ = locationZ;
    }

    public String getWorldName()
    {
        return worldName;
    }

    public void setWorldName( String worldName )
    {
        this.worldName = worldName;
    }

    @Override
    public boolean equals( Object o )
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BellOfTeleportationBlock that = (BellOfTeleportationBlock) o;
        return Double.compare( locationX, that.locationX ) == 0 && Double.compare( locationY, that.locationY ) == 0 && Double.compare( locationZ, that.locationZ ) == 0 && Objects.equals( worldName, that.worldName );
    }

    @Override
    public int hashCode()
    {
        return Objects.hash( locationX, locationY, locationZ, worldName );
    }
}
