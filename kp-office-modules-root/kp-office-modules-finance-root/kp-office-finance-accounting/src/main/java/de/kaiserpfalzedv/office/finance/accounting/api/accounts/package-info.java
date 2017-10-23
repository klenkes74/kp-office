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

/**
 * The base data for accounts. Coming from the base {@link de.kaiserpfalzedv.office.finance.accounting.accounts.Account}
 * (not mapped for human use) to the {@link de.kaiserpfalzedv.office.finance.accounting.api.accounts.ChartedAccount} (that
 * is mapped to a human usable number in a {@link de.kaiserpfalzedv.office.finance.accounting.api.accounts.ChartOfAccounts})
 * and the {@link de.kaiserpfalzedv.office.finance.accounting.accounts.CostCenter} as special case for accounts.
 *
 * @author klenkes  {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 0.3.0
 * @since 2016-03-25
 */
package de.kaiserpfalzedv.office.finance.accounting.api.accounts;