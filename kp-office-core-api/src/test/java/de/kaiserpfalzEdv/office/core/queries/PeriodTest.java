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

package de.kaiserpfalzEdv.office.core.queries;

import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 08.08.15 22:57
 */
@Test
public class PeriodTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(PeriodTest.class);

    private static final Integer         DEFAULT_START  = 10;
    private static final Integer         DEFAULT_LAST   = 19;
    private static final Period<Integer> DEFAULT_PERIOD = new Period<>(DEFAULT_START, DEFAULT_LAST);

    public PeriodTest() {
        super(PeriodTest.class, LOG);
    }


    public void checkSelfContainment() {
        logMethod("self-containment", "Checks if the period is within itself ...");

        assertTrue(DEFAULT_PERIOD.isWithin(DEFAULT_PERIOD), "The period should be contained within itself!");
    }


    public void checkStartElement() {
        logMethod("start-element", "Checks if the start element is contained ...");

        assertTrue(DEFAULT_PERIOD.isWithin(DEFAULT_START), "The start of the period should be included in the period!");
    }

    public void checkLastElement() {
        logMethod("last-element", "Checks if the last element is contained ...");

        assertTrue(DEFAULT_PERIOD.isWithin(DEFAULT_LAST), "The start of the period should be included in the period!");
    }


    public void checkWithinOverlappingStart() {
        logMethod("overlapping-start-within", "Checks if the overlapping period is accepted as within ...");

        Period<Integer> check = new Period<>(DEFAULT_START - 10, DEFAULT_START + 1);

        assertFalse(DEFAULT_PERIOD.isWithin(check), "The period is overlapping with start and not within!");
    }

    public void checkWithinOverlappingLast() {
        logMethod("overlapping-last-within", "Checks if the overlapping period is accepted as within ...");

        Period<Integer> check = new Period<>(DEFAULT_LAST - 1, DEFAULT_LAST + 10);

        assertFalse(DEFAULT_PERIOD.isWithin(check), "The period is overlapping with start and not within!");
    }


    public void checkOverlapStartFirstElement() {
        logMethod("overlap-start-first", "Checks if the period overlaps the start of the other period ...");

        Period<Integer> check = new Period<>(DEFAULT_START - 10, DEFAULT_START);

        assertTrue(DEFAULT_PERIOD.overlapStart(check), "The check period should overlap the start of default period!");
    }

    public void checkOverlapStartSecondElement() {
        logMethod("overlap-start-second", "Checks if the period overlaps the start of the other period ...");

        Period<Integer> check = new Period<>(DEFAULT_START - 10, DEFAULT_START + 1);

        assertTrue(DEFAULT_PERIOD.overlapStart(check), "The check period should overlap the start of default period!");
    }

    public void checkOverlapStartSecondLastElement() {
        logMethod("overlap-start-secondlast", "Checks if the period overlaps the start of the other period ...");

        Period<Integer> check = new Period<>(DEFAULT_START - 10, DEFAULT_LAST - 1);

        assertTrue(DEFAULT_PERIOD.overlapStart(check), "The check period should overlap the start of default period!");
    }

    public void checkOverlapStartLastElement() {
        logMethod("overlap-start-last", "Checks if the period overlaps the start of the other period ...");

        Period<Integer> check = new Period<>(DEFAULT_START - 10, DEFAULT_LAST);

        assertTrue(DEFAULT_PERIOD.overlapStart(check), "The check period should overlap the start of default period!");
    }

    public void checkOverlapStartBeforeFirstElement() {
        logMethod("overlap-start-before-first", "Checks if the period overlaps the start of the other period ...");

        Period<Integer> check = new Period<>(DEFAULT_START - 1, DEFAULT_START + 1);

        assertTrue(DEFAULT_PERIOD.overlapStart(check), "The check period should overlap the start of default period!");
    }

    public void checkNoOverlapStartBefore() {
        logMethod("no-overlap-start-before-first", "No overlap of these periods (before) ...");

        Period<Integer> check = new Period<>(DEFAULT_START - 10, DEFAULT_START - 1);

        assertFalse(DEFAULT_PERIOD.overlapStart(check), "The check period should overlap the start of default period!");
    }

    public void checkNoOverlapStartAfter() {
        logMethod("no-overlap-start-after-last", "No overlap of these periods (after) ...");

        Period<Integer> check = new Period<>(DEFAULT_LAST + 1, DEFAULT_LAST + 10);

        assertFalse(DEFAULT_PERIOD.overlapStart(check), "The check period should overlap the start of default period!");
    }


    public void checkOverlapLastFirstElement() {
        logMethod("overlap-last-first", "Checks if the period overlaps the start of the other period ...");

        Period<Integer> check = new Period<>(DEFAULT_LAST, DEFAULT_LAST + 10);

        assertTrue(DEFAULT_PERIOD.overlapLast(check), "The check period should overlap the start of default period!");
    }

    public void checkOverlapLastSecondElement() {
        logMethod("overlap-last-second", "Checks if the period overlaps the start of the other period ...");

        Period<Integer> check = new Period<>(DEFAULT_LAST - 1, DEFAULT_LAST + 10);

        assertTrue(DEFAULT_PERIOD.overlapLast(check), "The check period should overlap the start of default period!");
    }

    public void checkOverlapLastSecondLastElement() {
        logMethod("overlap-last-secondlast", "Checks if the period overlaps the start of the other period ...");

        Period<Integer> check = new Period<>(DEFAULT_START + 1, DEFAULT_LAST + 10);

        assertTrue(DEFAULT_PERIOD.overlapLast(check), "The check period should overlap the start of default period!");
    }

    public void checkOverlapLastLastElement() {
        logMethod("overlap-last-last", "Checks if the period overlaps the start of the other period ...");

        Period<Integer> check = new Period<>(DEFAULT_START, DEFAULT_LAST + 10);

        assertTrue(DEFAULT_PERIOD.overlapLast(check), "The check period should overlap the start of default period!");
    }

    public void checkOverlapLastBeforeFirstElement() {
        logMethod("overlap-last-before-first", "Checks if the period overlaps the start of the other period ...");

        Period<Integer> check = new Period<>(DEFAULT_LAST - 1, DEFAULT_LAST + 1);

        assertTrue(DEFAULT_PERIOD.overlapLast(check), "The check period should overlap the start of default period!");
    }

    public void checkNoOverlapLastBefore() {
        logMethod("no-overlap-last-before-first", "No overlap of these periods (before) ...");

        Period<Integer> check = new Period<>(DEFAULT_START - 10, DEFAULT_START - 1);

        assertFalse(DEFAULT_PERIOD.overlapLast(check), "The check period should overlap the start of default period!");
    }

    public void checkNoOverlapLastAfter() {
        logMethod("no-overlap-last-after-last", "No overlap of these periods (after) ...");

        Period<Integer> check = new Period<>(DEFAULT_LAST + 1, DEFAULT_LAST + 10);

        assertFalse(DEFAULT_PERIOD.overlapLast(check), "The check period should overlap the start of default period!");
    }
}
