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

package de.kaiserpfalzedv.office.finance.chartofaccounts.api;

import java.io.Serializable;

/**
 * The fiscal period needed on a lot of book keeping events. In book keeping the fiscal year may not match the calendar
 * year and contain more than 12 periods. Most of times a period matches a calendar month plus an additional 13. or even
 * 14. period for book keeping uses. But we don't limit it to that. You may have up to
 * {@value java.lang.Integer#MAX_VALUE} periods in this system.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 0.3.0
 * @since 2015-12-27
 */
public interface FiscalPeriod extends Serializable {
    /**
     * @return The fiscal year this period belongs to.
     */
    int getYear();

    /**
     * @return The period within the fiscal year.
     */
    int getPeriod();
}