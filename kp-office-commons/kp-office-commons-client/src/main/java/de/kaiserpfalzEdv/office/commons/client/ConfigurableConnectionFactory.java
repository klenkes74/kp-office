/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.commons.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;

import javax.annotation.PreDestroy;
import java.io.Serializable;
import java.util.HashSet;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.03.15 22:01
 */
public class ConfigurableConnectionFactory implements ConnectionFactory, Serializable {
    private static final Logger                      LOG       = LoggerFactory.getLogger(ConfigurableConnectionFactory.class);
    private final        HashSet<ConnectionListener> listeners = new HashSet<>();
    private AmqpConfigurationProperties configuration;
    private CachingConnectionFactory    factory;

    public ConfigurableConnectionFactory(AmqpConfigurationProperties configuration) {
        LOG.trace("Created: {}", this);

        this.configuration = configuration;
        LOG.trace("   configuration: {}", this.configuration);

        LOG.debug("Initialized: {}", this);

    }

    @PreDestroy
    public void close() {
        factory.clearConnectionListeners();
        factory = null;

        LOG.trace("Destroyed: {}", this);
    }

    private void initializeFactory() {
        if (factory != null)
            return;

        synchronized (this) {
            if (factory != null)
                return;

            if (configuration != null) {
                factory = new CachingConnectionFactory(configuration.getHost(), configuration.getPort());
                factory.setUsername(configuration.getUsername());

                if (isNotBlank(configuration.getPassword()))
                    factory.setPassword(configuration.getPassword());

                if (isNotBlank(configuration.getVirtual()))
                    factory.setVirtualHost(configuration.getVirtual());

                listeners.forEach(factory::addConnectionListener);

                LOG.info(
                        "Created AMQP Connection Factory. host={}, port={}, virtual={}, user={}, pass=***",
                        configuration.getHost(), configuration.getPort(), configuration.getVirtual(), configuration.getUsername()
                );

            } else {
                throw new IllegalStateException("Sorry, no host and port for AMQP broker given!");
            }
        }
    }

    @Override
    public Connection createConnection() throws AmqpException {
        return new ConfigurableConnection(this);
    }

    Connection createRealConnection() throws AmqpException {
        initializeFactory();

        return factory.createConnection();
    }

    @Override
    public String getHost() {
        return configuration.getHost();
    }

    @Override
    public int getPort() {
        return configuration.getPort();
    }

    @Override
    public String getVirtualHost() {
        return configuration.getVirtual();
    }

    @Override
    public void addConnectionListener(ConnectionListener listener) {
        if (factory != null) {
            factory.addConnectionListener(listener);
        }

        listeners.add(listener);
    }

    @Override
    public boolean removeConnectionListener(ConnectionListener listener) {
        if (factory != null) {
            factory.removeConnectionListener(listener);
        }

        return listeners.remove(listener);
    }

    @Override
    public void clearConnectionListeners() {
        if (factory != null)
            factory.clearConnectionListeners();
    }
}
