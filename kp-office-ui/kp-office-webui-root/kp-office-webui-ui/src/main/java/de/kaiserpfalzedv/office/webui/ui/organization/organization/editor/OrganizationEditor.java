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

package de.kaiserpfalzEdv.piracc.organization.organization.editor;

import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.piracc.backend.db.master.Identity;
import de.kaiserpfalzEdv.piracc.backend.db.master.Organization;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.impl.BaseEditorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.09.15 08:09
 */
public class OrganizationEditor extends BaseEditorImpl<Organization, OrganizationEditorView> {
    public static final  int    EDITOR_FIELDS    = 9;
    private static final long   serialVersionUID = 4936300645409696315L;
    private static final Logger LOG              = LoggerFactory.getLogger(de.kaiserpfalzEdv.piracc.tech.account.editor.AccountEditor.class);
    private VerticalLayout layout;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private TextField code, commonName, country, lv, bv, rv, kv, ov;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ComboBox dsb;


    public OrganizationEditor(OrganizationEditorPresenter presenter) {
        super(presenter, Organization.class);

        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setResponsive(false);
        setCompositionRoot(layout);

        code = createTextField("organizations.code", 1);
        layout.addComponent(code);

        commonName = createTextField("organizations.commonName", 2);
        layout.addComponent(commonName);

        dsb = createComboBox("organizations.dsb.commonName", "commonName", 3, Identity.class);
        layout.addComponent(dsb);

        country = createTextField("organizations.country", 4);
        country.setColumns(3);
        layout.addComponent(country);

        lv = createTextField("organizations.lv", 5);
        lv.setColumns(3);
        layout.addComponent(lv);

        bv = createTextField("organizations.bv", 6);
        bv.setColumns(3);
        layout.addComponent(bv);

        rv = createTextField("organizations.rv", 7);
        rv.setColumns(3);
        layout.addComponent(rv);

        kv = createTextField("organizations.kv", 8);
        kv.setColumns(3);
        layout.addComponent(kv);

        ov = createTextField("organizations.ov", 9);
        ov.setColumns(3);
        layout.addComponent(ov);
    }
}
