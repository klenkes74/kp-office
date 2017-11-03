/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.commons.impl.info.test;

import de.kaiserpfalzedv.commons.api.config.ManifestReader;
import de.kaiserpfalzedv.commons.impl.info.ManifestReaderImpl;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-31
 */
public class ManifestReaderTest {
    private static final Logger LOG = LoggerFactory.getLogger(ManifestReaderTest.class);

    private ManifestReader cut;

    @BeforeClass
    public static void setUpMDC() {
        MDC.put("test-class", "manifest-reader");
    }

    @AfterClass
    public static void tearDownMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void implementationVersionIs1_0_0_SNAPSHOT() {
        logMethod("normal-line", "Check a normal line");

        Optional<String> result = cut.read("KPO-Version");

        assertTrue(result.isPresent());
        assertEquals("1.0.0-SNAPSHOT", result.get());
    }

    private void logMethod(final String shortName, final String message, Object... data) {
        MDC.put("test", shortName);

        LOG.info(message, data);
    }

    @Test
    public void shouldJoinLinesWhenFollowingLineStartWithSpaces() {
        logMethod("joined-line", "Check handling of joined lines");

        Optional<String> result = cut.read("KPO-URL");

        assertTrue(result.isPresent());
        assertEquals("https://devzone.kaiserpfalz-edv.de/kp-office/kp-office/kp-office-base-root/kp-office-base-info/", result
                .get());
    }

    @Before
    public void setUp() {
        cut = new ManifestReaderImpl();
    }

    @After
    public void tearDown() {
        cut = null;

        MDC.remove("test");
    }
}
