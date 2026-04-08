/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.empire;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.List;
import java.util.Map;

@JsonIdentityInfo (
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "ObjectID")
public class Empire
{
    private String empireUUID;
    private String empireName;
    private String leaderName;
    private String leaderUUID;
    private EmpireLeaderEntityType leaderType;
    private List<Enemy> enemyList;
    private List<Ally> allyList;
    private List<EmpireTerritory> territoryList;
    private List<EmpireUnit> unitList;
    private Map< String, List<EmpireUnit> > unitGroupNameToUnitListMap;

    public Empire()
    {
    }

    public Empire( String empireUUID, String empireName, String leaderName, String leaderUUID, EmpireLeaderEntityType leaderType, List<Enemy> enemyList, List<Ally> allyList, List<EmpireTerritory> territoryList, List<EmpireUnit> unitList, Map<String, List<EmpireUnit>> unitGroupNameToUnitListMap )
    {
        this.empireUUID = empireUUID;
        this.empireName = empireName;
        this.leaderName = leaderName;
        this.leaderUUID = leaderUUID;
        this.leaderType = leaderType;
        this.enemyList = enemyList;
        this.allyList = allyList;
        this.territoryList = territoryList;
        this.unitList = unitList;
        this.unitGroupNameToUnitListMap = unitGroupNameToUnitListMap;
    }

    public String getEmpireUUID()
    {
        return empireUUID;
    }

    public void setEmpireUUID( String empireUUID )
    {
        this.empireUUID = empireUUID;
    }

    public String getEmpireName()
    {
        return empireName;
    }

    public void setEmpireName( String empireName )
    {
        this.empireName = empireName;
    }

    public String getLeaderName()
    {
        return leaderName;
    }

    public void setLeaderName( String leaderName )
    {
        this.leaderName = leaderName;
    }

    public String getLeaderUUID()
    {
        return leaderUUID;
    }

    public void setLeaderUUID( String leaderUUID )
    {
        this.leaderUUID = leaderUUID;
    }

    public EmpireLeaderEntityType getLeaderType()
    {
        return leaderType;
    }

    public void setLeaderType( EmpireLeaderEntityType leaderType )
    {
        this.leaderType = leaderType;
    }

    public List<Enemy> getEnemyList()
    {
        return enemyList;
    }

    public void setEnemyList( List<Enemy> enemyList )
    {
        this.enemyList = enemyList;
    }

    public List<Ally> getAllyList()
    {
        return allyList;
    }

    public void setAllyList( List<Ally> allyList )
    {
        this.allyList = allyList;
    }

    public List<EmpireTerritory> getTerritoryList()
    {
        return territoryList;
    }

    public void setTerritoryList( List<EmpireTerritory> territoryList )
    {
        this.territoryList = territoryList;
    }

    public List<EmpireUnit> getUnitList()
    {
        return unitList;
    }

    public void setUnitList( List<EmpireUnit> unitList )
    {
        this.unitList = unitList;
    }

    public Map<String, List<EmpireUnit>> getUnitGroupNameToUnitListMap()
    {
        return unitGroupNameToUnitListMap;
    }

    public void setUnitGroupNameToUnitListMap( Map<String, List<EmpireUnit>> unitGroupNameToUnitListMap )
    {
        this.unitGroupNameToUnitListMap = unitGroupNameToUnitListMap;
    }
}
