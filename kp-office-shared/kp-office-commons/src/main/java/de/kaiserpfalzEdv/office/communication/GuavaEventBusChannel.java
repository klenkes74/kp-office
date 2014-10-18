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

import com.google.common.eventbus.EventBus;
import de.kaiserpfalzEdv.office.commands.OfficeCommand;
import de.kaiserpfalzEdv.office.notifications.OfficeNotification;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.validation.constraints.NotNull;

/**
 * This channel is used by the {@link de.kaiserpfalzEdv.office.communication.GuavaEventBusSender} implementation for
 * the office communications. One instance per used {@link com.google.common.eventbus.EventBus} is needed.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Service
public class GuavaEventBusChannel implements CommunicationChannel {
    private static final Logger LOG = LoggerFactory.getLogger(GuavaEventBusChannel.class);


    private EventBus bus;


    @Inject
    public GuavaEventBusChannel(@NotNull final EventBus bus) {
        this.bus = bus;

        LOG.debug("***** Created: {}", this);
        LOG.trace("*   *   event bus: ", this.bus);
    }

    @PreDestroy
    public void close() {
        LOG.debug("***** Destroyed: {}", this);
    }


    /**
     * Sends the command to the channel.
     *
     * @param command The command to be sent.
     * @throws OfficeCommandNotSentException The command can not be sent. Check the cause!
     */
    public void send(@NotNull OfficeCommand command) throws OfficeCommandNotSentException {
        validate();

        LOG.debug("Posting Command: {}", command);

        try {
            bus.post(command);

            LOG.trace("Posted Command to {}: {}", bus, command.getId());
        } catch (RuntimeException e) {
            throw new OfficeCommandNotSentException(e.getMessage(), e);
        }
    }

    public void send(@NotNull OfficeNotification notification) throws OfficeNotificationNotSentException {
        validate();

        LOG.debug("Posting Notification: {}", notification);

        try {
            bus.post(notification);

            LOG.trace("Posted Notification to {}: {}", bus, notification.getNotificationId());
        } catch (RuntimeException e) {
            throw new OfficeCommandNotSentException(e.getMessage(), e);
        }
    }


    /**
     * Registers an observer to this channel.
     *
     * @param observer The observer to be registered.
     */
    public void register(@NotNull final Object observer) {
        try {
            bus.register(observer);
        } catch (IllegalStateException e) {
            LOG.warn("The observer is already registered to {}: {}", bus, observer);
        }
    }

    /**
     * Unregisters an observer from this channel.
     *
     * @param observer The obser to be unregistered.
     */
    public void unregister(@NotNull final Object observer) {
        try {
            bus.unregister(observer);
        } catch (IllegalStateException e) {
            LOG.warn("The observer has not been registered to {}: {}", bus, observer);
        }
    }


    @Override
    public void validate() throws ChannelNotAvailableException {
        if (bus == null) {
            throw new ChannelNotAvailableException(this, "The Guava EventBus is not set!");
        }
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
        GuavaEventBusChannel rhs = (GuavaEventBusChannel) obj;
        return new EqualsBuilder()
                .append(this.bus, rhs.bus)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(bus)
                .toHashCode();
    }
}
