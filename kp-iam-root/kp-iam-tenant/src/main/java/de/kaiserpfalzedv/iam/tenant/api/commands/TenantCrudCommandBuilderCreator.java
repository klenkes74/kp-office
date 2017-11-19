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

package de.kaiserpfalzedv.iam.tenant.api.commands;

import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;

import de.kaiserpfalzedv.commons.api.action.commands.CrudCommand;
import de.kaiserpfalzedv.commons.api.action.commands.CrudCommandBuilderCreator;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-19
 */
@ApplicationScoped
public class TenantCrudCommandBuilderCreator implements CrudCommandBuilderCreator<Tenant> {
    @Override
    public CrudCommand<Tenant> create(UUID source, UUID commandId, Tenant data) {
        return new TenantCreateCommand(source, commandId, data);
    }

    @Override
    public TenantRetrieveCommand retrieve(UUID source, UUID commandId, Predicate<Tenant> predicate, Pageable page) {
        return new TenantRetrieveCommand(source, commandId, predicate, page);
    }

    @Override
    public TenantUpdateCommand update(UUID source, UUID commandId, Tenant data) {
        return new TenantUpdateCommand(source, commandId, data);
    }

    @Override
    public TenantDeleteCommand delete(UUID source, UUID commandId, UUID dataId) {
        return new TenantDeleteCommand(source, commandId, dataId);
    }
}
