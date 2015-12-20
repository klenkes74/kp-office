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

import de.kaiserpfalzEdv.office.core.tenants.Tenant;
import de.kaiserpfalzEdv.office.ui.web.commons.actions.UserTransactionInfo;
import de.kaiserpfalzEdv.office.ui.web.commons.actions.Action;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 21.08.15 05:23
 */
public class TenantChangeEvent extends Action {
    private static final long serialVersionUID = -2926117713940731029L;

    private Tenant tenant;

    /**
     * @param source The object on which the Event initially occurred.
     * @param transaction The user transaction.
     * @param tenant The new tenant choosen.
     *
     * @throws IllegalArgumentException if source is null.
     */
    TenantChangeEvent(final Object source, final UserTransactionInfo transaction, final Tenant tenant) {
        super(transaction, source);

        this.tenant = tenant;
    }

    public Tenant getTenant() {
        return tenant;
    }
}
