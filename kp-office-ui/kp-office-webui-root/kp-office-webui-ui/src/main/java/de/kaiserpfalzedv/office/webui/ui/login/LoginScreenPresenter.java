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

import javax.inject.Inject;
import javax.servlet.ServletException;

import com.google.common.eventbus.Subscribe;
import com.vaadin.cdi.access.JaasAccessControl;
import com.vaadin.ui.Notification;
import de.kaiserpfalzedv.office.webui.ui.boilerplate.SplashScreenView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addon.cdimvp.AbstractMVPPresenter;
import org.vaadin.addon.cdimvp.AbstractMVPPresenter.ViewInterface;
import org.vaadin.addon.cdiproperties.TextBundle;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-07-03
 */
@ViewInterface(LoginScreenView.class)
public class LoginScreenPresenter extends AbstractMVPPresenter<SplashScreenView> {
    public static final String VIEW_ENTER = "LoginScreenPresenter_ve";
    private static final Logger LOG = LoggerFactory.getLogger(LoginScreenPresenter.class);
    @Inject
    private TextBundle i18n;


    @Override
    public void viewEntered() {
        LOG.info("Activated view: {}", view.getClass().getSimpleName());
    }


    @Subscribe
    private void login(final LoginEvent event) {
        try {
            LOG.info("Trying to log in: {}", event.getUserName());

            JaasAccessControl.login(event.getUserName(), event.getPassWord());
        } catch (ServletException e) {
            Notification.show(
                    i18n.getText("login.failed.caption"),
                    i18n.getText("login.failed.description"),
                    Notification.Type.ERROR_MESSAGE
            );
        }
    }

}
