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

package de.kaiserpfalzEdv.office.ui.accounting.journal;

import com.vaadin.data.Item;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.AccountMapping;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.ChartsOfAccounts;
import de.kaiserpfalzEdv.office.accounting.journal.AccountChange;
import de.kaiserpfalzEdv.office.accounting.journal.Journal;
import de.kaiserpfalzEdv.office.accounting.journal.JournalEntry;
import de.kaiserpfalzEdv.office.accounting.journal.JournalEntryBuilder;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.lazyquerycontainer.LazyQueryDefinition;
import org.vaadin.addons.lazyquerycontainer.Query;
import org.vaadin.addons.lazyquerycontainer.QueryFactory;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 16:18
 */
public class JournalQuery implements Query {
    private static final Logger LOG = LoggerFactory.getLogger(JournalQuery.class);
    private ChartsOfAccounts accounts;
    private Journal          journal;
    private AccountMapping   mapping;

    JournalQuery(
            @NotNull final Journal journal,
            @NotNull final AccountMapping mapping,
            @NotNull final ChartsOfAccounts accounts
    ) {
        this.journal = journal;
        this.mapping = mapping;
        this.accounts = accounts;

        LOG.trace("Created: {}", this);
        LOG.trace("  chart of account: {}", accounts);
        LOG.trace("  auto selected journal: {}", journal);
        LOG.trace("  auto selected mapping: {}", mapping);
    }

    @Override
    public int size() {
        return journal.size();
    }

    @Override
    public List<Item> loadItems(int startIndex, int count) {
        ArrayList<Item> result = new ArrayList<>(count);

        List<? extends JournalEntry> entries = journal.getEntries().subList(startIndex, startIndex + count);

        entries.forEach(e -> result.add(constructItem(mapAccounts(e))));

        return result;
    }

    private JournalEntry mapAccounts(JournalEntry e) {
        JournalEntryBuilder result = new JournalEntryBuilder().withEntry(e).clearAmounts();

        ArrayList<AccountChange> debits = e.getDebits();
        ArrayList<AccountChange> credits = e.getCredits();

        debits.forEach(c -> result.addDebit(mapping.renumber(c.getAccount()), c.getAmount()));
        credits.forEach(c -> result.addCredit(mapping.renumber(c.getAccount()), c.getAmount()));

        return result.build();
    }

    private Item constructItem(@NotNull final JournalEntry entry) {
        PropertysetItem result = new PropertysetItem();

        result.addItemProperty(Column.id, new ObjectProperty<>(entry.getId()));
        result.addItemProperty(Column.counter, new ObjectProperty<>(entry.getJournalEntryCounter()));
        result.addItemProperty(Column.entryDate, new ObjectProperty<>(entry.getEntryDate(), LocalDate.class));
        result.addItemProperty(Column.valutaDate, new ObjectProperty<>(entry.getValutaDate(), LocalDate.class));
        result.addItemProperty(Column.documentNumber, new ObjectProperty<>(entry.getDocumentNumber()));
        result.addItemProperty(Column.documentDate, new ObjectProperty<>(entry.getDocumentDate(), LocalDate.class));
        result.addItemProperty(Column.notice, new ObjectProperty<>(entry.getNotice(), String.class));
        result.addItemProperty(Column.debit, new ObjectProperty<>(entry.getDebit(), MonetaryAmount.class));
        result.addItemProperty(Column.credit, new ObjectProperty<>(entry.getCredit(), MonetaryAmount.class));
        result.addItemProperty(Column.debits, new ObjectProperty<List<AccountChange>>(entry.getDebits()));
        result.addItemProperty(Column.credits, new ObjectProperty<List<AccountChange>>(entry.getCredits()));

        LOG.trace("Converted: {} -> {}", entry, result);
        return result;
    }

    @Override
    public void saveItems(List<Item> list, List<Item> list1, List<Item> list2) {
        throw new UnsupportedOperationException("Sorry, read only demo implementation!");
    }

    @Override
    public boolean deleteAllItems() {
        throw new UnsupportedOperationException("Sorry, read only demo implementation!");
    }

    @Override
    public Item constructItem() {
        LOG.error("Should not call {}.constructItem!", getClass().getSimpleName());

        PropertysetItem result = new PropertysetItem();

        result.addItemProperty(Column.id, new ObjectProperty<>(UUID.randomUUID()));
        result.addItemProperty(Column.counter, new ObjectProperty<>(0));
        result.addItemProperty(Column.entryDate, new ObjectProperty<>(LocalDate.MIN));
        result.addItemProperty(Column.valutaDate, new ObjectProperty<>(LocalDate.MIN));
        result.addItemProperty(Column.documentNumber, new ObjectProperty<>(""));
        result.addItemProperty(Column.documentDate, new ObjectProperty<>(LocalDate.MIN));
        result.addItemProperty(Column.notice, new ObjectProperty<>(""));
        result.addItemProperty(Column.debit, new ObjectProperty<MonetaryAmount>(Money.of(0, "EUR")));
        result.addItemProperty(Column.credit, new ObjectProperty<MonetaryAmount>(Money.of(0, "EUR")));
        result.addItemProperty(Column.debits, new ObjectProperty<>(generateNullAccountChange(UUID.fromString("ddb684b8-501c-47fc-a46c-7990110bf092"))));
        result.addItemProperty(Column.credits, new ObjectProperty<>(generateNullAccountChange(UUID.fromString("804327a3-50f7-4f2c-8ca0-1c3cd502b1ab"))));

        return result;
    }

    private List<AccountChange> generateNullAccountChange(@NotNull final UUID accountId) {
        ArrayList<AccountChange> result = new ArrayList<>(1);

        result.add(new AccountChange(accounts.getAccount(accountId), Money.of(0, "EUR")));

        return result;
    }

    public static enum Column {
        id, counter, documentDate, entryDate, valutaDate, documentNumber, notice, debits, credits, debit, credit;

        public boolean is(Object other) {
            if (other instanceof String)
                return this.toString().equals(other);
            else
                return (this == other);
        }
    }

    public static class Factory implements QueryFactory {
        private JournalDataLoader client;
        private ChartsOfAccounts  accounts;
        private AccountMapping    mapping;

        private UUID journalId;

        public Factory(
                @NotNull final UUID journalId,
                @NotNull final JournalDataLoader client,
                @NotNull final AccountMapping mapping,
                @NotNull final ChartsOfAccounts accounts
        ) {
            this.client = client;
            this.accounts = accounts;
            this.mapping = mapping;

            this.journalId = journalId;

            LOG.trace("Created: {}", this);
            LOG.trace("  journal client: {}", client);
            LOG.trace("  chart of account: {}", accounts);
            LOG.trace("  journal id: {}", journalId);
            LOG.trace("  account mapping: {}", mapping);
        }


        @Override
        public Query constructQuery(org.vaadin.addons.lazyquerycontainer.QueryDefinition queryDefinition) {
            Journal journal = client.loadJournal(journalId);

            return new JournalQuery(journal, mapping, accounts);
        }
    }

    public static class QueryDefinition extends LazyQueryDefinition {

        public QueryDefinition() {
            super(false, 20, Column.id);
        }
    }
}