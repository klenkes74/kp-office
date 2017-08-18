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

package de.kaiserpfalzedv.office.finance.accounting.balancesheet;

import de.kaiserpfalzedv.office.finance.accounting.PagedPostingRecordList;
import de.kaiserpfalzedv.office.finance.accounting.accounts.Account;

import javax.money.MonetaryAmount;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 0.3.0
 * @since 2016-03-25
 */
public interface BalanceSheet extends PagedPostingRecordList {
    /**
     * @return The account this balance sheet is created for.
     */
    Account getAccount();


    /**
     * @return The balance of this account at start of the period.
     */
    MonetaryAmount getStartingBalance();

    /**
     * @return The balance of this account at end of the period of this balance sheet.
     */
    MonetaryAmount getEndingBalance();
}
