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

import de.kaiserpfalzEdv.commons.util.BuilderException;
import org.apache.commons.lang3.builder.Builder;
import org.javamoney.moneta.CurrencyUnitBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.money.CurrencyUnit;
import java.util.ArrayList;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 16:36
 */
public class AccountBuilder implements Builder<Account> {
    private static final Logger       LOG      = LoggerFactory.getLogger(AccountBuilder.class);
    private static final CurrencyUnit currency = CurrencyUnitBuilder.of("EUR", MoneyAmountBuilder);

    private UUID   id;
    private String number;
    private String name;
    private String description;


    @Override
    public Account build() {
        validate();
        AccountImpl result = new AccountImpl(id, name);

        if (isNotBlank(number)) result.setNumber(number);
        if (isNotBlank(description)) result.setDescription(description);

        return result;
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (id == null) failures.add("An account needs an internal identifier!");
        if (name == null) failures.add("An account needs a name!");

        if (failures.size() > 0) {
            throw new BuilderException(failures);
        }
    }


    public AccountBuilder withAccount(final Account account) {
        withId(account.getId());
        withNumber(account.getNumber());
        withName(account.getName());
        withDescription(account.getDescription());

        return this;
    }


    public AccountBuilder withId(final UUID id) {
        this.id = id;

        return this;
    }

    public AccountBuilder newId() {
        this.id = UUID.randomUUID();

        return this;
    }


    public AccountBuilder withNumber(final String number) {
        this.number = number;

        return this;
    }


    public AccountBuilder withName(final String name) {
        this.name = name;

        return this;
    }


    public AccountBuilder withDescription(final String description) {
        this.description = description;

        return this;
    }
}
