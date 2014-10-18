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

package de.kaiserpfalzEdv.office.notifications;

import de.kaiserpfalzEdv.office.commands.OfficeCommand;
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
public abstract class OfficeNotification implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * The unique id of this notification.
     */
    private UUID notificationId = UUID.randomUUID();

    /**
     * The unique id of this command.
     */
    private UUID commandId;

    /**
     * The timestamp this notification has been created.
     */
    private ZonedDateTime notificationTimestamp = ZonedDateTime.now();

    @Deprecated // Only for JAX-B, Jackson, JPA!
    public OfficeNotification() {
    }

    public OfficeNotification(final OfficeCommand command) {
        setCommandId(command.getId());
    }

    public OfficeNotification(final UUID commandId) {
        setCommandId(commandId);
    }

    /**
     * @return The uniform name of the target entity for the given command.
     */
    public abstract String getTarget();


    /**
     * @return The official timestamp of this command.
     */
    public ZonedDateTime getNotificationTimestamp() {
        return notificationTimestamp;
    }

    public UUID getNotificationId() {
        return notificationId;
    }

    /**
     * @return The official unique id of this command.
     */
    public UUID getCommandId() {
        return commandId;
    }

    public void setCommandId(final UUID commandId) {
        checkArgument(commandId != null, "Sorry, no notification without a command!");

        this.commandId = commandId;
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
        OfficeNotification rhs = (OfficeNotification) obj;
        return new EqualsBuilder()
                .append(this.notificationId, rhs.notificationId)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(notificationId)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("notificationId", notificationId)
                .append("notificationTimestamp", notificationTimestamp)
                .append("commandId", commandId)
                .toString();
    }
}
