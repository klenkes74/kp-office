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

package de.kaiserpfalzedv.office.finance.chartofaccounts.impl;

import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.CostCenter;
import org.apache.commons.lang3.builder.Builder;

import javax.money.CurrencyUnit;
import java.util.ArrayList;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * The builder for building a default cost center. May be overwritten for other purposes.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 29.12.15 19:14
 */
public class CostCenterBuilder implements Builder<CostCenter> {
    private UUID tenantId;
    private UUID id;
    private String displayName;
    private String fullName;
    private CurrencyUnit currency;

    @Override
    public CostCenter build() {
        setDefaultValues();
        validate();

        return generateObject(tenantId, id, displayName, fullName, currency);
    }

    /**
     * Sets default values if not initialized by the user.
     */
    private void setDefaultValues() {
        if (id == null) {
            id = UUID.randomUUID();
        }

        if (isBlank(displayName)) {
            displayName = fullName;
        }

        if (isBlank(fullName)) {
            fullName = displayName;
        }
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>(4);

        if (tenantId == null) failures.add("CostCenter needs a tenant!");
        if (id == null) failures.add("CostCenter needs an unique id!");
        if (isBlank(displayName)) failures.add("CostCenter needs a display name!");
        if (isBlank(fullName)) failures.add("CostCenter needs a full name!");

        if (failures.size() != 0) {
            throw new BuilderException(CostCenter.class, failures.toArray(new String[1]));
        }
    }

    /**
     * Method to generate the account. This is the method that needs to be overwritten to generate another type of
     * account.
     *
     * @param tenantId    The tenant for which this account exists.
     * @param id          The id of the account.
     * @param displayName The display name of the account.
     * @param fullName    The full name of the account.
     *
     * @return An object of type AccountImpl.
     */
    private CostCenter generateObject(final UUID tenantId, final UUID id, final String displayName, final String fullName, CurrencyUnit currency) {
        return new CostCenterImpl(tenantId, id, displayName, fullName, currency);
    }

    public CostCenterBuilder withCostCenter(CostCenter orig) {
        withTenantId(orig.getTenant());
        withId(orig.getId());
        withDisplayName(orig.getDisplayName());
        withFullName(orig.getFullName());

        return this;
    }


    public CostCenterBuilder withTenantId(UUID tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public CostCenterBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public CostCenterBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public CostCenterBuilder withFullName(String fullName) {
        this.fullName = fullName;
        return this;
    }

    public CostCenterBuilder withCurrency(CurrencyUnit currency) {
        this.currency = currency;
        return this;
    }
}
