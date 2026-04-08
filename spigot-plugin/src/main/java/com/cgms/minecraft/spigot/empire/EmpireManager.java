/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.empire;

import com.cgms.minecraft.spigot.Exception.EmpireAlreadyExistsException;
import com.cgms.minecraft.spigot.Exception.PlayerMaxEmpiresException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.NonNull;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import net.citizensnpcs.api.npc.NPCRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmpireManager
{
    private static final EmpireManager INSTANCE = new EmpireManager();
    private static final String EMPIRES_FILE = "./plugins/Empires/empires.json";
    private static final File EMPIRES_JSON_FILE = new File( EMPIRES_FILE );
    private static final Logger LOGGER = LoggerFactory.getLogger( EmpireManager.class );
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private final Map<String, Empire> empireNameToEmpireMap;
    private final Map<String, Empire> playerMameToEmpireNameMap;

    public static EmpireManager getInstance()
    {
        return INSTANCE;
    }

    private EmpireManager()
    {
        this.empireNameToEmpireMap = new ConcurrentHashMap<>(10);
        this.playerMameToEmpireNameMap = new ConcurrentHashMap<>(10);

        OBJECT_MAPPER.enable( SerializationFeature.INDENT_OUTPUT );

        if( EMPIRES_JSON_FILE.exists() )
        {
            try
            {
                Empire[] empireArray = OBJECT_MAPPER.readValue( EMPIRES_JSON_FILE, Empire[].class );
                for ( Empire e : empireArray )
                {
                    this.empireNameToEmpireMap.put( e.getEmpireName(), e );
                    this.playerMameToEmpireNameMap.put( e.getLeaderName(), e );
                }
            }
            catch ( Exception e )
            {
                LOGGER.error( "Failed to read empires.json file.", e );
                throw new RuntimeException( "Failed to read empires.json file.", e );
            }
        }
    }

    public Empire createEmpire( @NonNull String empireName, @NonNull NPC leader ) throws EmpireAlreadyExistsException, PlayerMaxEmpiresException
    {
        LOGGER.debug( "Creating empire {} for player {}", empireName, leader.getName() );

        synchronized ( this )
        {
            Empire empire = this.empireNameToEmpireMap.get( empireName );
            if ( empire != null )
            {
                throw new EmpireAlreadyExistsException( "Empire with name " + empireName + " already exists!" );
            }

            empire = this.playerMameToEmpireNameMap.get( leader.getName() );
            if ( empire != null )
            {
                throw new PlayerMaxEmpiresException( "Player " + leader.getName() + " already has an empire!" );
            }

            empire = new Empire();
            empire.setEmpireName( empireName );
            empire.setLeaderName( leader.getName() );
            empire.setLeaderUUID( leader.getUniqueId().toString() );

            this.empireNameToEmpireMap.put( empireName, empire );
            this.playerMameToEmpireNameMap.put( leader.getName(), empire );

            this.saveEmpireData();

            return empire;
        }
    }

    public void removeEmpiresForPlayer( @NonNull String playerName )
    {
        this.removeEmpire( this.playerMameToEmpireNameMap.get( playerName ) );
        this.playerMameToEmpireNameMap.remove( playerName );
    }

    public void removeEmpire( @NonNull String empireName )
    {
        this.removeEmpire( this.empireNameToEmpireMap.get( empireName ) );
    }

    public void removeEmpire( @NonNull Empire empire )
    {
        LOGGER.debug( "Removing empire {}", empire.getEmpireName() );

        synchronized ( this )
        {
            this.empireNameToEmpireMap.remove( empire.getEmpireName() );
            this.playerMameToEmpireNameMap.remove( empire.getLeaderName() );

            NPCRegistry npcRegistry = CitizensAPI.getNPCRegistry();
            for( NPC npc : npcRegistry )
            {
                if( npc.getName().contains( empire.getLeaderName() ) )
                {
                    npc.destroy();
                }
            }

            this.saveEmpireData();
        }
    }

    private void saveEmpireData()
    {
        LOGGER.debug( "Saving empire data to disk." );

        synchronized ( this )
        {
            try
            {
                OBJECT_MAPPER.writeValue( EMPIRES_JSON_FILE, this.empireNameToEmpireMap.values().stream().toList() );
            }
            catch ( IOException e )
            {
                LOGGER.error( "Error serializing empires to file: {}", e.getMessage() );
                throw new RuntimeException( e );
            }
        }
    }
}
