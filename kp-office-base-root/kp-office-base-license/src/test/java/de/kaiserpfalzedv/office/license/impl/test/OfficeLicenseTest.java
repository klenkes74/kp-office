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

package de.kaiserpfalzedv.office.license.impl.test;

import java.time.Instant;
import java.util.UUID;

import de.kaiserpfalzedv.office.license.api.OfficeLicense;
import de.kaiserpfalzedv.office.license.impl.OfficeLicenseBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-30
 */
public class OfficeLicenseTest {
    private static final Logger LOG = LoggerFactory.getLogger(OfficeLicenseTest.class);

    private static final String LICENSE_FILE_NAME = "kpoffice.license";


    private OfficeLicense cut;

    @BeforeClass
    public static void setUpMDC() {
        MDC.put("test-class", "office-license");
    }

    @AfterClass
    public static void tearDownMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void licenseShouldWorkWithValidLicense() {
        logMethod("valid-license-file", "Check a valid license file");

        assertNotNull(cut);
    }

    private void logMethod(final String shortName, final String message, Object... data) {
        MDC.put("test", shortName);

        LOG.info(message, data);
    }

    @Test
    public void licenseIsInvalidIfDateIsBeforeNovember5th1974() {
        logMethod("to-early-used", "Check if the license is too early used");

        assertFalse(cut.isValidDuration(Instant.parse("1974-11-04T23:59:59.999Z")));
    }

    @Test
    public void licenseIsValidWhenUsedBetweenNovember5th1974AndNovember6th2024() {
        logMethod("valid-used", "Check if the license accepts all timecodes");

        assertTrue(cut.isValidDuration(Instant.parse("1974-11-05T00:00:00.000Z")));
        assertTrue(cut.isValidDuration(Instant.parse("2024-11-06T00:00:00.000Z")));
    }

    @Test
    public void licenseIsInvalidIfDateIsAfterNovember6th2024() {
        logMethod("to-late-used", "Check if the license is too early used");


        assertFalse(cut.isValidDuration(Instant.parse("2024-11-06T00:00:00.001Z")));
    }

    @Test
    public void checkIdOfLicense() {
        logMethod("check-id", "THe license has the correct id");

        assertEquals(UUID.fromString("b85905f3-0348-4e79-91c8-beba51183ea6"), cut.getId());
    }

    @Test
    public void checkIssuer() {
        logMethod("check-issuer", "The license needs to be issued by Kaiserpfalz EDV-Service");

        assertEquals("Kaiserpfalz EDV-Service", cut.getLicensor());
    }

    @Test
    public void checkLicensee() {
        logMethod("check-licensee", "The license needs to be issued to Roland T. Lichti");

        assertEquals("Roland T. Lichti", cut.getLicensee());
    }

    @Before
    public void setUp() {
        cut = new OfficeLicenseBuilder()
                .withLicenseFile(LICENSE_FILE_NAME)
                .build();
    }

    @After
    public void tearDown() {
        MDC.remove("test");
    }
}
