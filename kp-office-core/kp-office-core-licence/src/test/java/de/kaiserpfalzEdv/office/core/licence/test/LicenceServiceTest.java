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

package de.kaiserpfalzEdv.office.core.licence.test;

import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.commons.SoftwareVersion;
import de.kaiserpfalzEdv.office.commons.SoftwareVersionRange;
import de.kaiserpfalzEdv.office.core.licence.LicencingException;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import de.kaiserpfalzEdv.office.core.licence.impl.LicenceServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

import static org.testng.Assert.assertTrue;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 08.02.15 22:58
 */
@Test
public class LicenceServiceTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(LicenceServiceTest.class);

    private LicenceServiceImpl service;

    public LicenceServiceTest() {
        super(LicenceServiceTest.class, LOG);
    }


    public void checkBaseFunctionality() throws LicencingException {
        logMethod("licence-basics", "Checking basic licensing functionality.");
        service.init();

        OfficeLicence license = service.getLicence();

        SoftwareVersionRange range = new SoftwareVersionRange(new SoftwareVersion(0, 0, 0), new SoftwareVersion(999, 999, 999));
        assertTrue(license.isValid("KP Office", range), "The licence is invalid!");


        LOG.debug(
                "Licensee valid: {} - {}",
                license.getStart().format(DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY)),
                license.getExpiry().format(DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY))
        );
    }


    @BeforeMethod
    protected void setService() {
        service = new LicenceServiceImpl("./target/test-classes/kpoffice.lic");
    }
}
