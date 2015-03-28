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

package de.kaiserpfalzEdv.office.core.licence.impl;

import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.commons.notifications.Notification;
import de.kaiserpfalzEdv.office.core.licence.commands.LicenceCommand;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.MessageConverter;

import javax.inject.Inject;
import javax.inject.Named;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Implementation;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 20:03
 */
@Named
@KPO(Implementation)
public class LicenceServer implements MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(LicenceServer.class);

    private RabbitTemplate     sender;
    private LicenceApplication service;
    private MessageConverter converter;


    @Inject
    public LicenceServer(final RabbitTemplate sender, final LicenceApplication service, final MessageConverter converter) {
        this.sender = sender;
        this.service = service;
        this.converter = converter;

        LOG.trace("Created: {}", this);
        LOG.trace("  amqp sender: {}", this.sender);
        LOG.trace("  licence command executor: {}", this.service);
        LOG.trace("  message converter: {}", this.converter);
    }


    @Override
    public void onMessage(Message message) {
        LicenceCommand command = (LicenceCommand) converter.fromMessage(message);

        Notification result = command.execute(service);

        sender.convertAndSend(getReplyExchange(message), getReplyRoutingKey(message), result);
    }


    private String getReplyExchange(final Message message) {
        return message.getMessageProperties().getReplyToAddress().getExchangeName();
    }

    private String getReplyRoutingKey(final Message message) {
        return message.getMessageProperties().getReplyToAddress().getRoutingKey();

    }
}
