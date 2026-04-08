/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.item;

import java.util.List;

public class BellOfTeleportations
{
    public List<BellOfTeleportation> bells;

    public BellOfTeleportations()
    {
    }

    public BellOfTeleportations( List<BellOfTeleportation> bells )
    {
        this.bells = bells;
    }

    public List<BellOfTeleportation> getBells()
    {
        return bells;
    }

    public void setBells( List<BellOfTeleportation> bells )
    {
        this.bells = bells;
    }
}
