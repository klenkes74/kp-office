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

import com.vaadin.data.fieldgroup.FieldGroup;
import com.vaadin.ui.Button;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.TextArea;
import com.vaadin.ui.TextField;
import com.vaadin.ui.TwinColSelect;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.piracc.backend.db.auth.Role;
import de.kaiserpfalzEdv.piracc.backend.db.auth.User;
import de.kaiserpfalzEdv.piracc.backend.db.master.Identity;
import de.kaiserpfalzEdv.piracc.backend.db.master.Organization;
import de.kaiserpfalzEdv.vaadin.ui.defaultviews.editor.impl.BaseEditorImpl;
import org.kaj.passwordcomponent.PasswordComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.09.15 08:09
 */
public class UserEditor extends BaseEditorImpl<User, UserEditorViewImpl> {
    private static final long serialVersionUID = 2075855257409051270L;

    private static final Logger LOG = LoggerFactory.getLogger(UserEditor.class);


    private VerticalLayout layout;


    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private TextField login;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private PasswordComponent password;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private TwinColSelect roles;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private ComboBox identity;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private TwinColSelect organizations;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private TextArea comment;

    @SuppressWarnings({"FieldCanBeLocal", "unused"})
    private CheckBox locked;


    public UserEditor(UserEditorPresenter presenter) {
        super(presenter, User.class);

        layout = new VerticalLayout();
        layout.setSizeFull();
        layout.setSpacing(true);
        layout.setResponsive(false);
        setCompositionRoot(layout);


        identity = createComboBox("user.identity", "commonName", 1, Identity.class);
        layout.addComponent(identity);


        login = createTextField("user.login", 2);
        layout.addComponent(login);

        password = new PasswordComponent();
        password.setCaption(presenter.translate("user.password"));
        password.setConfirmText(presenter.translate("user.password.confirm-text"));
        password.setPasswordText(presenter.translate("user.password.text"));
        password.setVisibilityPasswordText(presenter.translate("user.password.visibility-password-text"));
        password.setVisibilityPlainText(presenter.translate("user.password.visibility-plain-text"));
        layout.addComponent(password);

        roles = createTwinColSelect("user.roles", "commonName", 5, 4, Role.class);
        layout.addComponent(roles);

        organizations = createTwinColSelect("user.organizations", "commonName", 5, 6, Organization.class);
        layout.addComponent(organizations);

        comment = createTextArea("base.comment", 8, 5);
        layout.addComponent(comment);

        locked = new CheckBox(presenter.translate("base.locked"));
        locked.setTabIndex(9);
        layout.addComponent(locked);


        identity.addValueChangeListener(
                event -> {
                    if (isBlank(login.getValue())) {
                        login.setValue(presenter.calculateAccountName((Long) event.getProperty().getValue()));

                        presenter.notate(
                                "users.editor.identity-change.account-changed.caption",
                                "users.editor.identity-change.account-changed.description",
                                fieldGroup.getItemDataSource().getBean().getLogin(),
                                fieldGroup.getItemDataSource().getBean().getIdentity().getCommonName()
                        );
                    } else if (login.isEnabled()) { // Warning only if the data is not yet saved!
                        presenter.warn(
                                "users.editor.identity-change.account-not-changed.caption",
                                "users.editor.identity-change.account-not-changed.description",
                                fieldGroup.getItemDataSource().getBean().getLogin(),
                                fieldGroup.getItemDataSource().getBean().getIdentity().getCommonName()
                        );
                    }
                }
        );
    }


    @Override
    public void setEditorData(User data) {
        if (data.getId() != null) {
            LOG.debug("Disabling changing of account name to disable security issues due to priviledge extension.");
            login.setEnabled(false);
            login.setDescription(presenter.translate("user.login.description.disabled"));
        } else {
            login.setEnabled(true);
        }

        super.setEditorData(data);

        if (data.getId() != null) {
            login.setEnabled(false);
            login.setDescription(presenter.translate("user.login.description"));
        }
    }


    @Override
    public void commit(Button.ClickEvent event) throws FieldGroup.CommitException {
        super.commit(event);

        if (fieldGroup != null) {
            fieldGroup.getItemDataSource().getBean().setPassword(password.getPasswordValue());
        }
    }
}
