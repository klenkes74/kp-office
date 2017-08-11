/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import de.kaiserpfalzedv.office.commons.jpa.impl.JPAAbstractIdentifiable;
import de.kaiserpfalzedv.office.commons.jpa.impl.JPANameable;
import de.kaiserpfalzedv.office.tenant.api.Tenant;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.LockModeType.NONE;
import static javax.persistence.LockModeType.OPTIMISTIC;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-20
 */
@Entity(name = "Tenant")
@Table(
        name = "TENANTS"
)
@Access(FIELD)
@NamedQueries({
        @NamedQuery(name = "find-by-key", query = "SELECT t from Tenant t WHERE key=:key", lockMode = OPTIMISTIC),
        @NamedQuery(name = "find-by-tenant", query = "SELECT t from Tenant t WHERE tenant=:id", lockMode = OPTIMISTIC),
        @NamedQuery(name = "fetch-all", query = "SELECT t FROM Tenant t", lockMode = NONE)
})
public class TenantJpaImpl extends JPAAbstractIdentifiable implements Tenant {
    private static final long serialVersionUID = -7636795197921084792L;

    @Embedded
    private JPANameable name;

    @Column(name = "KEY_", length = 50, nullable = false)
    private String key;


    @SuppressWarnings({"unused", "deprecation"})
    @Deprecated // Only for JPA
    protected TenantJpaImpl() {}


    TenantJpaImpl(final Tenant orig) {
        this(orig.getTenant(), orig.getId(), orig.getKey(), orig.getDisplayName(), orig.getFullName());
    }

    TenantJpaImpl(
            final UUID tenant,
            final UUID id,
            final String key,
            final String displayName,
            final String fullName
    ) {
        super(id, tenant);

        name = new JPANameable(displayName, fullName);

        this.key = key;
    }

    void update(final Tenant orig) {
        // id is not updated!
        setTenant(orig.getTenant());
        setKey(orig.getKey());

        name.setDisplayName(orig.getDisplayName());
        name.setFullName(orig.getFullName());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("key", getKey())
                .append("displayName", getDisplayName())
                .toString();
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getDisplayName() {
        return name.getDisplayName();
    }

    @Override
    public String getFullName() {
        return name.getFullName();
    }

    private void setKey(final String key) {
        this.key = key;
    }
}
