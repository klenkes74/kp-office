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

import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.accounting.DatabaseMoney;
import de.kaiserpfalzEdv.office.accounting.automation.FunctionKey;
import de.kaiserpfalzEdv.office.accounting.automation.impl.FunctionKeyImpl;
import de.kaiserpfalzEdv.office.accounting.automation.impl.TaxKeyImpl;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.CostCenter;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.AccountImpl;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.CostCenterImpl;
import de.kaiserpfalzEdv.office.accounting.postingRecord.DocumentInformation;
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import de.kaiserpfalzEdv.office.accounting.postingRecord.impl.DocumentInformationImpl;
import de.kaiserpfalzEdv.office.accounting.postingRecord.impl.PostingRecordBuilder;
import de.kaiserpfalzEdv.office.accounting.tax.TaxKey;
import org.apache.commons.lang3.builder.Builder;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;

/**
 * Creates a new prima nota entry.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 13.08.15 19:35
 */
public class PrimaNotaEntryBuilder implements Builder<PrimaNotaEntryImpl> {
    private PostingRecordBuilder builder = new PostingRecordBuilder();

    private PrimaNotaImpl primaNota;


    public PrimaNotaEntryImpl build() {
        calculateDefaultValues();
        validate();

        return new PrimaNotaEntryImpl(builder.build(), primaNota);
    }


    private void calculateDefaultValues() {}

    private void validate() {
        ArrayList<String> errors = new ArrayList<>();

        if (primaNota == null) errors.add("No prima nota for this entry given!");

        if (!errors.isEmpty()) {
            throw new BuilderException(errors);
        }
    }

    public PrimaNotaEntryBuilder withPrimaNota(PrimaNotaImpl primaNota) {
        this.primaNota = primaNota;
        return this;
    }


    public PrimaNotaEntryBuilder withFunctionKey(FunctionKey key) {
        builder.withFunctionKey(key);
        return this;
    }

    public PrimaNotaEntryBuilder withId(UUID id) {
        builder.withId(id);
        return this;
    }

    public PrimaNotaEntryBuilder withAccountDebitted(Account account) {
        builder.withAccountDebitted(account);
        return this;
    }

    public PrimaNotaEntryBuilder withAmount(DatabaseMoney amount) {
        builder.withAmount(amount);
        return this;
    }

    public PrimaNotaEntryBuilder withAccountingDate(LocalDate accountingDate) {
        builder.withAccountingDate(accountingDate);
        return this;
    }

    public PrimaNotaEntryBuilder withAccountCreditted(AccountImpl accountCreditted) {
        builder.withAccountCreditted(accountCreditted);
        return this;
    }

    public PrimaNotaEntryBuilder withCostCenter2(CostCenterImpl costCenter2) {
        builder.withCostCenter2(costCenter2);
        return this;
    }

    public PrimaNotaEntryBuilder withTaxKey(TaxKey taxKey) {
        builder.withTaxKey(taxKey);
        return this;
    }

    public PrimaNotaEntryBuilder withDisplayName(String displayName) {
        builder.withDisplayName(displayName);
        return this;
    }

    public PrimaNotaEntryBuilder withCostCenter1(CostCenter costCenter) {
        builder.withCostCenter1(costCenter);
        return this;
    }

    public PrimaNotaEntryBuilder withAmount(MonetaryAmount amount) {
        builder.withAmount(amount);
        return this;
    }

    public PrimaNotaEntryBuilder withFunctionKey(FunctionKeyImpl functionKey) {
        builder.withFunctionKey(functionKey);
        return this;
    }

    public PrimaNotaEntryBuilder withDocument(DocumentInformationImpl document) {
        builder.withDocument(document);
        return this;
    }

    public PrimaNotaEntryBuilder withDisplayNumber(String displayNumber) {
        builder.withDisplayNumber(displayNumber);
        return this;
    }

    public PrimaNotaEntryBuilder withNotice1(String notice1) {
        builder.withNotice1(notice1);
        return this;
    }

    public PrimaNotaEntryBuilder withEntryDate(OffsetDateTime entryDate) {
        builder.withEntryDate(entryDate);
        return this;
    }

    public PrimaNotaEntryBuilder withTenantId(UUID tenantId) {
        builder.withTenantId(tenantId);
        return this;
    }

    public PrimaNotaEntryBuilder withCostCenter1(CostCenterImpl costCenter1) {
        builder.withCostCenter1(costCenter1);
        return this;
    }

    public PrimaNotaEntryBuilder withAccountDebitted(AccountImpl accountDebitted) {
        builder.withAccountDebitted(accountDebitted);
        return this;
    }

    public PrimaNotaEntryBuilder withTaxKey(TaxKeyImpl taxKey) {
        builder.withTaxKey(taxKey);
        return this;
    }

    public PrimaNotaEntryBuilder withDocument(DocumentInformation document) {
        builder.withDocument(document);
        return this;
    }

    public PrimaNotaEntryBuilder withAccountCreditted(Account account) {
        builder.withAccountCreditted(account);
        return this;
    }

    public PrimaNotaEntryBuilder withValutaDate(LocalDate valutaDate) {
        builder.withValutaDate(valutaDate);
        return this;
    }

    public PrimaNotaEntryBuilder withCostCenter2(CostCenter costCenter) {
        builder.withCostCenter2(costCenter);
        return this;
    }

    public PrimaNotaEntryBuilder withPostingRecord(PostingRecord original) {
        builder.withPostingRecord(original);
        return this;
    }

    public PrimaNotaEntryBuilder withNotice2(String notice2) {
        builder.withNotice2(notice2);
        return this;
    }
}