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

package de.kaiserpfalzEdv.office.ui.core.i18n;

import de.kaiserpfalzEdv.office.core.i18n.impl.SpringMessageProviderBridge;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.text.MessageFormat;
import java.util.Locale;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 23.02.15 10:04
 */
@Named
public class MessageProvider implements org.vaadin.spring.i18n.MessageProvider {
    private static final Logger LOG = LoggerFactory.getLogger(MessageProvider.class);

    private SpringMessageProviderBridge provider;

    @Inject
    public MessageProvider(@NotNull SpringMessageProviderBridge provider) {
        this.provider = provider;

        LOG.trace("Created/Initialized: {}", this);
        LOG.trace("  internal messsage provider: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Override
    public MessageFormat resolveCode(String s, Locale locale) {
        return provider.resolveCode(s, locale);
    }
}
