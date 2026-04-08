/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.Exception;

public class PlayerMaxEmpiresException extends Exception
{
    public PlayerMaxEmpiresException()
    {
        super();
    }

    public PlayerMaxEmpiresException( String message )
    {
        super( message );
    }

    public PlayerMaxEmpiresException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public PlayerMaxEmpiresException( Throwable cause )
    {
        super( cause );
    }

    protected PlayerMaxEmpiresException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
