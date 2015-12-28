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

package de.kaiserpfalzedv.office.finance.impl;

import de.kaiserpfalzedv.office.finance.accounting.Period;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 28.12.15 07:03
 */
public class PeriodImpl implements Period {
    private static final long serialVersionUID = 622330645962397997L;


    private int year;
    private int period;


    public PeriodImpl(final int year, final int period) {
        this.year = year;
        this.period = period;
    }


    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getPeriod() {
        return period;
    }
}
