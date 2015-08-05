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
import de.kaiserpfalzEdv.office.core.i18n.TranslationCommandExecutor;
import de.kaiserpfalzEdv.office.core.i18n.TranslationService;
import de.kaiserpfalzEdv.office.core.i18n.commands.RequestTranslationsCommand;
import de.kaiserpfalzEdv.office.core.i18n.notifications.TranslationsNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Implementation;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 05.08.15 08:02
 */
@Named
public class TranslationApplication implements TranslationCommandExecutor {
    private static final Logger LOG = LoggerFactory.getLogger(TranslationApplication.class);

    private TranslationService service;


    @Inject
    public TranslationApplication(@KPO(Implementation) final TranslationService service) {
        LOG.trace("***** Created: {}", this);

        this.service = service;
        LOG.trace("*   *   i18n service: {}", this.service);

        LOG.debug("***** Created: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this);
    }


    @Override
    public TranslationsNotification execute(RequestTranslationsCommand command) {
        LOG.info("Working on: {}", command);

        return new TranslationsNotification(service.getTranslationEntries());
    }
}
