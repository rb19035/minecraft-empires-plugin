/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.command;

import com.cgms.minecraft.spigot.Exception.UniqueConstraintException;
import com.cgms.minecraft.spigot.database.EmpireFacade;
import com.cgms.minecraft.spigot.empire.Empire;
import com.cgms.minecraft.spigot.empire.EmpireLeaderEntityType;
import org.apache.commons.text.StringEscapeUtils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmpireCommand implements CommandExecutor
{
    private static final Logger LOGGER = LoggerFactory.getLogger( EmpireCommand.class.getName() );

    @Override
    public boolean onCommand( CommandSender sender, Command command, String label, String[] args )
    {
        EmpireFacade empireFacade = EmpireFacade.getInstance();
        Player player = (Player) sender;
        Empire empire = new Empire();

        if (!(sender instanceof Player ) )
        {
            sender.sendMessage("Only players can use this command.");
            LOGGER.debug( "Command sender is not a player." );
            return true;
        }


        if ( args.length == 0 )
        {
            player.sendMessage("Uhhmmm ... The command was empty. Nothing to see here. Move along.");
            return true;
        }

        try
        {
            if ( args[ 0 ].equalsIgnoreCase( EmpireCommandOptions.CREATE_COMMAND ) )
            {
                if ( args[ 1 ] == null )
                {
                    player.sendMessage( "Empire name cannot be null. Please provide a valid empire name." );
                    return true;
                }

                empire.setName( StringEscapeUtils.escapeJava( args[ 1 ] ) );
                empire.setLeaderUUID( player.getUniqueId().toString() );
                empire.setLeaderName( player.getName() );
                empire.setLeaderType( EmpireLeaderEntityType.HUMAN_PLAYER );
                empireFacade.create( empire );

                LOGGER.debug( "Created Empire: " + empire.getName() );
                player.sendMessage( "Empire created successfully." );
            }
            else if ( args[ 0 ].equalsIgnoreCase( EmpireCommandOptions.RENAME_COMMAND ) )
            {
                empire = empireFacade.findByPlayerUUID( player.getUniqueId().toString() );
                empire.setName( StringEscapeUtils.escapeJava( args[ 1 ] ) );
                empireFacade.update( empire );

                player.sendMessage( "Empire renamed successfully." );

            } else
            {
                player.sendMessage( "Empire command not found. Better luck next time." );
            }


            return true;

        } catch ( UniqueConstraintException e )
        {
            player.sendMessage( "An empire with that name already exists or you already own an empire (you can only own one empire)." );
            return true;
        }
    }
}
