package com.cgms.minecraft.spigot.listener;

import com.cgms.minecraft.spigot.item.BellOfTeleportation;
import com.cgms.minecraft.spigot.util.BellOfTeleportationUtil;
import com.cgms.minecraft.spigot.util.MinecraftAiConstants;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Bell;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
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
            LOGGER.debug( "Block placed event caught for Bell." );

            ItemStack placedItem = event.getItemInHand();
            Block placedBlock = event.getBlockPlaced();
            Bell bellState = (Bell) placedBlock.getState();

            BellOfTeleportationUtil bellOfTeleportationUtil = BellOfTeleportationUtil.getInstance();

            BellOfTeleportation bellOfTeleportation = bellOfTeleportationUtil.getBellOfTeleportationUUIDFromPersistentDataContainer(
                    placedItem.getItemMeta().getPersistentDataContainer()
            );

            if ( bellOfTeleportation != null )
            {
                LOGGER.debug( "Bell of teleportation found {}.", bellOfTeleportation.getUuid() );

                bellOfTeleportation.setBellOfTeleportationBlockFromSpigotBlock( placedBlock );
                bellOfTeleportationUtil.updateBellOfTeleportationMappings( bellOfTeleportation );

                bellState.getPersistentDataContainer().set(
                        NamespacedKey.minecraft( MinecraftAiConstants.BELLS_OF_TELEPORTATION_UUID_FIELD ),
                        PersistentDataType.STRING, bellOfTeleportation.getUuid()
                );

                bellState.update();
            }
        }
    }

}
