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

package de.kaiserpfalzEdv.office.ui.web.commons.actions;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.EventObject;
import java.util.UUID;

/**
 * @author klenkes
 * @version 0.2.0
 * @since 21.08.15 05:23
 */
public class Action extends EventObject implements Serializable {
    private static final long serialVersionUID = 6866922890057106548L;

    private UserTransactionInfo transaction;
    private UUID                id;

    /**
     * Constructs a prototypical Event.
     *
     * @param eventId unique ID of this event.
     * @param transaction The user transaction this event belongs to.
     * @param source The object on which the Event initially occurred.
     *
     * @throws IllegalArgumentException if source is null.
     */
    public Action(final UUID eventId, final UserTransactionInfo transaction, final Object source) {
        super(source);

        this.transaction = transaction;
        this.id = eventId;
    }

    /**
     * Constructs a prototypical Event.
     *
     * @param transaction The user transaction this event belongs to.
     * @param source The object on which the Event initially occurred.
     *
     * @throws IllegalArgumentException if source is null.
     */
    public Action(final UserTransactionInfo transaction, final Object source) {
        this(UUID.randomUUID(), transaction, source);
    }


    public UUID getId() {
        return id;
    }

    public UserTransactionInfo getTransaction() {
        return transaction;
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
        Action rhs = (Action) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.id, rhs.getId())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(id)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("id", id)
                .append("transaction", transaction)
                .toString();
    }
}