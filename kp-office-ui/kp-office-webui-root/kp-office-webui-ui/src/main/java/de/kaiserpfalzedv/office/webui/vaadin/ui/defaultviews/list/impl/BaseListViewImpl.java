/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.vaadin.ui.defaultviews.list.impl;

import com.vaadin.addon.jpacontainer.JPAContainer;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CustomComponent;
import com.vaadin.ui.Grid;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.vaadin.backend.db.BaseEntity;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.DataContainerProvider;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.list.BaseListView;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.list.TableDescription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import static com.vaadin.server.Sizeable.Unit.PERCENTAGE;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 12.09.15 22:53
 */
public abstract class BaseListViewImpl<T extends BaseEntity, V extends BaseListView<T>> extends CustomComponent implements BaseListView<T>, View {
    private static final Logger LOG              = LoggerFactory.getLogger(BaseListViewImpl.class);
    private static final long   serialVersionUID = 414229543281278013L;

    private BaseListPresenterImpl<T, V> presenter;
    private DataContainerProvider       dataContainerProvider;

    private VerticalLayout  layout;
    private Grid            grid;
    private JPAContainer<T> data;

    private Class<T>                    clasz;
    private ArrayList<TableDescription> fields;


    public BaseListViewImpl(
            final BaseListPresenterImpl<T, V> presenter,
            final DataContainerProvider dataContainerProvider,
            final Class<T> clasz
    ) {
        this.presenter = presenter;
        this.presenter.setView((V) this);

        this.dataContainerProvider = dataContainerProvider;
        this.clasz = clasz;

        setWidth(100f, PERCENTAGE);
        setHeight(97f, PERCENTAGE);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.debug("Opening: {}", this);

        if (fields == null) {
            fields = initializeFields();
        }

        if (layout == null) {
            initializeLayout();
        }

        if (grid == null) {
            initializeData();
        } else {
            refresh();
        }
    }

    abstract protected ArrayList<TableDescription> initializeFields();

    private void initializeLayout() {
        LOG.trace("Initializing layout: {}", this);

        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setMargin(true);
        layout.setSpacing(true);
        layout.setResponsive(true);

        setCompositionRoot(layout);
    }

    private void initializeData() {
        LOG.trace("Initializing data: {}", this);

        data = dataContainerProvider.getReadOnlyContainer(clasz);

        grid = new Grid(data);
        grid.setSizeFull();
        grid.setFrozenColumnCount(1);

        setGridColumns();

        grid.setSelectionMode(Grid.SelectionMode.SINGLE);
        grid.addSelectionListener(
                event -> {
                    if (!event.getSelected().isEmpty())
                        presenter.startEditor((Long) event.getSelected().iterator().next());
                }
        );

        if (layout != null)
            layout.addComponent(grid);
    }

    private void setGridColumns() {
        if (fields == null) {
            LOG.error("No fields defined. Table {} will be empty!", clasz.getSimpleName());

            presenter.error(
                    "error.developer-failure.caption",
                    "error.data.list.generation-failure.description",
                    clasz.getSimpleName()
            );

            return;
        }

        String[] columnsToDisplay = new String[fields.size()];

        int i = 0;
        for (TableDescription f : fields) {
            if (f.getFieldName().contains(".")) {
                try {
                    data.addNestedContainerProperty(f.getFieldName());

                    LOG.trace("Added nested property to table: {} + '{}'", clasz.getSimpleName(), f.getFieldName());

                    grid.addColumn(f.getFieldName());
                } catch (IllegalStateException e) {
                    LOG.error("Can't find property '{}' for table '{}'.", f.getFieldName(), clasz.getSimpleName());


                    presenter.error(
                            "error.developer-failure.caption",
                            "error.data.list.field-generation-failure.description",
                            clasz.getSimpleName(), f.getFieldName()
                    );

                    continue; // next field ...
                }
            }

            try {
                grid.getColumn(f.getFieldName()).setHeaderCaption(presenter.translate(f.getI18nHeadingKey()));
                grid.getColumn(f.getFieldName()).setExpandRatio(f.getExpandRatio());

                columnsToDisplay[i] = f.getFieldName();
                i++;
            } catch (NullPointerException e) {
                LOG.error("Can't find field '{}' for table '{}'.", f.getFieldName(), clasz.getSimpleName());

                presenter.error(
                        "error.developer-failure.caption",
                        "error.data.list.field-generation-failure.description",
                        clasz.getSimpleName(), f.getFieldName()
                );
            }
        }

        grid.setColumns(columnsToDisplay);
    }


    @Override
    public void refresh() {
        LOG.debug("Refreshing data display: {}", this);

        if (data != null)
            data.refresh();

        if (grid != null) { // Hack until Vaadin Bug is fixed.
            grid.setComponentError(null);

            try {
                grid.setSelectionMode(Grid.SelectionMode.NONE);
            } catch (NoSuchElementException e) {
                // FIXME 2015-09-19 klenkes74: Check the exception and find out how to deselect after throwing the event.
            }

            grid.setSelectionMode(Grid.SelectionMode.SINGLE);

            grid.setCellStyleGenerator(grid.getCellStyleGenerator());
        }
    }
}
