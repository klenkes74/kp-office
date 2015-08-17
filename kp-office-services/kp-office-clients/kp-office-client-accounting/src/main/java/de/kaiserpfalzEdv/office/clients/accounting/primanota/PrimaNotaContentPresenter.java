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

package de.kaiserpfalzEdv.office.clients.accounting.primanota;

import de.kaiserpfalzEdv.commons.jee.paging.Pageable;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNota;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaEntry;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaPage;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaService;
import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.commons.client.mvp.Presenter;
import de.kaiserpfalzEdv.office.commons.paging.PageableImpl;
import de.kaiserpfalzEdv.office.commons.paging.SortImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Collection;
import java.util.List;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Mock;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 16.08.15 21:33
 */
@Named
public class PrimaNotaContentPresenter extends Presenter<PrimaNotaContent> {
    private static final Logger LOG = LoggerFactory.getLogger(PrimaNotaContentPresenter.class);

    private PrimaNotaService primaNotaService;

    private PrimaNota     primaNota;
    private PrimaNotaPage currentPage;


    @Inject
    public PrimaNotaContentPresenter(
            @KPO(Mock) final PrimaNotaService primaNotaService
    ) {
        LOG.trace("***** Created: {}", this);

        this.primaNotaService = primaNotaService;
        LOG.trace("*   *   prima nota service: {}", this.primaNotaService);

        LOG.debug("***** Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this);
    }


    @Override
    public void setView(PrimaNotaContent view) {
        LOG.debug("Setting view: {}", view);

        super.setView(view);

        LOG.trace("Setting prima nota list to view: {}", view);
    }


    public Collection<PrimaNota> loadAvailablePrimaNota() {
        return primaNotaService.listAllPrimaNota();
    }

    public void selectPrimaNota(PrimaNota primaNota) {
        if (this.primaNota != null && this.primaNota.equals(primaNota)) {
            LOG.warn("Tried to set the currently selected prima nota: {}", primaNota);
            return;
        }

        this.primaNota = primaNota;
        this.currentPage = null;

        LOG.debug("Switched to prima nota: {}", this.primaNota);
    }

    public List<PrimaNotaEntry> load(long offset, int size) {
        checkPrimaNotaLoaded();

        if (currentPage == null) {
            loadPage(offset, size);
        }

        return currentPage.getContent();
    }

    private void loadPage(long offset, int size) {
        checkPrimaNotaLoaded();

        currentPage = primaNotaService.loadPrimaNota(primaNota, generatePageDefinition(offset, size));
        LOG.trace("Loaded prima nota page: {} of {}", currentPage.getNumber(), currentPage.getTotalPages());
    }

    private Pageable generatePageDefinition(long offset, int size) {
        return new PageableImpl((int) offset, size, new SortImpl("entryDate"));
    }

    public long size() {
        checkPrimaNotaLoaded();

        if (currentPage == null) {
            loadPage(0, 50);
        }

        return currentPage.getTotalElements();
    }

    private void checkPrimaNotaLoaded() {
        if (primaNota == null)
            throw new IllegalStateException("No prima nota loaded!");
    }

    public void addEntry(PrimaNotaEntry entry) {
        primaNotaService.addEntry(primaNota, entry);

        getView().addEntry(entry);
    }
}
