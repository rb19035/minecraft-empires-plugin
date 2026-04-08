/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.item;

import java.io.Serializable;
import java.util.Objects;

public class BellOfTeleportationItemStack implements Serializable
{

    private int spigotItemStackHashCode;

    public BellOfTeleportationItemStack()
    {
    }

    public BellOfTeleportationItemStack( int spigotItemStackHashCode )
    {
        this.spigotItemStackHashCode = spigotItemStackHashCode;
    }

    public int getSpigotItemStackHashCode()
    {
        return spigotItemStackHashCode;
    }

    public void setSpigotItemStackHashCode( int spigotItemStackHashCode )
    {
        this.spigotItemStackHashCode = spigotItemStackHashCode;
    }

    @Override
    public boolean equals( Object o )
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BellOfTeleportationItemStack that = (BellOfTeleportationItemStack) o;

        return spigotItemStackHashCode == that.spigotItemStackHashCode;
    }

    @Override
    public int hashCode()
    {
        return Objects.hashCode( spigotItemStackHashCode );
    }
}
