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

package de.kaiserpfalzEdv.piracc.organization.dsv.list;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.piracc.backend.db.dynamic.DataProtectionContract;
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
@Access({"dsb", "office", "board", "admin"})
@MenuEntry(
        name = DataProtectionContractListViewImpl.VIEW_NAME,
        i18nKey = DataProtectionContractListViewImpl.VIEW_NAME,
        access = {"dsb", "office", "board", "admin"},
        order = 54,
        separator = false
)
@SpringView(name = DataProtectionContractListViewImpl.VIEW_NAME)
public class DataProtectionContractListViewImpl extends BaseListViewImpl<DataProtectionContract, DataProtectionContractListView> implements DataProtectionContractListView, View {
    public static final  String VIEW_NAME        = "dsvs.list";
    private static final long   serialVersionUID = -6411954405002978841L;


    @Inject
    public DataProtectionContractListViewImpl(
            final DataProtectionContractListPresenter presenter,
            final DataContainerProvider dataContainerProvider
    ) {
        super(presenter, dataContainerProvider, DataProtectionContract.class);
    }


    @Override
    protected ArrayList<TableDescription> initializeFields() {
        ArrayList<TableDescription> result = new ArrayList<>(8);

        result.add(new TableDescription("id", 5).setI18nHeadingKey("base.id"));

        result.add(new TableDescription("identity.commonName", 25).setI18nHeadingKey("dsvs.identity.commonName"));
        result.add(new TableDescription("trainer.commonName", 25).setI18nHeadingKey("dsvs.trainer.commonName"));
        result.add(new TableDescription("from", 10).setI18nHeadingKey("base.from"));
        result.add(new TableDescription("till", 10).setI18nHeadingKey("base.till"));
        result.add(new TableDescription("locked", 5).setI18nHeadingKey("dsvs.locked"));

        result.add(new TableDescription("created", 10).setI18nHeadingKey("base.created"));
        result.add(new TableDescription("lastModified", 10).setI18nHeadingKey("base.lastModified"));

        return result;
    }
}
