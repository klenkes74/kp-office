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

import javax.money.CurrencyUnit;
import javax.money.MonetaryAmount;
import javax.money.MonetaryAmountFactory;
import javax.money.MonetaryContext;
import javax.money.NumberValue;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A database version of the {@link MonetaryAmount} interface. It uses a transient {@link MonetaryAmount} to delegate
 * all methods of the {@link MonetaryAmount} interface to it. The database consists of two columns: the
 * {@link BigDecimal} containing the value (named "value_") and a string named "currency_" containing the currency code
 * as used by the monetary amount. As embeddable the column names should be overwritten using the appropriate
 * annotations:
 *
 * <code>
 *     @AttributeOverrides({
 *     @AttributeOverride(name = "value", column = @Column(name = "document_amount_value_")),
 *     @AttributeOverride(name = "currency", column = @Column(name = "document_amount_currency_"))
 *     })
 *     @Embedded
 *     private DatabaseMoney amount;
</code>
 *
 * @author klenkes
 * @version 2015Q1
 * @since 27.02.15 10:54
 */
@Embeddable
public class DatabaseMoney implements MonetaryAmount, Serializable {
    private static final long serialVersionUID = 409128734911221329L;


    @Column(name = "value_", nullable = false)
    private BigDecimal value;
    @Column(name = "currency_", nullable = false)
    private String     currency;

    @Transient
    private MonetaryAmount money;


    @Deprecated // Only for Jackson, JAX-B, JPA, ...
    protected DatabaseMoney() {}

    public DatabaseMoney(@NotNull MonetaryAmount money) {
        setMoney(money);
    }


    public BigDecimal getValue() {
        return value;
    }

    public void setValue(@NotNull final BigDecimal value) {
        this.value = value;

        unsetMoney();
    }


    public String getCurrencyCode() {
        return currency;
    }

    public void setCurrencyCode(@NotNull final String currency) {
        this.currency = currency;

        unsetMoney();
    }


    public MonetaryAmount getMoney() {
        if (money == null)
            money = Money.of(value, currency);

        return money;
    }

    public synchronized void setMoney(@NotNull final MonetaryAmount money) {
        this.money = money;

        value = BigDecimal.valueOf(money.getNumber().doubleValueExact());
        currency = money.getCurrency().getCurrencyCode();
    }

    public synchronized void unsetMoney() {
        this.money = null;
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
                .append(this.value, rhs.getValue())
                .append(this.currency, rhs.getCurrency())
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


    @Override
    public MonetaryContext getMonetaryContext() {
        return getMoney().getMonetaryContext();
    }

    @Override
    public MonetaryAmountFactory<? extends MonetaryAmount> getFactory() {
        return getMoney().getFactory();
    }

    @Override
    public boolean isGreaterThan(MonetaryAmount amount) {
        return getMoney().isGreaterThan(amount);
    }

    @Override
    public boolean isGreaterThanOrEqualTo(MonetaryAmount amount) {
        return getMoney().isGreaterThanOrEqualTo(amount);
    }

    @Override
    public boolean isLessThan(MonetaryAmount amount) {
        return getMoney().isLessThan(amount);
    }

    @Override
    public boolean isLessThanOrEqualTo(MonetaryAmount amt) {
        return getMoney().isLessThanOrEqualTo(amt);
    }

    @Override
    public boolean isEqualTo(MonetaryAmount amount) {
        return getMoney().isEqualTo(amount);
    }

    @Override
    public int signum() {
        return getMoney().signum();
    }

    @Override
    public MonetaryAmount add(MonetaryAmount amount) {
        return getMoney().add(amount);
    }

    @Override
    public MonetaryAmount subtract(MonetaryAmount amount) {
        return getMoney().subtract(amount);
    }

    @Override
    public MonetaryAmount multiply(long multiplicand) {
        return getMoney().multiply(multiplicand);
    }

    @Override
    public MonetaryAmount multiply(double multiplicand) {
        return getMoney().multiply(multiplicand);
    }

    @Override
    public MonetaryAmount multiply(Number multiplicand) {
        return getMoney().multiply(multiplicand);
    }

    @Override
    public MonetaryAmount divide(long divisor) {
        return getMoney().divide(divisor);
    }

    @Override
    public MonetaryAmount divide(double divisor) {
        return getMoney().divide(divisor);
    }

    @Override
    public MonetaryAmount divide(Number divisor) {
        return getMoney().divide(divisor);
    }

    @Override
    public MonetaryAmount remainder(long divisor) {
        return getMoney().remainder(divisor);
    }

    @Override
    public MonetaryAmount remainder(double divisor) {
        return getMoney().remainder(divisor);
    }

    @Override
    public MonetaryAmount remainder(Number divisor) {
        return getMoney().remainder(divisor);
    }

    @Override
    public MonetaryAmount[] divideAndRemainder(long divisor) {
        return getMoney().divideAndRemainder(divisor);
    }

    @Override
    public MonetaryAmount[] divideAndRemainder(double divisor) {
        return getMoney().divideAndRemainder(divisor);
    }

    @Override
    public MonetaryAmount[] divideAndRemainder(Number divisor) {
        return getMoney().divideAndRemainder(divisor);
    }

    @Override
    public MonetaryAmount divideToIntegralValue(long divisor) {
        return getMoney().divideToIntegralValue(divisor);
    }

    @Override
    public MonetaryAmount divideToIntegralValue(double divisor) {
        return getMoney().divideToIntegralValue(divisor);
    }

    @Override
    public MonetaryAmount divideToIntegralValue(Number divisor) {
        return getMoney().divideToIntegralValue(divisor);
    }

    @Override
    public MonetaryAmount scaleByPowerOfTen(int power) {
        return getMoney().scaleByPowerOfTen(power);
    }

    @Override
    public MonetaryAmount abs() {
        return getMoney().abs();
    }

    @Override
    public MonetaryAmount negate() {
        return getMoney().negate();
    }

    @Override
    public MonetaryAmount plus() {
        return getMoney().plus();
    }

    @Override
    public MonetaryAmount stripTrailingZeros() {
        return getMoney().stripTrailingZeros();
    }

    @Override
    public int compareTo(@NotNull MonetaryAmount o) {
        return getMoney().compareTo(o);
    }

    @Override
    public NumberValue getNumber() {
        return getMoney().getNumber();
    }

    @Override
    public CurrencyUnit getCurrency() {
        return getMoney().getCurrency();
    }
}
