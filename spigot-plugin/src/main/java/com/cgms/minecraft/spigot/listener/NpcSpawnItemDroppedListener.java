/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.listener;

import com.cgms.minecraft.spigot.database.EmpireFacade;
import com.cgms.minecraft.spigot.database.EmpireNpcFacade;
import com.cgms.minecraft.spigot.empire.Empire;
import com.cgms.minecraft.spigot.empire.EmpireNPC;
import com.cgms.minecraft.spigot.plugin.EntityType;
import com.cgms.minecraft.spigot.plugin.MinecraftEmpiresConstants;
import com.cgms.minecraft.spigot.plugin.NpcType;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NpcSpawnItemDroppedListener implements Listener
{
    private static final Logger LOGGER = LoggerFactory.getLogger( NpcSpawnItemDroppedListener.class.getName() );

    @EventHandler
    public void onDrop( PlayerDropItemEvent event )
    {
        LOGGER.debug( "PlayerDropItemEvent caught for custom NPC." );

        Player player = event.getPlayer();
        NPC npc = null;

        Item droppedItem = event.getItemDrop();
        if ( droppedItem.getItemStack().getType() == Material.PLAYER_HEAD )
        {
            String itemDroppedName = droppedItem.getItemStack().getItemMeta().getDisplayName();

            LOGGER.debug( "PlayerDropItemEvent caught for custom NPC {}", itemDroppedName );

            EmpireNpcFacade empireNpcFacade = null;
            EmpireFacade empireFacade = EmpireFacade.getInstance();
            Empire empire = empireFacade.findByName( player.getName() );

            EmpireNPC empireNPC = null;
            if( itemDroppedName.equalsIgnoreCase( MinecraftEmpiresConstants.NPC_VILLAGER_TYPE ) )
            {
                empireNPC = new EmpireNPC(
                        player.getName() + "Villager",
                        EntityType.VILLAGER,
                        NpcType.VILLAGER,
                        empire
                );

            } else if ( itemDroppedName.equalsIgnoreCase( MinecraftEmpiresConstants.NPC_ARCHER_TYPE ) )
            {
                empireNPC = new EmpireNPC(
                        player.getName() + "Archer",
                        EntityType.PLAYER,
                        NpcType.ARCHER,
                        empire
                );
            }
            else if ( itemDroppedName.equalsIgnoreCase( MinecraftEmpiresConstants.NPC_BODYGUARD_TYPE ) )
            {
                empireNPC = new EmpireNPC(
                        player.getName() + "Bodyguard",
                        EntityType.PLAYER,
                        NpcType.BODY_GUARD,
                        empire
                );
            } else if ( itemDroppedName.equalsIgnoreCase( MinecraftEmpiresConstants.NPC_ARMORED_KNIGHT_TYPE ) )
            {
                empireNPC = new EmpireNPC(
                        player.getName() + "Knight",
                        EntityType.PLAYER,
                        NpcType.ARMORED_KNIGHT,
                        empire
                );
            } else if ( itemDroppedName.equalsIgnoreCase( MinecraftEmpiresConstants.NPC_FOOT_SOLDIER_TYPE ) )
            {
                empireNPC = new EmpireNPC(
                        player.getName() + "Soldier",
                        EntityType.PLAYER,
                        NpcType.FOOT_SOLDIER,
                        empire
                );
            }

            empireNpcFacade.create( empireNPC );
            empireNPC.spawn( event.getItemDrop().getLocation() );
            droppedItem.remove();
        }
    }

}
