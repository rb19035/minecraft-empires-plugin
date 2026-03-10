package com.cgms.minecraft.spigot.util;

import com.cgms.minecraft.spigot.item.BellOfTeleportation;
import com.cgms.minecraft.spigot.item.BellOfTeleportationBlock;
import com.cgms.minecraft.spigot.item.BellOfTeleportationItemStack;
import lombok.NonNull;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
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
    private final Map<BellOfTeleportationItemStack, BellOfTeleportation> itemStackBellOfTeleportationMap;
    private final Map<BellOfTeleportationBlock, BellOfTeleportation> blockBellOfTeleportationMap;

    private BellOfTeleportationUtil()
    {
        this.uuidBellsOfTeleportationMap = new ConcurrentHashMap<String, BellOfTeleportation>();
        this.blockBellOfTeleportationMap = new ConcurrentHashMap<BellOfTeleportationBlock, BellOfTeleportation>();
        this.itemStackBellOfTeleportationMap = new ConcurrentHashMap<BellOfTeleportationItemStack, BellOfTeleportation>();
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


    public BellOfTeleportation getBellOfTeleportationByUUID( @NonNull String uuid )
    {
        return this.uuidBellsOfTeleportationMap.get( uuid );
    }

    public BellOfTeleportation getBellOfTeleportationByItemStack( @NonNull ItemStack itemStack)
    {
        BellOfTeleportationItemStack bellOfTeleportationItemStack = new BellOfTeleportationItemStack( itemStack.hashCode() );
        return this.itemStackBellOfTeleportationMap.get( bellOfTeleportationItemStack );
    }

    public BellOfTeleportation getBellOfTeleportationByBlock( @NonNull Block block )
    {
        Location location = block.getLocation();
        BellOfTeleportationBlock bellOfTeleportationBlock = new BellOfTeleportationBlock(
                location.getWorld().getName(), location.getX(), location.getY(), location.getZ()
        );

        return this.blockBellOfTeleportationMap.get( bellOfTeleportationBlock );
    }

    public void addBellOfTeleportation( @NonNull BellOfTeleportation bellOfTeleportation )
    {
        if( bellOfTeleportation.getBellOfTeleportationBlock() != null )
        {
            this.blockBellOfTeleportationMap.put( bellOfTeleportation.getBellOfTeleportationBlock(), bellOfTeleportation );
        }

        if( bellOfTeleportation.getBellOfTeleportationItemStack() != null )
        {
            this.itemStackBellOfTeleportationMap.put( bellOfTeleportation.getBellOfTeleportationItemStack(), bellOfTeleportation );
        }

        if( bellOfTeleportation.getUuid() != null )
        {
            this.uuidBellsOfTeleportationMap.put( bellOfTeleportation.getUuid(), bellOfTeleportation );
        }
    }

    public void updateBellOfTeleportationMappings( BellOfTeleportation bellOfTeleportation )
    {
        this.addBellOfTeleportation( bellOfTeleportation );

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
