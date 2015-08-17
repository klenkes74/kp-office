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

package de.kaiserpfalzEdv.office.accounting.automation;

import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import de.kaiserpfalzEdv.office.commons.data.DisplayNameHolder;
import de.kaiserpfalzEdv.office.commons.data.DisplayNumberHolder;
import de.kaiserpfalzEdv.office.commons.data.IdentityHolder;

import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * The tax information. This entity holds all information about calculating the tax for a posting record.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 08.08.15 08:06
 */
public interface TaxKey extends IdentityHolder, DisplayNumberHolder, DisplayNameHolder, Serializable {
    /**
     * @return The account the tax should be posted to if it is to be creditted.
     */
    Account getAccountCreditted();

    /**
     * @return The account the tax should be posted to if it is to be debitted.
     */
    Account getAccountDebitted();

    /**
     * @return The tax rate (e.g. 19.0 for the german VAT)
     */
    BigDecimal getTaxRate();

    /**
     * Calculates the posting record for the tax for the given posting record if the tax is to be creditted.
     *
     * @param originalRecord posting record to base the tax calculation on.
     * @param amount         amount to be taxed.
     *
     * @return a posting record for this tax.
     */
    PostingRecord calculateCredittedPostingRecord(PostingRecord originalRecord, MonetaryAmount amount);

    /**
     * Calculates the posting record for the tax for the given posting record if the tax is to be debitted.
     *
     * @param originalRecord posting record to base the tax calculation on.
     * @param amount         amount to be taxed.
     *
     * @return a posting record for this tax.
     */
    PostingRecord calculateDebittedPostingRecord(PostingRecord originalRecord, MonetaryAmount amount);
}