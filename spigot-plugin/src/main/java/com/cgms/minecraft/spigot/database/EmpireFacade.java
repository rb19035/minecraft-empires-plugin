/*
 * Copyright (c) 2026
 * SPDX-License-Identifier: MIT
 * The Cow Goes Moo Software (TCGMS)
 */

package com.cgms.minecraft.spigot.database;

import com.cgms.minecraft.spigot.Exception.UniqueConstraintException;
import com.cgms.minecraft.spigot.empire.Empire;
import com.cgms.minecraft.spigot.empire.EmpireLeaderEntityType;
import lombok.NonNull;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class EmpireFacade extends DatabaseFacade<Empire>
{
    private static final Logger LOGGER = LoggerFactory.getLogger( EmpireFacade.class );
    private static final EmpireFacade INSTANCE = new EmpireFacade();
    private final Map<String, Empire> empireNameToEmpireMap;

    public static EmpireFacade getInstance()
    {
        return INSTANCE;
    }


    private EmpireFacade()
    {
        super();
        this.initEmpireTable();
        this.initEmpireAllyTable();
        this.initEmpireEnemyTable();
        this.empireNameToEmpireMap = new ConcurrentHashMap<>( 10 );
    }

    @Override
    public void create( @NonNull Empire object ) throws UniqueConstraintException
    {
        try( PreparedStatement ps = DATABASE_CONNECTION.prepareStatement( INSET_EMPIRE_SQL, Statement.RETURN_GENERATED_KEYS  ) )
        {
            object.setName( StringEscapeUtils.escapeJava( object.getName() ) );

            ps.setString( 1, object.getName() );
            ps.setString( 2, object.getLeaderUUID() );
            ps.setString( 3, object.getLeaderType().name() );
            ps.setString( 4, object.getLeaderName() );

            ps.executeUpdate();
            try ( ResultSet generatedKeys = ps.getGeneratedKeys() )
            {
                if ( generatedKeys.next() )
                {
                    object.setId( generatedKeys.getInt( 1 ) );
                    LOGGER.debug( "Generated ID for new Empire: {}", object.getId() );
                }
            }

            this.saveEmpireAllies( object );
            this.saveEmpireEnemies( object );
            this.empireNameToEmpireMap.put( object.getName(), object );
        }
        catch ( SQLException e )
        {
            LOGGER.error( "Failed to create empire for {}", object.getName(), e );
            throw new UniqueConstraintException( e );
        }
    }

    @Override
    public void update( @NonNull Empire object ) throws UniqueConstraintException
    {
        try( PreparedStatement ps = DATABASE_CONNECTION.prepareStatement( UPDATE_EMPIRE_SQL ) )
        {
            object.setName( StringEscapeUtils.escapeJava( object.getName() ) );

            ps.setString( 1, object.getName() );
            ps.setInt( 2, object.getId() );
            ps.executeUpdate();

            this.empireNameToEmpireMap.put( object.getName(), object );
        }
        catch ( SQLException e )
        {
            // Check for unique constraint violation
            if (e.getMessage() != null && e.getMessage().contains("UNIQUE constraint failed"))
            {
                LOGGER.error( "Failed to update empire. Empire name already exists: {}.", object.getName(), e );
                throw new UniqueConstraintException( "With name " + object.getName() + " already exists." );
            } else
            {
                LOGGER.error( "Failed to update empire for: {}", object.getName(), e );
                throw new RuntimeException( e );
            }
        }
    }

    @Override
    public void delete( @NonNull Empire object )
    {
        try( PreparedStatement ps = DATABASE_CONNECTION.prepareStatement( DELETE_EMPIRE_SQL ) )
        {
            ps.setInt( 1, object.getId() );
            ps.executeUpdate();

            this.deleteEmpireAllies( object );
            this.deleteEmpireEnemies( object );

            this.empireNameToEmpireMap.remove( object.getName() );
        }
        catch ( SQLException e )
        {
            LOGGER.error( "Failed to delete empire {}.", object.getName(), e );
            throw new RuntimeException( e );
        }
    }

    @Override
    public Empire findByName( int id )
    {
        LOGGER.debug( "Finding empire by id: {}", id );

        for( Empire empire : this.empireNameToEmpireMap.values() )
        {
            if( empire.getId() == id )
            {
                LOGGER.debug( "Found empire in cache by id: {}", id );
                return empire;
            }
        }

        try( PreparedStatement ps = DATABASE_CONNECTION.prepareStatement( SELECT_EMPIRE_BY_ID_SQL ) )
        {
            ps.setInt( 1, id );

            Empire empire = this.loadEmpire( ps.executeQuery() );

            LOGGER.debug( "Found empire in DB by id: {}", id );

            this.empireNameToEmpireMap.put( empire.getName(), empire );

            return empire;

        } catch ( SQLException e )
        {
            LOGGER.error( "Failed to load empire from database with Id: {}", id, e );
            throw new RuntimeException( e );
        }
    }

    @Override
    public Empire findByName( @NonNull String name )
    {
        LOGGER.debug( "Finding empire by name: {}", name );

        Empire empire = this.empireNameToEmpireMap.get( name );
        if ( empire != null )
        {
            LOGGER.debug( "Found empire in cache by name: {}", name );
            return this.empireNameToEmpireMap.get( name );
        }

        try( PreparedStatement ps = DATABASE_CONNECTION.prepareStatement( SELECT_EMPIRE_BY_NAME_SQL ) )
        {
            ps.setString( 1, name );
            empire = this.loadEmpire( ps.executeQuery() );

            LOGGER.debug( "Found empire in DB by name: {}", name );

            this.empireNameToEmpireMap.put( empire.getName(), empire );

            return empire;

        } catch ( SQLException e )
        {
            LOGGER.error( "Failed to load empire from database with name: {}", name, e );
            throw new RuntimeException( e );
        }
    }

    public Empire findByPlayerUUID( @NonNull String uuid )
    {
        LOGGER.debug( "Finding empire by player UUID: {}", uuid );

        for( Empire empire : this.empireNameToEmpireMap.values() )
        {
            if( empire.getLeaderUUID().equalsIgnoreCase( uuid ) )
            {
                return empire;
            }
        }

        try( PreparedStatement ps = DATABASE_CONNECTION.prepareStatement( SELECT_EMPIRE_BY_LEADER_UUID_SQL ) )
        {
            ps.setString( 1, uuid );
            Empire empire = this.loadEmpire( ps.executeQuery() );

            LOGGER.debug( "Found empire in DB by player UUID: {}", uuid );

            this.empireNameToEmpireMap.put( empire.getName(), empire );

            return empire;

        } catch ( SQLException e )
        {
            LOGGER.error( "Failed to load empire from database with player UUID: {}", uuid, e );
            throw new RuntimeException( e );
        }
    }


    private Empire loadEmpire( @NonNull ResultSet empireQueryResultSet ) throws SQLException
    {
        Empire empire = null;
        if ( empireQueryResultSet.next() )
        {
            empire = new Empire();
            empire.setId( empireQueryResultSet.getInt( 1 ) );
            empire.setName( empireQueryResultSet.getString( 2 ) );
            empire.setLeaderUUID( empireQueryResultSet.getString( 3 ) );
            empire.setLeaderType( EmpireLeaderEntityType.valueOf( empireQueryResultSet.getString( 4 ) ) );
            empire.setLeaderName( empireQueryResultSet.getString( 5 ) );

            this.empireNameToEmpireMap.put( empire.getName(), empire );
        }

        return empire;

    }

    private void loadEmpireAllies( Empire empire )
    {
        try ( PreparedStatement ps = DATABASE_CONNECTION.prepareStatement( SELECT_ALL_EMPIRE_ALLIES_SQL ) )
        {
            ps.setInt( 1, empire.getId() );
            ResultSet rs = ps.executeQuery();

            // Bad ... Need to refactor this.
            while( rs.next() )
            {
                empire.getAllyList().add( this.findByName( rs.getInt( 2 ) ) );
            }

            this.empireNameToEmpireMap.put( empire.getName(), empire );

        } catch ( SQLException e )
        {
            LOGGER.error( "Failed to load empire allies for empire: {}", empire.getName(), e );
            throw new RuntimeException( e );
        }
    }

    private void loadEmpireEnemies( Empire empire )
    {
        try ( PreparedStatement ps = DATABASE_CONNECTION.prepareStatement( SELECT_ALL_EMPIRE_ENEMIES_SQL ) )
        {
            ps.setInt( 1, empire.getId() );
            ResultSet rs = ps.executeQuery();

            // Bad ... Need to refactor this.
            while( rs.next() )
            {
                empire.getEnemyList().add( this.findByName( rs.getInt( 2 ) ) );
            }

            this.empireNameToEmpireMap.put( empire.getName(), empire );

        } catch ( SQLException e )
        {
            LOGGER.error( "Failed to load empire enemies for empire: {}", empire.getName(), e );
            throw new RuntimeException( e );
        }
    }

    private void saveEmpireAllies( Empire empire )
    {
        try ( PreparedStatement ps = DATABASE_CONNECTION.prepareStatement( INSERT_EMPIRE_ALLY_SQL ) )
        {
            this.deleteEmpireAllies( empire );

            for( Empire empireAlly: empire.getAllyList() )
            {
                ps.setInt( 1, empire.getId() );
                ps.setInt( 2, empireAlly.getId() );
                ps.addBatch();
            }

            ps.executeBatch();

            this.empireNameToEmpireMap.put( empire.getName(), empire );

        } catch ( SQLException e )
        {
            LOGGER.error( "Failed to save empire allies for empire: {}", empire.getName(), e );
            throw new RuntimeException( e );
        }
    }

    private void saveEmpireEnemies( Empire empire )
    {
        try ( PreparedStatement ps = DATABASE_CONNECTION.prepareStatement( INSERT_EMPIRE_ENEMY_SQL ) )
        {
            this.deleteEmpireAllies( empire );

            for( Empire empireEnemy: empire.getEnemyList() )
            {
                ps.setInt( 1, empire.getId() );
                ps.setInt( 2, empireEnemy.getId() );
                ps.addBatch();
            }

            ps.executeBatch();

            this.empireNameToEmpireMap.put( empire.getName(), empire );

        } catch ( SQLException e )
        {
            LOGGER.error( "Failed to save empire allies for empire: {}", empire.getName(), e );
            throw new RuntimeException( e );
        }
    }

    private void deleteEmpireAllies( Empire empire )
    {
        try ( PreparedStatement ps = DATABASE_CONNECTION.prepareStatement( DELETE_EMPIRE_ALLIES_SQL ) )
        {
            ps.executeUpdate();
            this.empireNameToEmpireMap.put( empire.getName(), empire );

        } catch ( SQLException e )
        {
            LOGGER.error( "Failed to delete empire allies for empire: {}", empire.getName(), e );
            throw new RuntimeException( e );
        }
    }

    private void deleteEmpireEnemies( Empire empire )
    {
        try ( PreparedStatement ps = DATABASE_CONNECTION.prepareStatement( DELETE_EMPIRE_ENEMIES_SQL ) )
        {
            ps.executeUpdate();
            this.empireNameToEmpireMap.put( empire.getName(), empire );

        } catch ( SQLException e )
        {
            LOGGER.error( "Failed to delete empire enemies for empire: {}", empire.getName(), e );
            throw new RuntimeException( e );
        }
    }

    private void createEmpireRecord( Empire empire )
    {
        try ( PreparedStatement statement = DATABASE_CONNECTION.prepareStatement( INSET_EMPIRE_SQL, Statement.RETURN_GENERATED_KEYS ) )
        {
            statement.setString( 1, empire.getName() );
            statement.setString( 2, empire.getLeaderUUID().toString() );
            statement.setString( 3, empire.getLeaderType().name() );
            statement.execute();

            try ( ResultSet generatedKeys = statement.getGeneratedKeys() )
            {
                if ( generatedKeys.next() )
                {
                    empire.setId( generatedKeys.getInt( 1 ) );
                    LOGGER.debug( "Generated ID for new Empire: {}", empire.getId() );
                    ;
                }
            }

        }
        catch ( SQLException e )
        {
            LOGGER.error( "Failed to create new empire.", e );
        }
    }

//    public void addNpcToEmpire( @NonNull NPC npc )
//    {
//        Owner ownerTrait = npc.getOrAddTrait( Owner.class );
//        Empire empire = this.playerMameToEmpireNameMap.get( Bukkit.getPlayer( ownerTrait.getOwnerId() ).getName() );
//
//        if ( empire == null )
//        {
//            empire = new Empire();
//        }
//
//        empire.getEmpireNPCList().add( npc );
//    }

    private void initEmpireAllyTable()
    {
        try ( PreparedStatement ps = DATABASE_CONNECTION.prepareStatement( CREATE_EMPIRE_ALLY_TABLE_SQL ) )
        {
            ps.executeUpdate();
        } catch ( SQLException e )
        {
            LOGGER.error( "Failed to create empire ally table", e );
            throw new RuntimeException( e );
        }
    }

    private void initEmpireTable()
    {
        try ( Statement s = DATABASE_CONNECTION.createStatement() )
        {
            s.executeUpdate( CREATE_EMPIRE_TABLE_SQL );
            s.executeUpdate( CREATE_EMPIRE_NAME_TABLE_INDEX_SQL );
            s.executeUpdate( CREATE_EMPIRE_LEADER_UUID_TABLE_INDEX_SQL );
            s.executeUpdate( CREATE_EMPIRE_LEADER_NAME_TABLE_INDEX_SQL );

        } catch ( SQLException e )
        {
            LOGGER.error( "Failed to create empire table", e );
            throw new RuntimeException( e );
        }
    }

    private void initEmpireEnemyTable()
    {
        try ( Statement s = DATABASE_CONNECTION.createStatement() )
        {
            s.executeUpdate( CREATE_EMPIRE_ENEMY_TABLE_SQL );
        } catch ( SQLException e )
        {
            LOGGER.error( "Failed to create empire enemy table", e );
            throw new RuntimeException( e );
        }
    }

    private static final String CREATE_EMPIRE_TABLE_SQL =
                    "CREATE TABLE IF NOT EXISTS empire (" +
                    " id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " name TEXT NOT NULL," +
                    " leader_uuid TEXT NOT NULL," +
                    " leader_type TEXT NOT NULL," +
                    " leader_name TEXT NOT NULL" +
                    ");";

    private static final String CREATE_EMPIRE_NAME_TABLE_INDEX_SQL = "CREATE UNIQUE INDEX IF NOT EXISTS empire_name_index ON empire (name);";
    private static final String CREATE_EMPIRE_LEADER_UUID_TABLE_INDEX_SQL = "CREATE UNIQUE INDEX IF NOT EXISTS empire_leader_uuid_index ON empire (leader_uuid);";
    private static final String CREATE_EMPIRE_LEADER_NAME_TABLE_INDEX_SQL = "CREATE UNIQUE INDEX IF NOT EXISTS empire_leader_name_index ON empire (leader_name);";
    private static final String CREATE_EMPIRE_ENEMY_TABLE_SQL =
                    "CREATE TABLE IF NOT EXISTS empire_enemy ( " +
                    "empire_id INTEGER NOT NULL, " +
                    "empire_enemy_id INTEGER NOT NULL," +
                    "PRIMARY KEY (empire_id,empire_enemy_id )," +
                    " FOREIGN KEY (empire_id) REFERENCES empire(id) ON DELETE CASCADE," +
                    " FOREIGN KEY (empire_enemy_id) REFERENCES empire(id) ON DELETE CASCADE" +
                    ");";
    private static final String INSERT_EMPIRE_ALLY_SQL = "INSERT INTO empire_ally (empire_id, empire_ally_id) VALUES (?, ?)";
    private static final String INSERT_EMPIRE_ENEMY_SQL = "INSERT INTO empire_enemy (empire_id, empire_enemy_id) VALUES (?, ?)";
    private static final String DELETE_EMPIRE_ALLIES_SQL = "DELETE FROM empire_ally WHERE empire_id = ?";
    private static final String DELETE_EMPIRE_ENEMIES_SQL = "DELETE FROM empire_enemy WHERE empire_id = ?";
    private static final String CREATE_EMPIRE_ALLY_TABLE_SQL =
                    "CREATE TABLE IF NOT EXISTS empire_ally ( " +
                    "empire_id INTEGER NOT NULL, " +
                    "empire_ally_id INTEGER NOT NULL," +
                    "PRIMARY KEY (empire_id,empire_ally_id )," +
                    " FOREIGN KEY (empire_id) REFERENCES empire(id) ON DELETE CASCADE," +
                    " FOREIGN KEY (empire_ally_id) REFERENCES empire(id) ON DELETE CASCADE" +
                    ");";

    private static final String SELECT_ALL_EMPIRE_ALLIES_SQL = "SELECT * FROM empire_ally WHERE empire_id = ?";
    private static final String SELECT_ALL_EMPIRE_ENEMIES_SQL = "SELECT * FROM empire_enemy WHERE empire_id = ?";
    private static final String UPDATE_EMPIRE_SQL = "UPDATE empire SET name = ? WHERE id = ?";
    private static final String DELETE_EMPIRE_SQL = "DELETE FROM empire WHERE id = ?";
    private static final String INSET_EMPIRE_SQL = "INSERT INTO empire (name, leader_uuid, leader_type, leader_name) VALUES (?, ?, ?, ?)";
    private static final String SELECT_EMPIRE_BY_NAME_SQL = "SELECT * FROM empire WHERE name = ?";
    private static final String SELECT_EMPIRE_BY_ID_SQL = "SELECT * FROM empire WHERE id = ?";
    private static final String SELECT_EMPIRE_BY_LEADER_UUID_SQL = "SELECT * FROM empire WHERE leader_uuid = ?";

}
