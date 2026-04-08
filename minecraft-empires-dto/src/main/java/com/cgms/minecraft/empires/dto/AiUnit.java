/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 */

package com.cgms.minecraft.empires.dto;

import java.util.List;

public class AiUnit
{
    private UnitType type;
    private boolean canAttack;
    private int health;
    private int maxHealth;
    private int armor;
    private int maxArmor;
    private List<InventoryItem> inventory;

    public AiUnit( UnitType type, int health, int maxHealth, int armor, int maxArmor, List<InventoryItem> inventory )
    {
        this.type = type;
        this.health = health;
        this.maxHealth = maxHealth;
        this.armor = armor;
        this.maxArmor = maxArmor;
        this.inventory = inventory;
    }

    public AiUnit(){}

    public UnitType getType()
    {
        return type;
    }

    public void setType( UnitType type )
    {
        this.type = type;
    }

    public int getHealth()
    {
        return health;
    }

    public void setHealth( int health )
    {
        this.health = health;
    }

    public int getMaxHealth()
    {
        return maxHealth;
    }

    public void setMaxHealth( int maxHealth )
    {
        this.maxHealth = maxHealth;
    }

    public int getArmor()
    {
        return armor;
    }

    public void setArmor( int armor )
    {
        this.armor = armor;
    }

    public int getMaxArmor()
    {
        return maxArmor;
    }

    public void setMaxArmor( int maxArmor )
    {
        this.maxArmor = maxArmor;
    }

    public List<InventoryItem> getInventory()
    {
        return inventory;
    }

    public void setInventory( List<InventoryItem> inventory )
    {
        this.inventory = inventory;
    }
}
