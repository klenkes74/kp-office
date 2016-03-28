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

package de.kaiserpfalzEdv.vaadin.auth.user.list;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.piracc.backend.db.auth.User;
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
@Access({"admin"})
@MenuEntry(
        name = UserListViewImpl.VIEW_NAME,
        i18nKey = UserListViewImpl.VIEW_NAME,
        access = {"admin"},
        order = 90,
        separator = true
)
@SpringView(name = UserListViewImpl.VIEW_NAME)
public class UserListViewImpl extends BaseListViewImpl<User, UserListView> implements UserListView, View {
    public static final  String VIEW_NAME        = "users.list";
    private static final long   serialVersionUID = 2709953831331411802L;


    @Inject
    public UserListViewImpl(
            final UserListPresenter presenter,
            final DataContainerProvider dataContainerProvider
    ) {
        super(presenter, dataContainerProvider, User.class);
    }


    @Override
    protected ArrayList<TableDescription> initializeFields() {
        ArrayList<TableDescription> result = new ArrayList<>(8);

        result.add(new TableDescription("id", 5).setI18nHeadingKey("base.id"));
        result.add(new TableDescription("login", 10).setI18nHeadingKey("user.login"));
        result.add(new TableDescription("surName", 20).setI18nHeadingKey("user.surName"));
        result.add(new TableDescription("givenName", 20).setI18nHeadingKey("user.givenName"));
        result.add(new TableDescription("organizationCodeList", 20).setI18nHeadingKey("user.organizationCodeList"));
        result.add(new TableDescription("locked", 5).setI18nHeadingKey("base.locked"));
        result.add(new TableDescription("created", 10).setI18nHeadingKey("base.created"));
        result.add(new TableDescription("lastModified", 10).setI18nHeadingKey("base.lastModified"));

        return result;
    }
}