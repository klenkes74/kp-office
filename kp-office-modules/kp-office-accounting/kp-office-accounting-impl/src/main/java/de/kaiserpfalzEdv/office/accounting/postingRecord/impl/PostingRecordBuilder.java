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

import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.accounting.DatabaseMoney;
import de.kaiserpfalzEdv.office.accounting.automation.FunctionKey;
import de.kaiserpfalzEdv.office.accounting.automation.impl.FunctionKeyImpl;
import de.kaiserpfalzEdv.office.accounting.automation.impl.TaxKeyImpl;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.CostCenter;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.AccountBuilder;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.AccountImpl;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.CostCenterBuilder;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.CostCenterImpl;
import de.kaiserpfalzEdv.office.accounting.postingRecord.DocumentInformation;
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import de.kaiserpfalzEdv.office.accounting.tax.TaxKey;
import org.apache.commons.lang3.builder.Builder;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Builds a {@link PostingRecord}.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 13.08.15 19:07
 */
public class PostingRecordBuilder implements Builder<PostingRecordImpl> {
    private UUID                    id;
    private String                  displayName;
    private String                  displayNumber;
    private UUID                    tenantId;
    private OffsetDateTime          entryDate;
    private LocalDate               accountingDate;
    private LocalDate               valutaDate;
    private DatabaseMoney           amount;
    private TaxKeyImpl              taxKey;
    private FunctionKeyImpl         functionKey;
    private AccountImpl             accountDebitted;
    private AccountImpl             accountCreditted;
    private CostCenterImpl          costCenter1;
    private CostCenterImpl          costCenter2;
    private DocumentInformationImpl document;
    private String                  notice1;
    private String                  notice2;


    @Override
    public PostingRecordImpl build() {
        calculateDefaults();
        validate();

        return new PostingRecordImpl(
                id, displayName, displayNumber, tenantId,
                entryDate, accountingDate, valutaDate,
                amount,
                taxKey, functionKey,
                accountDebitted, accountCreditted,
                costCenter1, costCenter2,
                document,
                notice1, notice2
        );
    }

    private void calculateDefaults() {
        if (id == null) id = UUID.randomUUID();
        if (isBlank(displayName)) displayName = id.toString();
        if (isBlank(displayNumber)) displayNumber = id.toString();

        if (entryDate == null) entryDate = OffsetDateTime.now();
        if (accountingDate == null) accountingDate = LocalDate.now();
        if (valutaDate == null) valutaDate = accountingDate;

        if (document == null) {
            document = new DocumentInformationImpl("", "", accountingDate, amount);
        }
    }

    public void validate() {
        ArrayList<String> errors = new ArrayList<>();


        if (id == null) errors.add("No ID given!");
        if (isBlank(displayName)) errors.add("No entry name given!");
        if (isBlank(displayNumber)) errors.add("No entry number given!");
        if (tenantId == null) errors.add("No tenant id given!");

        if (entryDate == null) errors.add("No entry date set!");
        if (accountingDate == null) errors.add("No accounting date set!");
        if (valutaDate == null) errors.add("No valuta date set!");

        if (amount == null) errors.add("No amount set!");
        if (accountDebitted == null) errors.add("No account debitted set!");
        if (accountCreditted == null) errors.add("No account creditted set!");

        if (document == null) errors.add("No document information given!");
        if (isBlank(notice1)) errors.add("No notice1 set!");

        if (!errors.isEmpty()) {
            throw new BuilderException(errors);
        }
    }


