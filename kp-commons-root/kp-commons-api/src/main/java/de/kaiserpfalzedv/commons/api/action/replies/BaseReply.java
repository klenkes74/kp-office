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

package de.kaiserpfalzedv.commons.api.action.replies;

import java.io.Serializable;
import java.util.EventObject;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonTypeInfo;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.NONE, include = JsonTypeInfo.As.PROPERTY)
public abstract class BaseReply<T extends Serializable> extends EventObject {
    private static final long serialVersionUID = 6191377606858080850L;

    protected UUID command;
    protected UUID reply;

    public BaseReply(
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
    public String getActionType() {
        return getClass().getCanonicalName();
    }

    @Override
    public UUID getSource() {
        return (UUID) super.getSource();
    }
}
