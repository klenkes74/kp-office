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

package de.kaiserpfalzedv.iam.access.jpa.roles;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Access;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.jpa.JPAAbstractTenantIdentifiable;
import de.kaiserpfalzedv.commons.jpa.JPANameable;
import de.kaiserpfalzedv.iam.access.api.roles.Entitlement;
import de.kaiserpfalzedv.iam.access.api.roles.Role;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import static de.kaiserpfalzedv.iam.access.jpa.roles.JPARole.ENTITY_NAME;
import static javax.persistence.AccessType.FIELD;
import static javax.persistence.LockModeType.NONE;
import static javax.persistence.LockModeType.OPTIMISTIC;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2017-11-04
 */
@Entity(name = "Role")
@Table(
        name = "ROLES",
        indexes = {
                @Index(name = "ROLES_TENANT_UK", columnList = "TENANT_", unique = true)
        }
)
@Access(FIELD)
@NamedQueries({
        @NamedQuery(name = ENTITY_NAME + ".find-by-tenant", query = "SELECT t from Role t WHERE tenant=:id", lockMode = OPTIMISTIC),
        @NamedQuery(name = ENTITY_NAME + ".fetch-all", query = "SELECT t FROM Role t", lockMode = NONE)
})
public class JPARole extends JPAAbstractTenantIdentifiable implements Role {
    public static final String ENTITY_NAME = "Role";
    private static final long serialVersionUID = -740317110390170918L;

    @Embedded
    private JPANameable name;

    @ManyToMany
    @JoinTable(
            name = "ROLES_ROLES",
            joinColumns = @JoinColumn(name = "MASTER_", referencedColumnName = "ID_"),
            inverseJoinColumns = @JoinColumn(name = "SLAVE_", referencedColumnName = "ID_")
    )
    private Set<JPARole> roles;

    @ManyToMany
    @JoinTable(
            name = "ROLES_ENTITLEMENTS",
            joinColumns = @JoinColumn(name = "ROLE_", referencedColumnName = "ID_"),
            inverseJoinColumns = @JoinColumn(name = "ENTITLEMENT_", referencedColumnName = "ID_")
    )
    private Set<JPAEntitlement> entitlements;


    @SuppressWarnings({"unused", "deprecation"})
    @Deprecated // Only for JPA
    protected JPARole() {
    }

    JPARole(
            @NotNull final UUID tenantId,
            @NotNull final UUID id,
            @NotNull final String displayName,
            @NotNull final String fullName,
            @NotNull final HashSet<JPARole> roles,
            @NotNull final HashSet<JPAEntitlement> entitlements
    ) {
        super(id, tenantId);

        name = new JPANameable(displayName, fullName);
        this.roles = roles;
        this.entitlements = entitlements;
    }

    void update(final JPARole orig) {
        // id is not updated!

        name.setDisplayName(orig.getDisplayName());
        name.setFullName(orig.getFullName());
    }

    @Override
    public String getDisplayName() {
        return name.getDisplayName();
    }

    public void setDisplayName(@NotNull final String displayName) {
        name.setDisplayName(displayName);
    }

    @Override
    public String getFullName() {
        return name.getFullName();
    }

    public void setFullName(@NotNull final String fullName) {
        name.setFullName(fullName);
    }

    @Override
    public String getName() {
        return getDisplayName();
    }

    public void setName(@NotNull final String name) {
        setDisplayName(name);
    }


    public Set<? extends Role> getDirectRoles() {
        return Collections.unmodifiableSet(roles);
    }

    @Override
    public Set<? extends Entitlement> getEntitlements() {
        return Collections.unmodifiableSet(entitlements);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Role)) return false;

        Role role = (Role) o;

        return new EqualsBuilder()
                .appendSuper(super.equals(o))
                .append(getDisplayName(), role.getDisplayName())
                .append(getTenant(), role.getTenant())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .appendSuper(super.hashCode())
                .append(getDisplayName())
                .append(getTenant())
                .toHashCode();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder()
                .append(getClass().getSimpleName()).append('@').append(System.identityHashCode(this)).append('{')
                .append(getId()).append(", ").append(getDisplayName());

        if (roles.size() >= 1) {
            result.append(", roles=").append(roles.size());
        }

        return result.append('}').toString();
    }
}
