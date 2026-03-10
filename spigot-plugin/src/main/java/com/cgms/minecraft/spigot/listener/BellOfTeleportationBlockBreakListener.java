package com.cgms.minecraft.spigot.listener;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.Plugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BellOfTeleportationBlockBreakListener implements Listener
{
    private static final Logger LOGGER = LoggerFactory.getLogger( BellOfTeleportationBlockBreakListener.class.getName() );
    private final Plugin plugin;


    public BellOfTeleportationBlockBreakListener( Plugin plugin )
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak( BlockBreakEvent event)
    {
//        if (event.getBlock().getType() == Material.BELL)
//        {
//            LOGGER.info( "Bell block broken event caught." );
//
//            LOGGER.info( "Checking dropped item to see if it is a Bell of Teleportation." );
//
//            Block block = event.getBlock();
//
//            if (block.getState() instanceof TileState)
//            {
//                TileState state = (TileState) block.getState();
//                PersistentDataContainer container = state.getPersistentDataContainer();
//                NamespacedKey customKey = new NamespacedKey( this.plugin, "bellOfTeleportationUUID" );
//
//                if (container.has( customKey, PersistentDataType.STRING ) )
//                {
//                    String bellOfTeleportationUUID = container.get(customKey, PersistentDataType.STRING);
//                    BellOfTeleportationUtil.getInstance().getBellOfTeleportation( bellOfTeleportationUUID ).setCurrentLocationOfPlacedBell( null );
//
//                    LOGGER.info( "Bell of teleportation found {}.", bellOfTeleportationUUID );
//                    LOGGER.info( "Bell of location removed from Bell of Teleportation." );
//                }
//            }
//        }
    }

}
