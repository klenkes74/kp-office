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

import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * A short information about a {@link de.kaiserpfalzEdv.office.accounting.primaNota.Primanota}.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 06:39
 */
class PrimaNotaInfoImpl implements PrimaNotaInfo {
    private static final long serialVersionUID = -7733476807351203505L;

    private final UUID tenantId;
    private final UUID   id;
    private final String number;
    private final String name;
    private final int    entries;


    public PrimaNotaInfoImpl(
            @NotNull final UUID tenantId,
            @NotNull final UUID id,
            @NotNull final String number,
            @NotNull final String name,
            final int entries
    ) {
        this.tenantId = tenantId;
        this.id = id;
        this.number = number;
        this.name = name;
        this.entries = entries;
    }


    @Override
    public UUID getTenantId() {
        return tenantId;
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getDisplayNumber() {
        return number;
    }

    @Override
    public String getDisplayName() {
        return name;
    }

    @Override
    public int size() {
        return entries;
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
        PrimaNotaInfoImpl rhs = (PrimaNotaInfoImpl) obj;
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
                .append("number", number)
                .append("name", name)
                .append("entries", entries)
                .toString();
    }
}
