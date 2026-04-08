/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.item;

public class Building
{
    public static final String BUILDING_TYPE_TOWN_CENTER = "Town Center";
    public static final String BUILDING_TYPE_SMALL_HOUSE = "Small House";
    public static final String BUILDING_TYPE_BARRACKS = "Barracks";
    public static final String BUILDING_TYPE_FARM = "Farm";
    public static final String BUILDING_TYPE_WOOD_FARM = "Wood Farm";
    public static final String BUILDING_TYPE_STONE_MINE = "Stone Mine";
    public static final String BUILDING_TYPE_IRON_MINE = "Iron Mine";
    public static final String BUILDING_TYPE_GOLD_MINE = "Gold Mine";
    public static final String BUILDING_TYPE_COPPER_MINE = "Copper Mine";

    private String uuid;

    public String getUuid()
    {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }


}
