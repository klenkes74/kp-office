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

package de.kaiserpfalzEdv.office.clients.accounting.mock;

import de.kaiserpfalzEdv.office.accounting.tax.FiscalPeriod;
import de.kaiserpfalzEdv.office.accounting.tax.FiscalPeriodService;
import de.kaiserpfalzEdv.office.accounting.tax.FiscalYear;
import de.kaiserpfalzEdv.office.accounting.tax.impl.FiscalPeriodImpl;
import de.kaiserpfalzEdv.office.accounting.tax.impl.FiscalYearImpl;
import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.commons.client.mock.Store;
import de.kaiserpfalzEdv.office.commons.paging.PageableImpl;
import de.kaiserpfalzEdv.office.commons.paging.SortImpl;
import de.kaiserpfalzEdv.office.core.tenants.TenantService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.time.Month;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Mock;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 16.08.15 11:35
 */
@Named
@KPO(Mock)
public class FiscalPeriodMockClient implements FiscalPeriodService {
    private static final Logger LOG = LoggerFactory.getLogger(FiscalPeriodMockClient.class);


    private static final HashMap<FiscalYear, Store<FiscalPeriod, UUID>> fiscalPeriodStore = new HashMap<>();


    private TenantService tenantService;


    @Inject
    public FiscalPeriodMockClient(
            @KPO(Mock) final TenantService tenantService
    ) {
        LOG.trace("***** Created: {}", this);

        this.tenantService = tenantService;
        LOG.trace("*   *   tenant service: {}", this.tenantService);

        generatingMockData();

        LOG.debug("***** Initialized: {}", this);
    }

    private void generatingMockData() {
        UUID tenant = tenantService.listTenants(new PageableImpl(0, 1, new SortImpl("id"))).iterator().next().getId();

        FiscalYear year = new FiscalYearImpl(tenant, UUID.randomUUID(), 2015);
        year = save(year);

        for (int i = 0; i < FiscalYear.DEFAULT_MAX_PERIODS; i++) {
            FiscalPeriodImpl p = new FiscalPeriodImpl(UUID.randomUUID(), year, i);

            if (i >= 1 && i <= 12) {
                p.setMonth(Month.of(i));
            }

            p = (FiscalPeriodImpl) save(p);
        }
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this);
    }


    @Override
    public FiscalYear save(FiscalYear year) {
        if (!fiscalPeriodStore.containsKey(year))
            fiscalPeriodStore.put(year, new Store<>());

        return year;
    }

    @Override
    public Set<FiscalYear> findAllYears() {
        return fiscalPeriodStore.keySet();
    }

    @Override
    public FiscalYear findOneYear(UUID id) {
        for (FiscalYear year : fiscalPeriodStore.keySet()) {
            if (year.getId().equals(id))
                return year;
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public FiscalYear close(FiscalYear year) {
        LOG.info("Closing: {}", year);

        return year;
    }

    @Override
    public FiscalPeriod save(FiscalPeriod period) {
        return fiscalPeriodStore.get(period.getYear()).save(period);
    }

    @Override
    public Set<FiscalPeriod> findAllPeriods() {
        HashSet<FiscalPeriod> result = new HashSet<>();
        for (Store<FiscalPeriod, UUID> year : fiscalPeriodStore.values()) {
            result.addAll(year.findAll());
        }

        return result;
    }

    @Override
    public Set<FiscalPeriod> findAllPeriods(FiscalYear year) {
        return new HashSet<>(fiscalPeriodStore.get(year).findAll());
    }

    @Override
    public FiscalPeriod findOnePeriod(UUID id) {
        for (Store<FiscalPeriod, UUID> year : fiscalPeriodStore.values()) {
            FiscalPeriod result = year.findOne(id.toString());

            if (result != null)
                return result;
        }

        return null;
    }

    @Override
    public FiscalPeriod close(FiscalPeriod period) {
        LOG.info("Closing period: {}", period);

        return period;
    }
}
