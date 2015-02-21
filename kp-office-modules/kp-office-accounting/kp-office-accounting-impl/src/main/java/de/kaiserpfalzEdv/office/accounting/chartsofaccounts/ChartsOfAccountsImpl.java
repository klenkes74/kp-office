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
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 17:57
 */
public class ChartsOfAccountsImpl implements ChartsOfAccounts {
    private static final Logger LOG = LoggerFactory.getLogger(ChartsOfAccountsImpl.class);

    private final UUID   id;
    private final String name;

    private final HashMap<UUID, Account> accounts = new HashMap<>(500);


    public ChartsOfAccountsImpl(final UUID id, final String name) {
        this.id = id;
        this.name = name;

        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
        LOG.trace("  number of accounts: {}", accounts.size());
    }

    @PreDestroy
    public void close() {
        accounts.clear();

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
    public Account getAccount(@NotNull final UUID id) {
        return accounts.get(id);
    }

    @Override
    public Set<Account> allAccounts() {
        return Collections.unmodifiableSet(new HashSet<>(accounts.values()));
    }


    public void setAccounts(@NotNull final Collection<? extends Account> accounts) {
        this.accounts.clear();

        accounts.forEach(
                a -> {
                    LOG.trace("Add account: {}", a);
                    this.accounts.put(a.getId(), a);
                }
        );
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
        ChartsOfAccountsImpl rhs = (ChartsOfAccountsImpl) obj;
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
                .append("accounts", accounts.size())
                .toString();
    }
}
