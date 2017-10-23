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

package de.kaiserpfalzedv.office.finance.accounting.impl.primanota;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.money.MonetaryAmount;

import de.kaiserpfalzedv.office.finance.accounting.api.DocumentInformation;
import de.kaiserpfalzedv.office.finance.accounting.api.accounts.Account;
import de.kaiserpfalzedv.office.finance.accounting.api.accounts.CostCenter;
import de.kaiserpfalzedv.office.finance.accounting.api.primanota.PrimaNotaEntry;
import de.kaiserpfalzedv.office.finance.accounting.api.primanota.PrimanotaEntryNotCanceledException;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 31.12.15 06:52
 */
public class PrimaNotaEntryImpl implements PrimaNotaEntry {
    private static final long serialVersionUID = -3445341612027253548L;

    private long pn;

    private OffsetDateTime entryDate;
    private LocalDate      accountingDate;
    private LocalDate      valutaDate;

    private CostCenter costCenter1;
    private CostCenter costCenter2;

    private DocumentInformation document;
    private String              notice1;
    private String              notice2;

    private Account accountDebitted;
    private Account accountCreditted;

    private MonetaryAmount amount;

    private PrimaNotaEntry cancels;


    public PrimaNotaEntryImpl(
            long pn,
            OffsetDateTime entryDate, LocalDate accountingDate, LocalDate valutaDate,
            CostCenter costCenter1, CostCenter costCenter2,
            DocumentInformation document, String notice1, String notice2,
            Account accountDebitted, Account accountCreditted,
            MonetaryAmount amount,
            PrimaNotaEntry cancels
    ) {
        this.pn = pn;
        this.entryDate = entryDate;
        this.accountingDate = accountingDate;
        this.valutaDate = valutaDate;
        this.costCenter1 = costCenter1;
        this.costCenter2 = costCenter2;
        this.document = document;
        this.notice1 = notice1;
        this.notice2 = notice2;
        this.accountDebitted = accountDebitted;
        this.accountCreditted = accountCreditted;
        this.amount = amount;
        this.cancels = cancels;
    }


    @Override
    public long getPN() {
        return pn;
    }

    @Override
    public boolean isCancelationEntry() {
        return cancels != null;
    }

    @Override
    public PrimaNotaEntry getCanceledEntry() throws PrimanotaEntryNotCanceledException {
        if (cancels == null) throw new PrimanotaEntryNotCanceledException(this);

        return cancels;
    }

    @Override
    public OffsetDateTime getEntryDate() {
        return entryDate;
    }

    @Override
    public LocalDate getAccountingDate() {
        return accountingDate;
    }

    @Override
    public LocalDate getValutaDate() {
        return valutaDate;
    }

    @Override
    public CostCenter getCostCenter1() {
        return costCenter1;
    }

    @Override
    public CostCenter getCostCenter2() {
        return costCenter2;
    }

    @Override
    public DocumentInformation getDocument() {
        return document;
    }

    @Override
    public String getNotice1() {
        return notice1;
    }

    @Override
    public String getNotice2() {
        return notice2;
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
    public MonetaryAmount getAmount() {
        return amount;
    }
}
