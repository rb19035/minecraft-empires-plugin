package com.cgms.minecraft.spigot.listener;

import com.cgms.minecraft.spigot.item.BellOfTeleportation;
import com.cgms.minecraft.spigot.util.BellOfTeleportationUtil;
import org.bukkit.block.Bell;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BellRingEvent;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;


public class BellOfTeleportationRingListener implements Listener
{
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger( BellOfTeleportationRingListener.class );
    private final Plugin plugin;

    public BellOfTeleportationRingListener( Plugin plugin )
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBellRing( BellRingEvent event )
    {
        Entity ringer = event.getEntity();

        LOGGER.debug( "Bell ringing event caught." );

        if ( ringer instanceof Player player )
        {
            LOGGER.debug( "Player rang bell." );

            Block block = event.getBlock();
            Bell bellState = (Bell) block.getState();

            BellOfTeleportationUtil bellOfTeleportationUtil = BellOfTeleportationUtil.getInstance();

            LOGGER.debug( "Finding Bell of Teleportation based on block." );

            BellOfTeleportation bellOfTeleportation = bellOfTeleportationUtil.getBellOfTeleportationUUIDFromPersistentDataContainer( bellState.getPersistentDataContainer() );

            if ( bellOfTeleportation != null )
            {
                LOGGER.debug( "Bell of teleportation found {}.", bellOfTeleportation.getUuid() );

                if ( bellOfTeleportation.canTeleport() )
                {
                    LOGGER.debug( "Teleporting player to entangled bell location {}.",
                                 bellOfTeleportation.getEntangledBellOfTeleportation().getSpigotLocationOfPlacedBell()
                    );

                    player.teleport( bellOfTeleportation.getEntangledBellOfTeleportation().getSpigotLocationOfPlacedBell() );

                } else
                {
                    LOGGER.debug( "Bell of teleportation cannot be teleported to." );
                }
            } else
            {
                LOGGER.debug( "Bell of teleportation not found." );
            }
        }
    }
}
