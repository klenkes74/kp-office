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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

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
public class JournalEntryImpl implements JournalEntry {
    private final UUID id;

    private final LocalDate documentDate;
    private final LocalDate entryDate;
    private final LocalDate valutaDate;
    private final String    documentNumber;
    private final ArrayList<AccountChange> debits  = new ArrayList<>(1);
    private final ArrayList<AccountChange> credits = new ArrayList<>(1);
    private int            journalEntryCounter;
    private String         notice;
    private MonetaryAmount debit;
    private MonetaryAmount credit;


    JournalEntryImpl(
            @NotNull final UUID id,
            @NotNull final String documentNumber,
            @NotNull final LocalDate documentDate,
            @NotNull final LocalDate entryDate,
            @NotNull final LocalDate valutaDate,
            @NotNull final MonetaryAmount debit,
            @NotNull final MonetaryAmount credit,
            @NotNull final List<AccountChange> debits,
            @NotNull final List<AccountChange> credits
    ) {
        this.id = id;
        this.documentNumber = documentNumber;
        this.documentDate = documentDate;
        this.entryDate = entryDate;
        this.valutaDate = valutaDate;

        this.debit = debit;
        this.credit = credit;

        setDebits(debits);
        setCredits(credits);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public int getJournalEntryCounter() {
        return journalEntryCounter;
    }

    void setJournalEntryCounter(int journalEntryCounter) {
        this.journalEntryCounter = journalEntryCounter;
    }

    @Override
    public LocalDate getDocumentDate() {
        return documentDate;
    }

    @Override
    public LocalDate getEntryDate() {
        return entryDate;
    }

    @Override
    public LocalDate getValutaDate() {
        return valutaDate;
    }

    @Override
    public String getDocumentNumber() {
        return documentNumber;
    }

    @Override
    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    @Override
    public ArrayList<AccountChange> getDebits() {
        return debits;
    }

    private void setDebits(List<AccountChange> debits) {
        this.debits.clear();
        this.debits.addAll(debits);
    }

    @Override
    public MonetaryAmount getDebit() {
        return debit;
    }

    @Override
    public ArrayList<AccountChange> getCredits() {
        return credits;
    }

    private void setCredits(List<AccountChange> credits) {
        this.credits.clear();
        this.credits.addAll(credits);
    }

    @Override
    public MonetaryAmount getCredit() {
        return credit;
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
        JournalEntryImpl rhs = (JournalEntryImpl) obj;
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("documentNumber", documentNumber)
                .append("amount", credit)
                .toString();
    }
}


