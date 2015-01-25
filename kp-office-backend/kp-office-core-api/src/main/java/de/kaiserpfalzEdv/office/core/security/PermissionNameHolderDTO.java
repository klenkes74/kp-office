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

package de.kaiserpfalzEdv.office.core.security;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class PermissionNameHolderDTO implements PermissionNameHolder, Serializable {
    private static final long serialVersionUID = 1L;


    private String permissionName;


    @Deprecated // Only for Jackson, JAX-B and JPA!
    public PermissionNameHolderDTO() {
    }

    @SuppressWarnings("deprecation")
    public PermissionNameHolderDTO(final String permissionName) {
        setPermissionName(permissionName);
    }


    public String getPermissionName() {
        return permissionName;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setPermissionName(final String permissionName) {
        this.permissionName = permissionName;
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
        PermissionNameHolderDTO rhs = (PermissionNameHolderDTO) obj;
        return new EqualsBuilder()
                .append(this.getPermissionName(), rhs.getPermissionName())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getPermissionName())
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("permissionName", getPermissionName())
                .toString();
    }
}
