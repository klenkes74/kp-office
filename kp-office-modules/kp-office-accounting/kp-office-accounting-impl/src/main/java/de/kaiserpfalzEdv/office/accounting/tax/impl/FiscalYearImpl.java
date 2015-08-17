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

import de.kaiserpfalzEdv.office.accounting.tax.FiscalYear;
import de.kaiserpfalzEdv.office.commons.server.data.KPOTenantHoldingEntity;
import org.apache.commons.lang3.builder.CompareToBuilder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * The fiscal year with its definitions. The default year starts at January 1st of the given year and has 15 periods
 * (12 monthly periods, the pre-year-period 0 and two closing periods 13 and 14.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 09.08.15 00:04
 */
@Entity
@Table(
        schema = "accounting",
        catalog = "accounting",
        name = "fiscalyears",
        uniqueConstraints = {
                @UniqueConstraint(name = "fiscalyear_name_uk", columnNames = {"tenant_", "display_name_"}),
                @UniqueConstraint(name = "fiscalyear_number_uk", columnNames = {"tenant_", "display_number_"}),

                @UniqueConstraint(name = "fiscalyear_start_uk", columnNames = {"tenant_", "start_"})
        }
)
public class FiscalYearImpl extends KPOTenantHoldingEntity implements FiscalYear {
    private static final long serialVersionUID = -7187623118873898316L;


    @Column(name = "start_")
    private LocalDate startDate;

    @Column(name = "length_")
    private int length = 0;

    @Column(name = "first_period_", nullable = false)
    private int firstPeriod = 0;

    @Column(name = "max_periods_", nullable = false)
    private int maxPeriods = Integer.MAX_VALUE;


    /**
     * @deprecated Only for brain-dead-interfaces like JPA, JAX-B and/or Jackson.
     */
    @Deprecated
    public FiscalYearImpl() {}


    public FiscalYearImpl(@NotNull final int year) {
        this(LocalDate.of(year, 1, 1), FiscalYear.DEFAULT_LENGTH, FiscalYear.DEFAULT_MIN_PERIOD, FiscalYear.DEFAULT_MAX_PERIODS);
    }


    public FiscalYearImpl(
            @NotNull final LocalDate start,
            @NotNull final int length,
            @NotNull final int firstPeriod,
            @NotNull final int maxPeriods
    ) {
        setStartDate(start);
        setLength(length);
        setFirstPeriod(firstPeriod);
        setMaxPeriods(maxPeriods);
    }


    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull LocalDate startDate) {
        this.startDate = LocalDate.of(startDate.getYear(), startDate.getMonth(), 1);

        if (!this.startDate.equals(startDate))
            throw new IllegalArgumentException("The fiscal year always starts on day 1 of a month.");
    }


    @Override
    public LocalDate getEndDate() {
        return startDate.plusMonths(length).minusDays(1);
    }


    @Override
    public int getLength() {
        return length;
    }

    public void setLength(@NotNull final int length) {
        checkArgument(length <= maxPeriods - 3);
        this.length = length;
    }


    @Override
    public int getFirstPeriod() {
        return firstPeriod;
    }

    public void setFirstPeriod(@NotNull int firstPeriod) {
        this.firstPeriod = firstPeriod;
    }

    @Override
    public int getMaxPeriod() {
        return firstPeriod + maxPeriods - 1;
    }

    @Override
    public int getMaxPeriods() {
        return maxPeriods;
    }

    public void setMaxPeriods(@NotNull int maxPeriods) {
        checkArgument(
                maxPeriods >= length + 3, "With a fiscal year of "
                        + length + " months you need at least"
                        + (length + 3) + " periods in your fiscal year."
        );

        this.maxPeriods = maxPeriods;
    }

    @Override
    public int compareTo(@NotNull FiscalYear o) {
        return new CompareToBuilder()
                .append(startDate, o.getStartDate())
                .append(maxPeriods, o.getMaxPeriods())
                .build();
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }

        if (!FiscalYear.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        FiscalYear rhs = (FiscalYear) obj;
        return new EqualsBuilder()
                .appendSuper(super.equals(obj))
                .append(this.startDate, rhs.getStartDate())
                .append(this.length, rhs.getLength())
                .append(this.firstPeriod, rhs.getFirstPeriod())
                .append(this.maxPeriods, rhs.getMaxPeriods())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .appendSuper(super.hashCode())
                .append(startDate)
                .append(length)
                .append(firstPeriod)
                .append(maxPeriods)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("start", startDate.format(DateTimeFormatter.ofPattern("YYYY-mm")))
                .append("length", length)
                .append("firstPeriod", firstPeriod)
                .append("maxPeriods", maxPeriods)
                .toString();
    }
}
