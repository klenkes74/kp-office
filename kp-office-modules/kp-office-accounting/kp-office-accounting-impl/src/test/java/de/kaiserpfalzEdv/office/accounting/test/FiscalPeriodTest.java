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
import de.kaiserpfalzEdv.office.accounting.FiscalPeriod;
import de.kaiserpfalzEdv.office.accounting.FiscalPeriodImpl;
import de.kaiserpfalzEdv.office.accounting.FiscalYear;
import de.kaiserpfalzEdv.office.accounting.FiscalYearImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 08.08.15 23:44
 */
@Test
public class FiscalPeriodTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(FiscalPeriodTest.class);

    private static final FiscalYear YEAR1 = new FiscalYearImpl(2015);
    private static final FiscalYear YEAR2 = new FiscalYearImpl(2016);

    public FiscalPeriodTest() {
        super(FiscalPeriodTest.class, LOG);
    }


    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "The period has to be at least '0' since the fiscal year starts with that period!")
    public void checkNegativePeriod() {
        logMethod("negative-period", "Checks that no period before 0 is allowed!");

        new FiscalPeriodImpl(YEAR1, -1);
    }

    @Test(expectedExceptions = IllegalArgumentException.class, expectedExceptionsMessageRegExp = "The Period has to be less or equal than '14' since the fiscal year ends with that period!")
    public void checkTooBigPeriod() {
        logMethod("too-big-period", "Checks that no period after 14 is allowed!");

        new FiscalPeriodImpl(YEAR1, 15);
    }

    public void checkZeroPeriod() {
        logMethod("zero-period", "Checks that period 0 is allowed!");

        new FiscalPeriodImpl(YEAR1, 0);
    }


    public void checkCompareYear() {
        logMethod("compare-year", "Checks the comparator for years ...");

        FiscalPeriod p1 = new FiscalPeriodImpl(YEAR1, 0);
        FiscalPeriod p2 = new FiscalPeriodImpl(YEAR2, 0);

        assertTrue(p2.compareTo(p1) > 0);
        assertTrue(p1.compareTo(p2) < 0);
        assertTrue(p1.compareTo(p1) == 0);
    }


    public void checkComparePeriod() {
        logMethod("compare-period", "Checks the comparator for periods ...");

        FiscalPeriod p1 = new FiscalPeriodImpl(YEAR1, 0);
        FiscalPeriod p2 = new FiscalPeriodImpl(YEAR1, 1);

        assertTrue(p2.compareTo(p1) > 0);
        assertTrue(p1.compareTo(p2) < 0);
        assertTrue(p1.compareTo(p1) == 0);
    }


    public void checkCompareYearAndPeriod() {
        logMethod("compare-yearAndPeriod", "Checks the comparator for years and periods ...");

        FiscalPeriod p1 = new FiscalPeriodImpl(YEAR1, 1);
        FiscalPeriod p2 = new FiscalPeriodImpl(YEAR2, 0);

        assertTrue(p2.compareTo(p1) > 0);
        assertTrue(p1.compareTo(p2) < 0);
        assertTrue(p1.compareTo(p1) == 0);
    }
}
