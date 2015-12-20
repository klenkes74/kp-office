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

package de.kaiserpfalzEdv.office.ui.core.tenant;

import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.core.tenants.Tenant;
import de.kaiserpfalzEdv.office.ui.web.commons.actions.NullTransaction;
import de.kaiserpfalzEdv.office.ui.web.commons.actions.UserTransactionInfo;
import org.apache.commons.lang3.builder.Builder;

import java.util.ArrayList;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 21.08.15 12:03
 */
public class TenantChangeEventBuilder implements Builder<TenantChangeEvent> {
    private Tenant tenant;
    private UserTransactionInfo transaction;
    private Object source;

    @Override
    public TenantChangeEvent build() {
        generateDefaults();
        validate();

        return new TenantChangeEvent(source,  transaction, tenant);
    }

    private void generateDefaults() {
        if (transaction == null) transaction = new NullTransaction();
    }


    public void validate() {
        ArrayList<String> failures = new ArrayList<>(3);

        if (source == null) failures.add("Every action needs to provide its source!");
        if (transaction == null) failures.add("Every action needs to be included in a (null)transaction!");
        if (tenant == null) failures.add("Every tenant change action needs to include a tenant!");

        if (failures.size() != 0)
            throw new BuilderException(failures);
    }


    public TenantChangeEventBuilder withTenant(final Tenant tenant) {
        this.tenant = tenant;
        return this;
    }

    public TenantChangeEventBuilder withTransaction(final UserTransactionInfo transaction) {
        this.transaction = transaction;
        return this;
    }

    public TenantChangeEventBuilder withSource(final Object source) {
        this.source = source;
        return this;
    }
}
