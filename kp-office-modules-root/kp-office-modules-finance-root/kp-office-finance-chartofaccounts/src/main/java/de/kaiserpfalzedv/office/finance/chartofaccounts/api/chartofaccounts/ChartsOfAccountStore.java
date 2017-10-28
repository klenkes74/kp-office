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

package de.kaiserpfalzedv.office.finance.chartofaccounts.api.chartofaccounts;

import java.util.Collection;
import java.util.Set;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-25
 */
public interface ChartsOfAccountStore {
    /**
     * Method for loading all current charts of accounts during startup of the system.
     *
     * @return all defined charts of accounts.
     */
    Set<ChartOfAccounts> load();

    /**
     * Saving all current charts of accounts during shutdown of the system.
     *
     * @param chartsOfAccounts All current charts of accounts  to be saved to the store (replacing the stored ones)
     */
    void save(Collection<ChartOfAccounts> chartsOfAccounts);

    /**
     * Saves the given chart of accounts to the store. Plan name for further requests will be the
     * {@link ChartOfAccounts#getDisplayName()} of the saved chart of accounts. If the name already exists, it will
     * be removed.
     *
     * @param chartOfAccounts The chart of accounts to be saved.
     *
     * @return
     */
    ChartOfAccounts save(ChartOfAccounts chartOfAccounts);

    boolean delete(String plan);

    boolean put(String plan, ChartedAccount account) throws ChartOfAccountsDoesNotExistException;

    boolean remove(String plan, ChartedAccount account) throws ChartOfAccountsDoesNotExistException;

    boolean clear(String plan);
}
