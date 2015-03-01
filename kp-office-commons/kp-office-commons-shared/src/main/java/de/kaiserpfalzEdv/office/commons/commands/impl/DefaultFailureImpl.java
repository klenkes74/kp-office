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

package de.kaiserpfalzEdv.office.commons.commands.impl;

import de.kaiserpfalzEdv.office.commons.i18n.MessageKey;
import de.kaiserpfalzEdv.office.commons.notifications.Failure;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.time.OffsetDateTime;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 20:32
 */
public class DefaultFailureImpl implements Failure {
    private String         message;
    private MessageKey     messageKey;
    private OffsetDateTime timestamp;


    public DefaultFailureImpl(final MessageKey messageKey, final String message, final OffsetDateTime timestamp) {
        this.message = message;
        this.messageKey = messageKey;
        this.timestamp = timestamp;

    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public MessageKey getMessageKey() {
        return messageKey;
    }

    @Override
    public OffsetDateTime getTimestamp() {
        return timestamp;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("message", message)
                .append("messageKey", messageKey)
                .append("timestamp", timestamp)
                .toString();
    }
}
