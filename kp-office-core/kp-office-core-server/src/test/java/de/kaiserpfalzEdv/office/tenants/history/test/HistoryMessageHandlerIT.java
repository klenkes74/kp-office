/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.tenants.history.test;

import de.kaiserpfalzEdv.office.tenants.commands.CreateTenantCommand;
import de.kaiserpfalzEdv.office.tenants.commands.DeleteTenantCommand;
import de.kaiserpfalzEdv.office.tenants.notifications.CreateTenantNotification;
import de.kaiserpfalzEdv.office.tenants.notifications.TenantStoreNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
@Named
@Test
@ContextConfiguration("/beans.xml")
public class HistoryMessageHandlerIT extends AbstractTestNGSpringContextTests {
    private static final Logger LOG = LoggerFactory.getLogger(HistoryMessageHandlerIT.class);

    private static final Long CONTAINER_TIMEOUT = 2000L;
    private final List<TenantStoreNotification> notifications = new ArrayList<>();
    private Waiter sleeper = new Waiter(CONTAINER_TIMEOUT);
    @Inject
    @Named("modifyTenant")
    private AmqpTemplate amqpSender;
    private UUID tenantId;
    private CreateTenantCommand creation;
    private DeleteTenantCommand deletion;

    public void sendCreate() {
        creation = new CreateTenantCommand("I'14-001", "History Tenant");

        LOG.info("Sending createCommand: {}", creation);
        amqpSender.convertAndSend(creation, new Process());
    }


    @Test(dependsOnMethods = {"sendCreate"})
    public void sendDelete() throws InterruptedException {
        deletion = new DeleteTenantCommand(creation.getTenantId());

        LOG.info("Sending deleteCommand: {}", deletion);
        amqpSender.convertAndSend(deletion, new Process());
    }

    public void handleNotification(CreateTenantNotification notification) {
        LOG.debug("Received Creation Notification: {}", notification);

        notifications.add(notification);
    }


    public void handleNotification(TenantStoreNotification notification) {
        notifications.add(notification);

        LOG.debug("Received: {}", notification);
    }


    public class Process implements MessagePostProcessor {

        @Override
        public Message postProcessMessage(Message message) throws AmqpException {
            MessageProperties properties = message.getMessageProperties();

            properties.setMessageId(UUID.randomUUID().toString());
            properties.setUserId("test");
            properties.setDeliveryMode(MessageDeliveryMode.PERSISTENT);
            properties.setAppId("Test");

            return message;
        }
    }
}

class Waiter {
    private Long timeout;

    public Waiter(Long timeout) {
        this.timeout = timeout;
    }

    public void sleep() throws InterruptedException {
        Thread.sleep(timeout);
    }
}