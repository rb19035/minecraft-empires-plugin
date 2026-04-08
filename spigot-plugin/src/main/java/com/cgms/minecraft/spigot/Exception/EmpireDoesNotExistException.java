/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.Exception;

public class EmpireDoesNotExistException extends Exception
{
    public EmpireDoesNotExistException()
    {
        super();
    }

    public EmpireDoesNotExistException( String message )
    {
        super( message );
    }

    public EmpireDoesNotExistException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public EmpireDoesNotExistException( Throwable cause )
    {
        super( cause );
    }

    protected EmpireDoesNotExistException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
