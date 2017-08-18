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

package de.kaiserpfalzedv.office.license.api.test;

import de.kaiserpfalzedv.office.license.api.Version;
import de.kaiserpfalzedv.office.license.api.VersionRange;
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
import static org.junit.Assert.assertTrue;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-16
 */
public class VersionRangeTest {
    private static final Logger LOG = LoggerFactory.getLogger(VersionRangeTest.class);

    private static final Version DEFAULT_FROM = new Version(2L, 0L, 1L);
    private static final Version DEFAULT_TILL = new Version(8L, 0L, 0L);

    private VersionRange cut;

    @BeforeClass
    public static void setMDC() {
        MDC.put("test-class", "version-range");
    }

    @AfterClass
    public static void removeMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void checkToString() {
        MDC.put("test", "to-string");
        LOG.debug("Checking string output of version range: {}", cut);

        String result = cut.toString();

        assertEquals("VersionRange[2.0.1,8.0.0]", result);
    }

    @Test
    public void checkOlderVersion() {
        Version other = new Version(1L, 0L, 0L, 0L, 1L);

        MDC.put("test", "older-version");
        LOG.debug("Checking older: {}", other);

        boolean result = cut.isValid(other);
        LOG.trace("Result: {}", result);

        assertFalse(result);
    }

    @Test
    public void checkVersionInRange() {
        Version other = new Version(6L, 5L, 3L);

        MDC.put("test", "version-in-range");
        LOG.debug("Checking version in range: version={}, range={}", other, cut);

        boolean result = cut.isValid(other);
        LOG.trace("Result: {}", result);

        assertTrue(result);
    }

    @Test
    public void checkNewerVersion() {
        Version other = new Version(10L, 5L, 3L);

        MDC.put("test", "newer-version");
        LOG.debug("Checking newer (in version): {}", other);

        boolean result = cut.isValid(other);
        LOG.trace("Result: {}", result);

        assertFalse(result);
    }

    @Test
    public void checkLowerBoundary() {
        Version other = new Version(DEFAULT_FROM);

        MDC.put("test", "lower-boundary");
        LOG.debug("Checking lower boundary: {}", other);

        boolean result = cut.isValid(other);
        LOG.trace("Result: {}", result);

        assertTrue(result);
    }

    @Test
    public void checkUpperBoundary() {
        Version other = new Version(DEFAULT_TILL);

        MDC.put("test", "upper-boundary");
        LOG.debug("Checking upper boundary: {}", other);

        boolean result = cut.isValid(other);
        LOG.trace("Result: {}", result);

        assertFalse(result);
    }

    @Before
    public void setUp() {
        cut = new VersionRange(DEFAULT_FROM, DEFAULT_TILL);
    }

    @After
    public void tearDown() {
        MDC.remove("test");
    }
}
