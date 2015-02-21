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

package de.kaiserpfalzEdv.office.accounting.journal;

import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import org.apache.commons.lang3.builder.Builder;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 21:05
 */
public class JournalEntryBuilder implements Builder<JournalEntry> {
    private final ArrayList<AccountChange> debits  = new ArrayList<>(5);
    private final ArrayList<AccountChange> credits = new ArrayList<>(5);
    private UUID      id;
    private LocalDate documentDate;
    private LocalDate entryDate;
    private LocalDate valutaDate;
    private int       journalEntryCounter;
    private String    documentNumber;
    private String    notice;

    @Override
    public JournalEntry build() {
        validate();

        JournalEntryImpl result = new JournalEntryImpl(
                id,
                documentNumber,
                documentDate,
                entryDate,
                valutaDate,
                calculateSum(debits),
                calculateSum(credits),
                debits,
                credits
        );

        result.setJournalEntryCounter(journalEntryCounter);

        if (isNotBlank(notice)) result.setNotice(notice);

        return result;
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();


        if (failures.size() > 0) {
            throw new BuilderException(failures);
        }
    }

    private MonetaryAmount calculateSum(Collection<AccountChange> changes) {
        final MonetaryAmount result = Money.of(0, "EUR");
        changes.forEach(
                c -> {
                    result.add(c.getAmount());
                }
        );
        return result;
    }


    public JournalEntryBuilder withEntry(@NotNull final JournalEntry entry) {
        withId(entry.getId());
        withDocumentNumber(entry.getDocumentNumber());
        withJournalEntryCounter(entry.getJournalEntryCounter());

        withDocumentDate(entry.getDocumentDate());
        withEntryDate(entry.getEntryDate());
        withValutaDate(entry.getValutaDate());

        if (isNotBlank(entry.getNotice())) withNotice(entry.getNotice());

        withDebits(entry.getDebits());
        withCredits(entry.getCredits());

        return this;
    }


    public JournalEntryBuilder withId(@NotNull final UUID id) {
        this.id = id;

        return this;
    }

    public JournalEntryBuilder generateId() {
        this.id = UUID.randomUUID();

        return this;
    }

    public JournalEntryBuilder withDocumentDate(@NotNull final LocalDate documentDate) {
        this.documentDate = documentDate;

        return this;
    }

    public JournalEntryBuilder withEntryDate(@NotNull final LocalDate entryDate) {
        this.entryDate = entryDate;

        return this;
    }

    public JournalEntryBuilder withValutaDate(@NotNull final LocalDate valutaDate) {
        this.valutaDate = valutaDate;

        return this;
    }

    public JournalEntryBuilder withJournalEntryCounter(final int journalEntryCounter) {
        this.journalEntryCounter = journalEntryCounter;

        return this;
    }

    public JournalEntryBuilder withDocumentNumber(@NotNull final String documentNumber) {
        this.documentNumber = documentNumber;

        return this;
    }

    public JournalEntryBuilder withNotice(@NotNull final String notice) {
        this.notice = notice;

        return this;
    }


    public JournalEntryBuilder simpleBooking(
            @NotNull final Account debitAccount,
            @NotNull final Account creditAccount,
            @NotNull final MonetaryAmount amount
    ) {
        this.debits.add(new AccountChange(debitAccount, amount));
        this.credits.add(new AccountChange(creditAccount, amount));

        return this;
    }


    public JournalEntryBuilder clearAmounts() {
        debits.clear();
        credits.clear();

        return this;
    }


    public JournalEntryBuilder withDebits(@NotNull final List<AccountChange> changes) {
        this.debits.clear();
        return addDebits(changes);
    }

    public JournalEntryBuilder addDebits(@NotNull final List<AccountChange> changes) {
        this.debits.addAll(changes);

        return this;
    }

    public JournalEntryBuilder addDebit(@NotNull final AccountChange change) {
        this.debits.add(change);

        return this;
    }

    public JournalEntryBuilder addDebit(@NotNull final Account account, @NotNull final MonetaryAmount amount) {
        this.debits.add(new AccountChange(account, amount));

        return this;
    }


    public JournalEntryBuilder withCredits(@NotNull final List<AccountChange> changes) {
        this.credits.clear();
        return addCredits(changes);
    }

    public JournalEntryBuilder addCredits(@NotNull final List<AccountChange> changes) {
        this.credits.addAll(changes);

        return this;
    }

    public JournalEntryBuilder addCredit(@NotNull final AccountChange change) {
        this.credits.add(change);

        return this;
    }

    public JournalEntryBuilder addCredit(@NotNull final Account account, @NotNull final MonetaryAmount amount) {
        this.credits.add(new AccountChange(account, amount));

        return this;
    }
}
