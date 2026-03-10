package com.cgms.minecraft.spigot.listener;

import com.cgms.minecraft.spigot.item.BellOfTeleportation;
import com.cgms.minecraft.spigot.util.BellOfTeleportationUtil;
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
    private Plugin plugin;

    public BellOfTeleportationRingListener( Plugin plugin )
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBellRing( BellRingEvent event )
    {
        Entity ringer = event.getEntity();

        LOGGER.info( "Bell ringing event caught." );

        if ( ringer instanceof Player player )
        {
            LOGGER.info( "Player rang bell." );

            Block block = event.getBlock();

            BellOfTeleportationUtil bellOfTeleportationUtil = BellOfTeleportationUtil.getInstance();

            LOGGER.info( "Finding Bell of Teleportation based on block." );

            BellOfTeleportation bellOfTeleportation = bellOfTeleportationUtil.getBellOfTeleportationByBlock( block );

            if ( bellOfTeleportation != null )
            {
                LOGGER.info( "Bell of teleportation found {}.", bellOfTeleportation.getUuid() );

                LOGGER.info( "Bell of Teleportation entangled with UUID {}.", bellOfTeleportation.getEntangledBellOfTeleportation().getUuid() );

                LOGGER.info( "Bell of Teleportation entangled with Block {}.", bellOfTeleportation.getEntangledBellOfTeleportation().getBellOfTeleportationBlock() );

                if ( bellOfTeleportation.canTeleport() )
                {
                    LOGGER.info( "Teleporting player to entangled bell location {}.",
                            bellOfTeleportation.getEntangledBellOfTeleportation().getSpigotLocationOfPlacedBell() );
                    player.teleport( bellOfTeleportation.getEntangledBellOfTeleportation().getSpigotLocationOfPlacedBell() );

                } else
                {
                    LOGGER.info( "Bell of teleportation cannot be teleported to." );
                }
            } else
            {
                LOGGER.info( "Bell of teleportation not found." );
            }
        }
    }
}
