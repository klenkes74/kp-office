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

import de.kaiserpfalzEdv.commons.jee.paging.Page;
import de.kaiserpfalzEdv.commons.jee.paging.Pageable;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNota;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaEntry;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaPage;
import de.kaiserpfalzEdv.office.accounting.tax.FiscalPeriod;
import de.kaiserpfalzEdv.office.commons.paging.PageImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 16.08.15 09:37
 */
public class PrimaNotaPageImpl extends PageImpl<PrimaNotaEntry> implements PrimaNotaPage {
    private PrimaNota primaNota;


    public PrimaNotaPageImpl(
            final PrimaNota primaNota,
            final Pageable pageable,
            final long totalEntries,
            final List<PrimaNotaEntry> data
    ) {
        super(pageable, totalEntries, data);
        this.primaNota = primaNota;
    }

    public PrimaNotaPageImpl(
            final PrimaNota primaNota,
            final Page<PrimaNotaEntry> entries
    ) {
        this(primaNota, entries.nextPageable().previousOrFirst(), entries.getTotalElements(), entries.getContent());
    }


    public static List<PrimaNotaEntry> convertData(final List<PrimaNotaEntryImpl> data) {
        ArrayList<PrimaNotaEntry> result = new ArrayList<>(data.size());

        data.forEach(d -> result.add((PrimaNotaEntry) d));

        return result;
    }


    @Override
    public String getDisplayName() {
        return primaNota.getDisplayName();
    }

    @Override
    public String getDisplayNumber() {
        return primaNota.getDisplayNumber();
    }

    @Override
    public boolean isHidden() {
        return primaNota.isHidden();
    }

    @Override
    public UUID getId() {
        return primaNota.getId();
    }

    @Override
    public FiscalPeriod getPeriod() {
        return primaNota.getPeriod();
    }
}
