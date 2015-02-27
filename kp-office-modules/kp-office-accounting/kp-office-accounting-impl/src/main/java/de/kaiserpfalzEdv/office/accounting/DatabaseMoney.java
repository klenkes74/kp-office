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

package de.kaiserpfalzEdv.office.accounting;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 27.02.15 10:54
 */
@Embeddable
public class DatabaseMoney implements Serializable {
    private static final long serialVersionUID = 7385229438153397034L;


    @Column(name = "value_", nullable = false)
    private BigDecimal value;
    @Column(name = "currency_", nullable = false)
    private String     currency;


    @Deprecated // Only for Jackson, JAX-B, JPA, ...
    protected DatabaseMoney() {}

    public DatabaseMoney(MonetaryAmount money) {
        value = BigDecimal.valueOf(money.getNumber().doubleValueExact());
        currency = money.getCurrency().getCurrencyCode();
    }


    public BigDecimal getValue() {
        return value;
    }


    public String getCurrency() {
        return currency;
    }


    public MonetaryAmount getMoney() {
        return Money.of(value, currency);
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
        DatabaseMoney rhs = (DatabaseMoney) obj;
        return new EqualsBuilder()
                .append(this.value, rhs.value)
                .append(this.currency, rhs.currency)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(value)
                .append(currency)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(currency)
                .append(value)
                .toString();
    }
}
