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

package de.kaiserpfalzEdv.office.core.tenants.impl;

import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.commons.notifications.Notification;
import de.kaiserpfalzEdv.office.core.tenants.commands.TenantCommand;
import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

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
public class TenantServer implements MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(TenantServer.class);

    private RabbitTemplate    sender;
    private TenantApplication service;


    @Inject
    public TenantServer(final RabbitTemplate sender, final TenantApplication service) {
        this.sender = sender;
        this.service = service;

        LOG.trace("Created: {}", this);
        LOG.trace("  amqp sender: {}", this.sender);
        LOG.trace("  security command executor: {}", this.service);
    }


    @Override
    public void onMessage(Message message) {
        TenantCommand command = (TenantCommand) SerializationUtils.deserialize(message.getBody());

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
