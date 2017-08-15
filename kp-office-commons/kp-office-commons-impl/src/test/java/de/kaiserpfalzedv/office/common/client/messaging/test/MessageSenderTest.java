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

package de.kaiserpfalzedv.office.common.client.messaging.test;

import java.io.IOException;
import java.util.Arrays;
import java.util.UUID;

import de.kaiserpfalzedv.office.common.api.messaging.MessageInfo;
import de.kaiserpfalzedv.office.common.api.messaging.MessageSender;
import de.kaiserpfalzedv.office.common.api.messaging.MessagingCore;
import de.kaiserpfalzedv.office.common.api.messaging.NoBrokerException;
import de.kaiserpfalzedv.office.common.api.messaging.NoResponseException;
import de.kaiserpfalzedv.office.common.api.messaging.ResponseOfWrongTypeException;
import de.kaiserpfalzedv.office.common.client.config.DefaultKPOfficeConfiguration;
import de.kaiserpfalzedv.office.common.client.messaging.JMSMessagingCoreImpl;
import de.kaiserpfalzedv.office.common.client.messaging.MessageSenderImpl;
import de.kaiserpfalzedv.office.common.client.messaging.NoResponseMessageInfo;
import org.apache.activemq.artemis.api.core.TransportConfiguration;
import org.apache.activemq.artemis.core.config.Configuration;
import org.apache.activemq.artemis.core.config.impl.ConfigurationImpl;
import org.apache.activemq.artemis.core.remoting.impl.invm.InVMAcceptorFactory;
import org.apache.activemq.artemis.jms.server.config.ConnectionFactoryConfiguration;
import org.apache.activemq.artemis.jms.server.config.JMSConfiguration;
import org.apache.activemq.artemis.jms.server.config.JMSQueueConfiguration;
import org.apache.activemq.artemis.jms.server.config.impl.ConnectionFactoryConfigurationImpl;
import org.apache.activemq.artemis.jms.server.config.impl.JMSConfigurationImpl;
import org.apache.activemq.artemis.jms.server.config.impl.JMSQueueConfigurationImpl;
import org.apache.activemq.artemis.jms.server.embedded.EmbeddedJMS;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-22
 */
public class MessageSenderTest {
    private static final Logger LOG = LoggerFactory.getLogger(MessageSenderTest.class);

    private EmbeddedJMS jmsServer;

    private MessagingCore core;

    private MessageReflector reflector;

    private MessageSender<String, String> service;

    public MessageSenderTest() throws Exception {
        startEmbeddedJmsServer();

        DefaultKPOfficeConfiguration producer = new DefaultKPOfficeConfiguration();

        core = new JMSMessagingCoreImpl(producer.getConfig());
        core.init();

        reflector = new MessageReflector(core.getConnectionPool(), "reflector");
    }

    private void startEmbeddedJmsServer() throws Exception {
        LOG.debug("Starting embedded ActiveMQ Artemis server ...");
        // Step 1. Create ActiveMQ Artemis core configuration, and set the properties accordingly
        Configuration configuration = new ConfigurationImpl()
                .setPersistenceEnabled(false)
                .setJournalDirectory("target/data/artemis/journal")
                .setCreateJournalDir(true)
                .setBindingsDirectory("target/data/artemis/bindings")
                .setCreateBindingsDir(true)
                .setLargeMessagesDirectory("target/data/artemis")
                .setSecurityEnabled(false)
                .addAcceptorConfiguration(new TransportConfiguration(InVMAcceptorFactory.class.getName()))
                .addConnectorConfiguration("connector", new TransportConfiguration(InVMAcceptorFactory.class.getName()));

        // Step 2. Create the JMS configuration
        JMSConfiguration jmsConfig = new JMSConfigurationImpl();

        // Step 3. Configure the JMS ConnectionFactory
        ConnectionFactoryConfiguration cfConfig = new ConnectionFactoryConfigurationImpl()
                .setName("cf")
                .setConnectorNames(Arrays.asList("connector"))
                .setBindings("cf");
        jmsConfig.getConnectionFactoryConfigurations()
                 .add(cfConfig);

        // Step 4. Configure the JMS Queue
        JMSQueueConfiguration queueConfig = new JMSQueueConfigurationImpl()
                .setName("reflector")
                .setDurable(false)
                .setBindings("queue/reflector");
        jmsConfig.getQueueConfigurations().add(queueConfig);

        // Step 5. Start the JMS Server using the ActiveMQ Artemis core server and the JMS configuration
        jmsServer = new EmbeddedJMS().setConfiguration(configuration).setJmsConfiguration(jmsConfig).start();

        LOG.info("Started embedded JMS server: connectionFactory={}, queue={}", jmsServer.lookup("cf"), jmsServer.lookup("queue/reflector"));
    }

    public void finalize() throws Throwable {
        if (reflector != null) {
            reflector.close();
        }

        if (core != null) {
            core.close();
        }

        if (jmsServer != null) {
            jmsServer.stop();
            LOG.info("Stopped embedded JMS server.");
        }

        super.finalize();
    }


    @Test
    public void checkSendMessage() throws NoBrokerException, ResponseOfWrongTypeException, NoResponseException, InterruptedException, IOException {
        LOG.info("Sending message without reflection ...");

        MessageInfo<String> response = service
                .withMessageId(UUID.randomUUID().toString())
                .withCorrelationId(UUID.randomUUID().toString())
                .withPersistentDelivery(false)
                .withPriority(9)
                .withTTL(1000L)
                .withDestination("reflector")
                .withPayload("Test Payload")
                .withCustomHeader("kpSecurity", UUID.randomUUID().toString())
                .withResponse()
                .sendMessage();

        assertFalse(NoResponseMessageInfo.class.isAssignableFrom(response.getClass()));

        String result = response.waitForResponse();
        response.close();

        assertEquals(result, "Test Payload");
    }


    @Before
    public void setupService() {
        service = new MessageSenderImpl<>(core);
    }
}
