/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.empire;

import java.util.List;

public class Enemy
{
    private List<Empire> enemies;

    public Enemy( List<Empire> enemies )
    {
        this.enemies = enemies;
    }

    public Enemy()
    {

    }

    public List<Empire> getEnemies()
    {
        return enemies;
    }

    public void setEnemies( List<Empire> enemies )
    {
        this.enemies = enemies;
    }
}
