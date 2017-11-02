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

package de.kaiserpfalzedv.commons.api.commands;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public class BaseError extends BaseReplyImpl {
    private static final long serialVersionUID = 2825378875380581840L;

    @JsonIgnore
    private Throwable cause;

    /**
     * Constructs a prototypical Event.
     *
     * @param source    The object on which the Event initially occurred.
     * @param commandId The unique ID of this command.
     * @param replyId   The unique ID of this reply.
     *
     * @throws IllegalArgumentException if source is null.
     */
    public BaseError(
            @JsonProperty("source") @NotNull final UUID source,
            @JsonProperty("command") @NotNull final UUID commandId,
            @JsonProperty("reply") @NotNull final UUID replyId,
            final Throwable cause
    ) {
        super(source, commandId, replyId);

        this.cause = cause;
    }

    @JsonIgnore
    public Throwable getCause() {
        return cause;
    }
}
