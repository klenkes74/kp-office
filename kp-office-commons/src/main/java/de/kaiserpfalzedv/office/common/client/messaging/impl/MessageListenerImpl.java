/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.kaiserpfalzedv.office.common.client.messaging.impl;

import java.util.Properties;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.Session;

import de.kaiserpfalzedv.office.common.api.init.InitializationException;
import de.kaiserpfalzedv.office.common.client.messaging.MessageListener;
import de.kaiserpfalzedv.office.common.client.messaging.MessageMultiplexer;
import de.kaiserpfalzedv.office.common.client.messaging.NoCorrelationInMessageException;
import de.kaiserpfalzedv.office.common.client.messaging.NoListenerForCorrelationId;
import org.apache.commons.pool2.ObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-22
 */
class MessageListenerImpl implements MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(MessageListenerImpl.class);

    private ObjectPool<Connection> connectionPool;

    private Connection connection;
    private Session session;
    private MessageConsumer consumer;
    private Destination destination;

    private MessageMultiplexer multiplexer;


    MessageListenerImpl(
            final ObjectPool<Connection> connectionPool,
            final MessageMultiplexer multiplexer) {
        this.connectionPool = connectionPool;
        this.multiplexer = multiplexer;
    }


    @Override
    public void onMessage(Message message) {
        try {
            multiplexer.multiplex(message);
        } catch (NoCorrelationInMessageException | NoListenerForCorrelationId e) {
            LOG.error(e.getMessage(), e);

        }
    }

    @Override
    public Destination getDestination() {
        return destination;
    }

    public MessageMultiplexer getMultiplexer() {
        return multiplexer;
    }

    @Override
    public void init() throws InitializationException {
        try {
            connection = connectionPool.borrowObject();
            session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            destination = session.createTemporaryQueue();
            consumer = session.createConsumer(destination);
            consumer.setMessageListener(this);
        } catch (Exception e) {
            try {
                throw new InitializationException(MessageListenerImpl.class, "Can't create the JMS Session", e);
            } finally {
                close();
            }
        }

        LOG.info("Started messaging listener: {}", multiplexer);
    }

    public void setMultiplexer(final MessageMultiplexer multiplexer) {
        this.multiplexer = multiplexer;
    }

    @Override
    public void init(Properties properties) throws InitializationException {
        init();
    }

    @Override
    public void close() {
        closeConsumer();
        multiplexer.close();
        closeSession();
        closeConnectionPool();

        LOG.info("Closed messaging listener: {}", multiplexer);
    }

    private void closeConsumer() {
        if (consumer != null) {
            try {
                consumer.close();
            } catch (JMSException e) {
                LOG.error("Can't close the message consumer: {}", consumer);
            }
        }
    }

    private void closeSession() {
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                LOG.error("Can't close JMS session: {}", session);
            }
        }
    }

    private void closeConnectionPool() {
        try {
            connectionPool.returnObject(connection);
        } catch (Exception e) {
            LOG.error("Can't close JMS connection: {}", connection);

            try {
                connectionPool.invalidateObject(connection);
            } catch (Exception e1) {
                LOG.error("Can't invalidate JMS connection: {}", connection);
            }
        }
    }


}
