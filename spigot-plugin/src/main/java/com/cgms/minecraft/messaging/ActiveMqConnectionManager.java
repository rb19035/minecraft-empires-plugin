package com.cgms.minecraft.messaging;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Queue;
import javax.jms.Session;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveMqConnectionManager
{
    private static final Logger LOGGER = LoggerFactory.getLogger( ActiveMqConnectionManager.class );
    private static final String AI_RESPONSE_QUEUE = "ai.response.queue";
    private static final String HEARTBEAT_RESPONSE_QUEUE = "heartbeat.response.queue";

    private final String brokerUrl;
    private final String username;
    private final String password;

    private Connection connection;
    private Session session;
    private AiRequestQueueSender aiRequestQueueSender;
    private HeartbeatRequestQueueSender heartbeatRequestQueueSender;

    private static ActiveMqConnectionManager INSTANCE;

    private ActiveMqConnectionManager( String brokerUrl, String username, String password )
    {
        this.brokerUrl = brokerUrl;
        this.username = username;
        this.password = password;
    }

    public static ActiveMqConnectionManager getInstance()
    {
        if ( INSTANCE == null )
        {
            INSTANCE = new ActiveMqConnectionManager( "tcp://localhost:61616", "admin", "admin" );
        }

        return INSTANCE;
    }

    public void start() throws JMSException
    {
        if( this.connection == null )
        {
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory( this.brokerUrl );
            connectionFactory.setUserName( this.username );
            connectionFactory.setPassword( this.password );

            this.connection = connectionFactory.createConnection();
            this.session = this.connection.createSession( false, Session.AUTO_ACKNOWLEDGE );

            Queue responseQueue = this.session.createQueue( AI_RESPONSE_QUEUE );
            MessageConsumer aiResponseConsumer = this.session.createConsumer( responseQueue );
            aiResponseConsumer.setMessageListener( new AiResponseQueueListener() );

            Queue heartbeatQueue = this.session.createQueue( HEARTBEAT_RESPONSE_QUEUE );
            MessageConsumer heartbeatResponseConsumer = this.session.createConsumer( heartbeatQueue );
            heartbeatResponseConsumer.setMessageListener( new HeartbeatResponseQueueListener() );

            this.aiRequestQueueSender = new AiRequestQueueSender( this.session );
            this.heartbeatRequestQueueSender = new HeartbeatRequestQueueSender( this.session );

            this.connection.start();
            LOGGER.info( "ActiveMQ connection established. Listening on queues: {}, {}", AI_RESPONSE_QUEUE, HEARTBEAT_RESPONSE_QUEUE );
        }
    }

    public void stop()
    {
        try
        {
            if ( this.connection != null )
            {
                this.connection.close();
                LOGGER.info( "ActiveMQ connection closed" );
            }
        }
        catch ( JMSException e )
        {
            LOGGER.error( "Failed to close ActiveMQ connection", e );
        }
    }

    public AiRequestQueueSender getAiRequestQueueSender()
    {
        return this.aiRequestQueueSender;
    }

    public HeartbeatRequestQueueSender getHeartbeatRequestQueueSender()
    {
        return this.heartbeatRequestQueueSender;
    }
}