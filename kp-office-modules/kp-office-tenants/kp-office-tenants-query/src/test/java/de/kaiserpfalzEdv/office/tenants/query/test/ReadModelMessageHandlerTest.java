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

import de.kaiserpfalzEdv.office.tenants.api.commands.CreateTenantCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.inject.Inject;

/**
 * @author klenkes
 * @since 2014Q
 */
@Test
@ContextConfiguration("/beans.xml")
public class ReadModelMessageHandlerTest extends AbstractTransactionalTestNGSpringContextTests {
    private static final Logger LOG = LoggerFactory.getLogger(ReadModelMessageHandlerTest.class);

    @Inject
    private AmqpTemplate amqpSender;

    public void sendMessage() {
        amqpSender.convertAndSend(new CreateTenantCommand("testTenant"));
    }
}
