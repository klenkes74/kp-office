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

package de.kaiserpfalzedv.commons.webui.events;

import java.io.Serializable;

import javax.inject.Inject;

import com.vaadin.cdi.UIScoped;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addon.cdiproperties.TextBundle;

import static com.vaadin.ui.Notification.Type.ERROR_MESSAGE;
import static com.vaadin.ui.Notification.Type.TRAY_NOTIFICATION;
import static com.vaadin.ui.Notification.Type.WARNING_MESSAGE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes (rlichti@kaiserpfalz-edv.de)
 * @version 2015Q1
 * @since 10.09.15 06:31
 */
@UIScoped
public class PresentationHelper implements Serializable {
    private static final long serialVersionUID = 8282894351828144290L;

    private static final Logger LOG = LoggerFactory.getLogger(PresentationHelper.class);

    private TextBundle i18n;


    @Inject
    public PresentationHelper(final TextBundle i18n) {
        this.i18n = i18n;
    }

    public void navigateTo(String target) {
        LOG.debug("Navigate to: {}", target);

        try {
            UI.getCurrent().getNavigator().navigateTo(target);
        } catch (RuntimeException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage()
                              + " while navigating to: " + target, e);

            error(new NotificationPayload(e.getMessage()).setCaption("error.navigation.generic-failure.caption"));
        }
    }

    public void error(NotificationPayload event) {
        LOG.trace("Error Notification: {}", event);

        displayNotification(event, ERROR_MESSAGE);
    }

    private void displayNotification(NotificationPayload notification, Notification.Type type) {
        String caption = notification.getCaption();
        String description = notification.getDescription();
        FontAwesome icon = notification.getIcon();

        if (notification.getParams() != null) {
            caption = translate(caption, notification.getParams());
            description = translate(description, notification.getParams());
        } else {
            caption = translate(caption);
            description = translate(description);
        }

        if (isNotBlank(caption)) {
            Notification.show(caption, description, type);
        } else {
            Notification.show(description, type);
        }
    }

    public String translate(final String key, Object[] params) {
        String result = i18n.getText(key, params);

        return isNotBlank(result) ? result : key;
    }

    public String translate(final String key) {
        String result = i18n.getText(key);

        return isNotBlank(result) ? result : key;
    }

    public void notate(NotificationPayload event) {
        LOG.trace("Notification: {}", event);

        displayNotification(event, TRAY_NOTIFICATION);
    }

    public void warn(NotificationPayload event) {
        LOG.trace("Warning Notification: {}", event);

        displayNotification(event, WARNING_MESSAGE);
    }
}
