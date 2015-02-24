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

import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaInfo;
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
public class PrimanotaInfoBuilder implements Builder<PrimaNotaInfo> {
    private static final Logger LOG = LoggerFactory.getLogger(PrimanotaInfoBuilder.class);

    private UUID tenantId;
    private UUID   id;
    private String number;
    private String name;
    private int    entries;

    @Override
    public PrimaNotaInfo build() {
        validate();

        return new PrimanotaInfoImpl(tenantId, id, number, name, entries);
    }

    public void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (tenantId == null) failures.add("No tenant ID for this primaNota given!");
        if (id == null) failures.add("No ID for this primaNota given!");
        if (isBlank(number)) failures.add("No display number for this primaNota given!");
        if (isBlank(name)) failures.add("No name for this primaNota given!");
        if (entries < 0) failures.add("Can't have a negative number of entries in a primaNota!");

        if (failures.size() > 0) {
            throw new BuilderException(failures);
        }
    }


    public PrimanotaInfoBuilder withJournal(@NotNull final PrimaNotaInfo journal) {
        withTenantId(journal.getTenantId());
        withId(journal.getId());
        withNumber(journal.getDisplayNumber());
        withName(journal.getDisplayName());
        withEntries(journal.size());

        return this;
    }


    public PrimanotaInfoBuilder withTenantId(final UUID tenantId) {
        this.tenantId = tenantId;

        return this;
    }

    public PrimanotaInfoBuilder withId(final UUID id) {
        this.id = id;

        return this;
    }

    public PrimanotaInfoBuilder withNumber(final String number) {
        this.number = number;

        return this;
    }

    public PrimanotaInfoBuilder withName(final String name) {
        this.name = name;

        return this;
    }

    public PrimanotaInfoBuilder withEntries(final int entries) {
        this.entries = entries;

        return this;
    }
}
