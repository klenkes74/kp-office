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

import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.AccountImpl;
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import de.kaiserpfalzEdv.office.accounting.tax.TaxKey;
import de.kaiserpfalzEdv.office.commons.server.data.KPOEntity;

import javax.money.MonetaryAmount;
import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 15.08.15 06:41
 */
@Entity
@Cacheable
@Table(
        schema = "accounting",
        catalog = "accounting",
        name = "taxkey",
        uniqueConstraints = {
                @UniqueConstraint(name = "taxkey_name_fk", columnNames = {"tenant_", "display_name_"}),
                @UniqueConstraint(name = "taxkey_number_fk", columnNames = {"tenant_", "display_number_"})
        }
)
public class TaxKeyImpl extends KPOEntity implements TaxKey {
    private static final long serialVersionUID = -7830877835221060360L;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "debitted_", nullable = false)
    private AccountImpl debitted;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    @JoinColumn(name = "creditted_", nullable = false)
    private AccountImpl creditted;

    @Column(name = "rate_", nullable = false)
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
