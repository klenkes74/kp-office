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

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 20:02
 */
public interface Journal extends JournalInfo, Serializable {
    @Override
    UUID getId();

    @Override
    String getNumber();

    @Override
    String getName();

    @Override
    int size();

    /**
     * @return All entries of this journal.
     */
    List<? extends JournalEntry> getEntries();

    /**
     * @param entry The journal entry to add to this journal.
     */
    void addEntry(@NotNull JournalEntry entry);

    /**
     * Replaces a journal entry by a new one.
     *
     * @param index The index of the entry to be replaced.
     * @param entry The new entry.
     */
    void replaceEntry(@NotNull int index, @NotNull JournalEntryImpl entry);
}
