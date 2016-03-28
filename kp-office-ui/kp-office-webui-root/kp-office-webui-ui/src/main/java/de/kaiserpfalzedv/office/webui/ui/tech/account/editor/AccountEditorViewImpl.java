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

package de.kaiserpfalzEdv.piracc.tech.account.editor;

import com.vaadin.navigator.View;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.piracc.backend.db.dynamic.Account;
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
@Access({"dsb", "admin"})
@MenuEntry(
        name = AccountEditorViewImpl.VIEW_NAME,
        i18nKey = AccountEditorViewImpl.VIEW_NAME,
        access = {"dsb", "admin"},
        order = 13,
        separator = false
)
@SpringView(name = AccountEditorViewImpl.VIEW_NAME)
public class AccountEditorViewImpl extends BaseEditorViewImpl<Account, AccountEditorView> implements AccountEditorView, View {
    public static final  String VIEW_NAME        = "accounts.editor";
    private static final long   serialVersionUID = -5085463514112494561L;

    private static final int BUTTON_TAB_INDEX = AccountEditor.EDITOR_FIELDS + 1;

    private static final Logger LOG = LoggerFactory.getLogger(AccountEditorViewImpl.class);


    private AccountEditor editor;

    @Inject
    public AccountEditorViewImpl(
            AccountEditorPresenter presenter,
            DataContainerProvider containerProvider
    ) {
        super(presenter, containerProvider, BUTTON_TAB_INDEX);
    }


    @Override
    public void setData(Account data) {
        getEditor().setEditorData(data);
    }


    @Override
    public BaseEditor<Account> getEditor(Account data) {
        if (editor == null) {
            editor = new AccountEditor((AccountEditorPresenter) presenter);
            editor.setEditorData(data);
        }

        return editor;
    }

    public BaseEditor<Account> getEditor() {
        if (editor == null) {
            editor = new AccountEditor((AccountEditorPresenter) presenter);
            editor.setEditorData(presenter.getData());
        }

        return editor;
    }
}