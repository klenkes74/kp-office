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

import de.kaiserpfalzEdv.office.accounting.DatabaseMoney;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import de.kaiserpfalzEdv.office.core.impl.KPOEntity;

import javax.money.MonetaryAmount;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 16:18
 */
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class PostingRecordImpl extends KPOEntity implements PostingRecord {

    @Column(name = "timestamp_entry_")
    private OffsetDateTime entryDate;

    @Column(name = "date_accounting_")
    private LocalDate accountingDate;

    @Column(name = "date_valuta_")
    private LocalDate valutaDate;


    @Column(name = "document_date_")
    private LocalDate documentDate;

    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "document_amount_value_")),
            @AttributeOverride(name = "currency", column = @Column(name = "document_amount_currency_"))
    })
    @Embedded
    private DatabaseMoney documentAmount;

    @Column(name = "notice", length = 255)
    private String notice;

    @Column(name = "account_debitted_", nullable = false)
    private Account accountDebitted;
    @Column(name = "account_creditted_", nullable = false)
    private Account accountCreditted;

    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "entry_amount_value_")),
            @AttributeOverride(name = "currency", column = @Column(name = "entry_amount_currency_"))
    })
    @Embedded
    private DatabaseMoney entryAmount;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for JAX-B, Jaxon, JPA, ...
    protected PostingRecordImpl() {}


    public PostingRecordImpl(
            @NotNull final UUID id,
            @NotNull final String entryId,
            @NotNull final OffsetDateTime entryDate,
            @NotNull final String documentNumber,
            @NotNull final LocalDate documentDate,
            @NotNull final MonetaryAmount documentAmount,
            @NotNull final Account accountDebitted,
            @NotNull final Account accountCreditted,
            @NotNull final MonetaryAmount accountingAmount
    ) {
        super(id, documentNumber, entryId);
        this.entryDate = entryDate;
        this.accountingDate = entryDate.toLocalDate();
        this.valutaDate = entryDate.toLocalDate();

        this.documentDate = documentDate;
        this.documentAmount = new DatabaseMoney(documentAmount);

        this.accountDebitted = accountDebitted;
        this.accountCreditted = accountCreditted;
        this.entryAmount = new DatabaseMoney(accountingAmount);
    }


    @Override
    public OffsetDateTime getEntryDate() {
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
        return documentAmount.getMoney();
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
        return entryAmount.getMoney();
    }
}