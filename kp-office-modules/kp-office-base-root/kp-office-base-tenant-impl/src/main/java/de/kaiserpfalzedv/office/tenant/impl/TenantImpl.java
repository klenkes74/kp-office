/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.tenant.impl;

import java.util.UUID;

import de.kaiserpfalzedv.office.tenant.Tenant;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-05
 */
public class TenantImpl implements Tenant {
    private static final long serialVersionUID = 2545215242144328003L;


    private UUID id;
    private UUID tenantId;

    private String displayName;
    private String fullName;


    @Deprecated // Only for JPA, JAX-B, Jackson, ...
    protected TenantImpl() {}

    TenantImpl(
            final UUID tenantId,
            final UUID id,
            final String displayName,
            final String fullName
    ) {
        this.tenantId = tenantId;
        this.id = id;
        this.displayName = displayName;
        this.fullName = fullName;
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
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        Tenant rhs;
        try {
            rhs = (Tenant) obj;
        } catch (ClassCastException e) {
            return false;
        }

        return new EqualsBuilder()
                .append(this.id, rhs.getId())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("tenantId", tenantId)
                .append("displayName", displayName)
                .toString();
    }
}
