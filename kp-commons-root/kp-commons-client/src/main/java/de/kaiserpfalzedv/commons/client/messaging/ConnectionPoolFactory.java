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

package de.kaiserpfalzedv.commons.client.messaging;

import de.kaiserpfalzedv.commons.api.init.Closeable;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.persistence.PreRemove;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-22
 */
public class ConnectionPoolFactory extends BasePooledObjectFactory<Connection> implements Closeable {
    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPoolFactory.class);

    private int counter = 0;
    private ConnectionFactory factory;
    private String clientId;

    private String userName;
    private String passWord;

    public ConnectionPoolFactory(
            final ConnectionFactory connectionFactory,
            final String clientId,
            final String userName,
            final String passWord
    ) {
        this(connectionFactory, clientId);

        this.userName = userName;
        this.passWord = passWord;
    }

    public ConnectionPoolFactory(final ConnectionFactory connectionFactory, final String clientId) {
        LOG.debug("Creating JMS connection pool: client={}, connectionFactory={}", clientId, connectionFactory);
        if (!LOG.isTraceEnabled()) {
            LOG.debug("Enable TRACE for '{}' to display connections leases.", ConnectionPoolFactory.class.getCanonicalName());
        }

        this.factory = connectionFactory;
        this.clientId = clientId;
    }


    public ConnectionFactory getConnectionFactory() {
        return factory;
    }


    @Override
    public Connection create() throws Exception {
        Connection result;

        counter++;

        if (isNotBlank(userName) && isNotBlank(passWord)) {
            result = factory.createConnection(userName, passWord);
        } else {
            result = factory.createConnection();
        }
        result.setClientID(clientId + counter);
        result.start();

        LOG.trace("Created connection: {}", result);
        return result;
    }

    @Override
    public PooledObject<Connection> wrap(Connection value) {
        return new DefaultPooledObject<>(value);
    }

    @Override
    public void destroyObject(PooledObject<Connection> p)
            throws Exception {

        LOG.trace("Destroying connection: {}", p.getObject());
        p.getObject().close();
    }

    @Override
    public boolean validateObject(PooledObject<Connection> value) {
        try {
            return value.getObject().getClientID().startsWith(clientId);
        } catch (NullPointerException | JMSException e) {
            return false;
        }

    }

    @Override
    public void activateObject(PooledObject<Connection> p)
            throws Exception {
        p.getObject().start();
        LOG.trace("Activated connection: {}", p.getObject());
    }

    @Override
    public void passivateObject(PooledObject<Connection> p)
            throws Exception {
        p.getObject().stop();
        LOG.trace("Passivated connection: {}", p.getObject());
    }

    @PreRemove
    @Override
    public void close() {
        LOG.debug("Closing ConnectionPoolFactory: {}", this);
    }
}
