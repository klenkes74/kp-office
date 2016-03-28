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

package de.kaiserpfalzEdv.piracc.organization.organization.list;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.piracc.backend.db.master.Organization;
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
@Access({"user", "board", "dsb", "office", "admin"})
@MenuEntry(
        name = OrganizationListViewImpl.VIEW_NAME,
        i18nKey = OrganizationListViewImpl.VIEW_NAME,
        access = {"user", "board", "dsb", "office", "admin"},
        order = 40,
        separator = true
)
@SpringView(name = OrganizationListViewImpl.VIEW_NAME)
public class OrganizationListViewImpl extends BaseListViewImpl<Organization, OrganizationListView> implements OrganizationListView, View {
    public static final  String VIEW_NAME        = "organizations.list";
    private static final long   serialVersionUID = -8053845298397528661L;


    @Inject
    public OrganizationListViewImpl(
            final OrganizationListPresenter presenter,
            final DataContainerProvider dataContainerProvider
    ) {
        super(presenter, dataContainerProvider, Organization.class);
    }


    @Override
    protected ArrayList<TableDescription> initializeFields() {
        ArrayList<TableDescription> result = new ArrayList<>(11);

        result.add(new TableDescription("id", 5).setI18nHeadingKey("base.id"));
        result.add(new TableDescription("code", 5).setI18nHeadingKey("organizations.code"));
        result.add(new TableDescription("commonName", 40).setI18nHeadingKey("organizations.commonName"));
        result.add(new TableDescription("country", 5).setI18nHeadingKey("organizations.country"));
        result.add(new TableDescription("lv", 5).setI18nHeadingKey("organizations.lv"));
        result.add(new TableDescription("bv", 5).setI18nHeadingKey("organizations.bv"));
        result.add(new TableDescription("rv", 5).setI18nHeadingKey("organizations.rv"));
        result.add(new TableDescription("kv", 5).setI18nHeadingKey("organizations.kv"));
        result.add(new TableDescription("ov", 5).setI18nHeadingKey("organizations.ov"));
        result.add(new TableDescription("created", 10).setI18nHeadingKey("base.created"));
        result.add(new TableDescription("lastModified", 10).setI18nHeadingKey("base.lastModified"));

        return result;
    }
}
