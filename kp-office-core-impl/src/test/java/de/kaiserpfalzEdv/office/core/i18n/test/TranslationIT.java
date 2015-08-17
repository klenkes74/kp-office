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
import de.kaiserpfalzEdv.office.commons.KPO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.context.MessageSource;
import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.time.Duration;
import java.time.OffsetTime;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Locale;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Implementation;
import static org.testng.Assert.assertTrue;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 24.02.15 08:39
 */
@Test
@ContextConfiguration("/i18n-beans.xml")
public class TranslationIT extends SpringTestNGBase {
    private static final Logger LOG = LoggerFactory.getLogger(TranslationIT.class);

    @Inject
    @KPO(Implementation)
    private MessageSource messageSource;

    public TranslationIT() {
        super(TranslationIT.class, LOG);

        if (!SLF4JBridgeHandler.isInstalled()) {
            SLF4JBridgeHandler.install();
        }
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

        result.add(new Object[]{Locale.GERMANY, "office.core.application", "Kaiserpfalz Office"});
        result.add(new Object[]{Locale.UK, "office.core.application", "Kaiserpfalz Office"});
        result.add(new Object[]{Locale.US, "office.core.application", "Kaiserpfalz Office"});

        return result.iterator();
    }


    @Test(dataProvider = "message-locale-provider-timing")
    public void testCaching(final Locale locale, final String code) {
        logMethod("check-cache", "Testing the message cache for '" + code + "' in locale '" + locale.getDisplayName() + "'.");

        OffsetTime start = OffsetTime.now();
        messageSource.getMessage(code, new Object[]{}, locale);
        OffsetTime firstLoad = OffsetTime.now();

        messageSource.getMessage(code, new Object[]{}, locale);
        OffsetTime secondLoad = OffsetTime.now();

        Duration firstLoadDuration = Duration.between(start, firstLoad);
        Duration secondLoadDuration = Duration.between(firstLoad, secondLoad);

        LOG.debug("First load: {}", firstLoadDuration);
        LOG.debug("Second load: {}", secondLoadDuration);

        assertTrue(secondLoadDuration.toMillis() <= 10l, "The second request should take less than 10 milliseconds!");
    }

    @DataProvider(name = "message-locale-provider-timing")
    protected Iterator<Object[]> generateMessageTiming() {
        ArrayList<Object[]> result = new ArrayList<>();

        result.add(new Object[]{Locale.GERMANY, "office.core.application"});

        return result.iterator();
    }
}
