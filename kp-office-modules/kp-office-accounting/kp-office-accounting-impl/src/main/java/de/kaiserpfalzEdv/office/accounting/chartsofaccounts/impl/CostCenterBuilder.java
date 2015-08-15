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

package de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl;

import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.CostCenter;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 16:36
 */
public class CostCenterBuilder implements Builder<CostCenterImpl> {
    private static final Logger LOG = LoggerFactory.getLogger(CostCenterBuilder.class);

    private UUID   tenantId;
    private UUID   id;
    private String name;

    private String mapping;
    private String number;


    @SuppressWarnings("deprecation")
    @Override
    public CostCenterImpl build() {
        validate();
        CostCenterImpl result = new CostCenterImpl(tenantId, id, name);

        if (isNotBlank(mapping)) result.setCurrentMapping(mapping);
        if (isNotBlank(number)) {
            result.setDisplayNumber(number);
            result.setDisplayName(number);
        }

        return result;
    }

    public void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (tenantId == null) failures.add("No tenant for the new cost center given!");
        if (id == null) failures.add("A cost center needs an internal identifier!");
        if (name == null) failures.add("A cost center needs a name!");

        if (failures.size() > 0) {
            throw new BuilderException(failures);
        }
    }


    public CostCenterBuilder withCostCenter(@NotNull final CostCenter account) {
        withTenantId(account.getTenantId());
        withId(account.getId());

        withName(account.getDisplayName());

        withMapping(account.getCurrentMapping());
        withNumber(account.getDisplayNumber());

        return this;
    }


    public CostCenterBuilder withTenantId(final UUID tenantId) {
        this.tenantId = tenantId;

        return this;
    }


    public CostCenterBuilder withId(final UUID id) {
        this.id = id;

        return this;
    }

    public CostCenterBuilder newId() {
        this.id = UUID.randomUUID();

        return this;
    }


    public CostCenterBuilder withMapping(final String mapping) {
        this.mapping = mapping;

        return this;
    }


    public CostCenterBuilder withNumber(final String number) {
        this.number = number;

        return this;
    }


    public CostCenterBuilder withName(final String name) {
        this.name = name;

        return this;
    }
}
