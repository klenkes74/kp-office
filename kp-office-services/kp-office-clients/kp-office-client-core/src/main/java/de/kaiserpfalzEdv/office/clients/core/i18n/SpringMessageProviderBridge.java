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

package de.kaiserpfalzEdv.office.clients.core.i18n;

import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.core.i18n.MessageProvider;
import de.kaiserpfalzEdv.office.core.i18n.TranslationEntry;
import de.kaiserpfalzEdv.office.core.i18n.TranslationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Client;
import static java.util.Arrays.asList;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 23.02.15 10:03
 */
@Named
@KPO(Client)
public class SpringMessageProviderBridge implements MessageProvider, Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(SpringMessageProviderBridge.class);

    private TranslationProvider translations;

    private HashMap<String, String> messages = new HashMap<>();

    @Inject
    public SpringMessageProviderBridge(
            final TranslationProvider translations
    ) {
        this.translations = translations;

        LOG.trace("Created: {}", this);
        LOG.trace("  message source: {}", this.translations);
    }

    @PostConstruct
    public void init() {
        Set<TranslationEntry> entries = translations.getTranslationEntries();

        entries.forEach(
                e -> {
                    messages.put(e.getKey() + "/" + e.getLanguage(), e.getValue());
                }
        );

        LOG.trace("Initialized: {}", this);
        LOG.trace("  {} messages loaded.", messages.size());
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    public MessageFormat resolveCode(final String key, final Locale locale) {
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