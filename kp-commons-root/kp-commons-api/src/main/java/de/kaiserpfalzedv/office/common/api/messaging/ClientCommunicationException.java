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

package de.kaiserpfalzedv.office.common.api.messaging;

import java.util.UUID;

import de.kaiserpfalzedv.office.common.api.BaseSystemException;
import de.kaiserpfalzedv.office.common.api.commands.CrudCommands;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-26
 */
public class ClientCommunicationException extends BaseSystemException {
    private static final long serialVersionUID = -2539006277931416171L;

    private String messageId;
    private UUID correlationId;

    private CrudCommands command;
    private Class<?> clasz;
    private UUID tenantId;

    public ClientCommunicationException(
            final Class<?> clasz,
            final CrudCommands command,
            final UUID correlationId,
            final String messageId,
            final UUID objectId,
            Throwable cause
    ) {
        super(String.format(
                "Communication failure for %s %s with id '%s'. (%s, correlation-id: %s, message-id: %s)",
                command, clasz.getSimpleName(), objectId,
                cause.getClass().getSimpleName(), correlationId, messageId
        ));

        this.command = command;
        this.clasz = clasz;
        this.tenantId = objectId;

        this.messageId = messageId;
        this.correlationId = correlationId;
    }

    public ClientCommunicationException(
            final Class<?> clasz,
            final CrudCommands command,
            final UUID correlationId,
            final String messageId,
            Throwable cause
    ) {
        super(String.format(
                "Communication failure for %s %s. (%s, correlation-id: %s, message-id: %s)",
                command, clasz.getSimpleName(),
                cause.getClass().getSimpleName(), correlationId, messageId
        ));

        this.command = command;
        this.clasz = clasz;

        this.messageId = messageId;
        this.correlationId = correlationId;
    }

    public String getMessageId() {
        return messageId;
    }

    public UUID getCorrelationId() {
        return correlationId;
    }

    public CrudCommands getCommand() {
        return command;
    }

    public Class<?> getClasz() {
        return clasz;
    }

    public UUID getTenantId() {
        return tenantId;
    }
}
