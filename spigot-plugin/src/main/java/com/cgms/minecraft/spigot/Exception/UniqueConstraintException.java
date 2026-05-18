/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.Exception;

public class UniqueConstraintException extends Exception
{
    public UniqueConstraintException()
    {
        super();
    }

    public UniqueConstraintException( String message )
    {
        super( message );
    }

    public UniqueConstraintException( String message, Throwable cause )
    {
        super( message, cause );
    }

    public UniqueConstraintException( Throwable cause )
    {
        super( cause );
    }

    protected UniqueConstraintException( String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace )
    {
        super( message, cause, enableSuppression, writableStackTrace );
    }
}
