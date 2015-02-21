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

package de.kaiserpfalzEdv.office.accounting.chartsofaccounts;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.money.CurrencyUnit;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 16:20
 */
public class AccountImpl implements Serializable, Account {
    private static final long serialVersionUID = 411611536246419400L;


    private UUID         id;
    private String       number;
    private String       name;
    private String       description;
    private CurrencyUnit currency;

    AccountImpl(@NotNull final UUID id, @NotNull final String name, @NotNull final CurrencyUnit currency) {
        this.id = id;
        this.name = name;
        this.currency = currency;
    }


    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    void setDescription(@NotNull String description) {
        this.description = description;
    }

    @Override
    public CurrencyUnit getCurrency() {
        return currency;
    }

    @Override
    public String getNumber() {
        return isNotBlank(number) ? number : "";
    }

    void setNumber(@NotNull String number) {
        this.number = number;
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
        AccountImpl rhs = (AccountImpl) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("name", name)
                .append("description", description)
                .toString();
    }
}
