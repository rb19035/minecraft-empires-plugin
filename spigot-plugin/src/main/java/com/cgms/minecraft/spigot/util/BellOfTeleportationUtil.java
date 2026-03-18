package com.cgms.minecraft.spigot.util;

import com.cgms.minecraft.spigot.item.BellOfTeleportation;
import lombok.NonNull;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.slf4j.Logger;

import java.io.*;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BellOfTeleportationUtil implements Serializable
{
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger( BellOfTeleportationUtil.class );
    private static final String SERIALIZATION_FILE = "./plugins/Empires/bellOfTeleportationUtil.ser";
    private static BellOfTeleportationUtil INSTANCE = null;

    private final Map<String, BellOfTeleportation> uuidBellsOfTeleportationMap;

    private BellOfTeleportationUtil()
    {
        this.uuidBellsOfTeleportationMap = new ConcurrentHashMap<>();
    }

    public static BellOfTeleportationUtil getInstance()
    {
        if( INSTANCE == null )
        {
            File file = new File( SERIALIZATION_FILE );
            if ( file.exists() )
            {
                try ( ObjectInputStream ois = new ObjectInputStream( new FileInputStream( file ) ) )
                {
                    INSTANCE = (BellOfTeleportationUtil) ois.readObject();
                }
                catch ( IOException | ClassNotFoundException e )
                {
                    LOGGER.error( "Error deserializing BellOfTeleportation mappings: {}", e.getMessage() );
                }

            } else
            {
                INSTANCE = new BellOfTeleportationUtil();
            }
        }

        return INSTANCE;
    }

    public BellOfTeleportation getBellOfTeleportationUUIDFromPersistentDataContainer( @NonNull PersistentDataContainer persistentDataContainer )
    {
        String uuid = persistentDataContainer.get(
            NamespacedKey.minecraft( MinecraftAiConstants.BELLS_OF_TELEPORTATION_UUID_FIELD ),
            PersistentDataType.STRING );

        BellOfTeleportation bellOfTeleportation = null;
        if( uuid != null )
        {
            bellOfTeleportation = this.getBellOfTeleportationByUUID( uuid );
        }

        return bellOfTeleportation;
    }

    public BellOfTeleportation getBellOfTeleportationByUUID( @NonNull String uuid )
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

        this.serializeBellOfTeleportationMappings();
    }

    private void serializeBellOfTeleportationMappings()
    {
        File file = new File( SERIALIZATION_FILE );

        if( !file.exists() )
        {
            file.getParentFile().mkdirs();
        }

        try ( ObjectOutputStream oos = new ObjectOutputStream( new FileOutputStream( SERIALIZATION_FILE ) ) )
        {
            oos.writeObject( INSTANCE );

        } catch ( IOException e)
        {
            LOGGER.error( "Error serializing BellOfTeleportation mappings: {}", e.getMessage() );
        }
    }
}
