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

import com.vaadin.data.Item;
import com.vaadin.data.util.ObjectProperty;
import com.vaadin.data.util.PropertysetItem;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.AccountMapping;
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNota;
import de.kaiserpfalzEdv.office.accounting.primaNota.impl.PrimaNotaDataLoader;
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
public class PrimanotaQuery implements Query {
    private static final Logger LOG = LoggerFactory.getLogger(PrimanotaQuery.class);
    private PrimaNota primaNota;
    private AccountMapping mapping;

    PrimanotaQuery(
            @NotNull final PrimaNota primaNota,
            @NotNull final AccountMapping mapping
    ) {
        this.primaNota = primaNota;
        this.mapping = mapping;

        LOG.trace("Created: {}", this);
        LOG.trace("  auto selected primaNota: {}", primaNota);
        LOG.trace("  auto selected mapping: {}", mapping);
    }

    @Override
    public int size() {
        return primaNota.size();
    }

    @Override
    public List<Item> loadItems(int startIndex, int count) {
        ArrayList<Item> result = new ArrayList<>(count);

        List<? extends PostingRecord> entries = primaNota.getEntries().subList(startIndex, startIndex + count);

        entries.forEach(e -> result.add(constructItem(e)));

        return result;
    }

    private Item constructItem(@NotNull final PostingRecord entry) {
        PropertysetItem result = new PropertysetItem();

        result.addItemProperty(Column.id, new ObjectProperty<>(entry.getId()));
        result.addItemProperty(Column.entryId, new ObjectProperty<>(entry.getDisplayNumber()));
        result.addItemProperty(Column.entryDate, new ObjectProperty<>(entry.getEntryDate(), LocalDate.class));
        result.addItemProperty(Column.accountingDate, new ObjectProperty<>(entry.getAccountingDate(), LocalDate.class));
        result.addItemProperty(Column.valutaDate, new ObjectProperty<>(entry.getValutaDate(), LocalDate.class));
        result.addItemProperty(Column.documentNumber, new ObjectProperty<>(entry.getDocumentNumber()));
        result.addItemProperty(Column.documentDate, new ObjectProperty<>(entry.getDocumentDate(), LocalDate.class));
        result.addItemProperty(Column.documentAmount, new ObjectProperty<>(entry.getDocumentAmount()));
        result.addItemProperty(Column.notice, new ObjectProperty<>(entry.getNotice(), String.class));
        result.addItemProperty(
                Column.accountDebitted, new ObjectProperty<>(
                        mapping.renumber(entry.getAccountDebitted())
                               .getDisplayNumber()
                )
        );
        result.addItemProperty(
                Column.accountCreditted, new ObjectProperty<>(
                        mapping.renumber(entry.getAccountCreditted())
                               .getDisplayNumber()
                )
        );
        result.addItemProperty(Column.amount, new ObjectProperty<>(entry.getPostingAmount()));

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

        result.addItemProperty(Column.tenantId, new ObjectProperty<>(UUID.randomUUID()));
        result.addItemProperty(Column.id, new ObjectProperty<>(UUID.randomUUID()));
        result.addItemProperty(Column.entryId, new ObjectProperty<>("" + 0));
        result.addItemProperty(Column.entryDate, new ObjectProperty<>(LocalDate.MIN));
        result.addItemProperty(Column.accountingDate, new ObjectProperty<>(LocalDate.MIN));
        result.addItemProperty(Column.valutaDate, new ObjectProperty<>(LocalDate.MIN));

        result.addItemProperty(Column.documentNumber, new ObjectProperty<>(""));
        result.addItemProperty(Column.documentDate, new ObjectProperty<>(LocalDate.MIN));
        result.addItemProperty(Column.documentAmount, new ObjectProperty<>(null, MonetaryAmount.class));
        result.addItemProperty(Column.notice, new ObjectProperty<>(""));

        result.addItemProperty(Column.accountDebitted, new ObjectProperty<>(null, Account.class));
        result.addItemProperty(Column.accountCreditted, new ObjectProperty<>(null, Account.class));
        result.addItemProperty(Column.amount, new ObjectProperty<>(null, MonetaryAmount.class));

        return result;
    }


    public static enum Column {
        tenantId, id, entryId, entryDate, accountingDate, valutaDate, documentNumber, documentDate, documentAmount, notice, accountDebitted, accountCreditted, amount;

        public boolean is(Object other) {
            if (other instanceof String)
                return this.toString().equals(other);
            else
                return (this == other);
        }
    }

    public static class Factory implements QueryFactory {
        private PrimaNotaDataLoader client;
        private AccountMapping      mapping;

        private UUID journalId;

        public Factory(
                @NotNull final UUID journalId,
                @NotNull final PrimaNotaDataLoader client,
                @NotNull final AccountMapping mapping
        ) {
            this.client = client;
            this.mapping = mapping;

            this.journalId = journalId;

            LOG.trace("Created: {}", this);
            LOG.trace("  primaNota client: {}", client);
            LOG.trace("  primaNota id: {}", journalId);
            LOG.trace("  account mapping: {}", mapping);
        }


        @Override
        public Query constructQuery(org.vaadin.addons.lazyquerycontainer.QueryDefinition queryDefinition) {
            PrimaNota primaNota = client.loadJournal(journalId);

            return new PrimanotaQuery(primaNota, mapping);
        }
    }

    public static class QueryDefinition extends LazyQueryDefinition {

        public QueryDefinition() {
            super(false, 20, Column.id);
        }
    }
}