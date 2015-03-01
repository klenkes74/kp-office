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

package de.kaiserpfalzEdv.office.accounting.chartsofaccounts;

import de.kaiserpfalzEdv.office.commons.data.DisplayNameHolder;
import de.kaiserpfalzEdv.office.commons.data.DisplayNumberHolder;
import de.kaiserpfalzEdv.office.commons.data.IdentityHolder;
import de.kaiserpfalzEdv.office.commons.data.TenantIdHolder;

import javax.money.CurrencyUnit;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 18:04
 */
public interface ChartsOfAccounts extends IdentityHolder, DisplayNameHolder, DisplayNumberHolder, TenantIdHolder, Serializable {
    Account getAccount(@NotNull UUID id);

    Set<Account> allAccounts();

    /**
     * @return The currency of this account.
     */
    CurrencyUnit getCurrencyUnit();
}
