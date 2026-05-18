/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.empire;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonIdentityInfo (
        generator = ObjectIdGenerators.UUIDGenerator.class,
        property = "ObjectID")
public class Empire
{
    private int id;
    private String name;
    private String leaderName;
    private String leaderUUID;
    private EmpireLeaderEntityType leaderType;
    private List<Empire> empireEnemyList;
    private List<Empire> empireAllyList;
    private List<EmpireTerritory> territoryList;
    private List<EmpireNPC> empireNPCList;
    private Map< String, List<EmpireNPC> > unitGroupNameToUnitListMap;

    public Empire()
    {
        this.empireEnemyList = new ArrayList<>();
        this.empireAllyList = new ArrayList<>();
        this.unitGroupNameToUnitListMap = new HashMap<>();
        this.territoryList = new ArrayList<>();
        this.empireNPCList = new ArrayList<>();
    }

    public Empire( int empireId, String empireName, String leaderName, String leaderUUID,
                   EmpireLeaderEntityType leaderType, List<Empire> empireEnemyList, List<Empire> empireAllyList,
                   List<EmpireTerritory> territoryList, List<EmpireNPC> empireNPCList, Map<String,
                   List<EmpireNPC>> unitGroupNameToUnitListMap
    )
    {
        this.id = id;
        this.name = empireName;
        this.leaderName = leaderName;
        this.leaderUUID = leaderUUID;
        this.leaderType = leaderType;
        this.empireEnemyList = empireEnemyList;
        this.empireAllyList = empireAllyList;
        this.territoryList = territoryList;
        this.empireNPCList = empireNPCList;
        this.unitGroupNameToUnitListMap = unitGroupNameToUnitListMap;
    }

    public int getId()
    {
        return this.id;
    }

    public void setId( int id )
    {
        this.id = id;
    }

    public String getName()
    {
        return this.name;
    }

    public void setName( String name )
    {
        this.name = name;
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

    public List<Empire> getEnemyList()
    {
        return empireEnemyList;
    }

    public void setEnemyList( List<Empire> empireEnemyList )
    {
        this.empireEnemyList = empireEnemyList;
    }

    public List<Empire> getAllyList()
    {
        return empireAllyList;
    }

    public void setAllyList( List<Empire> empireAllyList )
    {
        this.empireAllyList = empireAllyList;
    }

    public List<EmpireTerritory> getTerritoryList()
    {
        return territoryList;
    }

    public void setTerritoryList( List<EmpireTerritory> territoryList )
    {
        this.territoryList = territoryList;
    }

    public List<EmpireNPC> getEmpireNPCList()
    {
        return empireNPCList;
    }

    public void setEmpireNPCList( List<EmpireNPC> empireNPCList )
    {
        this.empireNPCList = empireNPCList;
    }

    public Map<String, List<EmpireNPC>> getUnitGroupNameToUnitListMap()
    {
        return unitGroupNameToUnitListMap;
    }

    public void setUnitGroupNameToUnitListMap( Map<String, List<EmpireNPC>> unitGroupNameToUnitListMap )
    {
        this.unitGroupNameToUnitListMap = unitGroupNameToUnitListMap;
    }
}
