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

package de.kaiserpfalzedv.office.tenant.adapter.data.jpa;

import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.persistence.Version;

import de.kaiserpfalzedv.office.tenant.Tenant;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.LockModeType.NONE;
import static javax.persistence.LockModeType.OPTIMISTIC;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-20
 */
@Entity(name = "Tenant")
@Table(
        name = "TENANTS",
        indexes = {
                @Index(name = "TENANTS_TENANT_IDX", columnList = "TENANT_")
        },
        uniqueConstraints = {
                @UniqueConstraint(name = "TENANTS_DISPLAY_NAME_UK", columnNames = {"TENANT_", "DISPLAY_NAME_"}),
                @UniqueConstraint(name = "TENANTS_FULL_NAME_UK", columnNames = {"TENANT_", "FULL_NAME_"})
        }
)
@Access(FIELD)
@NamedQueries({
        @NamedQuery(name = "find-by-id", query = "SELECT t FROM Tenant t WHERE id=:id", lockMode = OPTIMISTIC),
        @NamedQuery(name = "find-by-tenant", query = "SELECT t from Tenant t WHERE tenant=:id", lockMode = OPTIMISTIC),
        @NamedQuery(name = "fetch-all", query = "SELECT t FROM Tenant t", lockMode = NONE)
})
public class TenantJpaImpl implements Tenant {
    private static final long serialVersionUID = 8837106353519279958L;

    @Id
    @Column(name = "ID_", length = 40, nullable = false, unique = true, insertable = true, updatable = false)
    private String id;

    @Version
    @Column(name = "VERSION_", nullable = false)
    private long version;

    @Column(name = "TENANT_", length = 40, nullable = false)
    private String tenant;

    @Column(name = "DISPLAY_NAME_", length = 200, nullable = false)
    private String displayName;

    @Column(name = "FULL_NAME_", length = 1000, nullable = false)
    private String fullName;


    @SuppressWarnings("unused")
    @Deprecated
    protected TenantJpaImpl() {}


    public TenantJpaImpl(final Tenant orig) {
        this(orig.getTenant(), orig.getId(), orig.getDisplayName(), orig.getFullName());
    }

    public TenantJpaImpl(
            final UUID tenant,
            final UUID id,
            final String displayName,
            final String fullName
    ) {
        setId(id);
        setTenant(tenant);

        this.displayName = displayName;
        this.fullName = fullName;
    }

    void update(final Tenant orig) {
        // id is not updated!
        setTenant(orig.getTenant());

        displayName = orig.getDisplayName();
        fullName = orig.getFullName();
    }

    @Override
    public UUID getTenant() {
        try {
            return UUID.fromString(tenant);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("The tenant database string '" + tenant + "' can not be mapped to an UUID!", e);
        }
    }

    private void setTenant(final UUID id) {
        this.tenant = id.toString();
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
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .toHashCode();
    }

    @Override
    public UUID getId() {
        try {
            return UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("The tenant ID string '" + id + "' can not be mapped to an UUID!", e);
        }
    }

    private void setId(final UUID id) {
        this.id = id.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!Tenant.class.isAssignableFrom(o.getClass())) return false;

        Tenant tenant = (Tenant) o;

        return new EqualsBuilder()
                .append(getId(), tenant.getId())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("tenant", tenant)
                .append("displayName", displayName)
                .toString();
    }
}
