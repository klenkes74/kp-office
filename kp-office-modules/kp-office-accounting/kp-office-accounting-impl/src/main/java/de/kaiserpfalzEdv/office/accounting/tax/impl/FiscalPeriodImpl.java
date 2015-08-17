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

package de.kaiserpfalzEdv.office.accounting.tax.impl;

import de.kaiserpfalzEdv.office.accounting.tax.FiscalPeriod;
import de.kaiserpfalzEdv.office.accounting.tax.FiscalYear;
import de.kaiserpfalzEdv.office.commons.server.data.KPOTenantHoldingEntity;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.Month;

import static com.google.common.base.Preconditions.checkArgument;
import static javax.persistence.EnumType.STRING;

/**
 * The fiscal period used within the accounting system. The year and a positive integer as period within that year.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 08.08.15 23:30
 */
@Embeddable
public class FiscalPeriodImpl extends KPOTenantHoldingEntity implements FiscalPeriod, Serializable {
    private static final long serialVersionUID = 2785169539922383221L;

    /**
     * The fiscal year.
     */
    @ManyToOne(fetch = FetchType.EAGER, optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "fiscal_year_", nullable = false)
    private FiscalYearImpl year;

    @Enumerated(STRING)
    @Column(name = "fiscal_month_")
    private Month month;

    /**
     * The fiscal period.
     */
    @Column(name = "fiscal_period_", nullable = false)
    private int period;


    /**
     * @param year   The fiscal year.
     * @param period The fiscal period within the fiscal year.
     */
    public FiscalPeriodImpl(@NotNull final FiscalYear year, @NotNull final int period) {
        setYear(year);
        setPeriod(period);
    }

    /**
     * @deprecated Only for brain-dead interfaces like JAX-B, Jackson, JPA, ...
     */
    @Deprecated
    public FiscalPeriodImpl() {}


    @Override
    public FiscalYear getYear() {
        return year;
    }

    public void setYear(@NotNull final FiscalYear year) {
        this.year = (FiscalYearImpl) year;
    }


    @Override
    public Month getMonth() {
        return month;
    }

    public void setMonth(final Month month) {
        this.month = month;
    }


    @Override
    public int getPeriod() {
        return period;
    }

    public void setPeriod(@NotNull final int period) {
        checkArgument(
                period >= year.getFirstPeriod(), "The period has to be at least '"
                        + year.getFirstPeriod() + "' since the fiscal year starts with that period!"
        );
        checkArgument(
                period <= year.getMaxPeriod(),
                "The Period has to be less or equal than '" + year.getMaxPeriod()
                        + "' since the fiscal year ends with that period!"
        );

        this.period = period;
    }


    @Override
    public int compareTo(@NotNull FiscalPeriod o) {
        int result = year.compareTo(o.getYear());

        if (result == 0) {
            result = period - o.getPeriod();
        }

        return result;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!FiscalPeriod.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        FiscalPeriod rhs = (FiscalPeriod) obj;
        return new EqualsBuilder()
                .append(this.year, rhs.getYear())
                .append(this.period, rhs.getPeriod())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(year)
                .append(period)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("year", year)
                .append("month", month)
                .append("period", period)
                .toString();
    }
}