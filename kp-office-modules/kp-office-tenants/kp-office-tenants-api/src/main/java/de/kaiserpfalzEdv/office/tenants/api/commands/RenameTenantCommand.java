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

import de.kaiserpfalzEdv.office.commands.OfficeCommandHandler;
import de.kaiserpfalzEdv.office.commands.OfficeCommandException;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
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

    private UUID id;
    private String displayName;


    /**
     * Changes the name of the tenant defined by the displayNumber.
     *
     * @param id The tenant id of this tenant.
     * @param displayName The display name for this tenant.
     */
    public RenameTenantCommand(final UUID id, final String displayName) {
        setId(id);
        setDisplayName(displayName);
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        checkArgument(id != null, "A valid id is needed!");

        this.id = id;
    }


    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        checkArgument(isNotBlank(displayName), "A display name is needed!");

        this.displayName = displayName;
    }


    @Override
    public void execute(final OfficeCommandHandler context) throws OfficeCommandException {
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
        RenameTenantCommand rhs = (RenameTenantCommand) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("displayName", displayName)
                .toString();
    }
}
