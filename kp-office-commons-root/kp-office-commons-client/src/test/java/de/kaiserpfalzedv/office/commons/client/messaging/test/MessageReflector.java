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

import de.kaiserpfalzedv.office.common.init.Closeable;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.broker.BrokerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.net.URI;
import java.util.Enumeration;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-23
 */
public class MessageReflector implements Closeable, MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(MessageReflector.class);

    private BrokerService broker;

    private Connection connection;
    private Session session;
    private Destination destination;
    private MessageConsumer consumer;


    public MessageReflector() throws Exception {
        broker = new BrokerService();
        broker.setPersistent(false);
        broker.setVmConnectorURI(new URI("vm://localhost"));
        broker.start();

        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory("vm://localhost");
        connection = connectionFactory.createConnection();
        session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        destination = session.createQueue("reflector");
        consumer = session.createConsumer(destination);
        consumer.setMessageListener(this);

        LOG.info("Started message reflector: " + this);
    }

    @Override
    public void close() {
        closeJmsConsumer();
        closeJmsSession(session);
        closeJmsConnection();
        closeJmsBroker();

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

    private void closeJmsConnection() {
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                LOG.error("Can't close JMS connection: " + connection, e);
            }
        }
    }

    private void closeJmsBroker() {
        if (broker != null) {
            try {
                broker.stop();
            } catch (Exception e) {
                LOG.error("Can't stop JMS broker: " + broker, e);
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

                response = session.createObjectMessage();
                response.setJMSCorrelationID(message.getJMSCorrelationID());

                Enumeration properties = message.getPropertyNames();
                while (properties.hasMoreElements()) {
                    String prop = (String) properties.nextElement();
                    response.setStringProperty(prop, message.getStringProperty(prop));
                }

                producer = session.createProducer(replyTo);
                producer.send(replyTo, response);

                LOG.info("Relfected message: {}", response);
            }
        } catch (JMSException e) {
            LOG.error("Response not sent: " + response, e);
        } finally {
            closeJmsProducer(producer);
            closeJmsSession(session);
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

    private void closeJmsSession(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                LOG.error("Can't close JMS session: " + session, e);
            }
        }
    }
}
