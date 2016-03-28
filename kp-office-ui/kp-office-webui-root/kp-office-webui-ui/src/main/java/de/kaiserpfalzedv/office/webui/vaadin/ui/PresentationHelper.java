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
import com.google.common.eventbus.Subscribe;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Notification;
import com.vaadin.ui.UI;
import de.kaiserpfalzEdv.vaadin.event.ErrorNotificationEvent;
import de.kaiserpfalzEdv.vaadin.event.NavigateToEvent;
import de.kaiserpfalzEdv.vaadin.event.NotificationEvent;
import de.kaiserpfalzEdv.vaadin.event.NotificationPayload;
import de.kaiserpfalzEdv.vaadin.event.WarningEvent;
import de.kaiserpfalzEdv.vaadin.i18n.I18NHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.repository.cdi.Eager;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

import static com.vaadin.ui.Notification.Type.ERROR_MESSAGE;
import static com.vaadin.ui.Notification.Type.TRAY_NOTIFICATION;
import static com.vaadin.ui.Notification.Type.WARNING_MESSAGE;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 10.09.15 06:31
 */
@Named
@UIScope
@Eager
public class PresentationHelper implements Serializable {
    private static final long serialVersionUID = 8282894351828144290L;

    private static final Logger LOG = LoggerFactory.getLogger(PresentationHelper.class);

    private EventBus bus;
    private I18NHandler i18n;


    @Inject
    public PresentationHelper(
            final EventBus bus,
            final I18NHandler i18n
    ) {
        this.bus = bus;
        this.i18n = i18n;
    }

    @PostConstruct
    public void init() {
        bus.register(this);
    }

    @PreDestroy
    public void close() {
        bus.unregister(this);
    }

    @Subscribe
    public void navigateTo(NavigateToEvent event) {
        LOG.debug("Navigate to: {}", event);

        try {
            UI.getCurrent().getNavigator().navigateTo(event.getPayload());
        } catch (RuntimeException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            NotificationPayload error = new NotificationPayload(e.getMessage()).setCaption("error.navigation.generic-failure.caption");
            error(new ErrorNotificationEvent(this, error));
        }
    }

    @Subscribe
    public void notate(NotificationEvent event) {
        LOG.debug("BaseUser notification: {}", event);

        displayNotification(event.getPayload(), TRAY_NOTIFICATION);
    }

    @Subscribe
    public void warn(WarningEvent event) {
        LOG.debug("BaseUser warning: {}", event);

        displayNotification(event.getPayload(), WARNING_MESSAGE);
    }

    @Subscribe
    public void error(ErrorNotificationEvent event) {
        LOG.debug("BaseUser error: {}", event);

        displayNotification(event.getPayload(), ERROR_MESSAGE);
    }

    private void displayNotification(NotificationPayload notification, Notification.Type type) {
        if (isNotBlank(notification.getCaption())) {
            Notification.show(
                    translate(notification.getCaption(), notification.getParams()),
                    translate(notification.getDescription(), notification.getParams()), type
            );
        } else {
            Notification.show(translate(notification.getDescription(), notification.getParams()), type);
        }
    }

    private String translate(final String key) {
        return translate(key, null);
    }

    private String translate(final String key, Object[] params) {
        String result = (params == null) ? i18n.get(key) : i18n.get(key, params);

        return isNotBlank(result) ? result : key;
    }
}
