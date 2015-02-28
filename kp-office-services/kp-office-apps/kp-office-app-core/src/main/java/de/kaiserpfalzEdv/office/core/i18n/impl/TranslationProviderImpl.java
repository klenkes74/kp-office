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
import de.kaiserpfalzEdv.office.core.i18n.TranslationEntry;
import de.kaiserpfalzEdv.office.core.i18n.TranslationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Set;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Application;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 27.02.15 16:19
 */
@Named
@KPO(Application)
public class TranslationProviderImpl implements TranslationProvider {
    private static final Logger LOG = LoggerFactory.getLogger(TranslationProviderImpl.class);


    private TranslationEntryRepository repository;


    @Inject
    public TranslationProviderImpl(final TranslationEntryRepository repository) {
        this.repository = repository;

        LOG.trace("Created/Initialized: {}", this);
        LOG.trace("  translation repository: {}", this.repository);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Override
    public Set<TranslationEntry> getTranslationEntries() {
        HashSet<TranslationEntry> result = new HashSet<>();

        Iterable<TranslationEntryImpl> translations = repository.findAll();

        translations.forEach(result::add);
        return result;
    }
}
