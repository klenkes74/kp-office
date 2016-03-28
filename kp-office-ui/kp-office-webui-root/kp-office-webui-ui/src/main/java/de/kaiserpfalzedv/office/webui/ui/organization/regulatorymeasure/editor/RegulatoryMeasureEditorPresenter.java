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

package de.kaiserpfalzEdv.piracc.organization.regulatorymeasure.editor;

import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.piracc.backend.db.master.RegulatoryMeasure;
import de.kaiserpfalzEdv.piracc.backend.organization.regulatorymeasure.RegulatoryMeasureService;
import de.kaiserpfalzEdv.piracc.organization.regulatorymeasure.list.RegulatoryMeasureListViewImpl;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.DataContainerProvider;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.impl.BaseEditorPresenterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.Objects;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 12.09.15 22:46
 */
@Named
@UIScope
public class RegulatoryMeasureEditorPresenter extends BaseEditorPresenterImpl<RegulatoryMeasure, RegulatoryMeasureEditorView> {
    private static final Logger LOG = LoggerFactory.getLogger(RegulatoryMeasureEditorPresenter.class);

    @Inject
    public RegulatoryMeasureEditorPresenter(
            final RegulatoryMeasureService service,
            final DataContainerProvider containerProvider,
            final EventBus bus
    ) {
        super(service, containerProvider, bus);
    }

    @Override
    public RegulatoryMeasure createNew() {
        return new RegulatoryMeasure();
    }

    @Override
    public void navigateToTarget(Object source) {
        setData(createNew());

        navigateTo(RegulatoryMeasureListViewImpl.VIEW_NAME);
    }


    @Subscribe
    public void loadData(LoadRegulatoryMeasureEvent event) {
        LOG.debug("Loading data: {}", event);

        load(event.getPayload());

        if (Objects.equals(getData().getId(), event.getPayload()))
            navigateTo(de.kaiserpfalzEdv.piracc.organization.regulatorymeasure.editor.RegulatoryMeasureEditorViewImpl.VIEW_NAME);
    }
}
