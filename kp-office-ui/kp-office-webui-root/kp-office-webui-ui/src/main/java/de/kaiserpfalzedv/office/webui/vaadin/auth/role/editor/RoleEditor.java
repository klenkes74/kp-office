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

package de.kaiserpfalzEdv.vaadin.auth.role.editor;

import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.piracc.backend.db.auth.Role;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.impl.BaseEditorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.09.15 08:09
 */
public class RoleEditor extends BaseEditorImpl<Role, RoleEditorView> {
    private static final long   serialVersionUID = -2648628476268411539L;
    private static final Logger LOG              = LoggerFactory.getLogger(RoleEditor.class);

    private VerticalLayout layout;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private TextField name, commonName;


    public RoleEditor(RoleEditorPresenter presenter) {
        super(presenter, Role.class);

        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setResponsive(false);
        setCompositionRoot(layout);


        name = createTextField("role.name", 1);
        layout.addComponent(name);

        commonName = createTextField("role.commonName", 2);
        layout.addComponent(commonName);
    }


    @Override
    public void setEditorData(Role data) {
        super.setEditorData(data);

        if (data.getId() != null) {
            LOG.debug("Disabling changin of role name to disable security issues due to priviledge extension.");
            name.setEnabled(false);
            name.setDescription(presenter.translate("role.name.description.disabled"));
        }
    }
}
