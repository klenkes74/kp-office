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

package de.kaiserpfalzEdv.office.tenants.api.commands;

import de.kaiserpfalzEdv.office.commands.OfficeCommandException;
import de.kaiserpfalzEdv.office.commands.OfficeCommandHandler;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class CreateTenantCommand extends TenantStoreCommand {
    private static final long serialVersionUID = 1L;

    private String displayNumber;
    private String displayName;


    public CreateTenantCommand() {}


    /**
     * Creates a tenant creation command.
     * @param displayName The display name for this tenant.
     */
    public CreateTenantCommand(final String displayName) {
        setDisplayName(displayName);
    }


    /**
     * Creates a tenant creation command.
     * @param displayNumber The display number for this tenant.
     * @param displayName The display name for this tenant.
     */
    public CreateTenantCommand(final String displayNumber, final String displayName) {
        checkArgument(isNotBlank(displayNumber), "A valid displayNumber is needed for a tenant!");

        setDisplayNumber(displayNumber);
        setDisplayName(displayName);
    }


    public String getDisplayNumber() {
        return displayNumber;
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
    public void execute(OfficeCommandHandler context) throws OfficeCommandException {
        context.handle(this);
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
        CreateTenantCommand rhs = (CreateTenantCommand) obj;
        return new EqualsBuilder()
                .append(this.displayNumber, rhs.displayNumber)
                .append(this.displayName, rhs.displayName)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(displayNumber)
                .append(displayName)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("displayNumber", displayNumber)
                .append("displayName", displayName)
                .toString();
    }
}
