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

import de.kaiserpfalzEdv.office.tenants.api.TenantNotificationException;
import de.kaiserpfalzEdv.office.tenants.api.commands.CreateTenantCommand;
import de.kaiserpfalzEdv.office.tenants.api.notifications.CreateTenantNotification;
import de.kaiserpfalzEdv.office.tenants.query.QueryMessageHandler;
import de.kaiserpfalzEdv.office.tenants.query.TenantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class ReadModelHandlerTest {
    private static final Logger LOG = LoggerFactory.getLogger(ReadModelHandlerTest.class);

    private TenantRepository repository;

    private QueryMessageHandler service;


    public void createTenant() throws TenantNotificationException {
        CreateTenantCommand cmd = new CreateTenantCommand("I'14-002", "Tenant Test");
        CreateTenantNotification notification = new CreateTenantNotification(cmd, cmd.updateTenant(null));

        service.handle(notification);
    }


    @BeforeMethod
    protected void createService() {
        service = null;
    }
}
