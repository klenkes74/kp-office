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

package de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl;

import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.CostCenter;
import de.kaiserpfalzEdv.office.commons.server.data.KPOTenantHoldingEntity;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 16:20
 */
@Entity
@Table(
        schema = "accounting",
        catalog = "accounting",
        name = "costcenters",
        uniqueConstraints = {
                @UniqueConstraint(name = "costcenters_name_fk", columnNames = {"tenant_", "display_name_"}),
                @UniqueConstraint(name = "costcenters_number_fk", columnNames = {"tenant_", "display_number_"})
        }
)
public class CostCenterImpl extends KPOTenantHoldingEntity implements CostCenter, Serializable {
    private static final long serialVersionUID = 7289957250235947972L;


    @Transient
    private String mapping;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for JAX-B, Jackson, JPA, ...
    protected CostCenterImpl() {}

    CostCenterImpl(
            @NotNull final UUID tenantId,
            @NotNull final UUID id,
            @NotNull final String name
    ) {
        super(id, name, name, tenantId);
    }


    @Override
    public String getCurrentMapping() {
        return isNotBlank(mapping) ? mapping : "";
    }

    public void setCurrentMapping(@NotNull final String mapping) {
        this.mapping = mapping;
    }
}
