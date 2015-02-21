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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 16:25
 */
public class AccountMappingImpl implements AccountMapping {
    private static final Logger LOG = LoggerFactory.getLogger(AccountMappingImpl.class);

    private final UUID   id;
    private final String name;
    private final HashMap<UUID, String>      displayAccountNumbers = new HashMap<>(1000);
    private final HashMap<String, Set<UUID>> accountIdByNumber     = new HashMap<>(1000);


    public AccountMappingImpl(@NotNull final String id, @NotNull final String name) {
        this(UUID.fromString(id), name);
    }

    public AccountMappingImpl(@NotNull final UUID id, @NotNull final String name) {
        this.id = id;
        this.name = name;
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
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
    public Account renumber(final Account account) {
        AccountBuilder result = new AccountBuilder().withAccount(account);

        if (displayAccountNumbers.containsKey(account.getId())) {
            result.withNumber(displayAccountNumbers.get(account.getId()));
        }

        return result.build();
    }

    @Override
    public String getNumberForAcount(@NotNull Account account) {
        return displayAccountNumbers.get(account.getId());
    }

    @Override
    public Set<UUID> getAccountsForNumber(@NotNull String number) {
        return Collections.unmodifiableSet(accountIdByNumber.get(number));
    }

    @Override
    public boolean isBookable(@NotNull String number) {
        try {
            return accountIdByNumber.get(number).size() == 1;
        } catch (NullPointerException e) {
            LOG.warn("Tried to read account '{}' from mapping. No account with this number defined.", number);
            return false;
        }
    }

    public void setTranslations(@NotNull final Map<String, String[]> translations) {
        translations.forEach(this::addTranslation);
    }

    public void addTranslation(final String number, final String[] accounts) {
        for (String account : accounts) {
            try {
                addTranslation(number, UUID.fromString(account));
            } catch (IllegalArgumentException e) {
                LOG.error("Could not import translation: {} -> {}", number, account);
            }
        }
    }


    public void addTranslation(final String number, final UUID account) {
        LOG.trace("Add account mapping: {} -> {}", account, number);

        addForwardMatching(number, account);
        addReverseMatching(number, account);
    }

    public void addTranslation(final String number, final UUID... accounts) {
        LOG.trace("Add account mapping: {} -> {}", number, accounts);

        for (UUID account : accounts) {
            addForwardMatching(number, account);
            addReverseMatching(number, account);
        }
    }

    private void addForwardMatching(@NotNull final String number, @NotNull final UUID account) {
        displayAccountNumbers.put(account, number);
    }

    private void addReverseMatching(@NotNull final String number, @NotNull final UUID account) {
        if (!accountIdByNumber.containsKey(number)) {
            accountIdByNumber.put(number, new HashSet<>(5));
        }
        accountIdByNumber.get(number).add(account);
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
        AccountMappingImpl rhs = (AccountMappingImpl) obj;
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
                .append("displayAccountNumbers", displayAccountNumbers.size())
                .append("accountIdByNumber", accountIdByNumber.size())
                .toString();
    }
}
