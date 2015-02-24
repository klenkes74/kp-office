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

package de.kaiserpfalzEdv.office.ui.accounting.primanota;

import com.vaadin.ui.Table;
import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.AccountMappingLocator;
import de.kaiserpfalzEdv.office.accounting.primaNota.impl.PrimanotaDataLoader;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;
import org.vaadin.spring.i18n.I18N;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.UUID;

import static de.kaiserpfalzEdv.office.ui.accounting.primanota.PrimanotaQuery.Column.accountCreditted;
import static de.kaiserpfalzEdv.office.ui.accounting.primanota.PrimanotaQuery.Column.accountDebitted;
import static de.kaiserpfalzEdv.office.ui.accounting.primanota.PrimanotaQuery.Column.amount;
import static de.kaiserpfalzEdv.office.ui.accounting.primanota.PrimanotaQuery.Column.documentAmount;
import static de.kaiserpfalzEdv.office.ui.accounting.primanota.PrimanotaQuery.Column.documentDate;
import static de.kaiserpfalzEdv.office.ui.accounting.primanota.PrimanotaQuery.Column.documentNumber;
import static de.kaiserpfalzEdv.office.ui.accounting.primanota.PrimanotaQuery.Column.entryDate;
import static de.kaiserpfalzEdv.office.ui.accounting.primanota.PrimanotaQuery.Column.entryId;
import static de.kaiserpfalzEdv.office.ui.accounting.primanota.PrimanotaQuery.Column.notice;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 09:59
 */
@Named
@Scope("prototype")
public class PrimanotaTableBuilder implements Builder<Table> {
    private static final Logger LOG = LoggerFactory.getLogger(PrimanotaTableBuilder.class);

    private I18N i18n;

    private PrimanotaDataLoader client;
    private AccountMappingLocator mappingLocator;

    private UUID journalId;
    private UUID mappingId;

    @Inject
    public PrimanotaTableBuilder(
            final PrimanotaDataLoader client,
            final AccountMappingLocator mappingLocator,
            final I18N i18n
    ) {
        this.client = client;
        this.mappingLocator = mappingLocator;
        this.i18n = i18n;

        LOG.trace("Created: {}", this);
        LOG.trace("  primaNota data loader: {}", this.client);
        LOG.trace("  mapping locator: {}", this.mappingLocator);
        LOG.trace("  i18n provider: {}", this.i18n);
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

        LazyQueryContainer data = new PrimanotaContainerBuilder()
                .withJournalId(journalId)
                .withClient(client)
                .withMapping(mappingLocator.getMapping(mappingId))
                .build();

        String title = client.loadJournal(journalId).getDisplayName();
        Table result = new Table(title, data);

        result.setEditable(false);

        for (PrimanotaQuery.Column c : PrimanotaQuery.Column.values()) {
            result.setColumnHeader(c, i18n.get("office.ui.accounting.primanota.column." + c.toString()));
        }

        result.setVisibleColumns(entryId, entryDate, documentNumber, documentDate, documentAmount, notice, accountDebitted, accountCreditted, amount);

        return result;
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (journalId == null) failures.add("No primaNota selected!");
        if (mappingId == null) failures.add("No mapping selected!");

        if (client == null) failures.add("No primaNota data loader!");
        if (mappingLocator == null) failures.add("No account mapping locator!");

        if (failures.size() > 0) {
            throw new BuilderException(failures);
        }
    }


    public PrimanotaTableBuilder withJournalId(@NotNull final UUID journalId) {
        this.journalId = journalId;

        return this;
    }

    public PrimanotaTableBuilder withMappingId(@NotNull final UUID mappingId) {
        this.mappingId = mappingId;

        return this;
    }
}
