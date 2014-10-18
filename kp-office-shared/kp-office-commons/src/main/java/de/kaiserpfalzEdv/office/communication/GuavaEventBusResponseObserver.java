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

import com.google.common.eventbus.Subscribe;
import de.kaiserpfalzEdv.office.commands.OfficeCommand;
import de.kaiserpfalzEdv.office.notifications.OfficeNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public class GuavaEventBusResponseObserver<R extends OfficeNotification> implements ResponseObserver<R> {
    private static final Logger LOG = LoggerFactory.getLogger(GuavaEventBusResponseObserver.class);

    private UUID id;
    private volatile R notification;

    public GuavaEventBusResponseObserver(@NotNull final UUID id) {
        this.id = id;
    }

    @Override
    public R getResponse() {
        return notification;
    }

    @SuppressWarnings("UnusedDeclaration")
    @Subscribe
    public void setResponse(R notification) {
        LOG.debug("Working on: {}", notification);


        if (notification.getCommandId().equals(id)) {
            LOG.debug("Received notification for command {}: {}", id, notification);

            this.notification = notification;
        } else {
            LOG.debug("Received wrong notification #{} for command #{}", notification.getNotificationId(), notification.getCommandId());
        }
    }

    @Override
    public R receiveResponse(@NotNull CommunicationChannel channel, @NotNull OfficeCommand command) throws CommunicationException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
