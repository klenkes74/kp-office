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

import de.kaiserpfalzEdv.commons.jee.paging.Page;
import de.kaiserpfalzEdv.commons.jee.paging.Pageable;
import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl.AccountBuilder;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNota;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaEntry;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaPage;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaService;
import de.kaiserpfalzEdv.office.accounting.primaNota.impl.PrimaNotaEntryBuilder;
import de.kaiserpfalzEdv.office.accounting.primaNota.impl.PrimaNotaImpl;
import de.kaiserpfalzEdv.office.accounting.primaNota.impl.PrimaNotaPageImpl;
import de.kaiserpfalzEdv.office.accounting.tax.FiscalPeriod;
import de.kaiserpfalzEdv.office.accounting.tax.FiscalPeriodService;
import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.commons.client.mock.Store;
import de.kaiserpfalzEdv.office.core.tenants.TenantService;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Mock;

/**
 * A mock client for primanotae. To "fake it until I make it" this mock is needed for creating the GUI.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 16.08.15 08:35
 */
@Named
@KPO(Mock)
public class PrimaNotaMockClient implements PrimaNotaService {
    private static final Logger LOG = LoggerFactory.getLogger(PrimaNotaMockClient.class);

    private static final int NUM_ENTRIES = 1000;


    private static final HashMap<PrimaNota, Store<PrimaNotaEntry, UUID>> primaNotaEntryStore = new HashMap<>();


    private FiscalPeriodService periodService;
    private TenantService       tenantService;


    @Inject
    public PrimaNotaMockClient(
            @KPO(Mock) FiscalPeriodService fiscalPeriodService,
            @KPO(Mock) TenantService tenantService
    ) {
        LOG.trace("***** Created: {}", this);

        this.periodService = fiscalPeriodService;
        LOG.trace("*   *   fiscal period service: {}", this.periodService);

        this.tenantService = tenantService;
        LOG.trace("*   *   tenant service: {}", this.tenantService);


        generateMockData();

        LOG.debug("***** Initialized: {}", this);
    }

    private void generateMockData() {
        for (FiscalPeriod period : periodService.findAllPeriods()) {
            generatePrimaNotaForPeriod(period, 1);

            if (period.getPeriod() == 0)
                generatePrimaNotaForPeriod(period, 2);
        }
    }


    private void generatePrimaNotaForPeriod(FiscalPeriod period, int primaNotaNumber) {
        PrimaNota primaNota = new PrimaNotaImpl(
                UUID.randomUUID(), "Primanota #" + primaNotaNumber + " " + period.getDisplayName(), "" + period.getTenantId()
                                                                                                               .toString() + "-" + period
                .getPeriod(), period
        );
        primaNota = save(primaNota);
        LOG.trace("*   *   Created prima nota: {}", primaNota);


        for (int i = 1; i <= NUM_ENTRIES; i++) {
            PrimaNotaEntry entry = generateEntry(primaNota, i);
            addEntry(primaNota, entry);
        }

        LOG.debug("Generated a prima nota with {} entries: {}", NUM_ENTRIES, primaNota);
    }

    private PrimaNotaEntry generateEntry(PrimaNota primaNota, int i) {
        PrimaNotaEntryBuilder result = new PrimaNotaEntryBuilder()
                .withPrimaNota(primaNota)
                .withAmount(Money.of(1000, "EUR"))
                .withAccountCreditted(
                        new AccountBuilder().withName("Test-Account 1")
                                            .withTenantId(primaNota.getPeriod().getTenantId())
                                            .withNumber("1000")
                                            .build()
                )
                .withAccountDebitted(
                        new AccountBuilder().withName("Test-Account 2")
                                            .withTenantId(primaNota.getPeriod().getTenantId())
                                            .withNumber("2000")
                                            .build()
                )
                .withAccountingDate(LocalDate.now())
                .withNotice1("Testbuchung #" + i);

        if (primaNota.getPeriod().getMonth() != null)
            result.withAccountingDate(
                    primaNota.getPeriod()
                             .getYear()
                             .getStartDate()
                             .plusMonths(primaNota.getPeriod().getMonth().getValue())
            );
        else if (primaNota.getPeriod().getPeriod() > 12)
            result.withAccountingDate(primaNota.getPeriod().getYear().getStartDate().withMonth(12).withDayOfMonth(31));
        else if (primaNota.getPeriod().getPeriod() == 0)
            result.withAccountingDate(primaNota.getPeriod().getYear().getStartDate().withMonth(1).withDayOfMonth(1));


        try {
            PrimaNotaEntry returnValue = result.build();
            if (i == 1) LOG.debug("Generated: {}", returnValue);
            else LOG.trace("Generated: {}", returnValue);

            return returnValue;
        } catch (BuilderException e) {
            LOG.error("Can't build prima nota entry: " + e.getMessage(), e);
            LOG.info("Failures: {}", e.getFailures());
            throw e;
        }
    }


    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this);
    }


    @Override
    public Set<PrimaNota> listAllPrimaNota() {
        return Collections.unmodifiableSet(primaNotaEntryStore.keySet());
    }

    @Override
    public PrimaNotaPage loadPrimaNota(PrimaNota info, Pageable pageRequest) {
        checkValidPrimaNota(info);

        Page<PrimaNotaEntry> data = primaNotaEntryStore.get(info).findAll(pageRequest);

        return new PrimaNotaPageImpl(info, pageRequest, data.getTotalElements(), data.getContent());
    }

    @Override
    public PrimaNota save(PrimaNota created) {
        if (primaNotaEntryStore.containsKey(created))
            throw new IllegalStateException();

        primaNotaEntryStore.put(created, new Store<>());

        return created;
    }

    @Override
    public void addEntry(@NotNull PrimaNota primaNota, @NotNull PrimaNotaEntry entry) {
        checkValidPrimaNota(primaNota);

        primaNotaEntryStore.get(primaNota).save(entry);
    }

    private void checkValidPrimaNota(@NotNull PrimaNota primaNota) {
        if (!primaNotaEntryStore.containsKey(primaNota))
            throw new IllegalStateException();
    }

    @Override
    public void removeEntry(@NotNull PrimaNota primaNota, @NotNull PrimaNotaEntry entry) {
        checkValidPrimaNota(primaNota);

        primaNotaEntryStore.get(primaNota).delete(entry);
    }

    @Override
    public void closePrimaNota(PrimaNota info) {
        LOG.info("Prima nota would be closed for further bookings ...");
    }
}
