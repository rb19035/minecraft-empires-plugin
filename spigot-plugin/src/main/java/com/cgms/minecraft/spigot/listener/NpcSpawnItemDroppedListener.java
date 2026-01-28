package com.cgms.minecraft.spigot.listener;

import com.cgms.minecraft.spigot.util.NpcFactory;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;

import java.util.UUID;
import java.util.logging.Logger;

public class NpcSpawnItemDroppedListener implements Listener
{
    private static final Logger LOGGER = Logger.getLogger( NpcSpawnItemDroppedListener.class.getName() );

    @EventHandler
    public void onDrop( PlayerDropItemEvent event )
    {
        LOGGER.info( "PlayerDropItemEvent caught for custom NPC." );

        Player player = event.getPlayer();
        NPC npc = null;

        Item droppedItem = event.getItemDrop();
        if ( droppedItem.getItemStack().getType() == Material.PLAYER_HEAD )
        {
            LOGGER.info( "PlayerDropItemEvent caught for custom NPC." );

            // Remove the dropped diamond
            droppedItem.remove();

            // Spawn a NPC at the location
            npc = NpcFactory.spawnNPC( event.getItemDrop().getLocation(), droppedItem.getItemStack().getItemMeta().getDisplayName(), player, UUID.randomUUID().toString() );
        }
    }

}
