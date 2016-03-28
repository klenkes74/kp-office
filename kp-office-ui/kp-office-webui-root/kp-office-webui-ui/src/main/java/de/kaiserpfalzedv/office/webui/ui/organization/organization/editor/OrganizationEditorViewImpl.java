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

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.piracc.backend.db.master.Organization;
import de.kaiserpfalzEdv.vaadin.auth.Access;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.DataContainerProvider;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.BaseEditor;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.impl.BaseEditorViewImpl;
import de.kaiserpfalzEdv.vaadin.ui.menu.MenuEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.09.15 07:48
 */
@Named
@UIScope
@Access({"dsb", "office", "board"})
@MenuEntry(
        name = OrganizationEditorViewImpl.VIEW_NAME,
        i18nKey = OrganizationEditorViewImpl.VIEW_NAME,
        access = {"dsb", "office", "board"},
        order = 42,
        separator = false
)
@SpringView(name = OrganizationEditorViewImpl.VIEW_NAME)
public class OrganizationEditorViewImpl extends BaseEditorViewImpl<Organization, OrganizationEditorView> implements OrganizationEditorView, View {
    public static final  String VIEW_NAME        = "organizations.editor";
    private static final long   serialVersionUID = -2841221933902062282L;

    private static final int BUTTON_TAB_INDEX = OrganizationEditor.EDITOR_FIELDS + 1;

    private static final Logger LOG = LoggerFactory.getLogger(OrganizationEditorViewImpl.class);


    private OrganizationEditor editor;

    @Inject
    public OrganizationEditorViewImpl(
            OrganizationEditorPresenter presenter,
            DataContainerProvider containerProvider
    ) {
        super(presenter, containerProvider, BUTTON_TAB_INDEX);
    }


    @Override
    public void setData(Organization data) {
        getEditor().setEditorData(data);
    }


    @Override
    public BaseEditor<Organization> getEditor(Organization data) {
        if (editor == null) {
            editor = new OrganizationEditor((OrganizationEditorPresenter) presenter);
            editor.setEditorData(data);
        }

        return editor;
    }

    public BaseEditor<Organization> getEditor() {
        if (editor == null) {
            editor = new OrganizationEditor((OrganizationEditorPresenter) presenter);
            editor.setEditorData(presenter.getData());
        }

        return editor;
    }
}