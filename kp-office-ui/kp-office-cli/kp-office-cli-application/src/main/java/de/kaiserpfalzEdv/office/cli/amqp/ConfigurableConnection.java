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

import com.rabbitmq.client.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.connection.Connection;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.03.15 22:45
 */
public class ConfigurableConnection implements Connection {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurableConnection.class);

    private Connection                    connection;
    private ConfigurableConnectionFactory factory;

    ConfigurableConnection(final ConfigurableConnectionFactory factory) {
        this.factory = factory;
    }

    private void initializeConnection() {
        if (connection != null)
            return;

        synchronized (this) {
            if (connection != null)
                return;

            connection = factory.createRealConnection();
        }
    }


    @Override
    public Channel createChannel(boolean transactional) throws AmqpException {
        initializeConnection();

        return connection.createChannel(transactional);
    }

    @Override
    public void close() throws AmqpException {
        initializeConnection();

        connection.close();
    }

    @Override
    public boolean isOpen() {
        initializeConnection();

        return connection.isOpen();
    }
}
