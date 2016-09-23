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

package de.kaiserpfalzedv.office.commons.client.messaging.test;

import de.kaiserpfalzedv.office.commons.client.messaging.MessageInfo;
import de.kaiserpfalzedv.office.commons.client.messaging.MessageSender;
import de.kaiserpfalzedv.office.commons.client.messaging.NoBrokerException;
import de.kaiserpfalzedv.office.commons.client.messaging.impl.ActiveMqConnectionPoolFactory;
import de.kaiserpfalzedv.office.commons.client.messaging.impl.MessageSenderImpl;
import de.kaiserpfalzedv.office.commons.client.messaging.impl.NoResponseMessageInfo;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.omg.CORBA.Object;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.Connection;

import static org.junit.Assert.assertEquals;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-22
 */
public class MessageSenderTest {
    private static final Logger LOG = LoggerFactory.getLogger(MessageSenderTest.class);

    private ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost?broker.persistence=false");

    private ObjectPool<Connection> connectionPool = new GenericObjectPool<>(
            new ActiveMqConnectionPoolFactory(connectionFactory, "test-client")
    );

    private MessageReflector reflector;

    private MessageSender<String, String> service;

    public MessageSenderTest() throws Exception {
        reflector = new MessageReflector();
    }

    public void finalize() throws Throwable {
        if (reflector != null) {
            reflector.close();
        }

        connectionPool.close();

        super.finalize();
    }


    @Test
    public void checkSendMessage() throws NoBrokerException {
        LOG.info("Sending message without reflection ...");

        MessageInfo<String> response = service
                .withDestination("reflector")
                .withPayload("Test Payload")
                .sendMessage();

        assertEquals(true, NoResponseMessageInfo.class.isAssignableFrom(response.getClass()));
    }


    @Before
    public void setupService() {
        service = new MessageSenderImpl<>(connectionPool);
    }
}
