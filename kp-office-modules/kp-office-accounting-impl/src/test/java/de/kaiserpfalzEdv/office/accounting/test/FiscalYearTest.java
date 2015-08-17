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

package de.kaiserpfalzEdv.office.accounting.test;

import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.accounting.tax.FiscalYear;
import de.kaiserpfalzEdv.office.accounting.tax.server.FiscalYearImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.time.LocalDate;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 09.08.15 07:27
 */
@Test
public class FiscalYearTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(FiscalYearTest.class);

    private static final LocalDate DEFAULT_START_DATE   = LocalDate.of(2015, 7, 1);
    private static final int       DEFAULT_YEAR_LENGTH  = 6;
    private static final int       DEFAULT_START_PERIOD = 0;
    private static final int       DEFAULT_MAX_PERIODS  = 16;

    public FiscalYearTest() {
        super(FiscalYearTest.class, LOG);
    }


    public void checkBasicYear() {
        logMethod("basic-year", "Checks the creation of a basic fiscal year ...");

        FiscalYear subject = new FiscalYearImpl(DEFAULT_START_DATE, DEFAULT_YEAR_LENGTH, DEFAULT_START_PERIOD, DEFAULT_MAX_PERIODS);
        LOG.trace("Created a fiscal year from {} to {}.", subject.getStartDate(), subject.getEndDate());

        assertEquals(subject.getMaxPeriod(), DEFAULT_START_PERIOD + DEFAULT_MAX_PERIODS - 1);
        assertEquals(subject.getLength(), DEFAULT_YEAR_LENGTH);
    }


    public void checkDefaultYear() {
        logMethod("default-year", "Checks the creation of a default fiscal year ...");

        FiscalYear subject = new FiscalYearImpl(DEFAULT_START_DATE.getYear());
        LOG.trace("Created a fiscal year from {} to {}.", subject.getStartDate(), subject.getEndDate());

        assertEquals(subject.getMaxPeriod(), FiscalYear.DEFAULT_MIN_PERIOD + FiscalYear.DEFAULT_MAX_PERIODS - 1);
    }


    public void checkCompareTo() {
        logMethod("compare-to", "Checks the compare to of a fiscal year ...");

        FiscalYear subject = new FiscalYearImpl(DEFAULT_START_DATE.getYear());
        LOG.trace("Created a fiscal year from {} to {}.", subject.getStartDate(), subject.getEndDate());

        FiscalYear other = new FiscalYearImpl(DEFAULT_START_DATE.getYear() + 1);
        LOG.trace("Created a fiscal year from {} to {}.", other.getStartDate(), other.getEndDate());

        assertTrue(subject.compareTo(subject) == 0);
        assertTrue(subject.compareTo(other) < 0);
        assertTrue(other.compareTo(subject) > 0);
    }
}
