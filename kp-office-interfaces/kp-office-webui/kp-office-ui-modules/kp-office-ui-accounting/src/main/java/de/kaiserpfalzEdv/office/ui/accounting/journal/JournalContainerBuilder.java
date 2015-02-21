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

import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.AccountMapping;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.ChartsOfAccounts;
import org.apache.commons.lang3.builder.Builder;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;
import org.vaadin.addons.lazyquerycontainer.LazyQueryDefinition;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.counter;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.credit;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.credits;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.debit;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.debits;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.documentDate;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.documentNumber;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.entryDate;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.id;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.notice;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.valutaDate;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 11:10
 */
public class JournalContainerBuilder implements Builder<LazyQueryContainer> {
    private UUID              journalId;
    private JournalDataLoader client;
    private AccountMapping    mapping;
    private ChartsOfAccounts  accounts;

    @Override
    public LazyQueryContainer build() {
        validate();

        JournalQuery.Factory factory = new JournalQuery.Factory(journalId, client, mapping, accounts);
        LazyQueryDefinition query = new JournalQuery.QueryDefinition();

        LazyQueryContainer result = new LazyQueryContainer(query, factory);

        result.addContainerProperty(id, UUID.class, UUID.randomUUID(), true, false);
        result.addContainerProperty(counter, Integer.class, 0, true, true);
        result.addContainerProperty(documentNumber, String.class, "", true, false);
        result.addContainerProperty(documentDate, LocalDate.class, LocalDate.MIN, true, true);
        result.addContainerProperty(entryDate, LocalDate.class, LocalDate.MIN, true, true);
        result.addContainerProperty(valutaDate, LocalDate.class, LocalDate.MIN, true, true);
        result.addContainerProperty(notice, String.class, null, true, false);
        result.addContainerProperty(debit, MonetaryAmount.class, null, true, false);
        result.addContainerProperty(credit, MonetaryAmount.class, null, true, false);
        result.addContainerProperty(debits, List.class, new ArrayList<>(1), true, false);
        result.addContainerProperty(credits, List.class, new ArrayList<>(1), true, false);


        return result;
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (journalId == null) failures.add("No journal selected!");
        if (client == null) failures.add("No data source defined!");
        if (mapping == null) failures.add("No account mapping defined!");
        if (accounts == null) failures.add("No chart of accounts defined!");

        if (failures.size() > 0) {
            throw new BuilderException(failures);
        }
    }

    public JournalContainerBuilder withJournalId(@NotNull final UUID journalId) {
        this.journalId = journalId;

        return this;
    }

    public JournalContainerBuilder withClient(@NotNull final JournalDataLoader client) {
        this.client = client;

        return this;
    }

    public JournalContainerBuilder withMapping(@NotNull final AccountMapping mapping) {
        this.mapping = mapping;

        return this;
    }

    public JournalContainerBuilder withAccounts(@NotNull final ChartsOfAccounts accounts) {
        this.accounts = accounts;

        return this;
    }
}
