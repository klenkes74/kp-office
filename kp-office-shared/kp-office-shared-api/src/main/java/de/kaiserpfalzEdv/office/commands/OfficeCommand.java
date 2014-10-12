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

package de.kaiserpfalzEdv.office.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.kaiserpfalzEdv.office.core.IdentityHolder;
import de.kaiserpfalzEdv.office.core.PermissionNameHolder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public abstract class OfficeCommand implements IdentityHolder, PermissionNameHolder, Serializable {
    private static final long serialVersionUID = 8298856277742024129L;

    /**
     * The unique id of this command.
     */
    private UUID id = UUID.randomUUID();

    /**
     * The timestamp this command has been created.
     */
    private ZonedDateTime commandTimestamp = ZonedDateTime.now();

    /**
     * The timestamp this command has been handled.
     */
    private ZonedDateTime handledTimestamp;


    /**
     * @return The official unique id of this command.
     */
    public UUID getId() {
        return id;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setId(final UUID id) {
        this.id = id;
    }


    /**
     * @return The uniform name of the target entity for the given command.
     */
    public abstract String getTarget();

    @JsonIgnore
    @Override
    public String getPermissionName() {
        return getClass().getSimpleName();
    }


    /**
     * @return The official timestamp of this command.
     */
    public ZonedDateTime getCommandTimestamp() {
        return commandTimestamp;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setCommandTimestamp(final ZonedDateTime value) {
        checkArgument(value != null, "Can't set <null> as command time stamp!");

        this.commandTimestamp = value;
    }


    /**
     * @return The timestamp this command has been handled by the appropriate handler (if already handled).
     */
    public ZonedDateTime getHandledTimestamp() {
        return handledTimestamp;
    }

    /**
     * @param handledTimestamp The timestamp this command has been handled.
     */
    public void setHandledTimestamp(final ZonedDateTime handledTimestamp) {
        this.handledTimestamp = handledTimestamp;
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
        OfficeCommand rhs = (OfficeCommand) obj;
        return new EqualsBuilder()
                .append(this.getId(), rhs.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", getId())
                .append("commandTimestamp", getCommandTimestamp())
                .toString();
    }
}
