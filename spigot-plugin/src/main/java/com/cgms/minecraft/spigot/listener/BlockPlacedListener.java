/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.listener;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.WorldEditException;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.extent.clipboard.Clipboard;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardFormats;
import com.sk89q.worldedit.extent.clipboard.io.ClipboardReader;
import com.sk89q.worldedit.function.operation.Operation;
import com.sk89q.worldedit.function.operation.Operations;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.session.ClipboardHolder;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import org.slf4j.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class BlockPlacedListener implements Listener
{
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger( BlockPlacedListener.class );
    private static final String SCHEMA_FILE_NAME = "./plugins/WorldEdit/schematics/MedievalSurvivalBase.schem";
    private static final File SCHEMA_FILE = new File( SCHEMA_FILE_NAME );

    @EventHandler
    public void onBlockPlace( BlockPlaceEvent event )
    {
        if ( event.getBlockPlaced().getType() == Material.SPAWNER )
        {
            LOGGER.info( "Block placed event caught for chest." );

            Player player = event.getPlayer();

            if ( player.getName() != null && player.getName().equals( "RascallyBow9285" ) )
            {
                LOGGER.info( "Block placed event caught for chest for RascallyBow9285." );
                event.setCancelled( true );

                ItemStack placedItem = event.getItemInHand();

                if ( placedItem.getItemMeta().getDisplayName().equals( "BUILDING" ) )
                {
                    Clipboard clipboard;
                    try ( ClipboardReader reader = ClipboardFormats.findByFile(SCHEMA_FILE).getReader( new FileInputStream( SCHEMA_FILE ) ) )
                    {
                        clipboard = reader.read();

                    } catch ( IOException e) {
                        LOGGER.error( "Failed to read schematic file: {}", e.getMessage() );
                        return;
                    }

                    World world = player.getWorld();
                    Location location = event.getBlock().getLocation();

                    com.sk89q.worldedit.world.World weWorld = BukkitAdapter.adapt(world);

                    try ( EditSession editSession = WorldEdit.getInstance().newEditSession( weWorld ) )
                    {
                        Operation operation = new ClipboardHolder(clipboard)
                                .createPaste(editSession)
                                .to( BlockVector3.at( location.getX(), location.getY(), location.getZ() ) )
                                .ignoreAirBlocks(false) // Set to true to paste without air
                                .build();

                        Operations.complete(operation);
                        editSession.flushSession();

                    } catch ( WorldEditException e)
                    {
                        LOGGER.error( "Failed place building: {}", e.getMessage() );
                    }
                }

            }
        }
    }
}
