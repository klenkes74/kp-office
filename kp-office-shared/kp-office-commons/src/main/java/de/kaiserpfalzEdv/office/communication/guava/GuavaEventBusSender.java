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

package de.kaiserpfalzEdv.office.communication.guava;

import de.kaiserpfalzEdv.office.commands.OfficeCommand;
import de.kaiserpfalzEdv.office.communication.ChannelNotAvailableException;
import de.kaiserpfalzEdv.office.communication.CommandAndNotificationSender;
import de.kaiserpfalzEdv.office.communication.CommunicationChannel;
import de.kaiserpfalzEdv.office.communication.InvalidCommunicationChannelTypeException;
import de.kaiserpfalzEdv.office.communication.OfficeCommandNotSentException;
import de.kaiserpfalzEdv.office.communication.OfficeNotificationNotSentException;
import de.kaiserpfalzEdv.office.communication.ResponseObserver;
import de.kaiserpfalzEdv.office.notifications.OfficeNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;

/**
 * Implements the communication framework for the office communication by using the Google Guava
 * {@link com.google.common.eventbus.EventBus}.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public class GuavaEventBusSender implements CommandAndNotificationSender {
    private static final Logger LOG = LoggerFactory.getLogger(GuavaEventBusSender.class);

    @PostConstruct
    public void init() {
        LOG.debug("***** Created: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.debug("***** Destroyed: {}", this);
    }


    @Override
    public void send(@NotNull final CommunicationChannel channel, @NotNull final OfficeCommand command) throws OfficeCommandNotSentException {
        checkChannelType(channel);

        ((GuavaEventBusChannel) channel).send(command);
    }

    @Override
    public <R extends OfficeNotification> R sendAndReceive(@NotNull final CommunicationChannel channel, @NotNull final OfficeCommand command) throws OfficeCommandNotSentException, InvalidCommunicationChannelTypeException, ChannelNotAvailableException, UnsupportedOperationException {
        checkChannelType(channel);

        ResponseObserver<R> observer = new GuavaEventBusResponseObserver<>(command.getId());

        ((GuavaEventBusChannel) channel).register(observer);
        send(channel, command);
        ((GuavaEventBusChannel) channel).unregister(observer);

        return observer.getResponse();
    }

    @Override
    public void send(@NotNull final CommunicationChannel channel, @NotNull final OfficeNotification notification) throws OfficeNotificationNotSentException {
        checkChannelType(channel);

        ((GuavaEventBusChannel) channel).send(notification);
    }


    private void checkChannelType(@NotNull CommunicationChannel channel) {
        if (!GuavaEventBusChannel.class.isAssignableFrom(channel.getClass())) {
            throw new InvalidCommunicationChannelTypeException(
                    channel.getClass().asSubclass(CommunicationChannel.class),
                    GuavaEventBusChannel.class.asSubclass(CommunicationChannel.class)
            );
        }
    }
}
