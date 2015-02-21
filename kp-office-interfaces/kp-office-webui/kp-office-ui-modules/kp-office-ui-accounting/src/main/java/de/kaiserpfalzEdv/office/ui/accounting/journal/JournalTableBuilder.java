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

import com.vaadin.ui.Table;
import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.AccountMappingLocator;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.ChartsOfAccounts;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.UUID;

import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.counter;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.credits;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.debits;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.documentDate;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.documentNumber;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.entryDate;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.notice;
import static de.kaiserpfalzEdv.office.ui.accounting.journal.JournalQuery.Column.valutaDate;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 09:59
 */
@Named
@Scope("prototype")
public class JournalTableBuilder implements Builder<Table> {
    private static final Logger LOG = LoggerFactory.getLogger(JournalTableBuilder.class);

    private JournalDataLoader     client;
    private AccountMappingLocator mappingLocator;
    private ChartsOfAccounts      accounts;

    private String title = "Journal";
    private UUID journalId;
    private UUID mappingId;

    @Inject
    public JournalTableBuilder(
            final JournalDataLoader client,
            final AccountMappingLocator mappingLocator,
            final ChartsOfAccounts accounts
    ) {
        this.client = client;
        this.mappingLocator = mappingLocator;
        this.accounts = accounts;

        LOG.trace("Created: {}", this);
        LOG.trace("  journal data loader: {}", this.client);
        LOG.trace("  mapping locator: {}", this.mappingLocator);
        LOG.trace("  chart of accounts: {}", this.accounts);
    }

    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);

    }


    public Table build() {
        validate();

        LazyQueryContainer data = new JournalContainerBuilder()
                .withJournalId(journalId)
                .withClient(client)
                .withMapping(mappingLocator.getMapping(mappingId))
                .withAccounts(accounts)
                .build();

        Table result = new Table(title, data);

        result.setEditable(false);
        result.setColumnHeader(counter, "Journal-ID");
        result.setColumnHeader(documentDate, "Belegdatum");
        result.setColumnHeader(entryDate, "Eingabedatum");
        result.setColumnHeader(valutaDate, "Wertstellung");
        result.setColumnHeader(documentNumber, "Belegnummer");
        result.setColumnHeader(notice, "Anmerkung");
        result.setColumnHeader(debits, "SOLL");
        result.setColumnHeader(credits, "HABEN");
        result.setVisibleColumns(counter, documentDate, entryDate, valutaDate, documentNumber, notice, debits, credits);

        return result;
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (journalId == null) failures.add("No journal selected!");
        if (mappingId == null) failures.add("No mapping selected!");

        if (client == null) failures.add("No journal data loader!");
        if (mappingLocator == null) failures.add("No account mapping locator!");
        if (accounts == null) failures.add("No chart of accounts!");

        if (failures.size() > 0) {
            throw new BuilderException(failures);
        }
    }


    public JournalTableBuilder withTitle(@NotNull final String title) {
        this.title = title;

        return this;
    }

    public JournalTableBuilder withJournalId(@NotNull final UUID journalId) {
        this.journalId = journalId;

        return this;
    }

    public JournalTableBuilder withMappingId(@NotNull final UUID mappingId) {
        this.mappingId = mappingId;

        return this;
    }
}
