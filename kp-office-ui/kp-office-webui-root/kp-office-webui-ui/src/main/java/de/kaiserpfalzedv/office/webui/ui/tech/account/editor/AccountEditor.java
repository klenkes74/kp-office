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

import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.DateField;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.piracc.backend.db.dynamic.Account;
import de.kaiserpfalzEdv.piracc.backend.db.dynamic.ITSystem;
import de.kaiserpfalzEdv.piracc.backend.db.master.Identity;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.impl.BaseEditorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.09.15 08:09
 */
public class AccountEditor extends BaseEditorImpl<Account, AccountEditorView> {
    public static final  int    EDITOR_FIELDS    = 12;
    private static final long   serialVersionUID = -6113388675496868855L;
    private static final Logger LOG              = LoggerFactory.getLogger(AccountEditor.class);
    private VerticalLayout layout;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private TextField account, decision;

    private TextArea depends, comment;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ComboBox system, responsible;

    // private TwinColSelect users;

    private DateField from, till, accountCreated, accountRemoved;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private CheckBox locked;


    public AccountEditor(AccountEditorPresenter presenter) {
        super(presenter, Account.class);

        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setResponsive(false);
        setCompositionRoot(layout);


        system = createDropdownSelect("accounts.system", "name", 1, ITSystem.class);
        system.setTextInputAllowed(true);
        layout.addComponent(system);

        account = createTextField("accounts.account", 2);
        layout.addComponent(account);


        accountCreated = createDateField("accounts.accountCreated", 3);
        layout.addComponent(accountCreated);

        accountRemoved = createDateField("accounts.accountRemoved", 4);
        layout.addComponent(accountRemoved);

        responsible = createDropdownSelect("accounts.responsible", "commonName", 5, Identity.class);
        responsible.setTextInputAllowed(true);
        layout.addComponent(responsible);

        /*
        users = createTwinColSelect("accounts.users", "commonName", 5, 6, Identity.class);
        layout.addComponent(users);
        */

        decision = createTextField("accounts.decision", 7);
        layout.addComponent(decision);
        depends = createTextArea("accounts.depends", 8, 5);
        layout.addComponent(depends);
        from = createDateField("base.from", 9);
        layout.addComponent(from);
        till = createDateField("base.till", 10);
        layout.addComponent(till);


        comment = createTextArea("base.comment", 11, 5);
        layout.addComponent(comment);

        locked = new CheckBox(presenter.translate("base.locked"));
        locked.setTabIndex(12);
        layout.addComponent(locked);
    }
}
