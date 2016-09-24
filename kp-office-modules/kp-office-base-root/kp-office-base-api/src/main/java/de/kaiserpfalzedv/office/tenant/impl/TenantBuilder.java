package de.kaiserpfalzedv.office.tenant.impl;

import de.kaiserpfalzedv.office.common.BuilderException;
import de.kaiserpfalzedv.office.tenant.Tenant;
import org.apache.commons.lang3.builder.Builder;

import java.util.ArrayList;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-20
 */
public class TenantBuilder implements Builder<Tenant> {
    private UUID tenant;
    private UUID id;
    private String displayName;
    private String fullName;


    @Override
    public Tenant build() {
        defaultValues();
        validate();

        return new TenantImpl(tenant, id, displayName, fullName);
    }

    private void defaultValues() {
        if (tenant == null) {
            tenant = new NullTenant().getId();
        }

        if (id == null) {
            id = UUID.randomUUID();
        }

        if (isBlank(displayName)) {
            displayName = fullName;
        }

        if (isBlank(fullName)) {
            fullName = displayName;
        }
    }

    public void validate() {
        ArrayList<String> failures = new ArrayList<>(2);

        if (isBlank(displayName)) {
            failures.add("A tenant needs a display name. No name given.");
        }

        if (isBlank(fullName)) {
            failures.add("A tenant needs a full name. No name given.");
        }

        if (! failures.isEmpty()) {
            throw new BuilderException(Tenant.class, failures.toArray(new String[1]));
        }
    }


    public TenantBuilder withTenant(final Tenant orig) {
        withTenantId(orig.getTenantId());
        withId(orig.getId());
        withDisplayName(orig.getDisplayName());
        withFullName(orig.getFullName());

        return this;
    }


    public TenantBuilder withTenantId(final UUID tenant) {
        this.tenant = tenant;
        return this;
    }

    public TenantBuilder withId(final UUID id) {
        this.id = id;
        return this;
    }

    public TenantBuilder withDisplayName(final String displayName) {
        this.displayName = displayName;
        return this;
    }

    public TenantBuilder withFullName(final String fullName) {
        this.fullName = fullName;
        return this;
    }
}
