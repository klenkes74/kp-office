package de.kaiserpfalzedv.office.tenant.impl;

import com.sun.javafx.scene.traversal.TopMostTraversalEngine;
import de.kaiserpfalzedv.office.tenant.Tenant;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.UUID;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-20
 */
public class TenantImpl implements Tenant {
    private UUID tenant;
    private UUID id;

    private String displayName;
    private String fullName;

    @Deprecated
    public TenantImpl() {}


    TenantImpl(
            final UUID tenant,
            final UUID id,
            final String displayName,
            final String fullName
    ) {
        this.tenant = tenant;
        this.id = id;
        this.displayName = displayName;
        this.fullName = fullName;
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
    public UUID getId() {
        return id;
    }

    @Override
    public UUID getTenantId() {
        return tenant;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof TenantImpl)) return false;

        TenantImpl tenant = (TenantImpl) o;

        return new EqualsBuilder()
                .append(getId(), tenant.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(getId())
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id)
                .append("displayName", displayName)
                .toString();
    }
}
