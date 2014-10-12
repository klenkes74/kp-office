/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.security;

import de.kaiserpfalzEdv.commons.security.ActingSystem;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public final class OfficeSubjectDTO implements OfficeSubject, ActingSystem, Serializable {
    private static final long serialVersionUID = -5944250353391648123L;


    private Set<OfficePrincipal> principals = new HashSet<>();
    private Set<String> permissions = new HashSet<>();
    private Set<String> roles = new HashSet<>();


    @Deprecated // Only for Jackson, JAX-P and JPA!
    public OfficeSubjectDTO() {
    }

    @SuppressWarnings("deprecation")
    public OfficeSubjectDTO(
            final Collection<OfficePrincipal> principals,
            final Collection<String> permissions,
            final Collection<String> roles
    ) {
        setPrincipals(principals);
        setPermissions(permissions);
        setRoles(roles);
    }


    public Set<OfficePrincipal> getAllPrincipal() {
        return principals;
    }


    @Deprecated // Only for Jackson, JAX-P and JPA!
    public void setPrincipals(final Collection<OfficePrincipal> principals) {
        this.principals.clear();

        if (principals != null) {
            this.principals.addAll(principals);
        }
    }


    @Override
    public Set<String> getPermissions() {
        return permissions;
    }

    @Deprecated // Only for Jackson, JAX-P and JPA!
    public void setPermissions(final Collection<String> permissions) {
        this.permissions.clear();

        if (permissions != null) {
            this.permissions.addAll(permissions);
        }
    }


    @Override
    public Set<String> getRoles() {
        return roles;
    }

    @Deprecated // Only for Jackson, JAX-P and JPA!
    public void setRoles(final Collection<String> roles) {
        this.roles.clear();

        if (roles != null) {
            this.roles.addAll(roles);
        }
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
        OfficeSubjectDTO rhs = (OfficeSubjectDTO) obj;
        return new EqualsBuilder()
                .append(this.getAllPrincipal(), rhs.getAllPrincipal())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getAllPrincipal())
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("principals", getAllPrincipal())
                .toString();
    }
}
