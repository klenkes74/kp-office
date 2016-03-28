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

package de.kaiserpfalzEdv.piracc.organization.identity.editor;

import com.vaadin.ui.CheckBox;
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
public class IdentityEditor extends BaseEditorImpl<Identity, IdentityEditorView> {
    private static final long   serialVersionUID = -9082790642263480624L;
    private static final Logger LOG              = LoggerFactory.getLogger(IdentityEditor.class);

    private VerticalLayout layout;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private TextField memberNumber, surName, givenName;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ComboBox organization;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private CheckBox employee;


    public IdentityEditor(IdentityEditorPresenter presenter) {
        super(presenter, Identity.class);

        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setResponsive(false);
        setCompositionRoot(layout);


        memberNumber = createTextField("identity.memberNumber", 1);
        layout.addComponent(memberNumber);

        surName = createTextField("identity.surName", 2);
        layout.addComponent(surName);

        givenName = createTextField("identity.givenName", 3);
        layout.addComponent(givenName);

        organization = createDropdownSelect("identity.organization", "commonName", 4, Organization.class);
        layout.addComponent(organization);

        employee = new CheckBox(presenter.translate("identity.employee"));
        employee.setTabIndex(5);
        layout.addComponent(employee);
    }
}
