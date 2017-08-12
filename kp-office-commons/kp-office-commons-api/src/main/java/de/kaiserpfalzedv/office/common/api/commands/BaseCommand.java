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

package de.kaiserpfalzedv.office.common.api.commands;

import java.util.EventObject;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * A generic base for all commands send within KPO.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE, include = JsonTypeInfo.As.PROPERTY)
public abstract class BaseCommand extends EventObject {
    private static final long serialVersionUID = 1L;

    private UUID command;

    /**
     * Constructs a prototypical Event.
     *
     * @param source  The object on which the Event initially occurred.
     * @param command The unique ID of this command.
     *
     * @throws IllegalArgumentException if source is null.
     */
    @JsonCreator
    public BaseCommand(
            @JsonProperty("source") final UUID source,
            @JsonProperty("command") final UUID command
    ) {
        super(source);

        this.command = command;
    }

    @JsonIgnore
    public String getActionType() {
        return getClass().getCanonicalName();
    }

    @SuppressWarnings("unused")
    public void execute(CommandExecutor commandExecutor) throws CommandExecutionException {
        commandExecutor.execute(this);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(command)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!BaseCommand.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        BaseCommand rhs = (BaseCommand) obj;
        return new EqualsBuilder()
                .append(this.command, rhs.getCommand())
                .isEquals();
    }

    /**
     * @return The UUID of the source.
     */
    @Override
    public UUID getSource() {
        return (UUID) super.getSource();
    }

    public UUID getCommand() {
        return command;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(System.identityHashCode(this))
                .append("source", getSource())
                .append("command", command)
                .toString();
    }
}
