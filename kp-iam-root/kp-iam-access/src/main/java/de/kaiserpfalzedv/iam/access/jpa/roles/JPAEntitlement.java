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

package de.kaiserpfalzedv.iam.access.jpa.roles;

import de.kaiserpfalzedv.commons.jpa.JPAAbstractIdentifiable;
import de.kaiserpfalzedv.commons.jpa.JPANameable;
import de.kaiserpfalzedv.iam.access.api.roles.Entitlement;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.*;
import java.util.UUID;

import static javax.persistence.AccessType.FIELD;
import static javax.persistence.LockModeType.NONE;
import static org.apache.commons.lang3.builder.ToStringStyle.SHORT_PREFIX_STYLE;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-20
 */
@Entity(name = "Entitlement")
@Table(
        name = "ENTITLEMENTS",
        indexes = {
                @Index(name = "ENTITLEMENTS_DESCRIPTION_UK", columnList = "DESCRIPTION_", unique = true)
        }
)
@Access(FIELD)
@NamedQueries({
        @NamedQuery(name = "fetch-all", query = "SELECT t FROM Entitlement t", lockMode = NONE),
        @NamedQuery(name = "fetch-all.count", query = "SELECT count(t) FROM Entitlement t", lockMode = NONE)
})
public class JPAEntitlement extends JPAAbstractIdentifiable implements Entitlement {
    private static final long serialVersionUID = -6085847502814790165L;

    @Embedded
    private JPANameable name;

    @Column(name = "DESCRIPTION_", length = 100, unique = true)
    private String description;


    @SuppressWarnings({"unused", "deprecation"})
    @Deprecated // Only for JPA
    protected JPAEntitlement() {}


    JPAEntitlement(
            final UUID id,
            final String displayName,
            final String fullName,
            final String description
    ) {
        super(id);

        name = new JPANameable(displayName, fullName);
        this.description = description;
    }

    void update(final JPAEntitlement orig) {
        // id is not updated!

        name.setDisplayName(orig.getDisplayName());
        name.setFullName(orig.getFullName());
        description = orig.getDescriptionKey();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("displayName", getDisplayName())
                .toString();
    }

    @Override
    public String getDisplayName() {
        return name.getDisplayName();
    }

    @Override
    public String getFullName() {
        return name.getFullName();
    }

    @Override
    public String getName() {
        return getDisplayName();
    }

    @Override
    public String getDescriptionKey() {
        return description;
    }
}
