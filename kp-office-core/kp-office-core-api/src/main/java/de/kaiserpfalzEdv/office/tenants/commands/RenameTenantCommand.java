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

package de.kaiserpfalzEdv.office.tenants.commands;

import de.kaiserpfalzEdv.office.tenants.Tenant;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class RenameTenantCommand extends TenantStoreCommand {
    private static final long serialVersionUID = 1L;

    private String displayName;


    @Deprecated // Only for JPA
    protected RenameTenantCommand() {
    }


    /**
     * Changes the name of the tenant defined by the displayNumber.
     *
     * @param id          The tenant id of this tenant.
     * @param displayName The display name for this tenant.
     */
    public RenameTenantCommand(final UUID id, final String displayName) {
        setTenantId(id);
        setDisplayName(displayName);
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        checkArgument(isNotBlank(displayName), "A display name is needed!");

        this.displayName = displayName;
    }


    @Override
    public Tenant updateTenant(Tenant tenant) {
        return new Tenant.TenantDTO(tenant.getId(), tenant.getDisplayNumber(), displayName);
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("displayName", displayName)
                .toString();
    }
}
