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

import de.kaiserpfalzEdv.office.accounting.postingRecord.impl.PostingRecordImpl;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaEntry;

import javax.persistence.Cacheable;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 13.08.15 19:11
 */
@Entity
@Cacheable
@Table(
        schema = "accounting",
        catalog = "accounting",
        name = "primanotaentries",
        uniqueConstraints = {
                @UniqueConstraint(name = "primnota_name_fk", columnNames = {"tenant_", "display_name_"}),
                @UniqueConstraint(name = "primnota_number_fk", columnNames = {"tenant_", "display_number_"})
        }
)
public class PrimaNotaEntryImpl extends PostingRecordImpl implements PrimaNotaEntry {
    private static final long serialVersionUID = -1186568095868688419L;


    @ManyToOne
    @JoinColumn(name = "primanota_", nullable = false)
    private PrimaNotaImpl primaNota;


    /**
     * @deprecated Only for JPA, JAX-B, Jackson and other brain dead framework definitions.
     */
    @Deprecated
    protected PrimaNotaEntryImpl() {}

    PrimaNotaEntryImpl(PostingRecordImpl base, PrimaNotaImpl primaNota) {
        super(
                base.getId(),
                base.getDisplayName(),
                base.getDisplayNumber(),
                base.getTenantId(),

                base.getEntryDate(),
                base.getAccountingDate(),
                base.getValutaDate(),

                base.getAmount(),

                base.getTaxKey(),
                base.getFunctionKey(),
                base.getAccountDebitted(),
                base.getAccountCreditted(),

                base.getCostCenter1(),
                base.getCostCenter2(),

                base.getDocumentInformation(),
                base.getNotice(),
                base.getNotice2()
        );

        this.primaNota = primaNota;
    }


    @Override
    public PrimaNotaImpl getPrimaNota() {
        return primaNota;
    }

    public void setPrimaNota(PrimaNotaImpl primaNota) {
        this.primaNota = primaNota;
    }
}
