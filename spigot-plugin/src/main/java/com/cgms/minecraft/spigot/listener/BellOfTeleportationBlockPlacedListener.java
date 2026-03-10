package com.cgms.minecraft.spigot.listener;

import com.cgms.minecraft.spigot.item.BellOfTeleportation;
import com.cgms.minecraft.spigot.util.BellOfTeleportationUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BellOfTeleportationBlockPlacedListener implements Listener
{
    private static final Logger LOGGER = LoggerFactory.getLogger( BellOfTeleportationBlockPlacedListener.class.getName() );
    private final Plugin plugin;


    public BellOfTeleportationBlockPlacedListener( Plugin plugin )
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockPlace( BlockPlaceEvent event)
    {
        if (event.getBlockPlaced().getType() == Material.BELL)
        {
            LOGGER.info( "Block placed event caught for Bell." );

            ItemStack placedItem = event.getItemInHand();
            Block placedBlock = event.getBlockPlaced();
            BellOfTeleportationUtil bellOfTeleportationUtil = BellOfTeleportationUtil.getInstance();

            LOGGER.info( "Finding Bell of Teleportation based on ItemStack." );
            BellOfTeleportation bellOfTeleportation = bellOfTeleportationUtil.getBellOfTeleportationByItemStack( placedItem );

            if( bellOfTeleportation != null )
            {
                LOGGER.info( "Bell of teleportation found {}.", bellOfTeleportation.getUuid() );

                bellOfTeleportation.setBellOfTeleportationBlock( placedBlock );
                bellOfTeleportationUtil.updateBellOfTeleportationMappings( bellOfTeleportation );

                if( bellOfTeleportation.getEntangledBellOfTeleportation() == null )
                {
                    LOGGER.info( "Bell of teleportation has no entangled bell." );
                } else
                {
                    LOGGER.info( "Bell of teleportation has entangled bell {}.", bellOfTeleportation.getEntangledBellOfTeleportation().getUuid() );
                }


            } else
            {
                LOGGER.info( "Bell of teleportation not found." );
            }

//            ItemStack[] playerInventoryItemArray = event.getPlayer().getInventory().getContents();
//            for ( ItemStack playerInventoryItem : playerInventoryItemArray )
//            {
//                if ( playerInventoryItem != null && playerInventoryItem.getType() == Material.BELL )
//                {
//                    LOGGER.info( "Found Bell in player inventory." );
//                    if ( placedItem.equals( playerInventoryItem ) )
//                    {
//                        LOGGER.info( "Found matching Bell in player inventory." );
//                        break;
//                    }
//                }
//            }
        }
    }

}