    public PostingRecordBuilder withPostingRecord(PostingRecord original) {
        withId(original.getId());
        withDisplayName(original.getDisplayNumber());
        withDisplayNumber(original.getDisplayNumber());
        withTenantId(original.getTenantId());

        withEntryDate(original.getEntryDate());
        withAccountingDate(original.getAccountingDate());
        withValutaDate(original.getValutaDate());

        withAmount(original.getAmount());
        withTaxKey(original.getTaxKey());
        withFunctionKey(original.getFunctionKey());
        withAccountDebitted(original.getAccountDebitted());
        withAccountCreditted(original.getAccountCreditted());

        withCostCenter1(original.getCostCenter1());
        withCostCenter2(original.getCostCenter2());

        withDocument(original.getDocumentInformation());
        withNotice1(original.getNotice());
        withNotice2(original.getNotice2());

        return this;
    }


    public PostingRecordBuilder withId(UUID id) {
        this.id = id;
        return this;
    }

    public PostingRecordBuilder withDisplayName(String displayName) {
        this.displayName = displayName;
        return this;
    }

    public PostingRecordBuilder withDisplayNumber(String displayNumber) {
        this.displayNumber = displayNumber;
        return this;
    }

    public PostingRecordBuilder withTenantId(UUID tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public PostingRecordBuilder withEntryDate(OffsetDateTime entryDate) {
        this.entryDate = entryDate;
        return this;
    }

    public PostingRecordBuilder withAccountingDate(LocalDate accountingDate) {
        this.accountingDate = accountingDate;
        return this;
    }

    public PostingRecordBuilder withValutaDate(LocalDate valutaDate) {
        this.valutaDate = valutaDate;
        return this;
    }

    public PostingRecordBuilder withAmount(DatabaseMoney amount) {
        this.amount = amount;
        return this;
    }

    public PostingRecordBuilder withAmount(MonetaryAmount amount) {
        return withAmount(new DatabaseMoney(amount));
    }

    public PostingRecordBuilder withTaxKey(TaxKeyImpl taxKey) {
        this.taxKey = taxKey;
        return this;
    }

    public PostingRecordBuilder withTaxKey(TaxKey taxKey) {
        return withTaxKey((TaxKeyImpl) taxKey);
    }

    public PostingRecordBuilder withFunctionKey(FunctionKeyImpl functionKey) {
        this.functionKey = functionKey;
        return this;
    }

    public PostingRecordBuilder withFunctionKey(FunctionKey key) {
        return withFunctionKey((FunctionKeyImpl) key);
    }

    public PostingRecordBuilder withAccountDebitted(AccountImpl accountDebitted) {
        this.accountDebitted = accountDebitted;
        return this;
    }

    public PostingRecordBuilder withAccountDebitted(Account account) {
        return withAccountDebitted(new AccountBuilder().withAccount(account).build());
    }

    public PostingRecordBuilder withAccountCreditted(AccountImpl accountCreditted) {
        this.accountCreditted = accountCreditted;
        return this;
    }

    public PostingRecordBuilder withAccountCreditted(Account account) {
        return withAccountCreditted(new AccountBuilder().withAccount(account).build());
    }

    public PostingRecordBuilder withCostCenter1(CostCenterImpl costCenter1) {
        this.costCenter1 = costCenter1;
        return this;
    }

    public PostingRecordBuilder withCostCenter1(CostCenter costCenter) {
        return withCostCenter1(new CostCenterBuilder().withCostCenter(costCenter).build());
    }

    public PostingRecordBuilder withCostCenter2(CostCenterImpl costCenter2) {
        this.costCenter2 = costCenter2;
        return this;
    }

    public PostingRecordBuilder withCostCenter2(CostCenter costCenter) {
        return withCostCenter2(new CostCenterBuilder().withCostCenter(costCenter).build());
    }

    public PostingRecordBuilder withDocument(DocumentInformationImpl document) {
        this.document = document;
        return this;
    }

    public PostingRecordBuilder withDocument(DocumentInformation document) {
        return withDocument(new DocumentInformationBuilder().withDocument(document).build());
    }

    public PostingRecordBuilder withNotice1(String notice1) {
        this.notice1 = notice1;
        return this;
    }

    public PostingRecordBuilder withNotice2(String notice2) {
        this.notice2 = notice2;
        return this;
    }
}
