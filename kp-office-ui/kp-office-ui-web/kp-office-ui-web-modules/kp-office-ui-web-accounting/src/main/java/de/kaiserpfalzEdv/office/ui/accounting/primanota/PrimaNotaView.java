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

package de.kaiserpfalzEdv.office.ui.accounting.primanota;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItem;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.ErrorHandler;
import com.vaadin.server.FontAwesome;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Grid;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.Notification;
import de.kaiserpfalzEdv.office.accounting.primaNota.PrimaNotaEntry;
import de.kaiserpfalzEdv.office.clients.accounting.primanota.PrimaNotaContent;
import de.kaiserpfalzEdv.office.clients.accounting.primanota.PrimaNotaContentPresenter;
import de.kaiserpfalzEdv.office.commons.client.mvp.Presenter;
import de.kaiserpfalzEdv.office.ui.web.api.menu.MenuEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addons.lazyquerycontainer.LazyQueryContainer;
import org.vaadin.addons.lazyquerycontainer.Query;
import org.vaadin.addons.lazyquerycontainer.QueryDefinition;
import org.vaadin.addons.lazyquerycontainer.QueryFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

import static com.vaadin.ui.Notification.Type.ERROR_MESSAGE;

/**
 * Displays a prima nota as vaadin view.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 16:10
 */
@UIScope
@SpringView(name = PrimaNotaView.NAME)
public class PrimaNotaView extends GridLayout implements PrimaNotaContent, MenuEntry, View, QueryFactory {
    static final         String NAME = "accounting.primanota";
    private static final Logger LOG  = LoggerFactory.getLogger(PrimaNotaView.class);


    private PrimaNotaContentPresenter presenter;
    private ErrorHandler              errorHandler;


    @Inject
    public PrimaNotaView(
            final ErrorHandler errorHandler
    ) {
        super(5, 10);

        LOG.trace("***** Created: {}", this);

        setWidth(100f, Unit.PERCENTAGE);
        LOG.trace("*   *   with: 100%");

        for (int i = 0; i < 10; i++) {
            setColumnExpandRatio(i, 10.0f);
        }
        LOG.trace("*   *   column expand ration: 10.0f for all 10 columns");

        setHeight(100f, Unit.PERCENTAGE);
        LOG.trace("*   *   height: 100%");

        for (int i = 0; i < 10; i++) {
            setRowExpandRatio(i, 10.0f);
        }
        LOG.trace("*   *   row expand ratio: 10.0f for all 10 rows");

        this.errorHandler = errorHandler;
        setErrorHandler(errorHandler);
        LOG.trace("*   *   error handler: {}", this.errorHandler);

        setCaption(NAME);
        setIcon(FontAwesome.BANK);

        LOG.debug("***** Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        removeAllComponents();

        LOG.trace("***** Destroyed: {}", this);
    }

    @Override
    public void setPresenter(Presenter<?> presenter) {
        this.presenter = (PrimaNotaContentPresenter) presenter;
    }


    @Override
    public void addEntry(PrimaNotaEntry entry) {
        LOG.error("No prima nota displayed. Can't add entry to non-existing prima nota: {}", entry);

        Notification.show("Can't add a new entry to a non-existing prima nota!", ERROR_MESSAGE);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        // TODO 2015-08-16 klenkes Add a selection method for the prima nota.
        presenter.selectPrimaNota(presenter.loadAvailablePrimaNota().iterator().next());

        LazyQueryContainer data = new LazyQueryContainer(this, "id", 50, false);

        Grid primaNotaList = new Grid(data);
        primaNotaList.setErrorHandler(errorHandler);
        primaNotaList.setEditorEnabled(false);
        primaNotaList.setHeaderVisible(true);
        primaNotaList.setFooterVisible(true);
        addComponent(primaNotaList, 1, 2, 10, 7);
    }

    @Override
    public Query constructQuery(QueryDefinition queryDefinition) {
        return new QueryImpl();
    }

    @Override
    public String getViewName() {
        return NAME;
    }

    @Override
    public int getSortOrder() {
        return MenuEntry.HIGH;
    }


    private class QueryImpl implements Query {
        @Override
        public Item constructItem() {
            throw new UnsupportedOperationException("Kein Anlegen erlaubt");
        }

        @Override
        public boolean deleteAllItems() {
            throw new UnsupportedOperationException("Kein Löschen erlaubt");
        }

        @Override
        public List<Item> loadItems(int startIndex, int count) {
            List<PrimaNotaEntry> entries = presenter.load(startIndex, count);
            List<Item> resList = new ArrayList<>(entries.size());

            entries.forEach(e -> resList.add(convert(e)));

            return resList;
        }

        private Item convert(PrimaNotaEntry entry) {
            BeanItem<PrimaNotaEntry> result = new BeanItem<>(entry);

            return result;
        }

        @Override
        public void saveItems(List<Item> arg0, List<Item> arg1, List<Item> arg2) {
            throw new UnsupportedOperationException("Kein Speichern erlaubt");
        }

        @Override
        public int size() {
            return (int) presenter.size();
        }
    }
}
