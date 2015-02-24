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

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * This is the data transfer object of the login ticket.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public class OfficeLoginTicket {
    private final Set<String> roles        = new HashSet<>();
    private final Set<String> entitlements = new HashSet<>();
    /** The UUID of the ticket in the backend. */
    private UUID ticket;
    /** The last valid timestamp this ticket is valid. */
    private OffsetDateTime validity;
    /** The last time this ticket has been checked with the issuing server. */
    private OffsetDateTime lastCheck;
    private String accountName;
    private String displayName;


    @SuppressWarnings("UnusedDeclaration")
    @Deprecated // Only for JPA, JAX-B, Jackson and other frameworks.
    public OfficeLoginTicket() {}


    public OfficeLoginTicket(@NotNull final UUID ticketId,
                             @NotNull final String accountName,
                             @NotNull final String displayName,
                             @NotNull final OffsetDateTime validity) {
        this.ticket = ticketId;

        this.accountName = accountName;
        this.displayName = displayName;

        this.validity = validity;
        markCheck();
    }


    public UUID getTicketId() {
        return ticket;
    }

    public void setTicketId(@NotNull final UUID ticketId) {
        this.ticket = ticketId;
    }


    public String getAccountName() {
        return accountName;
    }

    @Deprecated // Only for frameworks
    public void setAccountName(@NotNull String accountName) {
        this.accountName = accountName;
    }


    public String getDisplayName() {
        return displayName;
    }

    @Deprecated // Only for frameworks
    public void setDisplayName(@NotNull final String displayName) {
        this.displayName = displayName;
    }


    public OffsetDateTime getValidity() {
        return validity;
    }

    public void setValidity(@NotNull final OffsetDateTime validity) {
        this.validity = validity;
    }


    public OffsetDateTime getLastCheck() {
        return lastCheck;
    }

    @Deprecated // please use only markCheck(). This is for JPA, JAX-B, Jackson and so on ...
    public void setLastCheck(@NotNull final OffsetDateTime lastCheck) {
        this.lastCheck = lastCheck;
    }

    @SuppressWarnings("deprecation")
    public void markCheck() {
        setLastCheck(OffsetDateTime.now(ZoneId.of("UTC")));
    }


    public Set<String> getRoles() {
        return Collections.unmodifiableSet(roles);
    }


    @Deprecated // Only for frameworks
    public void setRoles(@NotNull final Set<String> roles) {
        this.roles.clear();

        this.roles.addAll(roles);
    }


    public Set<String> getEntitlements() {
        return Collections.unmodifiableSet(entitlements);
    }


    @Deprecated // Only for frameworks
    public void setEntitlements(@NotNull final Set<String> entitlements) {
        this.entitlements.clear();

        this.entitlements.addAll(entitlements);
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
        OfficeLoginTicket rhs = (OfficeLoginTicket) obj;
        return new EqualsBuilder()
                .append(this.ticket, rhs.ticket)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(ticket)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("ticket", ticket)
                .append("accountName", accountName)
                .append("validity", validity)
                .append("lastCheck", lastCheck)
                .toString();
    }
}
