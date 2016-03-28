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

package de.kaiserpfalzEdv.vaadin.auth.role.list;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.piracc.backend.db.auth.Role;
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
 * @since 09.09.15 21:22
 */
@Named
@UIScope
@Access({"admin", "dsb", "board", "office"})
@MenuEntry(
        name = RoleListViewImpl.VIEW_NAME,
        i18nKey = RoleListViewImpl.VIEW_NAME,
        access = {"admin", "dsb", "board", "office"},
        order = 94,
        separator = false
)
@SpringView(name = RoleListViewImpl.VIEW_NAME)
public class RoleListViewImpl extends BaseListViewImpl<Role, RoleListView> implements RoleListView, View {
    public static final  String VIEW_NAME        = "roles.list";
    private static final long   serialVersionUID = 6324454584670203743L;


    @Inject
    public RoleListViewImpl(
            final RoleListPresenter presenter,
            final DataContainerProvider dataContainerProvider
    ) {
        super(presenter, dataContainerProvider, Role.class);

    }


    @Override
    protected ArrayList<TableDescription> initializeFields() {
        ArrayList<TableDescription> result = new ArrayList<>(5);

        result.add(new TableDescription("id", 5).setI18nHeadingKey("base.id"));

        result.add(new TableDescription("name", 25).setI18nHeadingKey("role.name"));
        result.add(new TableDescription("commonName", 50).setI18nHeadingKey("role.commonName"));

        result.add(new TableDescription("created", 10).setI18nHeadingKey("base.created"));
        result.add(new TableDescription("lastModified", 10).setI18nHeadingKey("base.lastModified"));

        return result;
    }
}