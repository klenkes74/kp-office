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

package de.kaiserpfalzEdv.piracc.tech.system.editor;

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.piracc.backend.db.dynamic.DataProcessingContract;
import de.kaiserpfalzEdv.piracc.backend.db.dynamic.ITSystem;
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
public class SystemEditor extends BaseEditorImpl<ITSystem, SystemEditorViewImpl> {
    public static final  int    EDITOR_FIELDS    = 9;
    private static final long   serialVersionUID = 7672973427915086856L;
    private static final Logger LOG              = LoggerFactory.getLogger(SystemEditor.class);
    private VerticalLayout layout;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private TextField name;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private TextArea location;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private TwinColSelect organizations, dataProcessingContracts;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ComboBox hardwareSupporter1, hardwareSupporter2, accountSupporter1, accountSupporter2;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private CheckBox locked;


    public SystemEditor(SystemEditorPresenter presenter) {
        super(presenter, ITSystem.class);

        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setResponsive(false);
        setCompositionRoot(layout);

        name = createTextField("systems.name", 1);
        layout.addComponent(name);

        location = createTextArea("systems.location", 2, 5);
        layout.addComponent(location);

        organizations = createTwinColSelect("systems.organizations.commonName", "commonName", 3, 5, Organization.class);
        layout.addComponent(organizations);

        dataProcessingContracts = createTwinColSelect("systems.dataProcessingContracts.commonName", "commonName", 4, 5, DataProcessingContract.class);
        layout.addComponent(dataProcessingContracts);

        hardwareSupporter1 = createDropdownSelect("systems.hardwareSupporter.commonName", "commonName", 5, Identity.class);
        hardwareSupporter1.setTextInputAllowed(true);
        layout.addComponent(hardwareSupporter1);

        hardwareSupporter2 = createDropdownSelect("systems.hardwareSupporter.commonName", "commonName", 6, Identity.class);
        hardwareSupporter2.setTextInputAllowed(true);
        layout.addComponent(hardwareSupporter2);

        accountSupporter1 = createDropdownSelect("systems.accountSupporter.commonName", "commonName", 7, Identity.class);
        accountSupporter1.setTextInputAllowed(true);
        layout.addComponent(accountSupporter1);

        accountSupporter2 = createDropdownSelect("systems.accountSupporter.commonName", "commonName", 8, Identity.class);
        accountSupporter2.setTextInputAllowed(true);
        layout.addComponent(accountSupporter2);

        locked = new CheckBox(presenter.translate("base.locked"));
        locked.setTabIndex(9);
        layout.addComponent(locked);
    }
}
