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

package de.kaiserpfalzedv.office.finance.chartofaccounts.impl.chartofaccounts;

import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartOfAccounts;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts.ChartedAccount;
import de.kaiserpfalzedv.office.tenant.api.NullTenant;
import org.apache.commons.lang3.builder.Builder;

import java.util.ArrayList;
import java.util.UUID;

/**
 * This builder returns a ChartedAccount that is build from the values given.
 *
 * @author klenkes  {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 0.3.0
 * @since 2016-03-25
 */
public class ChartOfAccountsBuilder implements Builder<ChartOfAccounts> {
    private UUID tenantId;
    private UUID id;
    private String displayName;
    private String fullName;

    @Override
    public ChartOfAccounts build() {
        setDefaultValues();

        validate();

        return new ChartOfAccountsImpl(tenantId, id, displayName, fullName);
    }

    private void setDefaultValues() {
        if (tenantId == null) {
            this.tenantId = new NullTenant().getId();
        }

        if (id == null) {
            this.id = UUID.randomUUID();
        }
    }

    public void validate() throws BuilderException {
        ArrayList<String> failures = new ArrayList<>(2);

        if (displayName == null) {
            failures.add("A chart of accounts needs a displayable name!");
        }

        if (fullName == null) {
            failures.add("A chart of accounts needs a full name!");
        }

        if (failures.size() > 0) {
            throw new BuilderException(ChartedAccount.class, failures.toArray(new String[1]));
        }
    }


    public ChartOfAccountsBuilder withChartofAccounts(final ChartOfAccounts account) {
        withTenantId(account.getTenant());
        withId(account.getId());
        withDisplayName(account.getDisplayName());
        withFullName(account.getFullName());

        return this;
    }


    public ChartOfAccountsBuilder withTenantId(final UUID tenantId) {
        this.tenantId = tenantId;

        return this;
    }

    public ChartOfAccountsBuilder withId(final UUID id) {
        this.id = id;

        return this;
    }

    public ChartOfAccountsBuilder withDisplayName(final String displayName) {
        this.displayName = displayName;

        return this;
    }

    public ChartOfAccountsBuilder withFullName(final String fullName) {
        this.fullName = fullName;

        return this;
    }
}
