/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.core.security;

import de.kaiserpfalzEdv.commons.jee.db.OffsetDateTimeJPAConverter;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Entity
@Table(
        name = "tickets"
)
public class SecurityTicket implements Serializable {
    private final static ZoneId TIMEZONE = ZoneId.of("UTC");
    private final static long DEFAULT_TTL = 600L;
    private final static long DEFAULT_RENEWAL = 600L;

    @Id @NotNull
    @Column(name = "id_", length=50, nullable = false, updatable = false, unique = true)
    private String id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "account_id_", nullable = false, updatable = false, unique = true)
    private Account account;

    @Convert(converter = OffsetDateTimeJPAConverter.class)
    @Column(name = "created_", nullable = false, updatable = false)
    private OffsetDateTime created;

    @Convert(converter = OffsetDateTimeJPAConverter.class)
    @Column(name = "validity_", nullable = false, updatable = false)
    private OffsetDateTime validity;


    @Deprecated
    public SecurityTicket() {
    }


    public SecurityTicket(@NotNull final Account account) {
        id = UUID.randomUUID().toString();
        this.account = account;
        created = OffsetDateTime.now(TIMEZONE);
        validity = created.plusSeconds(DEFAULT_TTL);
    }


    public void renew() {
        validity = OffsetDateTime.now(TIMEZONE).plusSeconds(DEFAULT_RENEWAL);
    }

    public boolean isValid() {
        OffsetDateTime now = OffsetDateTime.now(TIMEZONE);

        System.out.println(validity.toString() + " is hopefully after " + now.toString());

        return validity.isAfter(now);
    }

    public UUID getId() {
        return UUID.fromString(id);
    }

    public OffsetDateTime getValidity() {
        return validity;
    }

    public String getAccountName() {
        return account.getAccountName();
    }

    public String getDisplayName() {
        return account.getDisplayName();
    }

    public Set<String> getRoles() {
        HashSet<String> result = new HashSet<>();

        account.getRoles().forEach(t -> result.add(t.getDisplayNumber()));

        return Collections.unmodifiableSet(result);
    }

    public Set<String> getEntitlements() {
        return Collections.unmodifiableSet(new HashSet<>());
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
        SecurityTicket rhs = (SecurityTicket) obj;
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

                .append("account", account)
                .append("validity", validity)
                .toString();
    }
}
