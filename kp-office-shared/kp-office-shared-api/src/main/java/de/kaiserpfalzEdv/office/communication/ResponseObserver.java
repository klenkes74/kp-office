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

package de.kaiserpfalzEdv.office.communication;

import de.kaiserpfalzEdv.office.commands.OfficeCommand;

import javax.validation.constraints.NotNull;

/**
 * @param <R> Type of the response to wait for.
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public interface ResponseObserver<R> {
    /**
     * Receives the response to the given command.
     *
     * @param channel Channel to receive the response through.
     * @param command The command to retrieve the response for.
     * @return The response to the given command.
     * @throws CommunicationException If the receiving of the response failed. Check Cause!
     */
    public R receiveResponse(@NotNull final CommunicationChannel channel, @NotNull final OfficeCommand command) throws CommunicationException;

    public R getResponse();
}
