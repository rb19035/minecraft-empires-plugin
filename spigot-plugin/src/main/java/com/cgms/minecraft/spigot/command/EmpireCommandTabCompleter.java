/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;

import java.util.List;

public class EmpireCommandTabCompleter implements TabCompleter
{
    @Override
    public List<String> onTabComplete( CommandSender sender, Command command, String label, String[] args )
    {
        if( args.length == 1 )
        {
            return List.of( EmpireCommandOptions.CREATE_COMMAND, EmpireCommandOptions.RENAME_COMMAND );
        }

        return List.of();
    }
}
