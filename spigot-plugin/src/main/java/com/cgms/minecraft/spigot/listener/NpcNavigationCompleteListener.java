package com.cgms.minecraft.spigot.listener;

import net.citizensnpcs.api.ai.event.NavigationCompleteEvent;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NpcNavigationCompleteListener implements Listener
{
    private static final Logger LOGGER = LoggerFactory.getLogger( NpcNavigationCompleteListener.class.getName() );
    private static Location LOCATION1 = null;
    private static Location LOCATION2 = null;

    private static final double X_COORDINATE = 4.873;
    private static final double Y_COORDINATE = 78.00;
    private static final double Z_COORDINATE = 8.474;

    private static final double X_TO_COORDINATE = 4.337;
    private static final double Y_TO_COORDINATE = 70.00;
    private static final double Z_TO_COORDINATE = 17.403;

    private static boolean AT_ORIGINAL_LOCATION = false;


    @EventHandler
    public void onNavigationComplete( NavigationCompleteEvent event)
    {
        NPC npc = event.getNPC(); // Get the NPC that completed navigation

        // Example: Make the NPC wander to a new random location upon completion
        if (npc.getName().equals("Armored knight"))
        {
            LOGGER.debug( "Armored Knight navigation completed." );

            Location tempLocation = npc.getEntity().getLocation();

            int xLocation = tempLocation.getBlockX();
            int yLocation = tempLocation.getBlockY();
            int zLocation = tempLocation.getBlockZ();

            LOGGER.debug( "x = " + xLocation + " || y = " + yLocation + " || z = " + zLocation );

            if( AT_ORIGINAL_LOCATION )
            {
                LOGGER.debug( "Armored Knight navigating to location 1." );
                npc.getNavigator().setTarget( LOCATION2 );
                AT_ORIGINAL_LOCATION = false;

            } else
            {
                LOGGER.debug( "Armored Knight navigating to location 2." );
                npc.getNavigator().setTarget( LOCATION1 );
                AT_ORIGINAL_LOCATION = true;
            }
        }
    }

    @EventHandler
    public void onNpcSpawn( NPCSpawnEvent event)
    {
        // Handle the event here
        NPC npc = event.getNPC();

        if( npc.getName().equals( "Armored knight" ) )
        {
            LOGGER.debug( "Armored Knight was spawned." );

            LOCATION2 = new Location( npc.getEntity().getWorld(), X_TO_COORDINATE, Y_TO_COORDINATE, Z_TO_COORDINATE );
            LOCATION1 = new Location( npc.getEntity().getWorld(), X_COORDINATE, Y_COORDINATE, Z_COORDINATE );

            npc.faceLocation( LOCATION2 );

            event.getNPC().getNavigator().setTarget( LOCATION2 );
        }
    }
}
