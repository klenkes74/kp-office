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

package de.kaiserpfalzEdv.office.accounting.primaNota.impl;

import de.kaiserpfalzEdv.office.accounting.primaNota.FiscalYear;
import de.kaiserpfalzEdv.office.commons.server.data.KPOEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 09.08.15 00:04
 */
public class FiscalYearImpl extends KPOEntity implements FiscalYear {
    private static final Logger LOG = LoggerFactory.getLogger(FiscalYearImpl.class);


    private LocalDate startDate;

    private int firstPeriod;
    private int maxPeriods;


    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(@NotNull LocalDate startDate) {
        this.startDate = startDate;
    }


    @Override
    public int getFirstPeriod() {
        return firstPeriod;
    }

    public void setFirstPeriod(@NotNull int firstPeriod) {
        this.firstPeriod = firstPeriod;
    }

    @Override
    public int getMaxPeriods() {
        return maxPeriods;
    }

    public void setMaxPeriods(@NotNull int maxPeriods) {
        this.maxPeriods = maxPeriods;
    }
}
