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

package de.kaiserpfalzedv.office.tenant.api.commands;

import java.util.ArrayList;
import java.util.UUID;

import de.kaiserpfalzedv.office.common.BuilderException;
import de.kaiserpfalzedv.office.common.commands.CrudCommands;
import de.kaiserpfalzedv.office.tenant.api.Tenant;
import org.apache.commons.lang3.builder.Builder;

import static de.kaiserpfalzedv.office.common.commands.CrudCommands.CREATE;
import static de.kaiserpfalzedv.office.common.commands.CrudCommands.DELETE;
import static de.kaiserpfalzedv.office.common.commands.CrudCommands.RETRIEVE;
import static de.kaiserpfalzedv.office.common.commands.CrudCommands.RETRIEVE_ALL;
import static de.kaiserpfalzedv.office.common.commands.CrudCommands.RETRIEVE_BY_KEY;
import static de.kaiserpfalzedv.office.common.commands.CrudCommands.UPDATE;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public class TenantCommandBuilder<T extends TenantBaseCommand> implements Builder<T> {
    private UUID source;
    private UUID id;

    private CrudCommands command;
    private UUID tenantId;
    private Tenant tenant;
    private String key;

    @SuppressWarnings("unchecked")
    @Override
    public T build() {
        setDefaults();
        validate();

        switch (command) {
            case CREATE:
                return (T) new TenantCreateCommand(source, id, tenant);
            case RETRIEVE:
                return (T) new TenantRetrieveCommand(source, id, tenantId);
            case RETRIEVE_BY_KEY:
                return (T) new TenantRetrieveByKeyCommand(source, id, key);
            case RETRIEVE_ALL:
                return (T) new TenantRetrieveAllCommand(source, id);
            case UPDATE:
                return (T) new TenantUpdateCommand(source, id, tenant);
            case DELETE:
                return (T) new TenantDeleteCommand(source, id, tenantId);
            default:
                throw new IllegalStateException("The builder failed internally. Please report bug!");
        }
    }

    private void setDefaults() {
        if (id == null) {
            id = UUID.randomUUID();
        }
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>(2);

        if (tenant == null && (CREATE.equals(command) || UPDATE.equals(command))) {
            failures.add("No tenant data given for the " + command + " command");
        }

        if (tenantId == null && (RETRIEVE.equals(command) || DELETE.equals(command))) {
            failures.add("No tenant id given for the " + command + " command");
        }

        if (key == null && RETRIEVE_BY_KEY.equals(command)) {
            failures.add("No key given for the " + command + " command");
        }

        if (command == null) {
            failures.add("No command specified");
        }

        if (source == null) {
            failures.add("No source UUID given. Please generate an unique identifier for your service calling object!");
        }

        if (!failures.isEmpty()) {
            throw new BuilderException(TenantBaseCommand.class, failures);
        }
    }


    public TenantCommandBuilder<T> withSource(UUID source) {
        this.source = source;
        return this;
    }

    public TenantCommandBuilder<T> withId(UUID id) {
        this.id = id;
        return this;
    }

    public TenantCommandBuilder<T> withTenantId(UUID tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public TenantCommandBuilder<T> withKey(final String key) {
        this.key = key;
        return this;
    }

    public TenantCommandBuilder<T> withTenant(final Tenant tenant) {
        this.tenant = tenant;
        return this;
    }

    public TenantCommandBuilder<T> create() {
        command = CREATE;
        return this;
    }

    public TenantCommandBuilder<T> retrieve() {
        command = RETRIEVE;
        return this;
    }

    public TenantCommandBuilder<T> retrieveByKey() {
        command = RETRIEVE_BY_KEY;
        return this;
    }

    public TenantCommandBuilder<T> retrieveAll() {
        command = RETRIEVE_ALL;
        return this;
    }

    public TenantCommandBuilder<T> update() {
        command = UPDATE;
        return this;
    }

    public TenantCommandBuilder<T> delete() {
        command = DELETE;
        return this;
    }
}
