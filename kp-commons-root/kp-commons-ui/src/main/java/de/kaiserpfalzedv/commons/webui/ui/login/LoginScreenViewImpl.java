/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.commons.webui.ui.login;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.event.ShortcutAction;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.*;
import com.vaadin.ui.themes.ValoTheme;
import de.kaiserpfalzedv.commons.webui.ui.SerializableEventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addon.cdimvp.AbstractMVPView;
import org.vaadin.addon.cdiproperties.TextBundle;
import org.vaadin.addon.cdiproperties.annotation.*;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.persistence.PreRemove;

/**
 * UI content when the user is not logged in yet.
 */
@ViewScoped
@PermitAll
@CDIView(value = "login")
public class LoginScreenViewImpl extends AbstractMVPView implements View, LoginScreenView {
    private static final long serialVersionUID = -1379268123072026394L;

    private static final Logger LOG = LoggerFactory.getLogger(LoginScreenViewImpl.class);

    @Inject
    @CssLayoutProperties(styleName = {"login-screen"})
    private CssLayout screen;

    @Inject
    @VerticalLayoutProperties(styleName = {"centering-layout"})
    private VerticalLayout centeringLayout;

    @Inject
    @FormLayoutProperties(styleName = {"login-form"}, sizeUndefined = true, margin = false)
    private FormLayout loginForm;

    @Inject
    @TextFieldProperties(
            captionKey = "login.name.caption",
            descriptionKey = "login.name.description",
            nullRepresentation = "",
            required = true,
            requiredError = "login.name.missing",
            widthValue = 15, widthUnits = Unit.EM
    )
    private TextField username;

    @Inject
    @TextFieldProperties(
            captionKey = "login.password.caption",
            descriptionKey = "login.password.description",
            nullRepresentation = "",
            required = true,
            widthValue = 15, widthUnits = Unit.EM
    )
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
    @LabelProperties(captionKey = "login.info-text", contentMode = ContentMode.PREFORMATTED)
    private Label loginInfoText;


    @Inject
    private TextBundle i18n;

    @Inject
    @ViewScoped
    private SerializableEventBus bus;


    @PostConstruct
    public void init() {
        bus.register(this);
    }

    @PreRemove
    public void close() {
        bus.unregister(this);
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        enter();
    }

    public void enter() {
        super.enter();

        // login form, centered in the available part of the screen
        buildLoginForm();

        // layout to center login form when there is sufficient screen space
        // - see the theme for how this is made responsive for various screen
        // sizes
        centeringLayout.addComponent(loginForm);
        centeringLayout.setComponentAlignment(loginForm, Alignment.MIDDLE_CENTER);

        loginInformation.addComponent(loginInfoText);

        screen.addComponent(centeringLayout);
        screen.addComponent(loginInformation);
        setCompositionRoot(screen);

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
                        bus.post(new LoginEvent(username.getValue(), password.getValue()));
                    } finally {
                        username.focus();
                        login.setEnabled(true);
                    }
                }
        );
        login.setClickShortcut(ShortcutAction.KeyCode.ENTER);
        login.addStyleName(ValoTheme.BUTTON_FRIENDLY);

        buttons.addComponent(forgotPassword);
        forgotPassword.addClickListener(
                (Button.ClickListener) event -> {
                    try {
                        bus.post(new ForgottPasswordEvent());
                    } finally {
                        username.focus();
                        forgotPassword.setEnabled(true);
                    }
                }
        );
    }

    @Override
    public String getUserName() {
        return username.getValue();
    }

    @Override
    public String getPassword() {
        return password.getValue();
    }
}
