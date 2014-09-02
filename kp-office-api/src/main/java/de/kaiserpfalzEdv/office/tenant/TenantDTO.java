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

package de.kaiserpfalzEdv.office.tenant;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class TenantDTO implements Tenant {
    private UUID id;

    private String displayNumber;
    private String displayName;


    /**
     * @deprecated Only for JPA!
     */
    @Deprecated
    protected TenantDTO() {}


    /**
     * A copy-constructor.
     * @param orig The original tenant to be copied.
     */
    public TenantDTO(final Tenant orig) {
        checkArgument(orig != null, "An original tenant to copy is needed!");

        //noinspection ConstantConditions
        setId(orig.getId());
        setDisplayNumber(orig.getDisplayNumber());
        setDisplayName(orig.getDisplayName());
    }


    public TenantDTO(final String number, final String name) {
        setId(UUID.randomUUID());
        setDisplayNumber(number);
        setDisplayName(name);
    }

    public TenantDTO(final UUID id, final String number, final String name) {
        setId(id);
        setDisplayNumber(number);
        setDisplayName(name);
    }


    @Override
    public UUID getId() {
        return id;
    }

    private void setId(final UUID id) {
        checkArgument(id != null, "Can't unset the id!");

        this.id = id;
    }

    @Override
    public String getDisplayNumber() {
        return displayNumber;
    }

    public void setDisplayNumber(final String displayNumber) {
        checkArgument(isNotBlank(displayNumber), "A tenant needs a unique display number!");

        this.displayNumber = displayNumber;
    }


    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        checkArgument(isNotBlank(displayNumber), "A tenant needs a unique display name!");

        this.displayName = displayName;
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
        TenantDTO rhs = (TenantDTO) obj;
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
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("displayNumber", displayNumber)
                .append("displayName", displayName)
                .toString();
    }
}
