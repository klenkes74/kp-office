/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.commons.jpa;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PostLoad;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.multitenancy.TenantIdentifiable;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * The abstract base class for {@link TenantIdentifiable} JPA entities.
 * <p>
 * <p>Since JPA does not map UUID very fine, we need to convert the UUID to strings first. That is done via JPA
 * {@link PrePersist}, {@link PreUpdate} and {@link PreRemove} during writing events and {@link PostLoad} for reading
 * events. For performance reasons during the session only {@link Transient} caches are used to access the
 * {@link UUID}-based fields.</p>
 * <p>
 * <p>For Liquibase, the following definitions are needed:</p>
 * <pre>
 *     &lt;column name="ID_" type="VARCHAR(40)"&gt;
 *         &lt;constraints nullable="false" primaryKey="true" primaryKeyName="&lt;em&gt;TABLE_NAME&lt;/em&gt;_PK"/&gt;
 *     &lt;/column&gt;
 *
 *     &lt;column name="VERSION_" type="BIGINT" defaultValue="0"&gt;
 *         &lt;constraints nullable="false"/&gt;
 *     &lt;/column&gt;
 *
 *     &lt;column name="TENANT_" type="VARCHAR(40)"&gt;
 *         &lt;constraints nullable="false"/&gt;
 *     &lt;/column&gt;
 *
 *     &lt;createIndex indexName="&lt;em&gt;TABLE_NAME&lt;/em&gt;_TENANT_IDX" tableName="&lt;em&gt;TABLE_NAME&lt;/em&gt;"&gt;
 *         &lt;column name="TENANT_"/&gt;
 *     &lt;/createIndex&gt;
 * </pre>
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-30
 */
@MappedSuperclass
public class JPAAbstractTenantIdentifiable extends JPAAbstractIdentifiable implements TenantIdentifiable {
    private static final long serialVersionUID = 9029901785198108689L;

    @Column(name = "TENANT_", length = 40, nullable = false)
    private String tenant;

    @Transient
    private UUID tenantCache;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for JPA
    protected JPAAbstractTenantIdentifiable() {}


    public JPAAbstractTenantIdentifiable(
            @NotNull final UUID id,
            @NotNull final UUID tenant
    ) {
        super(id);

        this.tenantCache = tenant;
    }

    @Override
    public UUID getTenant() {
        return tenantCache;
    }

    protected void setTenant(UUID tenant) {
        tenantCache = tenant;
    }

    @PrePersist
    @PreUpdate
    @PreRemove
    protected void convertIdToString() {
        super.convertIdToString();
        this.tenant = tenantCache.toString();
    }

    @PostLoad
    protected void convertIdFromString() {
        super.convertIdFromString();
        try {
            tenantCache = UUID.fromString(tenant);
        } catch (IllegalArgumentException e) {
            throw new IllegalStateException("Tenant ID from database could not be converted to UUID: " + tenant, e);
        }
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("tenant", tenantCache)
                .toString();
    }
}
