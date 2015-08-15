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
import de.kaiserpfalzEdv.office.accounting.automation.impl.FunctionKeyImpl;
import de.kaiserpfalzEdv.office.accounting.automation.impl.TaxKeyImpl;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.AccountImpl;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.CostCenterImpl;
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import de.kaiserpfalzEdv.office.commons.server.data.KPOTenantHoldingEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

import static javax.persistence.FetchType.LAZY;

/**
 * The default posting record.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 11.08.15 03:08
 */
@MappedSuperclass
public class PostingRecordImpl extends KPOTenantHoldingEntity implements PostingRecord {
    private static final long serialVersionUID = 3240890814688813211L;


    @Column(name = "date_entry_", nullable = false, insertable = true, updatable = false)
    private OffsetDateTime entryDate;

    @Column(name = "date_accounting_", nullable = false, insertable = true, updatable = true)
    private LocalDate accountingDate;

    @Column(name = "date_valuta_", nullable = false, insertable = true, updatable = true)
    private LocalDate valutaDate;


    @Embedded
    private DatabaseMoney amount;


    @ManyToOne(optional = true, fetch = LAZY)
    @JoinColumn(name = "taxkey_")
    private TaxKeyImpl taxKey;

    @ManyToOne(optional = true, fetch = LAZY)
    @JoinColumn(name = "functionkey_")
    private FunctionKeyImpl functionKey;


    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "debitted_", nullable = false)
    private AccountImpl accountDebitted;

    @ManyToOne(optional = false, fetch = LAZY)
    @JoinColumn(name = "creditted_", nullable = false)
    private AccountImpl accountCreditted;


    @ManyToOne(optional = true, fetch = LAZY)
    @JoinColumn(name = "costcenter1_")
    private CostCenterImpl costCenter1;

    @ManyToOne(optional = true, fetch = LAZY)
    @JoinColumn(name = "costcenter2_")
    private CostCenterImpl costCenter2;

    @Embedded
    private DocumentInformationImpl document;


    @Column(name = "notice1_", length = 25)
    private String notice1;

    @Column(name = "notice2_", length = 25)
    private String notice2;


    /**
     * @deprecated Only for brain-dead interfaces like JPA, Jackson, JAX-B, ...
     */
    @Deprecated
    public PostingRecordImpl() {}


    public PostingRecordImpl(
            @NotNull UUID id,
            @NotNull String displayName,
            @NotNull String displayNumber,
            @NotNull UUID tenantId,
            @NotNull OffsetDateTime entryDate,
            @NotNull LocalDate accountingDate,
            @NotNull LocalDate valutaDate,
            @NotNull DatabaseMoney amount,
            TaxKeyImpl taxKey,
            FunctionKeyImpl functionKey,
            @NotNull AccountImpl accountDebitted,
            @NotNull AccountImpl accountCreditted,
            CostCenterImpl costCenter1,
            CostCenterImpl costCenter2,
            @NotNull DocumentInformationImpl document,
            @NotNull String notice1,
            String notice2
    ) {
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
    public TaxKeyImpl getTaxKey() {
        return taxKey;
    }

    public void setTaxKey(TaxKeyImpl taxKey) {
        this.taxKey = taxKey;
    }

    @Override
    public FunctionKeyImpl getFunctionKey() {
        return functionKey;
    }

    public void setFunctionKey(FunctionKeyImpl functionKey) {
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
    public DocumentInformationImpl getDocumentInformation() {
        return document;
    }

    public void setDocumentInformation(DocumentInformationImpl document) {
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


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("entryDate", entryDate)
                .append("accountingDate", accountingDate)
                .append("valutaDate", valutaDate)
                .append("amount", amount)
                .append("notice1", notice1)
                .toString();
    }
}
