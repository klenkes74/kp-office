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

import de.kaiserpfalzEdv.office.accounting.FiscalPeriodImpl;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNota;
import de.kaiserpfalzEdv.office.commons.server.data.KPOTenantHoldingEntity;

import javax.persistence.Cacheable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 13.08.15 19:16
 */
@Entity
@Cacheable
@Table(
        schema = "accounting",
        catalog = "accounting",
        name = "primanota",
        uniqueConstraints = {
                @UniqueConstraint(name = "primnota_name_fk", columnNames = {"tenant_", "display_name_"}),
                @UniqueConstraint(name = "primnota_number_fk", columnNames = {"tenant_", "display_number_"})
        }
)
public class PrimaNotaImpl extends KPOTenantHoldingEntity implements PrimaNota {
    private static final long serialVersionUID = 1343047899099202363L;


    @Embedded
    private FiscalPeriodImpl period;


    /**
     * @deprecated Only for brain-dead defined frameworks like JPA, JAX-B or Jackson ...
     */
    @Deprecated
    protected PrimaNotaImpl() {}

    public PrimaNotaImpl(@NotNull UUID id, @NotNull String displayName, @NotNull String displayNumber, @NotNull UUID tenantId, FiscalPeriodImpl period) {
        super(id, displayName, displayNumber, tenantId);
        this.period = period;
    }


    @Override
    public FiscalPeriodImpl getPeriod() {
        return period;
    }

    public void setPeriod(final FiscalPeriodImpl period) {
        this.period = period;
    }
}
