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

package de.kaiserpfalzEdv.office.core.i18n.test;

import de.kaiserpfalzEdv.commons.test.SpringTestNGBase;
import de.kaiserpfalzEdv.office.core.i18n.impl.DatabaseMessageSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 24.02.15 08:39
 */
@Test
@ContextConfiguration("/i18n-beans.xml")
public class I18NTest extends SpringTestNGBase {
    private static final Logger LOG = LoggerFactory.getLogger(I18NTest.class);

    @Inject
    private DatabaseMessageSource messageSource;

    public I18NTest() {
        super(I18NTest.class, LOG);
    }

    @Test(dataProvider = "message-locale-provider")
    public void testMessage(final Locale locale, final String code, final String message) {
        logMethod("check-message", "Testing the message for '" + code + "' in locale '" + locale.getDisplayName() + "'.");

        String result = messageSource.getMessage(code, new Object[]{}, locale);

        Assert.assertEquals(result, message, "Wrong message!");
    }


    @DataProvider(name = "message-locale-provider")
    protected Iterator<Object[]> generateMessageLocales() {
        ArrayList<Object[]> result = new ArrayList<>();

        result.add(new Object[]{Locale.GERMANY, "office.test", "Testeintrag für Integrationstests"});
        result.add(new Object[]{Locale.UK, "office.test", "Test entry for integration tests"});
        result.add(new Object[]{Locale.US, "office.test", "Test entry for integration tests"});

        return result.iterator();
    }
}
