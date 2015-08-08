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
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.CostCenter;
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingKey;
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import de.kaiserpfalzEdv.office.commons.server.data.KPOEntity;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 16:18
 */
public class PostingRecordImpl extends KPOEntity implements PostingRecord {

    private OffsetDateTime entryDate;

    private LocalDate accountingDate;

    private LocalDate valutaDate;

    private AccountingVoucher voucher;

    private String notice1;
    private String notice2;

    private PostingKeyImpl postingKey;

    private Account accountDebitted;
    private Account accountCreditted;
    private MonetaryAmount entryAmount;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for JAX-B, Jaxon, JPA, ...
    protected PostingRecordImpl() {}


    public PostingRecordImpl(
            @NotNull final UUID id,
            @NotNull final String entryId,
            @NotNull final OffsetDateTime entryDate,
            @NotNull final String documentNumber1,
            @NotNull final String documentNumber2,
            @NotNull final LocalDate documentDate,
            @NotNull final MonetaryAmount documentAmount,
            @NotNull final Account accountDebitted,
            @NotNull final Account accountCreditted,
            @NotNull final MonetaryAmount accountingAmount
    ) {
        super(id, entryId, entryId);
        this.entryDate = entryDate;
        this.accountingDate = entryDate.toLocalDate();
        this.valutaDate = entryDate.toLocalDate();

        this.voucher = new AccountingVoucher(documentNumber1, documentNumber2, documentDate, documentAmount);

        this.accountDebitted = accountDebitted;
        this.accountCreditted = accountCreditted;
        this.entryAmount = accountingAmount;
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
    public LocalDate getDocumentDate() {
        return voucher.getDate();
    }

    @Override
    public MonetaryAmount getDocumentAmount() {
        return voucher.getAmount();
    }

    @Override
    public Account getAccountDebitted() {
        return accountDebitted;
    }

    @Override
    public CostCenter getCostCenter1() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.accounting.postingRecord.impl.PostingRecordImpl.getCostCenter1
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public CostCenter getCostCenter2() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.accounting.postingRecord.impl.PostingRecordImpl.getCostCenter2
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getNotice1() {
        return notice1;
    }

    public void setNotice1(String notice) {
        this.notice1 = notice;
    }

    @Override
    public String getNotice2() {
        return notice2;
    }

    public void setNotice2(String notice) { this.notice2 = notice; }

    @Override
    public MonetaryAmount getAmount() {
        return entryAmount;
    }

    @Override
    public PostingKey getPostingKey() {
        return
    }

    @Override
    public Account getAccountCreditted() {
        return accountCreditted;
    }

    @Override
    public String getDocumentNumber1() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.accounting.postingRecord.impl.PostingRecordImpl.getDocumentNumber1
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String getDocumentNumber2() {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.accounting.postingRecord.impl.PostingRecordImpl.getDocumentNumber2
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}