/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.empire;

import java.util.List;

public class EmpireUnitGroup
{
    private String groupName;
    private List<EmpireUnit> units;

    public EmpireUnitGroup( String groupName, List<EmpireUnit> units )
    {
        this.groupName = groupName;
        this.units = units;
    }

    public EmpireUnitGroup()
    {

    }

    public String getGroupName()
    {
        return groupName;
    }

    public void setGroupName(String groupName)
    {
        this.groupName = groupName;
    }

    public List<EmpireUnit> getUnits()
    {
        return units;
    }

    public void setUnits( List<EmpireUnit> units )
    {
        this.units = units;
    }
}
