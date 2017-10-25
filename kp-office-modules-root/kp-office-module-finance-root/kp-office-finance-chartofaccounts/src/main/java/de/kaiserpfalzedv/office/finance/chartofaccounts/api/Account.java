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

package de.kaiserpfalzedv.office.finance.chartofaccounts.api;

import javax.money.CurrencySupplier;
import javax.money.CurrencyUnit;

import de.kaiserpfalzedv.office.common.api.data.Identifiable;
import de.kaiserpfalzedv.office.common.api.data.Nameable;
import de.kaiserpfalzedv.office.common.api.data.Tenantable;

/**
 * The real account in the system. Normally it will be used via an charted account (an account mapping practically
 * virtualizing the account numbers for the users).
 *
 * Every account is recorded in a currency.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 0.3.0
 * @since 2015-12-27
 */
public interface Account extends Identifiable, Nameable, Tenantable, Comparable<Account>, CurrencySupplier {
    /**
     * {@inheritDoc}
     *
     * @return The currency this account is calculated in.
     */
    CurrencyUnit getCurrency();
}
