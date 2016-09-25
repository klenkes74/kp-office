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
 */

package de.kaiserpfalzedv.office.common.client.messaging.impl;

import javax.jms.Connection;
import javax.jms.JMSException;

import de.kaiserpfalzedv.office.common.init.Closeable;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-22
 */
public class ActiveMqConnectionPoolFactory extends BasePooledObjectFactory<Connection> implements Closeable {

    private int counter = 0;
    private ActiveMQConnectionFactory factory;
    private String clientId;

    private String userName;
    private String passWord;

    public ActiveMqConnectionPoolFactory(
            final ActiveMQConnectionFactory connectionFactory,
            final String clientId,
            final String userName,
            final String passWord
    ) {
        this(connectionFactory, clientId);

        this.userName = userName;
        this.passWord = passWord;
    }

    public ActiveMqConnectionPoolFactory(final ActiveMQConnectionFactory connectionFactory, final String clientId) {
        this.factory = connectionFactory;
        this.clientId = clientId;
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
        result.setClientID(clientId + "-" + counter);
        result.start();

        return result;
    }

    @Override
    public PooledObject<Connection> wrap(Connection value) {
        return new DefaultPooledObject<>(value);
    }

    @Override
    public void destroyObject(PooledObject<Connection> p)
            throws Exception {
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
    }

    @Override
    public void passivateObject(PooledObject<Connection> p)
            throws Exception {
        p.getObject().stop();
    }

    @Override
    public void close() {
    }
}
