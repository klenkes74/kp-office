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

import javax.validation.constraints.NotNull;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 17:33
 */
public interface AccountMapping extends IdentityHolder, DisplayNameHolder, DisplayNumberHolder, TenantIdHolder {
    /**
     * Renumbers the account to the current chart of account.
     *
     * @param account The account to be renumbered.
     *
     * @return a new account object with the correct number.
     */
    Account renumber(@NotNull final Account account);

    /**
     * @param account The account to retrieve the account number for.
     *
     * @return the account number of the current chart of account.
     */
    String getNumberForAcount(@NotNull final Account account);

    /**
     * @param number The account number to retrieve the internal id for.
     *
     * @return the internal id of the account number given.
     */
    Set<UUID> getAccountsForNumber(@NotNull final String number);

    /**
     * Checks if the account with this number may be used for active booking. That's only possible if there is only one
     * account mapped to this number.
     * Multiple numbers may be given to one account and the account is still bookable.
     *
     * @param number the account number to be checked.
     *
     * @return TRUE, if the account number can be used for booking. FALSE if there are more than one accounts mapped to
     * that number.
     */
    boolean isBookable(@NotNull final String number);
}
