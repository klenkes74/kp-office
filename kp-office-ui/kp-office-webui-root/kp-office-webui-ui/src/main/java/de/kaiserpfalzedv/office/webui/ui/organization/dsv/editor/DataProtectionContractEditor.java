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

package de.kaiserpfalzEdv.piracc.organization.dsv.editor;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.piracc.backend.db.dynamic.DataProtectionContract;
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
public class DataProtectionContractEditor extends BaseEditorImpl<DataProtectionContract, DataProtectionContractEditorView> {
    public static final  int    EDITOR_FIELDS    = 8;
    private static final long   serialVersionUID = -9221645053351911329L;
    private static final Logger LOG              = LoggerFactory.getLogger(DataProtectionContractEditor.class);
    private VerticalLayout layout;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private TextField documentLink;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ComboBox identity, organization, trainer;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private DateField from, till;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private TextArea comment;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private CheckBox locked;


    public DataProtectionContractEditor(DataProtectionContractEditorPresenter presenter) {
        super(presenter, DataProtectionContract.class);

        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setResponsive(false);
        setCompositionRoot(layout);


        identity = createComboBox("dsvs.identity.commonName", "commonName", 1, Identity.class);
        layout.addComponent(identity);

        organization = createDropdownSelect("dsvs.organization.commonName", "commonName", 2, Organization.class);
        layout.addComponent(organization);

        trainer = createComboBox("dsvs.trainer.commonName", "commonName", 3, Identity.class);
        layout.addComponent(trainer);


        documentLink = createTextField("dsvs.documentLink", 4);
        layout.addComponent(documentLink);

        from = createDateField("base.from", 5);
        layout.addComponent(from);

        till = createDateField("base.till", 6);
        layout.addComponent(till);

        comment = createTextArea("base.comment", 7, 5);
        layout.addComponent(comment);

        locked = new CheckBox(presenter.translate("base.locked"));
        locked.setTabIndex(8);
        layout.addComponent(locked);
    }
}
