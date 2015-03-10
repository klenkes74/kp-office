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

import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.commons.SoftwareVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 08.02.15 22:58
 */
@Test
public class SoftwareVersionTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(SoftwareVersionTest.class);

    private SoftwareVersion service;

    public SoftwareVersionTest() {
        super(SoftwareVersionTest.class, LOG);
    }


    public void checkBaseFunctionality() {
        logMethod("software-base", "Checking basic versioning functionality.");

        assertEquals(service.getReleaseState(), Versionable.ReleaseState.release, "Wrong release state!");
        assertEquals(service.getVersion(), new int[]{1, 1, 1}, "Wrong version number!");
    }

    public void checkVersionWithTwoDigits() {
        logMethod("version-two-digit", "Checking software version with two digits.");

        service = new SoftwareVersion("1.1");

        assertEquals(service.getVersion(), new int[]{1, 1, 0}, "Wrong version number!");
    }

    public void checkVersionWithTwoDigitsAndReleaseState() {
        logMethod("version-two-digit-releaseState", "Checking software version with two digits and release state.");

        service = new SoftwareVersion("1.2-alpha");

        assertEquals(service.getVersion(), new int[]{1, 2, 0}, "Wrong version number!");
        assertEquals(service.getReleaseState(), Versionable.ReleaseState.alpha, "Wrong release state!");
    }

    public void checkVersionWithReleaseStateConstructor() {
        logMethod("version-with-state-constructor", "Checking the software version with state constructor.");

        service = new SoftwareVersion(Versionable.ReleaseState.releaseCandidate, 23, 42, 0);

        assertEquals(service.getBuildDescriptor(), "23.42.0-releaseCandidate");
    }


    @BeforeMethod
    protected void setService() {
        service = new SoftwareVersion("1.1.1-release");
    }
}
