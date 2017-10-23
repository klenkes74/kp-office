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

package de.kaiserpfalzedv.office.finance.accounting.impl;

import java.time.LocalDate;
import java.time.OffsetDateTime;

import javax.money.MonetaryAmount;

import de.kaiserpfalzedv.office.finance.accounting.api.BasePostingRecord;
import de.kaiserpfalzedv.office.finance.accounting.api.DocumentInformation;
import de.kaiserpfalzedv.office.finance.accounting.api.accounts.Account;
import de.kaiserpfalzedv.office.finance.accounting.api.accounts.CostCenter;
import de.kaiserpfalzedv.office.finance.accounting.impl.accounts.AccountImpl;
import de.kaiserpfalzedv.office.finance.accounting.impl.accounts.CostCenterImpl;
import org.javamoney.moneta.Money;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 28.12.15 07:08
 */
public class AbstractPostingRecord implements BasePostingRecord {
    private OffsetDateTime entryDate;
    private LocalDate      accountingDate;
    private LocalDate      valutaDate;

    private CostCenterImpl costCenter1;
    private CostCenterImpl costCenter2;

    private DocumentInformationImpl document;
    private String                  notice1;
    private String                  notice2;

    private AccountImpl debitted;
    private AccountImpl credited;

    private Money amount;

    public void setDebitted(AccountImpl debitted) {
        this.debitted = debitted;
    }

    public void setCredited(AccountImpl credited) {
        this.credited = credited;
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
    public CostCenter getCostCenter1() {
        return costCenter1;
    }

    public void setCostCenter1(CostCenterImpl costCenter1) {
        this.costCenter1 = costCenter1;
    }

    @Override
    public CostCenter getCostCenter2() {
        return costCenter2;
    }

    public void setCostCenter2(CostCenterImpl costCenter2) {
        this.costCenter2 = costCenter2;
    }

    @Override
    public DocumentInformation getDocument() {
        return document;
    }

    public void setDocument(DocumentInformationImpl document) {
        this.document = document;
    }

    @Override
    public String getNotice1() {
        return notice1;
    }

    public void setNotice1(String notice1) {
        this.notice1 = notice1;
    }

    @Override
    public String getNotice2() {
        return notice2;
    }

    public void setNotice2(String notice2) {
        this.notice2 = notice2;
    }

    @Override
    public Account getAccountDebitted() {
        return debitted;
    }

    @Override
    public Account getAccountCreditted() {
        return credited;
    }

    @Override
    public MonetaryAmount getAmount() {
        return amount;
    }

    public void setAmount(Money amount) {
        this.amount = amount;
    }
}
