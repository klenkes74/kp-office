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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Locale;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Implementation;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 23.02.15 10:21
 */
@Named
@KPO(Implementation)
public class DatabaseMessageSource implements MessageSource {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseMessageSource.class);

    private TranslationEntryRepository repository;


    @Inject
    DatabaseMessageSource(
            final TranslationEntryRepository repository
    ) {
        this.repository = repository;

        LOG.trace("Created/Initialized: {}", this);
        LOG.trace("  message repository: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Cacheable("i18n")
    @Override
    public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
        String result;

        try {
            result = getMessage(code, args, locale);
        } catch (NoSuchMessageException e) {
            result = defaultMessage;
        }

        return result;
    }

    @Cacheable("i18n")
    @Override
    public String getMessage(String code, Object[] args, Locale locale) throws NoSuchMessageException {
        TranslationEntryImpl entry = null;

        if (isNotBlank(locale.getVariant())) {
            LOG.debug("Looking up in {}: {}", locale.getLanguage() + "_" + locale.getCountry() + "." + locale.getVariant(), code);
            entry = repository.findOne(
                    new TranslationKey(code, locale.getLanguage() + "_" + locale.getCountry() + "." + locale.getVariant())
            );
        }

        if (entry == null && isNotBlank(locale.getCountry())) {
            LOG.debug("Looking up in {}: {}", locale.getLanguage() + "_" + locale.getCountry(), code);
            entry = repository.findOne(
                    new TranslationKey(code, locale.getLanguage() + "_" + locale.getCountry())
            );
        }

        if (entry == null) {
            LOG.debug("Looking up in {}: {}", locale.getLanguage(), code);
            entry = repository.findOne(
                    new TranslationKey(code, locale.getLanguage())
            );
        }

        if (entry == null) {
            throw new NoSuchMessageException("No message for '" + code + "'.", locale);
        }

        return entry.getValue();
    }

    @Cacheable("i18n")
    @Override
    public String getMessage(MessageSourceResolvable resolvable, Locale locale) throws NoSuchMessageException {
        return getMessage(resolvable.getCodes()[0], resolvable.getArguments(), resolvable.getDefaultMessage(), locale);
    }
}
