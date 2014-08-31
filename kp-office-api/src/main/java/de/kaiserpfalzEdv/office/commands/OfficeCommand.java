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

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public abstract class OfficeCommand implements Serializable {
    private static final long serialVersionUID = 2709709360542844294L;

    /** The unique id of this command. */
    private UUID commandId = UUID.randomUUID();

    /** The timestamp this command has been created. */
    private ZonedDateTime commandTimestamp = ZonedDateTime.now();

    /** The timestamp this command has been handled. */
    private ZonedDateTime handledTimestamp;


    /**
     * @return The uniform name of the target entity for the given command.
     */
    public abstract String getTarget();

    /**
     * Executes the command. Will be called by a handler and normally be implemented using a visitor pattern.
     *
     * @param context The handler for the visitor pattern.
     * @throws OfficeCommandException The only allowed type of exception to be thrown.
     */
    public abstract void execute(final OfficeCommandHandler context) throws OfficeCommandException;



    /**
     * @return The official timestamp of this command.
     */
    public ZonedDateTime getCommandTimestamp() {
        return commandTimestamp;
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

    /**
     * @return The official unique id of this command.
     */
    public UUID getCommandId() {
        return commandId;
    }
}
