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

package de.kaiserpfalzedv.commons.api.data.test;

import de.kaiserpfalzedv.commons.api.data.ValidityDuration;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.OffsetTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.*;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-16
 */
public class ValidityDurationTest {
    private static final Logger LOG = LoggerFactory.getLogger(ValidityDurationTest.class);

    private static final ZoneId UTC = ZoneId.of("UTC");
    private static final OffsetDateTime DEFAULT_FROM = OffsetDateTime.parse("2017-01-01T00:00:00.000+00:00");
    private static final OffsetDateTime DEFAULT_TILL = LocalDate.now()
                                                                .plusDays(1)
                                                                .atTime(OffsetTime.parse("00:00+00:00"))
                                                                .minus(1L, ChronoUnit.NANOS);

    private ValidityDuration cut;

    @BeforeClass
    public static void setMDC() {
        MDC.put("test-class", "duration");
    }

    @AfterClass
    public static void removeMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void checkToString() {
        MDC.put("test", "to-string");
        LOG.debug("Checking string output of duration: {}", cut);

        String expected = new StringBuilder("ValidityDuration[")
                .append("start=").append(DEFAULT_FROM)
                .append(",end=").append(DEFAULT_TILL)
                .append("]")
                .toString();

        String result = cut.toString();

        assertEquals(expected, result);
    }

    @Test
    public void checkBefore() {
        OffsetDateTime other = DEFAULT_FROM.minusDays(1L);

        MDC.put("test", "date-before");
        LOG.debug("Checking an older date: date={}, range={}", other, cut);

        boolean result = cut.isValid(other);
        LOG.trace("Result: {}", result);

        assertFalse(result);
    }

    @Test
    public void checkBetween() {
        OffsetDateTime other = DEFAULT_TILL.minusDays(5L);

        MDC.put("test", "date-between");
        LOG.debug("Checking a valid date: date={}, range={}", other, cut);

        boolean result = cut.isValid(other);
        LOG.trace("Result: {}", result);

        assertTrue(result);
    }

    @Test
    public void checkAfter() {
        OffsetDateTime other = DEFAULT_TILL.plusDays(1L);

        MDC.put("test", "date-after");
        LOG.debug("Checking a newer date: date={}, range={}", other, cut);

        boolean result = cut.isValid(other);
        LOG.trace("Result: {}", result);

        assertFalse(result);
    }

    @Test
    public void checkLowerBoundary() {
        MDC.put("test", "lower-boundary");
        LOG.debug("Checking lower boundary: {}", DEFAULT_FROM);

        boolean result = cut.isValid(DEFAULT_FROM.toInstant());
        LOG.trace("Result: {}", result);

        assertTrue(result);
    }

    @Test
    public void checkUpperBoundary() {
        MDC.put("test", "upper-boundary");
        LOG.debug("Checking upper boundary: {}", DEFAULT_TILL);

        boolean result = cut.isValid(DEFAULT_TILL.toInstant());
        LOG.trace("Result: {}", result);

        assertTrue(result);
    }

    @Before
    public void setUp() {
        cut = new ValidityDuration(DEFAULT_FROM, DEFAULT_TILL);
    }

    @After
    public void tearDown() {
        MDC.remove("test");
    }
}
