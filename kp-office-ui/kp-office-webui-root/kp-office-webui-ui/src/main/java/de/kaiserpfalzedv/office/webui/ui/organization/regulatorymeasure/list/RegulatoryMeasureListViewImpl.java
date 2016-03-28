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

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.piracc.backend.db.master.RegulatoryMeasure;
import de.kaiserpfalzEdv.vaadin.auth.Access;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.DataContainerProvider;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.list.TableDescription;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.list.impl.BaseListViewImpl;
import de.kaiserpfalzEdv.vaadin.ui.menu.MenuEntry;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.ArrayList;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 13.09.15 22:37
 */
@Named
@UIScope
@Access({"board", "dsb", "office"})
@MenuEntry(
        name = RegulatoryMeasureListViewImpl.VIEW_NAME,
        i18nKey = RegulatoryMeasureListViewImpl.VIEW_NAME,
        access = {"board", "dsb", "office"},
        order = 56,
        separator = false
)
@SpringView(name = RegulatoryMeasureListViewImpl.VIEW_NAME)
public class RegulatoryMeasureListViewImpl extends BaseListViewImpl<RegulatoryMeasure, RegulatoryMeasureListView> implements RegulatoryMeasureListView, View {
    public static final  String VIEW_NAME        = "regulatorymeasures.list";
    private static final long   serialVersionUID = 8040761630058637274L;


    @Inject
    public RegulatoryMeasureListViewImpl(
            final RegulatoryMeasureListPresenter presenter,
            final DataContainerProvider dataContainerProvider
    ) {
        super(presenter, dataContainerProvider, RegulatoryMeasure.class);
    }


    @Override
    protected ArrayList<TableDescription> initializeFields() {
        ArrayList<TableDescription> result = new ArrayList<>(4);

        result.add(new TableDescription("id", 5).setI18nHeadingKey("base.id"));

        result.add(new TableDescription("identity.commonName", 25).setI18nHeadingKey("identity.commonName"));
        result.add(new TableDescription("organization.commonName", 25).setI18nHeadingKey("organizations.commonName"));

        result.add(new TableDescription("from", 10).setI18nHeadingKey("base.from"));
        result.add(new TableDescription("till", 10).setI18nHeadingKey("base.till"));

        result.add(new TableDescription("locked", 5).setI18nHeadingKey("base.locked"));
        result.add(new TableDescription("created", 10).setI18nHeadingKey("base.created"));
        result.add(new TableDescription("lastModified", 10).setI18nHeadingKey("base.lastModified"));

        return result;
    }
}
