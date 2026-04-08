/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.Exception;

public class EmpireAlreadyExistsException extends Exception
{
    public EmpireAlreadyExistsException()
    {
        super();
    }

    public EmpireAlreadyExistsException( String message )
    {
        super( message );
    }

    public EmpireAlreadyExistsException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public EmpireAlreadyExistsException( Throwable cause )
    {
        super( cause );
    }

    protected EmpireAlreadyExistsException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
