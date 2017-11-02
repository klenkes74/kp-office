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

import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.office.finance.accounting.api.DocumentInformation;
import de.kaiserpfalzedv.office.finance.accounting.api.primanota.PrimaNota;
import de.kaiserpfalzedv.office.finance.accounting.api.primanota.PrimaNotaEntry;
import de.kaiserpfalzedv.office.finance.accounting.api.primanota.PrimaNotaNumberGenerator;
import de.kaiserpfalzedv.office.finance.accounting.api.primanota.PrimanotaEntryNotCanceledException;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.Account;
import de.kaiserpfalzedv.office.finance.chartofaccounts.api.account.CostCenter;
import org.apache.commons.lang3.builder.Builder;

import javax.inject.Inject;
import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.01.16 07:38
 */
public class PrimaNotaEntryBuilder implements Builder<PrimaNotaEntry> {

    private long pn;

    private OffsetDateTime entryDate;
    private LocalDate accountingDate;
    private LocalDate valutaDate;

    private CostCenter costCenter1;
    private CostCenter costCenter2;

    private DocumentInformation document;
    private String notice1;
    private String notice2;

    private Account accountDebitted;
    private Account accountCreditted;

    private MonetaryAmount amount;

    private PrimaNotaEntry cancels;


    @Inject
    private PrimaNotaNumberGenerator pnNumberGenerator;

    private PrimaNota primaNota;


    @Override
    public PrimaNotaEntry build() {
        setDefaultValues();
        validate();

        return new PrimaNotaEntryImpl(pn,
                                      entryDate, accountingDate, valutaDate,
                                      costCenter1, costCenter2,
                                      document, notice1, notice2,
                                      accountDebitted, accountCreditted,
                                      amount,
                                      cancels
        );
    }


    private void setDefaultValues() {
        if (entryDate == null) entryDate = OffsetDateTime.now();
        if (accountingDate == null) LocalDate.now();
        if (valutaDate == null) valutaDate = accountingDate;

        if (pn == 0) {
            try {
                pn = pnNumberGenerator.nextEntryNumber(primaNota);
            } catch (IllegalArgumentException e) {
                throw new BuilderException(PrimaNotaEntry.class, new String[]{e.getMessage()});
            }
        }
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>(3);

        if (accountCreditted == null) failures.add("No account creditted given for prima nota entry!");
        if (accountDebitted == null) failures.add("No account debitted given for prima nota entry!");
        if (amount == null) failures.add("No amount given for prima nota entry!");

        if (failures.size() > 0) {
            throw new BuilderException(PrimaNotaEntry.class, failures.toArray(new String[1]));
        }
    }


    public PrimaNotaEntryBuilder withPrimaNota(PrimaNota primaNota) {
        this.primaNota = primaNota;
        return this;
    }

    public PrimaNotaEntryBuilder withPNGenerator(PrimaNotaNumberGenerator primaNotaNumberGenerator) {
        this.pnNumberGenerator = primaNotaNumberGenerator;
        return this;
    }

    public PrimaNotaEntryBuilder cancel(PrimaNotaEntry primaNotaEntry) {
        if (primaNotaEntry.isCancelationEntry()) {
            throw new BuilderException(PrimaNotaEntry.class, new String[]{"A cancelation can't be canceled!"});
        }

        withPrimaNotaEntry(primaNotaEntry);
        withPN(0L);
        withAmount(primaNotaEntry.getAmount().negate());
        withCanceledEntry(primaNotaEntry);


        return this;
    }

    public PrimaNotaEntryBuilder withPrimaNotaEntry(PrimaNotaEntry primaNotaEntry) {
        withPN(primaNotaEntry.getPN());
        withEntryDate(primaNotaEntry.getEntryDate());
        withAccountingDate(primaNotaEntry.getAccountingDate());
        withValutaDate(primaNotaEntry.getValutaDate());
        withCostCenter1(primaNotaEntry.getCostCenter1());
        withCostCenter2(primaNotaEntry.getCostCenter2());
        withDocument(primaNotaEntry.getDocument());
        withNotice1(primaNotaEntry.getNotice1());
        withNotice2(primaNotaEntry.getNotice2());
        withAccountCreditted(primaNotaEntry.getAccountCreditted());
        withAccountDebitted(primaNotaEntry.getAccountDebitted());
        withAmount(primaNotaEntry.getAmount());
        try {
            withCanceledEntry(primaNotaEntry.getCanceledEntry());
        } catch (PrimanotaEntryNotCanceledException e) {
            // do nothing. That's ok.
        }

        return this;
    }

    public PrimaNotaEntryBuilder withPN(long pn) {
        this.pn = pn;
        return this;
    }

    public PrimaNotaEntryBuilder withAmount(MonetaryAmount amount) {
        this.amount = amount;
        return this;
    }

    public PrimaNotaEntryBuilder withCanceledEntry(PrimaNotaEntry cancels) {
        this.cancels = cancels;
        return this;
    }

    public PrimaNotaEntryBuilder withEntryDate(OffsetDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public PrimaNotaEntryBuilder withAccountingDate(LocalDate accountingDate) {
        this.accountingDate = accountingDate;
        return this;
    }

    public PrimaNotaEntryBuilder withValutaDate(LocalDate valutaDate) {
        this.valutaDate = valutaDate;
        return this;
    }

    public PrimaNotaEntryBuilder withCostCenter1(CostCenter costCenter1) {
        this.costCenter1 = costCenter1;
        return this;
    }

    public PrimaNotaEntryBuilder withCostCenter2(CostCenter costCenter2) {
        this.costCenter2 = costCenter2;
        return this;
    }

    public PrimaNotaEntryBuilder withDocument(DocumentInformation document) {
        this.document = document;
        return this;
    }

    public PrimaNotaEntryBuilder withNotice1(String notice1) {
        this.notice1 = notice1;
        return this;
    }

    public PrimaNotaEntryBuilder withNotice2(String notice2) {
        this.notice2 = notice2;
        return this;
    }

    public PrimaNotaEntryBuilder withAccountCreditted(Account accountCreditted) {
        this.accountCreditted = accountCreditted;
        return this;
    }

    public PrimaNotaEntryBuilder withAccountDebitted(Account accountDebitted) {
        this.accountDebitted = accountDebitted;
        return this;
    }
}
