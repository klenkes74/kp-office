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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.money.MonetaryAmount;
import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 28.02.15 13:08
 */
@Embeddable
public class AccountingVoucher implements Serializable {

    @Column(name = "document_number_")
    private String number;

    @Column(name = "document_date_")
    private LocalDate date;

    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "document_amount_value_")),
            @AttributeOverride(name = "currency", column = @Column(name = "document_amount_currency_"))
    })
    @Embedded
    private DatabaseMoney amount;


    @Deprecated // only for Jackson, JAX-B, JPA, ...
    protected AccountingVoucher() {}


    public AccountingVoucher(final String number, final LocalDate date, final MonetaryAmount amount) {
        this.number = number;
        this.date = date;
        this.amount = new DatabaseMoney(amount);
    }


    public String getNumber() {
        return number;
    }

    public LocalDate getDate() {
        return date;
    }

    public MonetaryAmount getAmount() {
        return amount.getMoney();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        AccountingVoucher rhs = (AccountingVoucher) obj;
        return new EqualsBuilder()
                .append(this.number, rhs.number)
                .append(this.date, rhs.date)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(number)
                .append(date)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("number", number)
                .append("date", date)
                .toString();
    }
}
