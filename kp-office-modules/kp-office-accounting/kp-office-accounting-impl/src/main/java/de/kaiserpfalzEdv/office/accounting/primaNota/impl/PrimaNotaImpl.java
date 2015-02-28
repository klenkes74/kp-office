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

import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaEntry;
import de.kaiserpfalzEdv.office.accounting.primaNota.Primanota;
import de.kaiserpfalzEdv.office.commons.impl.KPOTenantHoldingEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
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
@Entity
@Table(schema = "accounting", catalog = "accounting", name = "primanota")
public class PrimaNotaImpl extends KPOTenantHoldingEntity implements Primanota {
    private static final long serialVersionUID = -6634800863453395960L;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "primaNota",
            orphanRemoval = true
    )
    private List<PrimaNotaEntryImpl> entries = new ArrayList<>();


    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B, JPA, ...
    protected PrimaNotaImpl() {}

    PrimaNotaImpl(
            @NotNull final UUID tenantId,
            @NotNull final UUID id,
            @NotNull final String number,
            @NotNull final String name
    ) {
        super(id, name, number, tenantId);
    }


    @Override
    public int size() {
        return entries.size();
    }

    @Override
    public List<? extends PostingRecord> getEntries() {
        return Collections.unmodifiableList(entries);
    }

    public void addEntry(@NotNull PrimaNotaEntry entry) {
        entries.add((PrimaNotaEntryImpl) entry);
    }
}
