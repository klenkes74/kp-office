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

import de.kaiserpfalzedv.commons.api.action.CrudCommandType;
import de.kaiserpfalzedv.commons.api.action.commands.CrudCommandBuilderValueCalculator;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PageableBuilder;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-19
 */
public class TenantCrudCommandDefaults implements CrudCommandBuilderValueCalculator<Tenant> {
    @Override
    public UUID setDefaultId(CrudCommandType command, UUID dataId, Tenant data, Predicate<Tenant> predicate, Pageable page) {
        return (dataId == null && data != null) ? data.getId() : dataId;
    }

    @Override
    public Tenant setDefaultData(CrudCommandType command, UUID dataId, Tenant data, Predicate<Tenant> predicate, Pageable page) {
        return data;
    }

    @Override
    public Predicate<Tenant> setDefaultPredicate(CrudCommandType command, UUID dataId, Tenant data, Predicate<Tenant> predicate, Pageable page) {
        return predicate;
    }

    @Override
    public Pageable setDefaultPage(CrudCommandType command, UUID dataId, Tenant data, Predicate<Tenant> predicate, Pageable page) {
        if (CrudCommandType.RETRIEVE.equals(command) && page == null) {
            return new PageableBuilder().build();
        }

        return page;
    }
}
