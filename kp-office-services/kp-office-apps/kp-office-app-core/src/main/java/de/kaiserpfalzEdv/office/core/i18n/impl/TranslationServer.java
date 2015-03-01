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

package de.kaiserpfalzEdv.office.core.i18n.impl;

import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.core.i18n.TranslationService;
import de.kaiserpfalzEdv.office.core.i18n.notifications.TranslationsNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Address;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Implementation;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 27.02.15 16:19
 */
@Named
@KPO(Implementation)
public class TranslationServer implements MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(TranslationServer.class);


    private TranslationService service;

    @Inject
    private RabbitTemplate sender;


    @Inject
    public TranslationServer(final TranslationService service) {
        this.service = service;

        LOG.trace("Created/Initialized: {}", this);
        LOG.trace("  translation repository: {}", this.service);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Override
    public void onMessage(Message message) {
        Address replyTo = message.getMessageProperties().getReplyToAddress();

        TranslationsNotification response = new TranslationsNotification(service.getTranslationEntries());

        sender.convertAndSend(replyTo.getExchangeName(), replyTo.getRoutingKey(), response);
    }
}
