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

import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.AccountBuilder;
import de.kaiserpfalzEdv.office.accounting.journal.Journal;
import de.kaiserpfalzEdv.office.accounting.journal.JournalEntry;
import de.kaiserpfalzEdv.office.accounting.journal.JournalEntryBuilder;
import de.kaiserpfalzEdv.office.accounting.journal.JournalImpl;
import de.kaiserpfalzEdv.office.accounting.journal.JournalInfo;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.money.MonetaryAmount;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 06:32
 */
@Named
public class JournalDataLoader {
    private static final Logger LOG = LoggerFactory.getLogger(JournalDataLoader.class);

    private final HashMap<UUID, Journal> journals = new HashMap<>(5);


    public JournalDataLoader() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        generateFakeJournal();
        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    public Journal loadJournal(@NotNull final UUID journalId) {
        checkExisting(journalId);

        return journals.get(journalId);
    }

    private void checkExisting(@NotNull final UUID journalId) {
        if (!journals.containsKey(journalId)) {
            throw new IllegalStateException("No such journal!");
        }
    }


    public int countEntries(@NotNull final UUID journalId) {
        checkExisting(journalId);

        return journals.get(journalId).size();
    }


    public Set<JournalInfo> tableOfContents() {
        return Collections.unmodifiableSet(new HashSet<>(journals.values()));
    }


    private void generateFakeJournal() {
        Journal fake = new JournalImpl(UUID.fromString("400b4f5d-216e-4457-9dce-79859d8396af"), "1", "Journal 1", 0);

        Account creditAccount = new AccountBuilder()
                .withId(UUID.fromString("ddb684b8-501c-47fc-a46c-7990110bf092"))
                .withName("Bank")
                .build();
        Account debitAccount = new AccountBuilder()
                .withId(UUID.fromString("cb488173-b9b0-4f37-adb1-385d2051363e"))
                .withName("Privat R. Lichti")
                .withDescription("Privatkonto Roland Lichti")
                .build();

        for (int i = 0; i < 10; i++) {
            MonetaryAmount amount = Money.of(new Random().nextDouble(), "EUR");
            fake.addEntry(generateFakeEntry("Entry " + i, debitAccount, creditAccount, amount));
        }

        journals.put(fake.getId(), fake);
    }

    private JournalEntry generateFakeEntry(
            @NotNull final String documentNumber,
            @NotNull final Account debitAccount,
            @NotNull final Account creditAccount,
            @NotNull final MonetaryAmount amount
    ) {
        return new JournalEntryBuilder()
                .generateId()
                .withDocumentNumber(documentNumber)
                .simpleBooking(debitAccount, creditAccount, amount)
                .build();
    }
}
