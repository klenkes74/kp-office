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

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.piracc.backend.db.auth.Role;
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
@Access({"admin"})
@MenuEntry(
        name = RoleEditorViewImpl.VIEW_NAME,
        i18nKey = RoleEditorViewImpl.VIEW_NAME,
        access = {"admin"},
        order = 96,
        separator = false
)
@SpringView(name = RoleEditorViewImpl.VIEW_NAME)
public class RoleEditorViewImpl extends BaseEditorViewImpl<Role, RoleEditorView> implements RoleEditorView, View {
    public static final  String VIEW_NAME        = "roles.editor";
    private static final long   serialVersionUID = -472885929666251320L;
    private static final int    BUTTON_TAB_INDEX = 3;

    private static final Logger LOG = LoggerFactory.getLogger(RoleEditorViewImpl.class);


    private RoleEditor editor;

    @Inject
    public RoleEditorViewImpl(
            RoleEditorPresenter presenter,
            DataContainerProvider containerProvider
    ) {
        super(presenter, containerProvider, BUTTON_TAB_INDEX);
    }


    @Override
    public void setData(Role data) {
        getEditor().setEditorData(data);
    }


    @Override
    public BaseEditor<Role> getEditor(Role data) {
        if (editor == null) {
            editor = new RoleEditor((RoleEditorPresenter) presenter);
            editor.setEditorData(data);
        }

        return editor;
    }

    public BaseEditor<Role> getEditor() {
        if (editor == null) {
            editor = new RoleEditor((RoleEditorPresenter) presenter);
            editor.setEditorData(presenter.getData());
        }

        return editor;
    }
}
