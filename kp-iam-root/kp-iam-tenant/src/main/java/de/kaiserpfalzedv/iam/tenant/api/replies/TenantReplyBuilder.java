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

package de.kaiserpfalzedv.iam.tenant.api.replies;

import java.util.ArrayList;
import java.util.UUID;

import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantBaseCommand;
import org.apache.commons.lang3.builder.Builder;

import static de.kaiserpfalzedv.commons.api.commands.CrudCommands.CREATE;
import static de.kaiserpfalzedv.commons.api.commands.CrudCommands.RETRIEVE;
import static de.kaiserpfalzedv.commons.api.commands.CrudCommands.UPDATE;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public class TenantReplyBuilder<T extends TenantBaseReply> implements Builder<T> {
    private UUID source;
    private UUID replyId;

    private TenantBaseCommand command;
    private Tenant tenant;
    private PagedListable<Tenant> tenants;

    @SuppressWarnings("unchecked")
    @Override
    public T build() {
        setDefaults();
        validate();

        UUID commandId = command.getCommand();
        switch (command.getCrudType()) {
            case CREATE:
                return (T) new TenantCreateReply(source, commandId, replyId, tenant);
            case RETRIEVE:
                return (T) new TenantRetrieveReply(source, commandId, replyId, tenants);
            case UPDATE:
                return (T) new TenantUpdateReply(source, commandId, replyId, tenant);
            case DELETE:
                return (T) new TenantDeleteReply(source, commandId, replyId);
            default:
                throw new IllegalStateException("The builder failed internally. Please report bug!");
        }
    }

    private void setDefaults() {
        if (replyId == null) {
            replyId = UUID.randomUUID();
        }
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>(2);

        if (command == null) {
            failures.add("No command specified");
        }

        if (tenant == null && command != null
                && (CREATE.equals(command.getCrudType()) || UPDATE.equals(command.getCrudType()))) {
            failures.add("No tenant data given for the " + command + " command");
        }

        if (tenants == null && command != null && RETRIEVE.equals(command.getCrudType())) {
            failures.add("No set of tenants given for the " + command + " command");
        }

        if (source == null) {
            failures.add("No source UUID given. Please generate an unique identifier for your service calling object!");
        }

        if (!failures.isEmpty()) {
            throw new BuilderException(TenantBaseCommand.class, failures.toArray(new String[1]));
        }
    }

    public TenantReplyBuilder<T> withCommand(final TenantBaseCommand command) {
        this.command = command;
        return this;
    }

    public TenantReplyBuilder<T> withSource(UUID source) {
        this.source = source;
        return this;
    }

    public TenantReplyBuilder<T> withReplyId(UUID replyId) {
        this.replyId = replyId;
        return this;
    }

    public TenantReplyBuilder<T> withTenant(Tenant tenant) {
        this.tenant = tenant;
        return this;
    }

    public TenantReplyBuilder<T> withTenants(PagedListable<Tenant> tenants) {
        this.tenants = new PagedListBuilder<Tenant>()
                .withData(tenants.getEntries())
                .withPageable(tenants.getPage())
                .build();
        return this;
    }
}
