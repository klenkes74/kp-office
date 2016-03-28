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

package de.kaiserpfalzEdv.vaadin.auth.user.editor;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.piracc.backend.db.auth.User;
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
        name = UserEditorViewImpl.VIEW_NAME,
        i18nKey = UserEditorViewImpl.VIEW_NAME,
        access = {"admin"},
        order = 92,
        separator = false
)
@SpringView(name = UserEditorViewImpl.VIEW_NAME)
public class UserEditorViewImpl extends BaseEditorViewImpl<User, UserEditorView> implements UserEditorView, View {
    public static final  String VIEW_NAME        = "users.editor";
    private static final long   serialVersionUID = 4169067938354549404L;

    private static final int BUTTON_TAB_INDEX = 10;

    private static final Logger LOG = LoggerFactory.getLogger(de.kaiserpfalzEdv.vaadin.auth.role.editor.RoleEditorViewImpl.class);


    private UserEditor editor;

    @Inject
    public UserEditorViewImpl(
            final UserEditorPresenter presenter,
            final DataContainerProvider containerProvider
    ) {
        super(presenter, containerProvider, BUTTON_TAB_INDEX);
    }


    @Override
    public void setData(User data) {
        getEditor().setEditorData(data);
    }


    @Override
    public BaseEditor<User> getEditor(User data) {
        if (editor == null) {
            editor = new UserEditor((UserEditorPresenter) presenter);
            editor.setEditorData(data);
        }

        return editor;
    }

    public BaseEditor<User> getEditor() {
        if (editor == null) {
            editor = new UserEditor((UserEditorPresenter) presenter);
            editor.setEditorData(presenter.getData());
        }

        return editor;
    }
}
