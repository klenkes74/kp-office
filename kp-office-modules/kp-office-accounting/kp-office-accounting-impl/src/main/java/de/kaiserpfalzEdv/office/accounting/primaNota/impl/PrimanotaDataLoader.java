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

package de.kaiserpfalzEdv.office.accounting.primaNota.impl;

import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.AccountBuilder;
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import de.kaiserpfalzEdv.office.accounting.postingRecord.impl.PostingRecordBuilder;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaInfo;
import de.kaiserpfalzEdv.office.accounting.primaNota.Primanota;
import de.kaiserpfalzEdv.office.commons.TenantIdHolder;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.money.MonetaryAmount;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDate;
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
public class PrimaNotaDataLoader {
    private static final Logger LOG = LoggerFactory.getLogger(PrimaNotaDataLoader.class);

    private final HashMap<UUID, Primanota> journals = new HashMap<>(5);


    public PrimaNotaDataLoader() {
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


    public Primanota loadJournal(@NotNull final UUID journalId) {
        checkExisting(journalId);

        return journals.get(journalId);
    }

    private void checkExisting(@NotNull final UUID journalId) {
        if (!journals.containsKey(journalId)) {
            throw new IllegalStateException("No such primaNota!");
        }
    }


    public int countEntries(@NotNull final UUID journalId) {
        checkExisting(journalId);

        return journals.get(journalId).size();
    }


    public Set<PrimaNotaInfo> tableOfContents() {
        return Collections.unmodifiableSet(new HashSet<>(journals.values()));
    }


    private void generateFakeJournal() {
        Primanota fake = new PrimaNotaImpl(TenantIdHolder.DEFAULT_TENANT_ID, UUID.fromString("400b4f5d-216e-4457-9dce-79859d8396af"), "1", "Journal 1");

        Account debitAccount = new AccountBuilder()
                .withTenantId(TenantIdHolder.DEFAULT_TENANT_ID)
                .withId(UUID.fromString("cb488173-b9b0-4f37-adb1-385d2051363e"))
                .withName("Privat R. Lichti")
                .build();
        Account creditAccount = new AccountBuilder()
                .withTenantId(TenantIdHolder.DEFAULT_TENANT_ID)
                .withId(UUID.fromString("ddb684b8-501c-47fc-a46c-7990110bf092"))
                .withName("Bank")
                .build();
        Account otherCreditAccount = new AccountBuilder()
                .withTenantId(TenantIdHolder.DEFAULT_TENANT_ID)
                .withId(UUID.fromString("804327a3-50f7-4f2c-8ca0-1c3cd502b1ab"))
                .withName("Privat K. Lichti")
                .build();

        for (int i = 0; i < 10; i++) {
            if (i % 2 == 0) {
                MonetaryAmount amount = Money.of(new BigDecimal(Math.round(new Random().nextDouble() * 100000) / 100), "EUR");
                fake.addEntry(generateFakeEntry("" + (i + 1), LocalDate.now(), "Entry " + i, debitAccount, creditAccount, amount));
            } else {
                MonetaryAmount amount = Money.of(new BigDecimal(Math.round(new Random().nextDouble() * 100000) / 100), "USD");
                fake.addEntry(generateFakeEntry("" + (i + 1), LocalDate.now(), "Entry " + i, debitAccount, otherCreditAccount, amount));
            }
        }

        journals.put(fake.getId(), fake);
    }

    private PostingRecord generateFakeEntry(
            @NotNull final String entryId,
            @NotNull final LocalDate date,
            @NotNull final String documentNumber,
            @NotNull final Account debitAccount,
            @NotNull final Account creditAccount,
            @NotNull final MonetaryAmount amount
    ) {
        return new PostingRecordBuilder()
                .generateId()
                .withEntryId(entryId)
                .withEntryDate(date)
                .withDocumentDate(date)
                .withDocumentNumber(documentNumber)
                .createBooking(debitAccount, creditAccount, amount)
                .build();
    }
}
