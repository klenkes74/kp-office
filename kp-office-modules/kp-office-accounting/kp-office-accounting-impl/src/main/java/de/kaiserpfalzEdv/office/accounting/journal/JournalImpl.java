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

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 19:36
 */
public class JournalImpl implements Journal {
    private static final long serialVersionUID = -1287571639832420541L;

    private final UUID   id;
    private final String number;
    private final String name;
    private final ArrayList<JournalEntry> entries = new ArrayList<>();
    private int journalEntryCounter;


    JournalImpl(
            @NotNull final UUID id,
            @NotNull final String number,
            @NotNull final String name,
            @NotNull final int journalEntryCounter
    ) {
        this.id = id;
        this.number = number;
        this.name = name;

        this.journalEntryCounter = journalEntryCounter;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getNumber() {
        return number;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public List<? extends JournalEntry> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    @Override
    public synchronized void addEntry(@NotNull JournalEntry entry) {
        try {
            ((JournalEntryImpl) entry).setJournalEntryCounter(journalEntryCounter);
            entries.add(entry);
        } catch (ClassCastException e) {
            throw new IllegalArgumentException("Can not set journal entry counter into entry!", e);
        }

        journalEntryCounter++;
    }

    @Override
    public void replaceEntry(@NotNull int index, @NotNull final JournalEntryImpl entry) {
        int counter = entries.get(index).getJournalEntryCounter();
        entry.setJournalEntryCounter(counter);

        entries.remove(index);
        entries.add(index, entry);
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
        JournalImpl rhs = (JournalImpl) obj;
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
        return new ToStringBuilder(this)
                .append("id", id)
                .append("number", number)
                .append("name", name)
                .toString();
    }
}
