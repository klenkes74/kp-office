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

import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.AccountMapping;
import de.kaiserpfalzEdv.office.accounting.primaNota.impl.PrimaNotaDataLoader;
import org.apache.commons.lang3.builder.Builder;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;
import org.vaadin.addons.lazyquerycontainer.LazyQueryDefinition;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 11:10
 */
public class PrimanotaContainerBuilder implements Builder<LazyQueryContainer> {
    private UUID                primanotaId;
    private PrimaNotaDataLoader client;
    private AccountMapping      mapping;

    @Override
    public LazyQueryContainer build() {
        validate();

        PrimanotaQuery.Factory factory = new PrimanotaQuery.Factory(primanotaId, client, mapping);
        LazyQueryDefinition query = new PrimanotaQuery.QueryDefinition();

        LazyQueryContainer result = new LazyQueryContainer(query, factory);

        result.addContainerProperty(PrimanotaQuery.Column.tenantId, UUID.class, UUID.randomUUID(), true, false);
        result.addContainerProperty(PrimanotaQuery.Column.id, UUID.class, UUID.randomUUID(), true, false);
        result.addContainerProperty(PrimanotaQuery.Column.entryId, Integer.class, 0, true, true);
        result.addContainerProperty(PrimanotaQuery.Column.entryDate, LocalDate.class, LocalDate.MIN, true, true);
        result.addContainerProperty(PrimanotaQuery.Column.accountingDate, LocalDate.class, LocalDate.MIN, true, true);
        result.addContainerProperty(PrimanotaQuery.Column.valutaDate, LocalDate.class, LocalDate.MIN, true, true);
        result.addContainerProperty(PrimanotaQuery.Column.documentNumber, String.class, "", true, false);
        result.addContainerProperty(PrimanotaQuery.Column.documentDate, LocalDate.class, LocalDate.MIN, true, true);
        result.addContainerProperty(PrimanotaQuery.Column.documentAmount, MonetaryAmount.class, null, true, false);
        result.addContainerProperty(PrimanotaQuery.Column.notice, String.class, null, true, false);
        result.addContainerProperty(PrimanotaQuery.Column.amount, MonetaryAmount.class, null, true, false);
        result.addContainerProperty(PrimanotaQuery.Column.accountDebitted, Account.class, null, true, true);
        result.addContainerProperty(PrimanotaQuery.Column.accountCreditted, Account.class, null, true, true);


        return result;
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (primanotaId == null) failures.add("No primaNota selected!");
        if (client == null) failures.add("No data source defined!");
        if (mapping == null) failures.add("No account mapping defined!");

        if (failures.size() > 0) {
            throw new BuilderException(failures);
        }
    }

    public PrimanotaContainerBuilder withJournalId(@NotNull final UUID journalId) {
        this.primanotaId = journalId;

        return this;
    }

    public PrimanotaContainerBuilder withClient(@NotNull final PrimaNotaDataLoader client) {
        this.client = client;

        return this;
    }

    public PrimanotaContainerBuilder withMapping(@NotNull final AccountMapping mapping) {
        this.mapping = mapping;

        return this;
    }
}
