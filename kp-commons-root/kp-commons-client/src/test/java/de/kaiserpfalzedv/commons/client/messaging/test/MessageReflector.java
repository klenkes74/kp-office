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

package de.kaiserpfalzedv.commons.client.messaging.test;

import de.kaiserpfalzedv.commons.api.init.Closeable;
import org.apache.commons.pool2.ObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.Enumeration;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-23
 */
public class MessageReflector implements Closeable, MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(MessageReflector.class);

    private ObjectPool<Connection> connectionPool;

    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageConsumer consumer;


    public MessageReflector(ObjectPool<Connection> connectionPool, final String queueName) throws Exception {
        this.connectionPool = connectionPool;
        connection = connectionPool.borrowObject();

        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue(queueName);
        consumer = session.createConsumer(destination);
        consumer.setMessageListener(this);

        LOG.info("Started message reflector: queue={}, connectionPool={}", destination, connectionPool);
    }

    @Override
    public void close() {
        closeJmsConsumer();
        closeJmsSession(session);
        closeJmsConnection();

        LOG.info("Closed message reflector: {}", this);
    }

    private void closeJmsConsumer() {
        if (consumer != null) {
            try {
                consumer.close();
            } catch (JMSException e) {
                LOG.error("Can't close JMS consumer: " + consumer, e);
            }
        }
    }

    private void closeJmsSession(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                LOG.error("Can't close JMS session: " + session, e);
            }
        }
    }

    private void closeJmsConnection() {
        if (connection != null) {
            try {
                connectionPool.returnObject(connection);
            } catch (Exception e) {
                LOG.error("Can't return JMS connection: " + connection, e);

                try {
                    connectionPool.invalidateObject(connection);
                } catch (Exception e1) {
                    LOG.error("Can't invalidate JMS connection in pool: " + connection, e1);

                    try {
                        connection.close();
                    } catch (JMSException e2) {
                        LOG.error("Can't close JMS connection: {}" + connection, e2);
                    }
                }
            }
        }
    }

    @Override
    public void onMessage(Message message) {
        LOG.debug("Received message: {}", message);

        Session session = null;
        MessageProducer producer = null;
        Message response = null;

        try {
            Destination replyTo = message.getJMSReplyTo();

            if (replyTo != null) {
                session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

                if (TextMessage.class.isAssignableFrom(message.getClass())) {
                    response = session.createTextMessage(((TextMessage) message).getText());
                } else {
                    response = session.createObjectMessage(((ObjectMessage) message).getObject());
                }
                response.setJMSCorrelationID(message.getJMSCorrelationID());

                Enumeration properties = message.getPropertyNames();
                while (properties.hasMoreElements()) {
                    String prop = (String) properties.nextElement();
                    response.setStringProperty(prop, message.getStringProperty(prop));
                }

                producer = session.createProducer(replyTo);
                producer.send(response);

                LOG.info("Relfected message: {}", response);
            }
        } catch (JMSException e) {
            LOG.error("Response not sent: " + response, e);
        } finally {
            closeJmsProducer(producer);
        }
    }

    private void closeJmsProducer(MessageProducer producer) {
        if (producer != null) {
            try {
                producer.close();
            } catch (JMSException e) {
                LOG.error("Can't close JMS producer:" + producer, e);
            }
        }
    }
}
