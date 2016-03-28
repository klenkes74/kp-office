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

package de.kaiserpfalzedv.office.webui.vaadin.ui;

import com.google.common.eventbus.EventBus;
import com.vaadin.event.ShortcutAction;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.PasswordField;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import de.kaiserpfalzEdv.vaadin.backend.auth.AuthenticationProvider;
import de.kaiserpfalzEdv.vaadin.backend.auth.LoginFailedException;
import de.kaiserpfalzEdv.vaadin.event.ErrorNotificationEvent;
import de.kaiserpfalzEdv.vaadin.event.NotificationEvent;
import de.kaiserpfalzEdv.vaadin.event.NotificationPayload;
import de.kaiserpfalzEdv.vaadin.i18n.I18NHandler;

import java.io.Serializable;

/**
 * UI content when the user is not logged in yet.
 */
public class LoginScreen extends CssLayout {

    private TextField              username;
    private PasswordField          password;
    private Button                 login;
    private LoginListener          loginListener;
    private AuthenticationProvider accessControl;
    private I18NHandler            ui;
    private EventBus               bus;

    public LoginScreen(
            final AuthenticationProvider accessControl,
            final LoginListener loginListener,
            final I18NHandler i18n,
            final EventBus bus
    ) {
        this.ui = i18n;
        this.bus = bus;
        this.loginListener = loginListener;
        this.accessControl = accessControl;

        buildUI();
        username.focus();
    }

    private void buildUI() {
        addStyleName("login-screen");

        // login form, centered in the available part of the screen
        Component loginForm = buildLoginForm();

        // layout to center login form when there is sufficient screen space
        // - see the theme for how this is made responsive for various screen
        // sizes
        VerticalLayout centeringLayout = new VerticalLayout();
        centeringLayout.setStyleName("centering-layout");
        centeringLayout.addComponent(loginForm);
        centeringLayout.setComponentAlignment(
                loginForm,
                Alignment.MIDDLE_CENTER
        );

        // information text about logging in
        CssLayout loginInformation = buildLoginInformation();

        addComponent(centeringLayout);
        addComponent(loginInformation);
    }

    private Component buildLoginForm() {
        FormLayout loginForm = new FormLayout();

        loginForm.addStyleName("login-form");
        loginForm.setSizeUndefined();
        loginForm.setMargin(false);

        loginForm.addComponent(username = new TextField(translate("login.name.caption")));
        username.setDescription(translate("login.name.description"));
        username.setWidth(15, Unit.EM);
        loginForm.addComponent(password = new PasswordField(translate("login.password.caption")));
        password.setWidth(15, Unit.EM);
        password.setDescription(translate("login.password.description"));
        CssLayout buttons = new CssLayout();
        buttons.setStyleName("buttons");
        loginForm.addComponent(buttons);

        login = new Button(translate("login.login-button.caption"));
        buttons.addComponent(login);
        login.setDescription(translate("login.login-button.description"));
        login.setDisableOnClick(true);
        login.addClickListener(
                event -> {
                    try {
                        login();
                    } finally {
                        login.setEnabled(true);
                    }
                }
        );
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        login.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        Button forgotPassword;
        buttons.addComponent(forgotPassword = new Button(translate("login.password-forgotten.caption")));
        forgotPassword.setDescription(translate("login.password-forgotten.description"));
        forgotPassword.addClickListener(
                new Button.ClickListener() {
                    @Override
                    public void buttonClick(Button.ClickEvent event) {
                        NotificationPayload notification = new NotificationPayload("login.password-forgotten.text");
                        bus.post(new NotificationEvent(this, notification));
                    }
                }
        );
        forgotPassword.addStyleName(ValoTheme.BUTTON_LINK);
        return loginForm;
    }

    private CssLayout buildLoginInformation() {
        CssLayout loginInformation = new CssLayout();
        loginInformation.setStyleName("login-information");
        Label loginInfoText = new Label(translate("login.info-text"), ContentMode.HTML);
        loginInformation.addComponent(loginInfoText);
        return loginInformation;
    }

    private void login() {
        try {
            accessControl.signIn(username.getValue(), password.getValue());

            loginListener.loginSuccessful();
        } catch (LoginFailedException e) {
            NotificationPayload notification = new NotificationPayload("login.failed.description").setCaption("login.failed.caption");

            bus.post(new ErrorNotificationEvent(this, notification));
            username.focus();
        }
    }

    private String translate(final String key) {
        return ui.get(key);
    }


    public interface LoginListener extends Serializable {
        void loginSuccessful();
    }
}
