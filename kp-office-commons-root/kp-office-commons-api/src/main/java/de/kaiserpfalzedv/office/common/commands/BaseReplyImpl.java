/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.common.commands;

import java.util.EventObject;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE, include = JsonTypeInfo.As.PROPERTY)
public class BaseReplyImpl extends EventObject implements BaseReply {
    private static final long serialVersionUID = 1L;


    protected UUID command;
    protected UUID reply;


    public BaseReplyImpl(
            @NotNull final Object source,
            @NotNull final UUID command,
            @NotNull final UUID reply
    ) {
        super(source);

        this.command = command;
        this.reply = reply;
    }

    public UUID getCommand() {
        return command;
    }

    public UUID getReply() {
        return reply;
    }

    @JsonIgnore
    @Override
    public String getActionType() {
        return getClass().getCanonicalName();
    }

    @Override
    public UUID getSource() {
        return (UUID) super.getSource();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(System.identityHashCode(this))
                .append("command", command)
                .append("reply", reply)
                .toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(reply)
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
        if (!BaseReplyImpl.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        BaseReplyImpl rhs = (BaseReplyImpl) obj;
        return new EqualsBuilder()
                .append(this.reply, rhs.getReply())
                .isEquals();
    }
}
