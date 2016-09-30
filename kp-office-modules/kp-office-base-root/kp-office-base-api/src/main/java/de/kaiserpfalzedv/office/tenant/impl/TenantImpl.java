/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.tenant.impl;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.kaiserpfalzedv.office.tenant.Tenant;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-20
 */
public class TenantImpl implements Tenant {
    private static final long serialVersionUID = 1065912149569391908L;

    private UUID id;
    private UUID tenant;
    private String key;

    private String displayName;
    private String fullName;

    @SuppressWarnings("unused")
    @Deprecated
    protected TenantImpl() {}


    @JsonCreator
    protected TenantImpl(
            @JsonProperty("tenant") final UUID tenant,
            @JsonProperty("id") final UUID id,
            @JsonProperty("key") final String key,
            @JsonProperty("displayName") final String displayName,
            @JsonProperty("fullName") final String fullName
    ) {
        this.tenant = tenant;
        this.id = id;
        this.key = key;
        this.displayName = displayName;
        this.fullName = fullName;
    }


    @Override
    public UUID getTenant() {
        return tenant;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .toHashCode();
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Tenant)) return false;

        Tenant tenant = (Tenant) o;

        return new EqualsBuilder()
                .append(getId(), tenant.getId())
                .isEquals();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("tenant", tenant)
                .append("key", key)
                .append("displayName", displayName)
                .toString();
    }
}
