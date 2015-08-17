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

package de.kaiserpfalzEdv.office.commons.amqp.services;

import de.kaiserpfalzEdv.office.commons.amqp.session.AmqpRequest;
import de.kaiserpfalzEdv.office.commons.amqp.session.AmqpSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 15:31
 */
@Named
public class AmqpHeaderInjectorService implements MessagePostProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(AmqpHeaderInjectorService.class);


    @Inject
    private AmqpRequest request;

    @Value("${info.app.name}")
    private String appName;

    @Value("${amqp.ticketName}")
    private String securityTicketHeaderName;


    public AmqpHeaderInjectorService() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
        LOG.trace("  request: {}", this.request);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        if (request.isInitialized()) {
            forward(message.getMessageProperties());
        } else {
            send(message.getMessageProperties());
        }

        LOG.debug("Message after post processing: {}", message);
        return message;
    }


    private void send(MessageProperties properties) {
        LOG.trace("Sending a new message ...");

        properties.setAppId(appName);

        properties.setMessageId(UUID.randomUUID().toString());
        properties.setCorrelationId(UUID.randomUUID().toString().getBytes());

        properties.setHeader(securityTicketHeaderName, request.getUserId().toString());
    }

    private void forward(MessageProperties properties) {
        LOG.trace("Forwarding/Replying the message ...");

        checkInitializedRequest();

        properties.setAppId(appName);

        properties.setMessageId(UUID.randomUUID().toString());
        properties.setCorrelationId(request.getCorrelationId().toString().getBytes());

        UUID ticketId = request.getUserId();

        if (ticketId != null)
            properties.setHeader(securityTicketHeaderName, ticketId.toString());
    }

    public void checkInitializedRequest() {
        if (!request.isInitialized()) {
            throw new AmqpSessionException("Request is not initialized.");
        }
    }
}