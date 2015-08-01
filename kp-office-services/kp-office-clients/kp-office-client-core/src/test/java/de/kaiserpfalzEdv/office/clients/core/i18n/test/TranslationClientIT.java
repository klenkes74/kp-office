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

package de.kaiserpfalzEdv.office.clients.core.i18n.test;

import de.kaiserpfalzEdv.commons.test.SpringTestNGBase;
import de.kaiserpfalzEdv.office.core.i18n.TranslationEntry;
import de.kaiserpfalzEdv.office.core.i18n.TranslationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.Set;

import static org.testng.Assert.assertFalse;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 27.02.15 20:31
 */
@Test
@ContextConfiguration("/beans.xml")
public class TranslationClientIT extends SpringTestNGBase {
    private static final Logger LOG = LoggerFactory.getLogger(TranslationClientIT.class);

    @Inject
    private TranslationService service;


    public TranslationClientIT() {
        super(TranslationClientIT.class, LOG);
    }


    public void testTranslations() {
        logMethod("retrieve-translations", "Checks loading of all translations.");

        Set<TranslationEntry> result = service.getTranslationEntries();
        LOG.debug("Translations: {}", result);

        assertFalse(result.isEmpty(), "There should be translations in the result!");
    }


    @BeforeTest
    protected void checkService() {
//        checkNotNull(service, "Testable service for  '" + TranslationProvider.class.getSimpleName() + "' has not been injected!");
    }
}