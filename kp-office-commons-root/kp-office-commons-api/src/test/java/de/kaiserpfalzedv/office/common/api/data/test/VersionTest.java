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

package de.kaiserpfalzedv.office.common.api.data.test;

import de.kaiserpfalzedv.office.common.api.data.Version;
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
public class VersionTest {
    private static final Logger LOG = LoggerFactory.getLogger(VersionTest.class);
    private static final Long[] DEFAULT_VERSION = new Long[]{1L, 0L, 0L};
    private static final String DEFAULT_VERSION_ADD = "beta";

    private Version cut;

    @BeforeClass
    public static void setMDC() {
        MDC.put("test-class", "version");
    }

    @AfterClass
    public static void removeMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void checkToString() {
        MDC.put("test", "to-string");
        LOG.debug("Checking string output of version: {} ({})", DEFAULT_VERSION, DEFAULT_VERSION_ADD);

        String result = cut.toString();
        LOG.trace("Result: {}", result);

        assertEquals("1.0.0-beta", result);
    }

    @Test
    public void checkOlderVersion() {
        Version other = new Version(1L, 0L, 0L, 0L, 1L);

        MDC.put("test", "older-version");
        LOG.debug("Checking older (in version): {}", other);

        boolean result = cut.isOlderAs(other);
        LOG.trace("Result: {}", result);

        assertTrue(result);
        assertFalse(cut.isNewerAs(other));
    }

    @Test
    public void checkOlderVersionAdd() {
        Version other = new Version("gamma", 1L, 0L, 0L);

        MDC.put("test", "older-version-add");
        LOG.debug("Checking older (in version add): {}", other);

        boolean result = cut.isOlderAs(other);
        LOG.trace("Result: {}", result);

        assertTrue(result);
        assertFalse(cut.isNewerAs(other));
    }

    @Test
    public void checkNewerVersion() {
        Version other = new Version(0L, 9L);

        MDC.put("test", "newer-version");
        LOG.debug("Checking newer (in version): {}", other);

        boolean result = cut.isNewerAs(other);
        LOG.trace("Result: {}", result);

        assertTrue(result);
        assertFalse(cut.isOlderAs(other));
    }

    @Test
    public void checkNewerVersionAdd() {
        Version other = new Version("alpha", 1L, 0L, 0L);

        MDC.put("test", "newer-version-add");
        LOG.debug("Checking newer (in version): {}", other);

        boolean result = cut.isNewerAs(other);
        LOG.trace("Result: {}", result);

        assertTrue(result);
        assertFalse(cut.isOlderAs(other));
    }

    @Before
    public void setUp() {
        cut = new Version(DEFAULT_VERSION_ADD, DEFAULT_VERSION);
    }

    @After
    public void tearDown() {
        MDC.remove("test");
    }
}
