package com.cgms.minecraft.spigot.util;

import com.cgms.minecraft.spigot.item.BellOfTeleportation;
import com.cgms.minecraft.spigot.item.BellOfTeleportations;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import lombok.NonNull;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.slf4j.Logger;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BellOfTeleportationUtil implements Serializable
{
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger( BellOfTeleportationUtil.class );
    private static final String BELL_CONFIG_WORLD_JSON_FILE = "./plugins/Empires/bellOfTeleportationUtil.json";

    private static BellOfTeleportationUtil INSTANCE = null;
    private final Map<String, BellOfTeleportation> uuidBellsOfTeleportationMap;

    private BellOfTeleportationUtil()
    {
        this.uuidBellsOfTeleportationMap = new ConcurrentHashMap<>();
    }

    private BellOfTeleportationUtil( List<BellOfTeleportation> bellOfTeleportationList )
    {
        this.uuidBellsOfTeleportationMap = new ConcurrentHashMap<>();

        for( BellOfTeleportation bellOfTeleportation : bellOfTeleportationList )
        {
            this.uuidBellsOfTeleportationMap.put( bellOfTeleportation.getUuid().toString(), bellOfTeleportation );
        }
    }

    public static BellOfTeleportationUtil getInstance()
    {
        if( INSTANCE == null )
        {
            File file = new File( BELL_CONFIG_WORLD_JSON_FILE );
            if ( file.exists() )
            {
                ObjectMapper mapper = new ObjectMapper();
                try
                {
                    // This is fucking stupid ... Gotta return and fix this later.
                    BellOfTeleportations bellsOfTeleportationList = mapper.readValue( file, BellOfTeleportations.class );
                    INSTANCE = new BellOfTeleportationUtil( bellsOfTeleportationList.getBells() );

                } catch ( IOException e )
                {
                    LOGGER.error( "Error deserializing BellOfTeleportation mappings: {}", e.getMessage() );
                    throw new RuntimeException( e );
                }

            } else
            {
                INSTANCE = new BellOfTeleportationUtil();
            }
        }

        return INSTANCE;
    }

    public BellOfTeleportation retrieveBellOfTeleportationUUIDFromPersistentDataContainer( @NonNull PersistentDataContainer persistentDataContainer )
    {
        String uuid = persistentDataContainer.get(

            NamespacedKey.minecraft( MinecraftAiConstants.BELLS_OF_TELEPORTATION_UUID_FIELD ),
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
        File file = new File( BELL_CONFIG_WORLD_JSON_FILE );
        ObjectMapper objectMapper = new ObjectMapper();

        try
        {
            // This is fucking stupid ... Gotta return and fix this later.
            BellOfTeleportations bellsOfTeleportationList = new BellOfTeleportations( this.uuidBellsOfTeleportationMap.values().stream().toList() );

            objectMapper.enable( SerializationFeature.INDENT_OUTPUT );
            objectMapper.writeValue( file, bellsOfTeleportationList );

        } catch ( IOException e )
        {
            LOGGER.error( "Error serializing BellOfTeleportation mappings: {}", e.getMessage() );
            throw new RuntimeException( e );
        }
    }
}
