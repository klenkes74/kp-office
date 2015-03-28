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

package de.kaiserpfalzEdv.office.cli.amqp;

import com.google.common.eventbus.Subscribe;
import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.office.cli.ApplicationVersion;
import de.kaiserpfalzEdv.office.cli.CliModule;
import de.kaiserpfalzEdv.office.cli.executor.events.ConfigureCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.Connection;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionListener;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.03.15 22:01
 */
@Named
public class ConfigurableConnectionFactory implements ConnectionFactory, CliModule {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurableConnectionFactory.class);

    private String host;
    private int    port;
    private String virtual;
    private String username;
    private String password;

    private CachingConnectionFactory factory;

    private EventBusHandler bus;


    @Inject
    public ConfigurableConnectionFactory(EventBusHandler bus) {
        this.bus = bus;
        LOG.trace("Created: {}", this);
        LOG.trace("{} event bus: {}", this, this.bus);
    }


    @PostConstruct
    public void init() {
        bus.register(this);

        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        bus.unregister(this);

        factory.clearConnectionListeners();
        factory = null;

        LOG.trace("Destroyed: {}", this);
    }


    @SuppressWarnings("UnusedDeclaration") // Will be called via EventBus ...
    @Subscribe
    public void configure(ConfigureCommand command) {
        Properties configuration = command.getProperties();

        host = configuration.getProperty("amqp.host", "localhost");
        port = Integer.parseUnsignedInt(configuration.getProperty("amqp.port", "5672"), 10);
        virtual = configuration.getProperty("amqp.virtual", "kpo");
        username = configuration.getProperty("amqp.user", "guest");
        password = configuration.getProperty("amqp.password", "");
    }


    private void initializeFactory() {
        if (factory != null)
            return;

        synchronized (this) {
            if (factory != null)
                return;

            if (isNotBlank(host) && port != 0 && isNotBlank(username)) {
                factory = new CachingConnectionFactory(host, port);
                factory.setUsername(username);

                if (isNotBlank(password))
                    factory.setPassword(password);

                if (isNotBlank(virtual))
                    factory.setVirtualHost(virtual);

                listeners.forEach(factory::addConnectionListener);

                LOG.info(
                        "Created AMQP Connection Factory. host={}, port={}, virtual={}, user={}, pass=***",
                        host, port, virtual, username
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
        return host;
    }

    @Override
    public int getPort() {
        return port;
    }

    @Override
    public String getVirtualHost() {
        return virtual;
    }


    private final HashSet<ConnectionListener> listeners = new HashSet<>();

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


    @Override
    public Versionable getVersion() {
        return ApplicationVersion.INSTANCE;
    }

    @Override
    public String getShortDescription() {
        return "Configurable AMQP Connection Factory";
    }

    @Override
    public String getDescription() {
        return "A lazy initializable connection factory for the CLI application.";
    }

    @Override
    public boolean isInitialized() {
        return factory != null;
    }

    @Override
    public String getDisplayName() {
        return "AMQP Connection";
    }
}
