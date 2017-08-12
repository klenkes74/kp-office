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

package de.kaiserpfalzedv.office.common.client.messaging;

import java.util.Properties;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.common.api.config.ConfigReader;
import de.kaiserpfalzedv.office.common.api.init.InitializationException;
import de.kaiserpfalzedv.office.common.api.messaging.MessageListener;
import de.kaiserpfalzedv.office.common.api.messaging.MessageMultiplexer;
import de.kaiserpfalzedv.office.common.api.messaging.MessagingCore;
import de.kaiserpfalzedv.office.common.client.config.ConfigReaderBuilder;
import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.api.jms.ActiveMQJMSClient;
import org.apache.activemq.artemis.api.jms.JMSFactoryType;
import org.apache.activemq.artemis.core.remoting.impl.netty.NettyConnectorFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-22
 */
@ApplicationScoped
public class JMSMessagingCoreImpl implements MessagingCore {
    static private final Logger LOG = LoggerFactory.getLogger(JMSMessagingCoreImpl.class);

    private ConnectionFactory connectionFactory;
    private ObjectPool<Connection> connectionPool;
    private MessageListener listener;
    private String clientId = "client";

    private ConfigReader config;

    @Inject
    public JMSMessagingCoreImpl(
            @NotNull ConfigReader config
    ) {
        this.config = config;
    }


    @PostConstruct
    @Override
    public void init() throws InitializationException {
        init(config);
    }

    @Override
    public void init(final Properties config) throws InitializationException {
        init(new ConfigReaderBuilder().withProperties(config).build());
    }

    public void init(final ConfigReader config) throws InitializationException {
        this.clientId = config.getEntry("messaging.client-id", UUID.randomUUID().toString());

        String broker = config.getEntry("messaging.activemq.broker", "vm://0");
        String user = config.getEntry("messaging.activemq.user", null);
        String pass = config.getEntry("messaging.activemq.pass", null);

        TransportConfiguration tc;
        if (broker.startsWith("vm://")) {
            tc = new TransportConfiguration("org.apache.activemq.artemis.core.remoting.impl.invm.InVMConnectorFactory");
        } else {
            tc = new TransportConfiguration(NettyConnectorFactory.class.getName());
        }

        connectionFactory = ActiveMQJMSClient.createConnectionFactoryWithoutHA(JMSFactoryType.CF, tc);

        if (connectionPool == null) {
            connectionPool = new GenericObjectPool<>(
                    (isNotBlank(user) && isNotBlank(pass))
                            ? new ConnectionPoolFactory(connectionFactory, clientId, user, pass)
                            : new ConnectionPoolFactory(connectionFactory, clientId)
            );
        }

        if (listener == null) {
            listener = new MessageListenerBuilder()
                    .withConnectionPool(connectionPool)
                    .withMultiplexer(new MessageMultiplexerImpl())
                    .build();
        }

        LOG.info("Started messaging core: {}", this);
    }


    @PreDestroy
    @Override
    public void close() {
        listener.close();
        connectionPool.close();

        LOG.info("Stopped messaging core: {}", this);
    }

    @Override
    public ObjectPool<Connection> getConnectionPool() {
        return connectionPool;
    }

    @Override
    public MessageMultiplexer getMultiplexer() {
        return listener.getMultiplexer();
    }

    @Override
    public Destination getReplyTo() {
        return listener.getDestination();
    }

    @Override
    public String getClientId() {
        return clientId;
    }
}
