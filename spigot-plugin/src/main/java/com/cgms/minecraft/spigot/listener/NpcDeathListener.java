/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.listener;

import com.cgms.minecraft.spigot.plugin.MinecraftEmpiresConstants;
import net.citizensnpcs.api.event.NPCDeathEvent;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class NpcDeathListener implements Listener
{
    @EventHandler
    public void onNPCDeath( NPCDeathEvent event)
    {
        NPC npc = event.getNPC();

        if ( npc.getName() != null )
        {
            if ( npc.getName().contains( MinecraftEmpiresConstants.NPC_ARMORED_KNIGHT_TYPE ) ||
                    npc.getName().contains( MinecraftEmpiresConstants.NPC_FOOT_SOLDIER_TYPE ) ||
                    npc.getName().contains( MinecraftEmpiresConstants.NPC_BODYGUARD_TYPE ) ||
                    npc.getName().contains( MinecraftEmpiresConstants.NPC_ARCHER_TYPE )
            )
            {
                npc.setProtected( false );
                npc.destroy();
            }
        }
    }
}
