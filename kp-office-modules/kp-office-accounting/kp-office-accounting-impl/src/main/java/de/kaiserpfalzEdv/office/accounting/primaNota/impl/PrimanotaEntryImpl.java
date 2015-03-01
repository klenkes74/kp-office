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

package de.kaiserpfalzEdv.office.accounting.primaNota.impl;

import de.kaiserpfalzEdv.office.accounting.DatabaseMoney;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.postingRecord.impl.AccountingVoucher;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaEntry;
import de.kaiserpfalzEdv.office.commons.server.data.KPOEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.money.MonetaryAmount;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 27.02.15 11:08
 */
@Entity
@Table(schema = "accounting", catalog = "accounting", name = "primanota_entries")
public class PrimaNotaEntryImpl extends KPOEntity implements PrimaNotaEntry {
    private static final long serialVersionUID = -3139362054836866462L;


    @ManyToOne
    @Column(name = "primanota_", nullable = false)
    private PrimaNotaImpl primaNota;

    @Column(name = "timestamp_entry_")
    private OffsetDateTime entryDate;

    @Column(name = "date_accounting_")
    private LocalDate accountingDate;

    @Column(name = "date_valuta_")
    private LocalDate valutaDate;

    @Embedded
    private AccountingVoucher voucher;

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
    protected PrimaNotaEntryImpl() {}


    public PrimaNotaEntryImpl(
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

        this.voucher = new AccountingVoucher(documentNumber, documentDate, documentAmount);

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
        return voucher.getNumber();
    }

    @Override
    public LocalDate getDocumentDate() {
        return voucher.getDate();
    }

    @Override
    public MonetaryAmount getDocumentAmount() {
        return voucher.getAmount();
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


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("voucher", voucher)
                .toString();
    }
}
