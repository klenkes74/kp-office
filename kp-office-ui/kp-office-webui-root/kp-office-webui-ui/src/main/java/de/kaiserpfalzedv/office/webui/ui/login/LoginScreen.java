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

package de.kaiserpfalzedv.office.webui.ui.login;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.event.ShortcutAction;
import com.vaadin.server.Page;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.FormLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.TextField;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.vaadin.addon.cdiproperties.TextBundle;
import org.vaadin.addon.cdiproperties.annotation.ButtonProperties;
import org.vaadin.addon.cdiproperties.annotation.CssLayoutProperties;
import org.vaadin.addon.cdiproperties.annotation.FormLayoutProperties;
import org.vaadin.addon.cdiproperties.annotation.LabelProperties;
import org.vaadin.addon.cdiproperties.annotation.TextFieldProperties;
import org.vaadin.addon.cdiproperties.annotation.VerticalLayoutProperties;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.servlet.ServletException;

/**
 * UI content when the user is not logged in yet.
 */
@ViewScoped
@PermitAll
@CDIView
public class LoginScreen extends CssLayout {
    private static final long serialVersionUID = -1379268123072026394L;

    @Inject
    @VerticalLayoutProperties(styleName = {"centering-layout"})
    private VerticalLayout centeringLayout;

    @Inject
    @FormLayoutProperties(styleName = {"login-form"}, sizeUndefined = true, margin = false)
    private FormLayout loginForm;

    @Inject
    @TextFieldProperties(captionKey = "login.name.caption", widthValue = 15, widthUnits = Unit.EM)
    private TextField username;

    @Inject
    @TextFieldProperties(captionKey = "login.password.caption", widthValue = 15, widthUnits = Unit.EM)
    private TextField password;

    @Inject
    @CssLayoutProperties(styleName = {"buttons"})
    private CssLayout buttons;

    @Inject
    @ButtonProperties(
            captionKey = "login.login-button.caption",
            disableOnClick = true,
            styleName = {ValoTheme.BUTTON_FRIENDLY}
    )
    private Button login;

    @Inject
    @ButtonProperties(
            captionKey = "login.password-forgotten.caption",
            disableOnClick = true,
            styleName = {ValoTheme.BUTTON_FRIENDLY}
    )
    private Button forgotPassword;


    @Inject
    @CssLayoutProperties(styleName = "login-information")
    private CssLayout loginInformation;

    @Inject
    @LabelProperties(captionKey = "login.info-text", contentMode = ContentMode.HTML)
    private Label loginInfoText;


    @Inject
    private TextBundle i18n;


    @PostConstruct
    public void buildUI() {
        addStyleName("login-screen");

        // login form, centered in the available part of the screen
        buildLoginForm();

        // layout to center login form when there is sufficient screen space
        // - see the theme for how this is made responsive for various screen
        // sizes
        centeringLayout.addComponent(loginForm);
        centeringLayout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

        loginInformation.addComponent(loginInfoText);

        addComponent(centeringLayout);
        addComponent(loginInformation);

        username.focus();
    }


    private void buildLoginForm() {
        loginForm.addComponent(username);
        loginForm.addComponent(password);
        loginForm.addComponent(buttons);

        buttons.addComponent(login);
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

        buttons.addComponent(forgotPassword);
        forgotPassword.addClickListener(
                (Button.ClickListener) event -> Notification.show(i18n.getText("login.password-forgotten.text"))
        );
    }

    private void login() {
        try {
            JaasAccessControl.login(username.getValue(), password.getValue());

            Page page = Page.getCurrent();
            page.setLocation(page.getLocation());
        } catch (ServletException e) {
            Notification.show(
                    i18n.getText("login.failed.caption"),
                    i18n.getText("login.failed.description"),
                    Notification.Type.ERROR_MESSAGE
            );

            username.focus();
        }
    }
}
