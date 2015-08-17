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
import de.kaiserpfalzEdv.office.core.i18n.MessageProvider;
import de.kaiserpfalzEdv.office.core.i18n.TranslationEntry;
import de.kaiserpfalzEdv.office.core.i18n.TranslationService;
import de.kaiserpfalzEdv.office.core.i18n.commands.RequestTranslationsCommand;
import de.kaiserpfalzEdv.office.core.i18n.notifications.TranslationsNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.cache.annotation.Cacheable;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Client;
import static java.util.Arrays.asList;

/**
 * The translation requestor.
 * 
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 19:23
 */
@Named
@KPO(Client)
public class TranslationClient implements TranslationService, MessageProvider {
    private static final Logger LOG = LoggerFactory.getLogger(TranslationClient.class);

    private static final String MESSAGE_EXCHANGE = "kpo.core";
    private static final String ROUTING_KEY      = "core.i18n";


    private final HashMap<String, String> messages = new HashMap<>();
    private RabbitTemplate sender;


    @Inject
    public TranslationClient(final RabbitTemplate sender) {
        this.sender = sender;

        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    private void loadTranslations() {
        if (messages.size() != 0)
            return;

        synchronized (this) {
            if (messages.size() != 0)
                return;

            Set<TranslationEntry> entries = getTranslationEntries();

            entries.forEach(
                    e -> {
                        messages.put(e.getKey() + "/" + e.getLanguage(), e.getValue());
                    }
            );

            LOG.trace("{} messages loaded.", messages.size());
        }
    }


    @Cacheable
    @Override
    public Set<TranslationEntry> getTranslationEntries() {
        RequestTranslationsCommand command = new RequestTranslationsCommand();

        TranslationsNotification result
                = (TranslationsNotification) sender.convertSendAndReceive(MESSAGE_EXCHANGE, ROUTING_KEY, command);

        if (result == null) // return NULL-Object
            result = new TranslationsNotification(new HashSet<>());

        return result.getTranslations();
    }


    @Override
    public MessageFormat resolveCode(final String key, final Locale locale) {
        loadTranslations();


        List<String> messageKeys = asList(
                fullMessageKey(key, locale),
                countryMessageKey(key, locale),
                languageMessageKey(key, locale),
                languageMessageKey(key, Locale.GERMAN)
        );

        for (String k : messageKeys) {
            if (messages.containsKey(k))
                return getMessageFormat(messages.get(k), locale);
        }

        return getMessageFormat(key, locale);
    }

    public String languageMessageKey(@NotNull final String key, @NotNull final Locale locale) {
        return key + "/" + locale.getLanguage();
    }

    public String countryMessageKey(@NotNull final String key, @NotNull final Locale locale) {
        return key + "/" + locale.getLanguage() + "_" + locale.getCountry();
    }

    public String fullMessageKey(@NotNull final String key, @NotNull final Locale locale) {
        return key + "/" + locale.toLanguageTag();
    }


    private MessageFormat getMessageFormat(@NotNull final String message, @NotNull final Locale locale) {
        return new MessageFormat(message, locale);
    }
}
