package com.cgms.minecraft.spigot.listener;

import org.bukkit.event.Listener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BellsOfTeleportationItemDroppedListener implements Listener
{
    private static final Logger LOGGER = LoggerFactory.getLogger( BellsOfTeleportationItemDroppedListener.class.getName() );

//    @EventHandler
//    public void onDrop( PlayerDropItemEvent event )
//    {
//        Player player = event.getPlayer();
//
//        Item droppedItem = event.getItemDrop();
//        if ( droppedItem.getItemStack().getType() == Material.BELL &&
//             droppedItem.getName().equalsIgnoreCase( MinecraftAiConstants.BELLS_OF_TELEPORTATION )
//           )
//        {
//            LOGGER.debug( "PlayerDropItemEvent caught for Bells Of Teleportation." );
//
//            droppedItem.setGlowing( true );
//
//            droppedItem.setOwner( player.getUniqueId() );
//            droppedItem.setCustomNameVisible( true );
//
//            BellOfTeleportationItemStack droppedBellOfTeleportationItemStack = (BellOfTeleportationItemStack) droppedItem;
//            if( droppedBellOfTeleportationItemStack.getPairedBellOfTeleportationEntity() == null )
//            {
//                BellOfTeleportationItemStack bellOfTeleportationItemStack = new BellOfTeleportationItemStack();
//                bellOfTeleportationItemStack.setPairedBellOfTeleportationEntity( droppedItem );
//                player.getWorld().dropItemNaturally(player.getLocation(), bellOfTeleportationItemStack );
//            }
//
//            // Remove the dropped diamond
//            droppedItem.remove();
//        }
//    }

}
