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

package de.kaiserpfalzedv.office.tenant.client;

import java.util.Collection;
import java.util.Properties;
import java.util.UUID;

import javax.inject.Inject;

import de.kaiserpfalzedv.office.common.cdi.Implementation;
import de.kaiserpfalzedv.office.common.client.config.ConfigReader;
import de.kaiserpfalzedv.office.common.client.messaging.MessageInfo;
import de.kaiserpfalzedv.office.common.client.messaging.MessageSender;
import de.kaiserpfalzedv.office.common.client.messaging.MessagingCore;
import de.kaiserpfalzedv.office.common.client.messaging.NoBrokerException;
import de.kaiserpfalzedv.office.common.client.messaging.NoResponseException;
import de.kaiserpfalzedv.office.common.client.messaging.ResponseOfWrongTypeException;
import de.kaiserpfalzedv.office.common.client.messaging.impl.MessageSenderImpl;
import de.kaiserpfalzedv.office.common.init.InitializationException;
import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.TenantExistsException;
import de.kaiserpfalzedv.office.tenant.TenantService;
import de.kaiserpfalzedv.office.tenant.commands.TenantCreateCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantRetrieveCommand;
import de.kaiserpfalzedv.office.tenant.commands.TenantUpdateCommand;
import de.kaiserpfalzedv.office.tenant.replies.TenantContainingBaseReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantCreateReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantRetrieveReply;
import de.kaiserpfalzedv.office.tenant.replies.TenantUpdateReply;

import static de.kaiserpfalzedv.office.common.commands.CrudCommands.CREATE;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-20
 */
@Implementation
public class TenantClient implements TenantService {

    private MessagingCore messaging;
    private ConfigReader config;

    private String destination;


    @Inject
    public TenantClient(final ConfigReader config, final MessagingCore messaging) {
        this.config = config;
        this.messaging = messaging;

        this.destination = config.getEntry("tenant.queue", "tenant");
    }


    @Override
    public Tenant createTenant(Tenant data) throws TenantExistsException {
        MessageSender<TenantCreateCommand, TenantCreateReply> sender = new MessageSenderImpl<>(messaging);

        return getTenantFromResponse(sender, data.getId());
    }

    private Tenant getTenantFromResponse(MessageSender sender, final UUID tenantId) {
        MessageInfo response = null;

        try {
            response = sender
                    .withDestination(destination)
                    .withResponse()
                    .sendMessage();

            return ((TenantContainingBaseReply) response.waitForResponse()).getTenant();
        } catch (NoBrokerException | ResponseOfWrongTypeException | NoResponseException | InterruptedException e) {
            throw new TenantClientCommunicationException(
                    CREATE,
                    UUID.fromString(sender.getCorrelationId()),
                    "",
                    tenantId,
                    e
            );
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }

    @Override
    public Tenant retrieveTenant(UUID id) throws TenantDoesNotExistException {
        MessageSender<TenantRetrieveCommand, TenantRetrieveReply> sender = new MessageSenderImpl<>(messaging);

        return getTenantFromResponse(sender, id);
    }

    @Override
    public Collection<Tenant> retrieveTenants() {
        return null;
    }

    @Override
    public Tenant updateTenant(Tenant data) throws TenantDoesNotExistException {
        MessageSender<TenantUpdateCommand, TenantUpdateReply> sender = new MessageSenderImpl<>(messaging);

        return getTenantFromResponse(sender, data.getId());
    }

    @Override
    public void deleteTenant(UUID id) {

    }

    @Override
    public void init() throws InitializationException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.tenant.client.TenantClient.init
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void init(Properties properties) throws InitializationException {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.tenant.client.TenantClient.init
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void close() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzedv.office.tenant.client.TenantClient.close
        //To change body of implemented methods use File | Settings | File Templates.
    }
}
