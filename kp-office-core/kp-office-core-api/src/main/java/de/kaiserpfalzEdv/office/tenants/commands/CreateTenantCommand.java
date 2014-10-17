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
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class CreateTenantCommand extends TenantStoreCommand {
    private static final long serialVersionUID = -8403467871995004894L;

    private String displayNumber;
    private String displayName;


    @Deprecated // Only for JPA!
    protected CreateTenantCommand() {
    }


    /**
     * Creates a tenant creation command.
     *
     * @param displayNumber The display number for this tenant.
     * @param displayName   The display name for this tenant.
     */
    public CreateTenantCommand(final String displayNumber, final String displayName) {
        checkArgument(isNotBlank(displayNumber), "A valid displayNumber is needed for a tenant!");

        setDisplayNumber(displayNumber);
        setDisplayName(displayName);
    }


    public String getDisplayNumber() {
        return isNotBlank(displayNumber) ? displayNumber : getTenantId().toString();
    }

    public void setDisplayNumber(String displayNumber) {
        this.displayNumber = displayNumber;
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        checkArgument(isNotBlank(displayName), "A valid name is needed for a tenant!");
        this.displayName = displayName;
    }


    @Override
    public Tenant updateTenant(Tenant tenant) {
        return new Tenant.TenantDTO(getTenantId(), getDisplayNumber(), getDisplayName());
    }

    @Override
    public boolean validTenant(boolean current) {
        return true;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("displayNumber", displayNumber)
                .append("displayName", displayName)
                .toString();
    }
}
