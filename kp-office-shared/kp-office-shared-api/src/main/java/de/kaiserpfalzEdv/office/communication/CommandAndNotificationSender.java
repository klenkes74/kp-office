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
import de.kaiserpfalzEdv.office.notifications.OfficeNotification;

import javax.validation.constraints.NotNull;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public interface CommandAndNotificationSender {
    /**
     * Sends an office command to the selected bus system.
     *
     * @param channel Channel to send message to.
     * @param command Command to be sent.
     * @throws OfficeCommandNotSentException            The communication failed.
     * @throws InvalidCommunicationChannelTypeException The given channel is not usable by the selected sender.
     * @throws ChannelNotAvailableException             The selected channel is currently not available.
     */
    public void send(@NotNull final CommunicationChannel channel, @NotNull final OfficeCommand command) throws OfficeCommandNotSentException, InvalidCommunicationChannelTypeException, ChannelNotAvailableException;

    /**
     * Implementation of the request-response pattern.
     *
     * @param channel Channel to send message to.
     * @param command Command to be sent.
     * @return The notification as response of the command.
     * @throws OfficeCommandNotSentException            The communication failed.
     * @throws InvalidCommunicationChannelTypeException The given channel is not usable by the selected sender.
     * @throws ChannelNotAvailableException             The selected channel is currently not available.
     * @throws UnsupportedOperationException            The current implementation does not support the request-response-pattern.
     */
    @SuppressWarnings("deprecation")
    public <R extends OfficeNotification> R sendAndReceive(@NotNull final CommunicationChannel channel, @NotNull final OfficeCommand command) throws OfficeCommandNotSentException, InvalidCommunicationChannelTypeException, ChannelNotAvailableException, UnsupportedOperationException;

    /**
     * Sends an office notification to the selected bus system.
     *
     * @param channel      Channel to send message to.
     * @param notification Notifiaction to be sent.
     * @throws OfficeNotificationNotSentException       The communication failed.
     * @throws InvalidCommunicationChannelTypeException The given channel is not usable by the selected sender.
     * @throws ChannelNotAvailableException             The selected channel is currently not available.
     */
    public void send(@NotNull final CommunicationChannel channel, @NotNull final OfficeNotification notification) throws OfficeNotificationNotSentException, InvalidCommunicationChannelTypeException, ChannelNotAvailableException;
}
