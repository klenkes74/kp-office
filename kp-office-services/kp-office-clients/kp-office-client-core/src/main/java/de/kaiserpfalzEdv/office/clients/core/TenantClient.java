/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.clients.core;

import de.kaiserpfalzEdv.commons.jee.paging.Page;
import de.kaiserpfalzEdv.commons.jee.paging.Pageable;
import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.commons.notifications.Failure;
import de.kaiserpfalzEdv.office.core.security.InvalidLoginException;
import de.kaiserpfalzEdv.office.core.security.OfficeLoginTicket;
import de.kaiserpfalzEdv.office.core.security.commands.SecurityLoginCommand;
import de.kaiserpfalzEdv.office.core.security.notifications.OfficeLoginTicketNotification;
import de.kaiserpfalzEdv.office.core.tenants.NoSuchTenantException;
import de.kaiserpfalzEdv.office.core.tenants.Tenant;
import de.kaiserpfalzEdv.office.core.tenants.TenantAlreadyExistsException;
import de.kaiserpfalzEdv.office.core.tenants.TenantService;
import de.kaiserpfalzEdv.office.core.tenants.commands.ListAllTenantsCommand;
import de.kaiserpfalzEdv.office.core.tenants.commands.TenantCreateCommand;
import de.kaiserpfalzEdv.office.core.tenants.commands.TenantDeleteCommand;
import de.kaiserpfalzEdv.office.core.tenants.commands.TenantLoadByIdCommand;
import de.kaiserpfalzEdv.office.core.tenants.commands.TenantLoadByNumberCommand;
import de.kaiserpfalzEdv.office.core.tenants.commands.TenantUpdateCommand;
import de.kaiserpfalzEdv.office.core.tenants.notifications.TenantDataNotification;
import de.kaiserpfalzEdv.office.core.tenants.notifications.TenantListNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.UUID;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Client;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 22:40
 */
@Named
@KPO(Client)
public class TenantClient implements TenantService {
    private static final Logger LOG = LoggerFactory.getLogger(TenantClient.class);


    private static final String MESSAGE_EXCHANGE = "kpo.core";
    private static final String ROUTING_KEY      = "core.tenants";


    private RabbitTemplate sender;


    @Inject
    public TenantClient(final RabbitTemplate sender) {
        this.sender = sender;

        LOG.trace("Created/Initialized: {}", this);
    }


    public OfficeLoginTicket login(@NotNull String account, @NotNull String password) throws InvalidLoginException {
        Object result = sender.convertSendAndReceive(MESSAGE_EXCHANGE, ROUTING_KEY, new SecurityLoginCommand(account, password));

        if (result != null) {
            try {
                return ((OfficeLoginTicketNotification) result).getTicket();
            } catch (ClassCastException e) {
                throw new InvalidLoginException(account);
            }
        }

        throw new InvalidLoginException(account);
    }

    @Override
    public void create(@NotNull Tenant tenant) throws TenantAlreadyExistsException {
        Object result = sender.convertSendAndReceive(MESSAGE_EXCHANGE, ROUTING_KEY, new TenantCreateCommand(tenant));

        if (result != null && Failure.class.isAssignableFrom(result.getClass())) {
            throw new TenantAlreadyExistsException(tenant, ((Failure) result).getMessage());
        }
    }

    @Override
    public Tenant retrieve(@NotNull UUID id) throws NoSuchTenantException {
        Object result = sender.convertSendAndReceive(MESSAGE_EXCHANGE, ROUTING_KEY, new TenantLoadByIdCommand(id));

        if (result != null) {
            if (Failure.class.isAssignableFrom(result.getClass()))
                throw new NoSuchTenantException(id);

            return ((TenantDataNotification) result).getTenant();
        }

        throw new NoSuchTenantException(id);
    }

    @Override
    public Tenant retrieve(@NotNull String displayNumber) throws NoSuchTenantException {
        Object result = sender.convertSendAndReceive(MESSAGE_EXCHANGE, ROUTING_KEY, new TenantLoadByNumberCommand(displayNumber));

        if (result != null) {
            if (Failure.class.isAssignableFrom(result.getClass()))
                throw new NoSuchTenantException(displayNumber);

            return ((TenantDataNotification) result).getTenant();
        }

        throw new NoSuchTenantException(displayNumber);
    }

    @Override
    public void update(@NotNull UUID id, @NotNull Tenant tenant) throws NoSuchTenantException {
        Object result = sender.convertSendAndReceive(MESSAGE_EXCHANGE, ROUTING_KEY, new TenantUpdateCommand(id, tenant));

        if (result != null && Failure.class.isAssignableFrom(result.getClass())) {
            throw new NoSuchTenantException(id);
        }
    }

    @Override
    public void delete(@NotNull Tenant tenant) {
        delete(tenant.getId());
    }

    @Override
    public void delete(@NotNull UUID id) {
        sender.convertAndSend(MESSAGE_EXCHANGE, ROUTING_KEY, new TenantDeleteCommand(id));
    }

    @Override
    public Page<Tenant> listTenants(Pageable pageable) {
        Object result = sender.convertSendAndReceive(MESSAGE_EXCHANGE, ROUTING_KEY, new ListAllTenantsCommand(pageable));

        if (result != null) {
            return ((TenantListNotification) result).getTenants();
        }

        return null;
    }

    @Override
    public Page<Tenant> listTenants(Tenant tenant, Pageable pageable) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }

    @Override
    public Page<Tenant> listTenants(UUID tenant, Pageable pageable) {
        throw new UnsupportedOperationException("Not yet implemented.");
    }
}