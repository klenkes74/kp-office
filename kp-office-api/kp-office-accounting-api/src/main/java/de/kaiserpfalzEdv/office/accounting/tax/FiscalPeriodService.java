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

import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 16.08.15 11:17
 */
public interface FiscalPeriodService {
    FiscalYear save(FiscalYear year);

    Set<FiscalYear> findAllYears();

    FiscalYear findOneYear(UUID id);

    FiscalYear close(FiscalYear year);

    FiscalPeriod save(FiscalPeriod period);

    Set<FiscalPeriod> findAllPeriods();

    Set<FiscalPeriod> findAllPeriods(FiscalYear year);

    FiscalPeriod findOnePeriod(UUID id);

    FiscalPeriod close(FiscalPeriod period);
}
