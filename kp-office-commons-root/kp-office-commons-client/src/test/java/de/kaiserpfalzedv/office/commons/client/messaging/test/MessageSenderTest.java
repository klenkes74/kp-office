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

package de.kaiserpfalzedv.office.commons.client.messaging.test;

import java.util.UUID;

import de.kaiserpfalzedv.office.commons.client.messaging.MessageInfo;
import de.kaiserpfalzedv.office.commons.client.messaging.MessageSender;
import de.kaiserpfalzedv.office.commons.client.messaging.MessagingCore;
import de.kaiserpfalzedv.office.commons.client.messaging.NoBrokerException;
import de.kaiserpfalzedv.office.commons.client.messaging.NoResponseException;
import de.kaiserpfalzedv.office.commons.client.messaging.ResponseOfWrongTypeException;
import de.kaiserpfalzedv.office.commons.client.messaging.impl.ActiveMQMessagingCoreImpl;
import de.kaiserpfalzedv.office.commons.client.messaging.impl.MessageSenderImpl;
import de.kaiserpfalzedv.office.commons.client.messaging.impl.NoResponseMessageInfo;
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

    private MessagingCore core;

    private MessageReflector reflector;

    private MessageSender<String, String> service;

    public MessageSenderTest() throws Exception {
        core = new ActiveMQMessagingCoreImpl();
        core.init();

        reflector = new MessageReflector(core.getConnectionPool(), "reflector");
    }

    public void finalize() throws Throwable {
        if (reflector != null) {
            reflector.close();
        }

        core.close();

        super.finalize();
    }


    @Test
    public void checkSendMessage() throws NoBrokerException, ResponseOfWrongTypeException, NoResponseException, InterruptedException {
        LOG.info("Sending message without reflection ...");

        MessageInfo<String> response = service
                .withMessageId(UUID.randomUUID().toString())
                .withCorrelationId(UUID.randomUUID().toString())
                .withPersistentDelivery(false)
                .withPriority(9)
                .withTTL(1000L)
                .withDestination("reflector")
                .withPayload("Test Payload")
                .withCustomHeader("kp-security", UUID.randomUUID().toString())
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
