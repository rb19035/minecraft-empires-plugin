/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.item;

import com.cgms.minecraft.spigot.plugin.MinecraftEmpiresConstants;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.NonNull;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BellOfTeleportationManager implements Serializable
{
    private static final Logger LOGGER = LoggerFactory.getLogger( BellOfTeleportationManager.class );
    private static final String BELL_CONFIG_WORLD_JSON_FILE_NAME = "./plugins/Empires/bellOfTeleportationUtil.json";
    private static final File BELL_CONFIG_WORLD_JSON_FILE = new File( BELL_CONFIG_WORLD_JSON_FILE_NAME );
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final BellOfTeleportationManager INSTANCE = new BellOfTeleportationManager();
    private final Map<String, BellOfTeleportation> uuidBellsOfTeleportationMap;

    private BellOfTeleportationManager()
    {
        this.uuidBellsOfTeleportationMap = new ConcurrentHashMap<>();
        OBJECT_MAPPER.enable( SerializationFeature.INDENT_OUTPUT );

        if ( BELL_CONFIG_WORLD_JSON_FILE.exists() )
        {
            try
            {
                BellOfTeleportations bellsOfTeleportationList = OBJECT_MAPPER.readValue( BELL_CONFIG_WORLD_JSON_FILE, BellOfTeleportations.class );
                for ( BellOfTeleportation bellOfTeleportation : bellsOfTeleportationList.getBells() )
                {
                    this.uuidBellsOfTeleportationMap.put( bellOfTeleportation.getUuid(), bellOfTeleportation );
                }

            } catch ( IOException e )
            {
                LOGGER.error( "Error could not load BellOfTeleportation mappings: {}", e.getMessage() );
                throw new RuntimeException( e );
            }

        }
    }

    public static BellOfTeleportationManager getInstance()
    {
        return INSTANCE;
    }

    public BellOfTeleportation retrieveBellOfTeleportationUUIDFromPersistentDataContainer( @NonNull PersistentDataContainer persistentDataContainer )
    {
        String uuid = persistentDataContainer.get(

            NamespacedKey.minecraft( MinecraftEmpiresConstants.BELLS_OF_TELEPORTATION_UUID_FIELD ),
            PersistentDataType.STRING );

        BellOfTeleportation bellOfTeleportation = null;
        if( uuid != null )
        {
            bellOfTeleportation = this.retrieveBellOfTeleportationByUUID( uuid );
        }

        return bellOfTeleportation;
    }

    public BellOfTeleportation retrieveBellOfTeleportationByUUID( @NonNull String uuid )
    {
        return this.uuidBellsOfTeleportationMap.get( uuid );
    }


    public void updateBellOfTeleportationMappings( BellOfTeleportation... bellOfTeleportations )
    {
        for( BellOfTeleportation bellOfTeleportation : bellOfTeleportations )
        {
            if ( bellOfTeleportation.getUuid() != null )
            {
                this.uuidBellsOfTeleportationMap.put( bellOfTeleportation.getUuid(), bellOfTeleportation );
            }
        }

        this.saveBellOfTeleportationMappingsToDisk();
    }

    private void saveBellOfTeleportationMappingsToDisk()
    {
        try
        {
            BellOfTeleportations bellsOfTeleportationList = new BellOfTeleportations( this.uuidBellsOfTeleportationMap.values().stream().toList() );
            OBJECT_MAPPER.writeValue( BELL_CONFIG_WORLD_JSON_FILE, bellsOfTeleportationList );

        } catch ( IOException e )
        {
            LOGGER.error( "Error saving BellOfTeleportation mappings to disk: {}", e.getMessage() );
            throw new RuntimeException( e );
        }
    }
}
