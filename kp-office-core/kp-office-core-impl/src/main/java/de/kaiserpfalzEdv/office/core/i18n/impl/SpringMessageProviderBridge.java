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

import de.kaiserpfalzEdv.office.core.KPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.MessageSource;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.Locale;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 23.02.15 10:03
 */
@Named
public class SpringMessageProviderBridge implements de.kaiserpfalzEdv.office.core.i18n.MessageProvider {
    private static final Logger LOG = LoggerFactory.getLogger(SpringMessageProviderBridge.class);

    private final MessageSource messages;


    @Inject
    public SpringMessageProviderBridge(@KPO final MessageSource messages) {
        this.messages = messages;

        LOG.trace("Created/Initialized: {}", this);
        LOG.trace("  message source: {}", this.messages);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Override
    public MessageFormat resolveCode(String s, Locale locale) {
        final String message = messages.getMessage(s, new Object[]{}, s, locale);
        return getMessageFormat(message, locale);
    }


    private MessageFormat getMessageFormat(@NotNull final String message, @NotNull final Locale locale) {
        return new MessageFormat(message, locale);
    }
}