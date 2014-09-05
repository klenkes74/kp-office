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

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.time.ZonedDateTime;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class OfficeTicketDTO implements OfficeTicket {
    private UUID ticket;

    private ZonedDateTime ttl;


    @Deprecated // Only for Jackson, JAX-B and JPA!
    public OfficeTicketDTO() {}

    public OfficeTicketDTO(final UUID id, final ZonedDateTime ttl) {
        setTicket(id);
        setTtl(ttl);
    }


    @Override
    public UUID getTicket() {
        return ticket;
    }

    @Override
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setTicket(final UUID id) {
        checkArgument(id != null, "Can't create an empty Office Security Ticket!");

        this.ticket = id;
    }


    @Override
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public ZonedDateTime getTtl() {
        return ttl;
    }

    @JsonIgnore
    public boolean ttlReached() {
        return ttl.isBefore(ZonedDateTime.now());
    }

    @Override
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setTtl(ZonedDateTime ttl) {
        this.ttl = ttl;
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
        OfficeTicket rhs = (OfficeTicket) obj;
        return new EqualsBuilder()
                .append(this.getTicket(), rhs.getTicket())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getTicket())
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(ticket)
                .toString();
    }
}
