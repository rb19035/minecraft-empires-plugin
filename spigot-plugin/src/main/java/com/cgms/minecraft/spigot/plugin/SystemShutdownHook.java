/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.plugin;

import com.cgms.minecraft.spigot.database.EmpireFacade;

public class SystemShutdownHook implements Runnable
{
    @Override
    public void run()
    {
        EmpireFacade.getInstance().shutdown();
    }
}
