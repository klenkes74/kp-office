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
import de.kaiserpfalzEdv.office.accounting.automation.FunctionKey;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.AccountImpl;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.CostCenterImpl;
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import de.kaiserpfalzEdv.office.accounting.tax.TaxKey;
import de.kaiserpfalzEdv.office.commons.server.data.KPOTenantHoldingEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * The default posting record.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 11.08.15 03:08
 */
@MappedSuperclass
public abstract class PostingRecordImpl extends KPOTenantHoldingEntity implements PostingRecord {
    private static final Logger LOG = LoggerFactory.getLogger(PostingRecordImpl.class);

    private OffsetDateTime entryDate;
    private LocalDate      accountingDate;
    private LocalDate      valutaDate;

    private DatabaseMoney amount;

    private TaxKey      taxKey;
    private FunctionKey functionKey;

    private AccountImpl accountDebitted;
    private AccountImpl accountCreditted;

    private CostCenterImpl costCenter1;
    private CostCenterImpl costCenter2;

    private DocumentInfoImpl document;

    private String notice1;
    private String notice2;


    /**
     * @deprecated Only for brain-dead interfaces like JPA, Jackson, JAX-B, ...
     */
    @Deprecated
    public PostingRecordImpl() {}


    public PostingRecordImpl(@NotNull UUID id, @NotNull String displayName, @NotNull String displayNumber, @NotNull UUID tenantId, OffsetDateTime entryDate, LocalDate accountingDate, LocalDate valutaDate, DatabaseMoney amount, TaxKey taxKey, FunctionKey functionKey, AccountImpl accountDebitted, AccountImpl accountCreditted, CostCenterImpl costCenter1, CostCenterImpl costCenter2, DocumentInfoImpl document, String notice1, String notice2) {
        super(id, displayName, displayNumber, tenantId);

        this.entryDate = entryDate;
        this.accountingDate = accountingDate;
        this.valutaDate = valutaDate;
        this.amount = amount;
        this.taxKey = taxKey;
        this.functionKey = functionKey;
        this.accountDebitted = accountDebitted;
        this.accountCreditted = accountCreditted;
        this.costCenter1 = costCenter1;
        this.costCenter2 = costCenter2;
        this.document = document;
        this.notice1 = notice1;
        this.notice2 = notice2;
    }


    @Override
    public OffsetDateTime getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(OffsetDateTime entryDate) {
        this.entryDate = entryDate;
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

    public void setValutaDate(LocalDate valutaDate) {
        this.valutaDate = valutaDate;
    }

    @Override
    public DatabaseMoney getAmount() {
        return amount;
    }

    public void setAmount(DatabaseMoney amount) {
        this.amount = amount;
    }

    @Override
    public TaxKey getTaxKey() {
        return taxKey;
    }

    public void setTaxKey(TaxKey taxKey) {
        this.taxKey = taxKey;
    }

    @Override
    public FunctionKey getFunctionKey() {
        return functionKey;
    }

    public void setFunctionKey(FunctionKey functionKey) {
        this.functionKey = functionKey;
    }

    @Override
    public AccountImpl getAccountDebitted() {
        return accountDebitted;
    }

    public void setAccountDebitted(AccountImpl accountDebitted) {
        this.accountDebitted = accountDebitted;
    }

    @Override
    public AccountImpl getAccountCreditted() {
        return accountCreditted;
    }

    public void setAccountCreditted(AccountImpl accountCreditted) {
        this.accountCreditted = accountCreditted;
    }

    @Override
    public CostCenterImpl getCostCenter1() {
        return costCenter1;
    }

    public void setCostCenter1(CostCenterImpl costCenter1) {
        this.costCenter1 = costCenter1;
    }

    @Override
    public CostCenterImpl getCostCenter2() {
        return costCenter2;
    }

    public void setCostCenter2(CostCenterImpl costCenter2) {
        this.costCenter2 = costCenter2;
    }

    @Override
    public DocumentInfoImpl getDocumentInformation() {
        return document;
    }

    public void setDocumentInformation(DocumentInfoImpl document) {
        this.document = document;
    }

    @Override
    public String getNotice() {
        return notice1;
    }

    public void setNotice(String notice) {
        this.notice1 = notice;
    }

    @Override
    public String getNotice2() {
        return notice2;
    }

    public void setNotice2(String notice) {
        this.notice2 = notice;
    }
}
