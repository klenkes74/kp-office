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
import de.kaiserpfalzEdv.office.core.i18n.TranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Set;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Implementation;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 18:59
 */
@Named
@KPO(Implementation)
public class TranslationServiceImpl implements TranslationService {
    private static final Logger LOG = LoggerFactory.getLogger(TranslationServiceImpl.class);


    private TranslationEntryRepository repository;


    @Inject
    public TranslationServiceImpl(final TranslationEntryRepository repository) {
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
