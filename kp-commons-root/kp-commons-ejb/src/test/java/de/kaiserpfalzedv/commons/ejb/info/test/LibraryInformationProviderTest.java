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

package de.kaiserpfalzedv.commons.ejb.info.test;

import de.kaiserpfalzedv.commons.api.info.SoftwareLibrary;
import de.kaiserpfalzedv.commons.ejb.info.LibraryInformationProvider;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.validation.constraints.NotNull;
import java.util.Collection;

import static org.junit.Assert.assertTrue;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class LibraryInformationProviderTest {
    private static final Logger LOG = LoggerFactory.getLogger(LibraryInformationProviderTest.class);

    private LibraryInformationProvider cut;

    @BeforeClass
    public static void setUpMDC() {
        MDC.put("test-class", "library-information");
    }

    @AfterClass
    public static void tearDownMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    /**
     * This test is quite useless. But since we add or remove frameworks it would take too much work to keep this test
     * on the correct number of licenses. So we check basically that something comes back.
     */
    @Test
    public void checkIfThereAreAnyLibraries() {
        logMethod("take-5", "Check if there are at least 5 libraries used.");

        Collection<SoftwareLibrary> result = cut.libraries();
        LOG.trace("Result: {}", result);

        assertTrue(result.size() >= 5);
    }

    /**
     * Not really a test but I wanted some place where I list all libraries with their licenses so I can check from time
     * to time.
     * <p>
     * And all disclaimers and fulltexts are read once (not printed out) to check if the files are ok.
     */
    @Test
    public void listLibraries() {
        logMethod("list-libraries", "List all used libraries with their licenses");

        Collection<SoftwareLibrary> result = cut.libraries();

        result.forEach(l -> LOG.debug("License: library={}, license={}",
                l.getLibraryName(),
                l.getLicense().getTitle()));

        result.forEach(l -> {
            l.getLicense().getDisclaimer();
            l.getLicense().getFullText();
        });
    }

    private void logMethod(@NotNull final String shortName, @NotNull final String message, Object... data) {
        MDC.put("test", shortName);

        LOG.info(message, data);
    }

    @Before
    public void setUp() {
        cut = new LibraryInformationProvider();
    }

    @After
    public void tearDown() {
        cut = null;

        MDC.remove("test");
    }
}
