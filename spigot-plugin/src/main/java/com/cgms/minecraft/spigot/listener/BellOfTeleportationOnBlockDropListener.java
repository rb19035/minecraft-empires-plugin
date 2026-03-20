package com.cgms.minecraft.spigot.listener;

import com.cgms.minecraft.spigot.item.BellOfTeleportation;
import com.cgms.minecraft.spigot.util.BellOfTeleportationUtil;
import com.cgms.minecraft.spigot.util.MinecraftAiConstants;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.Bell;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockDropItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BellOfTeleportationOnBlockDropListener implements Listener
{
    private static final Logger LOGGER = LoggerFactory.getLogger( BellOfTeleportationOnBlockDropListener.class.getName() );
    private final Plugin plugin;


    public BellOfTeleportationOnBlockDropListener( Plugin plugin )
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockDrop( BlockDropItemEvent event)
    {
        if ( event.getBlockState().getType() == Material.BELL )
        {
            Bell bellState = (Bell) event.getBlockState();

            LOGGER.debug( "Bell drop detected. Checking if it was a Bell of Teleportation." );

            BellOfTeleportationUtil bellOfTeleportationUtil = BellOfTeleportationUtil.getInstance();
            BellOfTeleportation bellOfTeleportation = bellOfTeleportationUtil.retrieveBellOfTeleportationUUIDFromPersistentDataContainer( bellState.getPersistentDataContainer() );

            if ( bellOfTeleportation != null )
            {
                LOGGER.debug( "Bell of Teleportation {} found associated with the dropped Block.", bellOfTeleportation.getUuid() );

                // Prevent vanilla drops
                event.setCancelled( true );

                Location location = event.getBlock().getLocation();
                ItemStack itemStack = new ItemStack( Material.BELL );
                itemStack.setAmount( 1 );

                ItemMeta itemMeta = itemStack.getItemMeta();
                itemMeta.setItemName( MinecraftAiConstants.BELLS_OF_TELEPORTATION );
                itemMeta.setDisplayName( MinecraftAiConstants.BELLS_OF_TELEPORTATION );

                itemMeta.getPersistentDataContainer().set(
                        NamespacedKey.minecraft( MinecraftAiConstants.BELLS_OF_TELEPORTATION_UUID_FIELD ),
                        PersistentDataType.STRING,
                        bellOfTeleportation.getUuid()
                );

                itemStack.setItemMeta( itemMeta );

                bellOfTeleportation.clearLocationOfPlacedBell();
                location.getWorld().dropItemNaturally( location, itemStack );
            }
        }
    }

}
