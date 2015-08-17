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

import com.google.common.eventbus.Subscribe;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import de.kaiserpfalzEdv.office.clients.core.impl.TranslationClient;
import de.kaiserpfalzEdv.office.commons.KPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.text.MessageFormat;
import java.util.Locale;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Client;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 23.02.15 10:04
 */
@Named
@UIScope
public class MessageProvider implements Serializable { // implements org.vaadin.spring.i18n.MessageProvider {
    private static final Logger LOG = LoggerFactory.getLogger(MessageProvider.class);

    private TranslationClient client;

    private Locale locale;

    private EventBusHandler bus;


    @Inject
    public MessageProvider(@KPO(Client) final TranslationClient provider, final EventBusHandler bus, final Locale locale) {
        LOG.trace("***** Created: {}", this);

        this.client = provider;
        LOG.trace("*   *   internal messsage provider: {}", this.client);

        this.bus = bus;
        bus.register(this);
        LOG.trace("*   *   event bus: {}", this.bus);

        this.locale = locale;
        LOG.trace("*   *   locale: {}", this.locale);

        LOG.debug("***** Initialized: {}", this);
    }


    @PreDestroy
    public void close() {
        bus.unregister(this);
        LOG.trace("***** Destroyed: {}", this);
    }


    @Subscribe
    public void setLocale(LocaleChangeEvent event) {
        LOG.trace("Changing locale: {} -> {}", this.locale, event.getLocale());

        this.locale = event.getLocale();
    }


    //    @Override
    public MessageFormat resolveCode(String key, Locale locale) {
        return client.resolveCode(key, locale);
    }

    public MessageFormat resolveCode(String key) {
        return resolveCode(key, locale);
    }
}
