/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

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

    }

}
