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

package de.kaiserpfalzEdv.piracc.organization.regulatorymeasure.list;

import com.google.common.eventbus.EventBus;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.piracc.backend.db.master.RegulatoryMeasure;
import de.kaiserpfalzEdv.piracc.organization.regulatorymeasure.editor.LoadRegulatoryMeasureEvent;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.list.impl.BaseListPresenterImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 12.09.15 22:48
 */
@Named
@UIScope
public class RegulatoryMeasureListPresenter extends BaseListPresenterImpl<RegulatoryMeasure, RegulatoryMeasureListView> {
    private static final Logger LOG = LoggerFactory.getLogger(RegulatoryMeasureListPresenter.class);


    @Inject
    public RegulatoryMeasureListPresenter(
            final EventBus bus
    ) {
        super(bus);
    }


    @Override
    public void startEditor(Long id) {
        bus.post(new LoadRegulatoryMeasureEvent(this, id));
    }
}