/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.database;

import com.cgms.minecraft.spigot.Exception.UniqueConstraintException;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class DatabaseFacade<T>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( DatabaseFacade.class );
    private static final String DATABASE_FILE = "./plugins/Empires/empires.db";

    static Connection DATABASE_CONNECTION;

    public abstract void create( @NonNull T object) throws UniqueConstraintException;
    public abstract void update( @NonNull T object) throws UniqueConstraintException;
    public abstract void delete( @NonNull T object);
    public abstract T findById( int id );
    public abstract T findByName( @NonNull String name );

    public DatabaseFacade()
    {
        try
        {
            DATABASE_CONNECTION = DriverManager.getConnection( "jdbc:sqlite:" + DATABASE_FILE );
        }
        catch ( SQLException e )
        {
            LOGGER.error( "Failed to connect to Empires database.", e );
            throw new RuntimeException( "Empires database connection failed.", e );
        }
    }

    public void shutdown()
    {
        try
        {
            DATABASE_CONNECTION.close();
        }
        catch ( SQLException e )
        {
            LOGGER.error( "Failed to close database connection", e );
        }
    }
}
