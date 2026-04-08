/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 */

package com.cgms.minecraft.empires.dto;

public class InventoryItem
{
    private ItemType type;
    private int amount;

    public InventoryItem()
    {}


    public InventoryItem( ItemType type, int amount )
    {
        this.type = type;
        this.amount = amount;
    }

    public ItemType getType()
    {
        return type;
    }

    public int getAmount()
    {
        return amount;
    }
}
