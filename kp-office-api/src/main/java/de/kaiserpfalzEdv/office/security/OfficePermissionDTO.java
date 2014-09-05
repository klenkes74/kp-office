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

import de.kaiserpfalzEdv.office.core.OfficeAction;
import de.kaiserpfalzEdv.office.core.OfficeModule;
import de.kaiserpfalzEdv.office.tenants.Tenant;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author klenkes
 * @since 2014Q
 */
public class OfficePermissionDTO implements OfficePermission {
    private static final long serialVersionUID = 3476472050147347046L;

    private OfficeModule module;
    private OfficeAction action;
    private Tenant tenant;


    @Deprecated // Only for Jackson, JAX-B and JPA!
    public OfficePermissionDTO() {}

    @SuppressWarnings("deprecation")
    public OfficePermissionDTO(final OfficePermission permission) {
        setModule(permission.getModule());
        setAction(permission.getAction());
        setTenant(permission.getTenant());
    }

    @SuppressWarnings("deprecation")
    public OfficePermissionDTO(final OfficeModule module, final OfficeAction action, final Tenant tenant) {
        setModule(module);
        setAction(action);

        setTenant(tenant);
    }

    @Override
    public OfficeModule getModule() {
        return module;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setModule(OfficeModule module) {
        checkArgument(module != null, "No permission without a module!");

        this.module = module;
    }


    @Override
    public OfficeAction getAction() {
        return action;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setAction(OfficeAction action) {
        checkArgument(action != null, "No permission without an action!");
        this.action = action;
    }


    @Override
    public Tenant getTenant() {
        return tenant;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setTenant(Tenant tenant) {
        this.tenant = tenant;
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
        OfficePermissionDTO rhs = (OfficePermissionDTO) obj;
        return new EqualsBuilder()
                .append(this.getModule(), rhs.getModule())
                .append(this.getAction(), rhs.getAction())
                .append(this.getTenant(), rhs.getTenant())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getModule())
                .append(getAction())
                .append(getTenant())
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("module", module)
                .append("action", action)
                .append("tenant", tenant)
                .toString();
    }
}
