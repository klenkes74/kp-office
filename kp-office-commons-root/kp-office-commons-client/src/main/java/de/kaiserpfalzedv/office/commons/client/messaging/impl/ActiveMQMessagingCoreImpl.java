/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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
 *
 */

package de.kaiserpfalzedv.office.commons.client.messaging.impl;

import de.kaiserpfalzedv.office.common.init.InitializationException;
import de.kaiserpfalzedv.office.commons.client.config.ConfigReader;
import de.kaiserpfalzedv.office.commons.client.config.impl.ConfigReaderBuilder;
import de.kaiserpfalzedv.office.commons.client.messaging.MessageListener;
import de.kaiserpfalzedv.office.commons.client.messaging.MessageMultiplexer;
import de.kaiserpfalzedv.office.commons.client.messaging.MessagingCore;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.Destination;
import java.util.Properties;
import java.util.UUID;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-22
 */
public class ActiveMQMessagingCoreImpl implements MessagingCore {
    static private final Logger LOG = LoggerFactory.getLogger(ActiveMQMessagingCoreImpl.class);

    private GenericObjectPool<Connection> connectionPool;
    private MessageMultiplexer multiplexer;
    private MessageListener listener;


    public ActiveMQMessagingCoreImpl() { }

    public ActiveMQMessagingCoreImpl(
            final GenericObjectPool<Connection> connectionPool,
            final MessageListener listener
    ) {
        this.connectionPool = connectionPool;
        this.listener = listener;
        this.multiplexer = new MessageMultiplexerImpl(listener.getDestination());
    }


    @Override
    public void init() throws InitializationException {
        init(new ConfigReaderBuilder().build());
    }

    @Override
    public void init(final Properties config) throws InitializationException {
        init(new ConfigReaderBuilder().withProperties(config).build());
    }

    private void init(final ConfigReader config) {
        String clientId = config.getEntry("messaging.client-id", UUID.randomUUID().toString());
        String broker = config.getEntry("messaging.activemq.broker", "vm://localhost?broker.persistence=false");

        ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(broker);

        if (connectionPool == null) {
            connectionPool = new GenericObjectPool<>(
                    new ActiveMqConnectionPoolFactory(connectionFactory, clientId)
            );
        }

        if (listener == null) {
            listener = new MessageListenerBuilder()
                    .withConnectionPool(connectionPool)
                    .withMultiplexer(multiplexer)
                    .build();
        }

        if (multiplexer == null) {
            multiplexer = new MessageMultiplexerImpl(listener.getDestination());
        }
        listener.setMultiplexer(multiplexer);

        LOG.info("Started messaging core: {}", this);
    }


    @Override
    public void close() {
        listener.close();
        multiplexer.close();
        connectionPool.close();

        LOG.info("Stopped messaging core: {}", this);
    }

    @Override
    public ObjectPool<Connection> getConnectionPool() {
        return connectionPool;
    }

    @Override
    public Destination getReplyTo() {
        return listener.getDestination();
    }
}
