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

package de.kaiserpfalzEdv.office.accounting.journal;

import de.kaiserpfalzEdv.office.ui.accounting.chartsofaccounts.Account;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotNull;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 20.02.15 23:39
 */
public class AccountChange {
    private MonetaryAmount amount;
    private Account        account;

    public AccountChange(@NotNull final Account account, @NotNull final MonetaryAmount amount) {
        this.account = account;
        this.amount = amount;
    }

    public MonetaryAmount getAmount() {
        return amount;
    }

    public Account getAccount() {
        return account;
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
        AccountChange rhs = (AccountChange) obj;
        return new EqualsBuilder()
                .append(this.amount, rhs.amount)
                .append(this.account, rhs.account)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(amount)
                .append(account)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(amount.getNumber())
                .append(amount.getCurrency().getCurrencyCode())
                .append(account)
                .toString();
    }
}

