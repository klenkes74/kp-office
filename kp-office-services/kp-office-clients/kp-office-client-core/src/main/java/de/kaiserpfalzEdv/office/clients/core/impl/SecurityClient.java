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

package de.kaiserpfalzEdv.office.clients.core.impl;

import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.core.security.InvalidLoginException;
import de.kaiserpfalzEdv.office.core.security.InvalidTicketException;
import de.kaiserpfalzEdv.office.core.security.OfficeLoginTicket;
import de.kaiserpfalzEdv.office.core.security.SecurityService;
import de.kaiserpfalzEdv.office.core.security.commands.SecurityCheckCommand;
import de.kaiserpfalzEdv.office.core.security.commands.SecurityLoginCommand;
import de.kaiserpfalzEdv.office.core.security.commands.SecurityLogoutCommand;
import de.kaiserpfalzEdv.office.core.security.notifications.OfficeLoginTicketNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Client;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 22:40
 */
@Named
@KPO(Client)
public class SecurityClient implements SecurityService {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityClient.class);


    private static final String MESSAGE_EXCHANGE = "kpo.core";
    private static final String ROUTING_KEY      = "core.security";


    private RabbitTemplate sender;


    @Inject
    public SecurityClient(final RabbitTemplate sender) {
        this.sender = sender;

        LOG.trace("Created/Initialized: {}", this);
    }


    @Override
    public OfficeLoginTicket login(@NotNull String account, @NotNull String password) throws InvalidLoginException {
        Object result = sender.convertSendAndReceive(MESSAGE_EXCHANGE, ROUTING_KEY, new SecurityLoginCommand(account, password));

        if (result != null) {
            try {
                return ((OfficeLoginTicketNotification) result).getTicket();
            } catch (ClassCastException e) {
                throw new InvalidLoginException(account);
            }
        }

        throw new InvalidLoginException(account);
    }

    @Override
    public OfficeLoginTicket check(@NotNull OfficeLoginTicket ticket) throws InvalidTicketException {
        Object result = sender.convertSendAndReceive(MESSAGE_EXCHANGE, ROUTING_KEY, new SecurityCheckCommand(ticket));

        if (result != null) {
            try {
                return ((OfficeLoginTicketNotification) result).getTicket();
            } catch (ClassCastException e) {
                throw new InvalidTicketException(ticket.getTicketId());
            }
        }

        throw new InvalidTicketException(ticket.getTicketId());
    }

    @Override
    public void logout(@NotNull OfficeLoginTicket ticket) {
        sender.convertAndSend(MESSAGE_EXCHANGE, ROUTING_KEY, new SecurityLogoutCommand(ticket));
    }
}