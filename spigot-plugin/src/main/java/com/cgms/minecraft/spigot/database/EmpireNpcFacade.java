/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.database;

import com.cgms.minecraft.spigot.empire.EmpireNPC;
import org.apache.commons.text.StringEscapeUtils;
import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmpireNpcFacade extends DatabaseFacade<EmpireNPC>
{
    private static final Logger LOGGER = org.slf4j.LoggerFactory.getLogger( EmpireNpcFacade.class );
    private static final EmpireNpcFacade INSTANCE = new EmpireNpcFacade();
    private static final Map<String, EmpireNPC> EMPIRE_NPC_CACHE = new ConcurrentHashMap<>();


    private EmpireNpcFacade()
    {
        initEmpireNpcTable();
    }

    public static EmpireNpcFacade getInstance()
    {
        return INSTANCE;
    }

    @Override
    public void create( @NonNull EmpireNPC object )
    {
        try ( PreparedStatement statement = DATABASE_CONNECTION.prepareStatement( INSERT_EMPIRE_NPC_SQL, Statement.RETURN_GENERATED_KEYS ) )
        {
            object.setName( StringEscapeUtils.escapeJava( object.getName() ) );

            statement.setString( 1, object.getName() );
            statement.setString( 2, object.getEntityType().name() );
            statement.setString( 3, object.getNpcType().name() );
            statement.setInt( 4, object.getEmpire().getId() );
            statement.setString( 5, object.getUuid() );
            statement.executeUpdate();

            try ( ResultSet generatedKeys = statement.getGeneratedKeys() )
            {
                if ( generatedKeys.next() )
                {
                    object.setId( generatedKeys.getInt( 1 ) );
                    EMPIRE_NPC_CACHE.put( object.getName(), object );

                    LOGGER.debug( "Generated ID for Empire NPC: {}", object.getId() );
                }
            }
        }
        catch ( SQLException e )
        {
            LOGGER.error( "Failed to create empire npc", e );
            throw new RuntimeException( e );
        }
    }

    @Override
    public void update( @NonNull EmpireNPC object )
    {
        try ( PreparedStatement statement = DATABASE_CONNECTION.prepareStatement( UPDATE_EMPIRE_NPC_SQL ) )
        {
            object.setName( StringEscapeUtils.escapeJava( object.getName() ) );

            statement.setString( 1, object.getName() );
            statement.setInt( 2, object.getId() );
            statement.executeUpdate();

            EMPIRE_NPC_CACHE.put( object.getName(), object );

            LOGGER.debug( "Generated ID for Empire NPC: {}", object.getId() );

        }  catch ( SQLException e )
        {
            LOGGER.error( "Failed to create empire npc", e );
            throw new RuntimeException( e );
        }
    }

    @Override
    public void delete( @NonNull EmpireNPC object )
    {

    }

    @Override
    public EmpireNPC findById( int id )
    {
        return null;
    }

    @Override
    public EmpireNPC findByName( String name )
    {
        return null;
    }

    private void initEmpireNpcTable()
    {
        try ( Statement s = DATABASE_CONNECTION.createStatement() )
        {
            s.executeUpdate( CREATE_EMPIRE_NPC_TABLE_SQL );
            s.executeUpdate( CREATE_EMPIRE_NPC_TABLE_NAME_INDEX_SQL );
            s.executeUpdate( CREATE_EMPIRE_NPC_TABLE_EMPIRE_INDEX_SQL );

        } catch ( SQLException e )
        {
            LOGGER.error( "Failed to create empire table", e );
            throw new RuntimeException( e );
        }
    }

    private static final String CREATE_EMPIRE_NPC_TABLE_SQL =
            "CREATE TABLE IF NOT EXISTS empire_npc (" +
            " id INTEGER PRIMARY KEY AUTOINCREMENT," +
            " name TEXT NOT NULL," +
            " entity_type TEXT NOT NULL," +
            " npc_type TEXT NOT NULL," +
            " empire_id INTEGER NOT NULL," +
            " uuid TEXT NOT NULL," +
            " FOREIGN KEY (empire_id) REFERENCES empire(id) ON DELETE CASCADE" +
            ");";

    private static final String CREATE_EMPIRE_NPC_TABLE_NAME_INDEX_SQL =
            "CREATE UNIQUE INDEX IF NOT EXISTS " +
            "empire_npc_uuid_index ON empire_npc (uuid);";

    private static final String CREATE_EMPIRE_NPC_TABLE_EMPIRE_INDEX_SQL =
            "CREATE INDEX IF NOT EXISTS " +
            "empire_npc_empire_index ON empire_npc (empire_id);";

    private static final String INSERT_EMPIRE_NPC_SQL =
            "INSERT INTO empire_npc (name, entity_type, npc_type, empire_id, uuid) " +
            "VALUES (?, ?, ?, ?, ?)";

    private static final String UPDATE_EMPIRE_NPC_SQL =
            "UPDATE empire_npc (name) " +
            "VALUES (?) WHERE id = ?";

}
