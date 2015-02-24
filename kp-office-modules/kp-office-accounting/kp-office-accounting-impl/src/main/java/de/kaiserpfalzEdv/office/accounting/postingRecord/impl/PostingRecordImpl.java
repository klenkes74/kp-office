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

package de.kaiserpfalzEdv.office.accounting.postingRecord.impl;

import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import de.kaiserpfalzEdv.office.core.impl.KPOEntity;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 16:18
 */
@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PostingRecordImpl extends KPOEntity implements PostingRecord {

    @Column(name = "ENTRY_DATE_")
    private LocalDate entryDate;

    @Column(name = "ACCOUNTING_DATE_")
    private LocalDate accountingDate;

    @Column(name = "VALUTA_DATE_")
    private LocalDate valutaDate;


    @Column(name = "DOCUMENT_DATE_")
    private LocalDate documentDate;

    @Transient
    private MonetaryAmount documentAmount;


    private String notice;

    @Transient
    private Account accountDebitted;
    @Transient
    private Account accountCreditted;

    @Transient
    private MonetaryAmount amount;


    @Deprecated // Only for JAX-B, Jaxon, JPA, ...
    protected PostingRecordImpl() {}


    PostingRecordImpl(
            @NotNull final UUID id,
            @NotNull final String entryId,
            @NotNull final LocalDate entryDate,
            @NotNull final String documentNumber,
            @NotNull final LocalDate documentDate,
            @NotNull final MonetaryAmount documentAmount,
            @NotNull final Account accountDebitted,
            @NotNull final Account accountCreditted,
            @NotNull final MonetaryAmount accountingAmount
    ) {
        super(id, documentNumber, entryId);
        this.entryDate = entryDate;
        this.accountingDate = entryDate;
        this.valutaDate = entryDate;

        this.documentDate = documentDate;
        this.documentAmount = documentAmount;

        this.accountDebitted = accountDebitted;
        this.accountCreditted = accountCreditted;
        this.amount = accountingAmount;
    }


    @Override
    public LocalDate getEntryDate() {
        return entryDate;
    }

    @Override
    public LocalDate getAccountingDate() {
        return accountingDate;
    }

    public void setAccountingDate(LocalDate accountingDate) {
        this.accountingDate = accountingDate;
    }


    @Override
    public LocalDate getValutaDate() {
        return valutaDate;
    }

    public void setValutaDate(@NotNull final LocalDate date) {
        this.valutaDate = date;
    }


    @Override
    public String getDocumentNumber() {
        return getDisplayName();
    }

    @Override
    public LocalDate getDocumentDate() {
        return documentDate;
    }

    @Override
    public MonetaryAmount getDocumentAmount() {
        return documentAmount;
    }


    @Override
    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Override
    public Account getAccountDebitted() {
        return accountDebitted;
    }

    @Override
    public Account getAccountCreditted() {
        return accountCreditted;
    }

    @Override
    public MonetaryAmount getPostingAmount() {
        return amount;
    }
}