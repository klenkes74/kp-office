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

package de.kaiserpfalzEdv.office.accounting.postingRecord;

import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.CostCenter;
import de.kaiserpfalzEdv.office.commons.data.DisplayNumberHolder;
import de.kaiserpfalzEdv.office.commons.data.IdentityHolder;
import de.kaiserpfalzEdv.office.commons.data.TenantIdHolder;

import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * This is a single posting record.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 21:04
 */
public interface PostingRecord extends IdentityHolder, DisplayNumberHolder, TenantIdHolder, Serializable {
    /**
     * @return The date of entry into this primaNota.
     */
    OffsetDateTime getEntryDate();

    /**
     * A manual changed date that declares the entry of this record. Normally the same as {@link #getEntryDate()} .
     *
     * @return The manually entered accounting entry date.
     */
    LocalDate getAccountingDate();

    /**
     * Normally the entry date is the date when the entry should be accounted. Sometimes this is not valid.
     *
     * @return The date this entry should be calculated.
     */
    LocalDate getValutaDate();


    /**
     * @return The accounting relevant amount.
     */
    MonetaryAmount getAmount();

    /**
     * The posting key. Contains the tax key and function key. Strictly numeric.
     *
     * @return The posting key of this posting record.
     */
    PostingKey getPostingKey();

    /**
     * @return The creditted account.
     */
    Account getAccountCreditted();

    /**
     * @return The debitted account.
     */
    Account getAccountDebitted();

    /**
     * @return The cost center to book the amount to.
     */
    CostCenter getCostCenter1();

    /**
     * @return a cost center for a second method of cost accounting.
     */
    CostCenter getCostCenter2();

    /**
     * @return The information about the document that is base of this posting record.
     */
    DocumentInformation getDocumentInformation();

    /**
     * @return an informational note for this posting record. will be printed in lists.
     */
    String getNotice();

    /**
     * @return second informational note for this posting record. Will only be shown in detail records.
     */
    String getNotice2();
}
