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
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 06:44
 */
public class JournalInfoBuilder implements Builder<JournalInfo> {
    private static final Logger LOG = LoggerFactory.getLogger(JournalInfoBuilder.class);

    private UUID   id;
    private String number;
    private String name;
    private int    entries;

    @Override
    public JournalInfo build() {
        validate();

        return new JournalInfoImpl(id, number, name, entries);
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (id == null) failures.add("No ID for this journal given!");
        if (isBlank(number)) failures.add("No display number for this journal given!");
        if (isBlank(name)) failures.add("No name for this journal given!");
        if (entries < 0) failures.add("Can't have a negative number of entries in a journal!");

        if (failures.size() > 0) {
            throw new BuilderException(failures);
        }
    }


    public JournalInfoBuilder withJournal(@NotNull final JournalInfo journal) {
        withId(journal.getId());
        withNumber(journal.getNumber());
        withName(journal.getName());
        withEntries(journal.size());

        return this;
    }


    public JournalInfoBuilder withId(@NotNull final UUID id) {
        this.id = id;

        return this;
    }

    public JournalInfoBuilder withNumber(@NotNull final String number) {
        this.number = number;

        return this;
    }

    public JournalInfoBuilder withName(@NotNull final String name) {
        this.name = name;

        return this;
    }

    public JournalInfoBuilder withEntries(final int entries) {
        this.entries = entries;

        return this;
    }
}
