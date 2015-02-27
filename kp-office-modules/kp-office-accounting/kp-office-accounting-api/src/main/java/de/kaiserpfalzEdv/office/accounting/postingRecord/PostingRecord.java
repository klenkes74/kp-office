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
import de.kaiserpfalzEdv.office.commons.DisplayNumberHolder;
import de.kaiserpfalzEdv.office.commons.IdentityHolder;

import javax.money.MonetaryAmount;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 21:04
 */
public interface PostingRecord extends IdentityHolder, DisplayNumberHolder, Serializable {
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
     * @return The number on the document this entry is based on.
     */
    String getDocumentNumber();

    /**
     * @return The date of the base document for this primaNota entry.
     */
    LocalDate getDocumentDate();

    /**
     * @return The amount given on the document with the used currency.
     */
    MonetaryAmount getDocumentAmount();


    /**
     * @return A notice of the book keeping clerk.
     */
    String getNotice();


    /**
     * @return The debitted account.
     */
    Account getAccountDebitted();

    /**
     * @return The creditted account.
     */
    Account getAccountCreditted();


    /**
     * @return The accounting relevant amount.
     */
    MonetaryAmount getPostingAmount();
}
