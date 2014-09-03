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

package de.kaiserpfalzEdv.office.tenants.query.test;

import de.kaiserpfalzEdv.office.tenants.Tenant;
import de.kaiserpfalzEdv.office.tenants.commands.CreateTenantCommand;
import de.kaiserpfalzEdv.office.tenants.commands.DeleteTenantCommand;
import de.kaiserpfalzEdv.office.tenants.commands.TenantQueryByNumber;
import de.kaiserpfalzEdv.office.tenants.commands.TenantQueryCommand;
import de.kaiserpfalzEdv.office.tenants.notifications.CreateTenantNotification;
import de.kaiserpfalzEdv.office.tenants.notifications.DeleteTenantNotification;
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
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
@Test
@ContextConfiguration("/beans.xml")
public class QueryHandlerIT extends AbstractTestNGSpringContextTests {
    private static final Logger LOG = LoggerFactory.getLogger(QueryHandlerIT.class);

    @Inject
    @Named("amqp.tenant.notification")
    private AmqpTemplate amqpNotification;

    @Inject
    @Named("amqp.tenant.query")
    private AmqpTemplate amqpQuery;

    @Test
    public void createTenant() {
        CreateTenantCommand command = new CreateTenantCommand("I'14-002", "Second Tenant");
        CreateTenantNotification notification = new CreateTenantNotification(command, command.updateTenant(null));

        amqpNotification.convertAndSend(notification, new Process());
    }

    @Test(dependsOnMethods = {"createTenant"})
    public void deleteTenant() {
        TenantQueryCommand query = new TenantQueryByNumber("I'14-002");

        Tenant tenant = (Tenant) amqpQuery.convertSendAndReceive(query, new Process());
        LOG.info("Queried for tenant: {}", tenant);

        DeleteTenantCommand cmd = new DeleteTenantCommand(tenant.getId());
        DeleteTenantNotification notification = new DeleteTenantNotification(cmd, tenant);

        amqpNotification.convertAndSend(notification, new Process());
        LOG.info("Deleted tenant: {}", tenant);
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
