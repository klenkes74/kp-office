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

package de.kaiserpfalzEdv.office.accounting.tax;

import de.kaiserpfalzEdv.office.commons.data.TenantHoldingEntity;

import java.time.LocalDate;

/**
 * The fiscal year with its definitions. The default year starts at January 1st of the given year and has 15 periods
 * (12 monthly periods, the pre-year-period 0 and two closing periods 13 and 14.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 09.08.15 00:01
 */
public interface FiscalYear extends TenantHoldingEntity, Comparable<FiscalYear> {
    /**
     * The default length of a fiscal year is 12 months.
     */
    int DEFAULT_LENGTH      = 12;
    /**
     * The default start period is 0 for a pre-year period.
     */
    int DEFAULT_MIN_PERIOD  = 0;
    /**
     * The default number of periods (a pre-year period 0, the periods 1-12 for the year, two periods for the closing
     * bookings).
     */

    int DEFAULT_MAX_PERIODS = DEFAULT_LENGTH + 3;

    LocalDate getStartDate();

    int getLength();

    LocalDate getEndDate();

    int getFirstPeriod();

    int getMaxPeriod();

    int getMaxPeriods();
}
