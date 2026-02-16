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

    private final String brokerUrl;
    private final String username;
    private final String password;

    private Connection connection;
    private Session session;
    private AiRequestQueueSender requestSender;

    public ActiveMqConnectionManager( String brokerUrl, String username, String password )
    {
        this.brokerUrl = brokerUrl;
        this.username = username;
        this.password = password;
    }

    public void start() throws JMSException
    {
        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory( brokerUrl );
        connectionFactory.setUserName( username );
        connectionFactory.setPassword( password );

        connection = connectionFactory.createConnection();
        session = connection.createSession( false, Session.AUTO_ACKNOWLEDGE );

        Queue responseQueue = session.createQueue( AI_RESPONSE_QUEUE );
        MessageConsumer consumer = session.createConsumer( responseQueue );
        consumer.setMessageListener( new AiResponseQueueListener() );

        requestSender = new AiRequestQueueSender( session );

        connection.start();
        LOGGER.info( "ActiveMQ connection established. Listening on queue: {}", AI_RESPONSE_QUEUE );
    }

    public void stop()
    {
        try
        {
            if ( connection != null )
            {
                connection.close();
                LOGGER.info( "ActiveMQ connection closed" );
            }
        }
        catch ( JMSException e )
        {
            LOGGER.error( "Failed to close ActiveMQ connection", e );
        }
    }

    public AiRequestQueueSender getRequestSender()
    {
        return requestSender;
    }
}