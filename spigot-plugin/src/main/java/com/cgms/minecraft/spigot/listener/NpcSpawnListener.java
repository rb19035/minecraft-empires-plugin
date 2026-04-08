/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.listener;

import com.cgms.minecraft.spigot.plugin.MinecraftEmpiresConstants;
import com.cgms.minecraft.spigot.plugin.NpcFactory;
import net.citizensnpcs.api.event.NPCSpawnEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.slf4j.Logger;

public class NpcSpawnListener implements Listener
{
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger( NpcSpawnListener.class );
    @EventHandler
    public void onNpcSpawn( NPCSpawnEvent event)
    {
        LOGGER.info("*** NPC Spawned ***");
        NPC npc = event.getNPC();

        if ( npc.getName() != null )
        {
            if( npc.getName().contains( MinecraftEmpiresConstants.NPC_ARMORED_KNIGHT_TYPE ) )
            {
                LOGGER.info( "Found Knight...Setting equipment." );
                NpcFactory.setNpcKnightEquipment( npc );
            }

            if( npc.getName().contains( MinecraftEmpiresConstants.NPC_FOOT_SOLDIER_TYPE ) )
            {
                LOGGER.info( "Found Foot Soldier...Setting equipment." );
                NpcFactory.setNpcFootSoldierEquipment( npc );
            }

            if( npc.getName().contains( MinecraftEmpiresConstants.NPC_BODYGUARD_TYPE ) )
            {
                LOGGER.info( "Found Bodyguard...Setting equipment." );
                NpcFactory.setNpcBodyguardEquipment( npc );
            }

            if( npc.getName().contains( MinecraftEmpiresConstants.NPC_ARCHER_TYPE ) )
            {
                LOGGER.info( "Found Archer...Setting equipment." );
                NpcFactory.setNpcArcherEquipment( npc );
            }
        }
    }


}
