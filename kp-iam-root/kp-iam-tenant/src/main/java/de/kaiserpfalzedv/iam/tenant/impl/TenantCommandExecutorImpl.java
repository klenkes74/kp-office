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

package de.kaiserpfalzedv.iam.tenant.impl;

import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import de.kaiserpfalzedv.commons.api.action.CommandExecutionException;
import de.kaiserpfalzedv.commons.api.action.commands.BaseCommand;
import de.kaiserpfalzedv.commons.api.action.replies.BaseReply;
import de.kaiserpfalzedv.commons.api.action.replies.CrudReplyBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;
import de.kaiserpfalzedv.iam.tenant.api.TenantCommandExecutor;
import de.kaiserpfalzedv.iam.tenant.api.TenantDoesNotExistException;
import de.kaiserpfalzedv.iam.tenant.api.TenantExistsException;
import de.kaiserpfalzedv.iam.tenant.api.TenantService;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantCreateCommand;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantDeleteCommand;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantRetrieveCommand;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantUpdateCommand;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantCreateReply;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantCrudReplyBuilderCreator;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantDeleteReply;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantRetrieveReply;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantUpdateReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
@Dependent
public class TenantCommandExecutorImpl implements TenantCommandExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(TenantWorker.class);

    private UUID tenantWorkerId = UUID.randomUUID();

    private TenantService service;
    private TenantCrudReplyBuilderCreator creator;

    @Inject
    public TenantCommandExecutorImpl(
            final TenantService tenantService,
            final TenantCrudReplyBuilderCreator creator
    ) {
        this.service = tenantService;
        this.creator = creator;
    }


    @PostConstruct
    public void init() {
        LOG.debug("Tenant Executor created: {}", tenantWorkerId);
    }

    @PreDestroy
    public void close() {
        LOG.debug("Tenant Executor destroyed: {}", tenantWorkerId);
    }


    public Optional<TenantCreateReply> execute(TenantCreateCommand command) throws CommandExecutionException {
        try {
            Tenant data = service.create(command.getTenant());

            TenantCreateReply result
                    = new CrudReplyBuilder<TenantCreateReply, TenantCreateCommand, Tenant>(TenantCreateReply.class, creator)
                    .withCommand(command)
                    .withSource(tenantWorkerId)
                    .withData(data)
                    .build();

            return Optional.ofNullable(result);
        } catch (TenantExistsException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new CommandExecutionException(command, e);
        }
    }

    @Override
    public Optional<TenantRetrieveReply> execute(TenantRetrieveCommand command) throws CommandExecutionException {
        PagedListable<Tenant> data = service.retrieve(command.getPredicate(), command.getPage());

        TenantRetrieveReply result
                = new CrudReplyBuilder<TenantRetrieveReply, TenantRetrieveCommand, Tenant>(TenantRetrieveReply.class, creator)
                .withCommand(command)
                .withSource(tenantWorkerId)
                .withDataPage(data)
                .build();

        return Optional.of(result);
    }

    @Override
    public Optional<TenantUpdateReply> execute(TenantUpdateCommand command) throws CommandExecutionException {
        try {
            Tenant data = service.update(command.getTenant());

            TenantUpdateReply result
                    = new CrudReplyBuilder<TenantUpdateReply, TenantUpdateCommand, Tenant>(TenantUpdateReply.class, creator)
                    .withCommand(command)
                    .withSource(tenantWorkerId)
                    .withData(data)
                    .build();

            return Optional.ofNullable(result);
        } catch (TenantDoesNotExistException | TenantExistsException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            throw new CommandExecutionException(command, e);
        }
    }

    @Override
    public Optional<TenantDeleteReply> execute(TenantDeleteCommand command) throws CommandExecutionException {
        service.delete(command.getTenant());

        TenantDeleteReply result
                = new CrudReplyBuilder<TenantDeleteReply, TenantDeleteCommand, Tenant>(TenantRetrieveReply.class, creator)
                .withCommand(command)
                .withSource(tenantWorkerId)
                .build();

        return Optional.ofNullable(result);
    }


    @Override
    public Optional<? extends BaseReply> execute(BaseCommand command) throws CommandExecutionException {
        return command.execute(this);
    }
}
