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

package de.kaiserpfalzEdv.office.accounting.automation.impl;

import de.kaiserpfalzEdv.office.accounting.automation.TaxKey;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.AccountImpl;
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import de.kaiserpfalzEdv.office.commons.data.KPOEntity;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 15.08.15 06:41
 */
public class TaxKeyImpl extends KPOEntity implements TaxKey {
    private static final long serialVersionUID = -7830877835221060360L;

    private AccountImpl debitted;

    private AccountImpl creditted;

    private BigDecimal rate;


    /**
     * @deprecated Only for brain-dead-framework defintions like JPA, JAX-B or Jackson ...
     */
    @Deprecated
    protected TaxKeyImpl() {}

    public TaxKeyImpl(
            @NotNull final BigDecimal rate,
            @NotNull final AccountImpl debitted,
            @NotNull final AccountImpl creditted
    ) {
        setTaxRate(rate);
        setDebitted(debitted);
        setCreditted(creditted);
    }


    @Override
    public AccountImpl getAccountCreditted() {
        return creditted;
    }

    public void setCreditted(@NotNull final AccountImpl creditted) {
        this.creditted = creditted;
    }


    @Override
    public AccountImpl getAccountDebitted() {
        return debitted;
    }

    public void setDebitted(@NotNull final AccountImpl debitted) {
        this.debitted = debitted;
    }


    @Override
    public BigDecimal getTaxRate() {
        return rate;
    }

    public void setTaxRate(@NotNull final BigDecimal rate) {
        this.rate = rate;
    }

    @Override
    public PostingRecord calculateCredittedPostingRecord(PostingRecord originalRecord, MonetaryAmount amount) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.accounting.automation.impl.TaxKeyImpl.calculateCredittedPostingRecord
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PostingRecord calculateDebittedPostingRecord(PostingRecord originalRecord, MonetaryAmount amount) {
        // TODO klenkes Auto defined stub for: de.kaiserpfalzEdv.office.accounting.automation.impl.TaxKeyImpl.calculateDebittedPostingRecord
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
