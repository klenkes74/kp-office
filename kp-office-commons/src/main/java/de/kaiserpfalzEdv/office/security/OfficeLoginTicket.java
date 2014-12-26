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

package de.kaiserpfalzEdv.office.security;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * This is the data transfer object of the login ticket.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public class OfficeLoginTicket {
    /** The UUID of the ticket in the backend. */
    private UUID ticket;

    /** The last valid timestamp this ticket is valid. */
    private ZonedDateTime validity;

    /** The last time this ticket has been checked with the issuing server. */
    private ZonedDateTime lastCheck;


    @Deprecated // Only for JPA, JAX-B, Jackson and other frameworks.
    public OfficeLoginTicket() {}


    public OfficeLoginTicket(@NotNull final UUID ticketId, ZonedDateTime validity) {
        this.ticket = ticketId;
        this.validity = validity;
        this.lastCheck = validity;
    }


    public UUID getTicketId() {
        return ticket;
    }

    public void setTicketId(@NotNull final UUID ticketId) {
        this.ticket = ticketId;
    }


    public ZonedDateTime getValidity() {
        return validity;
    }

    public void setValidity(@NotNull final ZonedDateTime validity) {
        this.validity = validity;
    }


    public ZonedDateTime getLastCheck() {
        return lastCheck;
    }

    @Deprecated // please use only markCheck(). This is for JPA, JAX-B, Jackson and so on ...
    public void setLastCheck(@NotNull final ZonedDateTime lastCheck) {
        this.lastCheck = lastCheck;
    }

    @SuppressWarnings("deprecation")
    public void markCheck() {
        setLastCheck(ZonedDateTime.now(ZoneId.of("UTC")));
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
                .append("validity", validity)
                .append("lastCheck", lastCheck)
                .toString();
    }
}
