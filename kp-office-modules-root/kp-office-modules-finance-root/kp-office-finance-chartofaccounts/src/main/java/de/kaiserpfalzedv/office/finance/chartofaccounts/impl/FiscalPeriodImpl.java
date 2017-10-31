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

package de.kaiserpfalzedv.office.finance.chartofaccounts.impl;

import de.kaiserpfalzedv.office.finance.chartofaccounts.api.FiscalPeriod;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * Default implementation of the fiscal period needed.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 0.3.0
 * @since 2015-12-27
 */
public class FiscalPeriodImpl implements FiscalPeriod {
    private static final long serialVersionUID = -2538417471925345103L;


    private int year;
    private int period;


    public FiscalPeriodImpl(final int year, final int period) {
        this.year = year;
        this.period = period;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(year)
                .append(period)
                .toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        FiscalPeriod rhs = (FiscalPeriod) obj;
        return new EqualsBuilder()
                .append(this.getYear(), rhs.getYear())
                .append(this.getPeriod(), rhs.getPeriod())
                .isEquals();
    }

    @Override
    public int getYear() {
        return year;
    }

    @Override
    public int getPeriod() {
        return period;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("year", year)
                .append("period", period)
                .toString();
    }
}
