package com.cgms.minecraft.messaging;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ActiveMqConnectionManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger( ActiveMqConnectionManager.class );
    private static final String AI_RESPONSE_QUEUE = "ai.response.queue";
    private static final String HEARTBEAT_RESPONSE_QUEUE = "heartbeat.response.queue";
    private static final String CONFIG_FILE_PATH = "./plugins/Empires/minecraftEmpiresPluginConfig.properties";

    private String brokerUrl;
    private String username;
    private String password;

    private ActiveMQConnection activeMQConnection;
    private Session session;
    private AiRequestQueueSender aiRequestQueueSender;
    private static ActiveMqConnectionManager INSTANCE;

    private ActiveMqConnectionManager()
    {
        this.loadAiPropertiesFile();
    }

    public static ActiveMqConnectionManager getInstance()
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new ActiveMqConnectionManager();
        }

        return INSTANCE;
    }

    public void start() throws JMSException
    {
        if( this.activeMQConnection == null )
        {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory( this.brokerUrl );
            connectionFactory.setUserName( this.username );
            connectionFactory.setPassword( this.password );

            this.activeMQConnection = (ActiveMQConnection) connectionFactory.createConnection();
            this.session = this.activeMQConnection.createSession( false, Session.AUTO_ACKNOWLEDGE );

            Queue responseQueue = this.session.createQueue( AI_RESPONSE_QUEUE );
            MessageConsumer aiResponseConsumer = this.session.createConsumer( responseQueue );
            aiResponseConsumer.setMessageListener( new AiResponseQueueListener() );

            this.aiRequestQueueSender = new AiRequestQueueSender( this.session );

            this.activeMQConnection.start();
            LOGGER.debug( "ActiveMQ connection established. Listening on queues: {}, {}", AI_RESPONSE_QUEUE, HEARTBEAT_RESPONSE_QUEUE );
        }
    }

    public void stop()
    {
        try
        {
            if ( this.activeMQConnection != null )
            {
                this.session.close();
                this.activeMQConnection.close();
                LOGGER.debug( "ActiveMQ session and connection closed." );
            }
        }
        catch ( JMSException e )
        {
            LOGGER.error( "Failed to close ActiveMQ session and connection.", e );
        }
    }

    Session getJmsSession()
    {
        return this.session;
    }

    private void loadAiPropertiesFile()
    {
        Properties properties = new Properties();

        try ( FileInputStream input = new FileInputStream( CONFIG_FILE_PATH ) )
        {
            properties.load( input );

            LOGGER.debug( "Loaded AI properties file." );

        } catch ( IOException e)
        {
            LOGGER.error( "Failed to load AI properties file. Will continue with default settings.", e );
        }

        this.brokerUrl = properties.getProperty( "mqtt.broker.url", "tcp://localhost:61616" );
        this.username = properties.getProperty( "mqtt.username", "admin" );
        this.password = properties.getProperty( "mqtt.password", "admin" );
    }
}