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

package de.kaiserpfalzEdv.vaadin.ui.mvp.impl;

import com.google.common.eventbus.EventBus;
import de.kaiserpfalzEdv.vaadin.event.ErrorNotificationEvent;
import de.kaiserpfalzEdv.vaadin.event.NavigateToEvent;
import de.kaiserpfalzEdv.vaadin.event.NotificationEvent;
import de.kaiserpfalzEdv.vaadin.event.NotificationPayload;
import de.kaiserpfalzEdv.vaadin.event.WarningEvent;
import de.kaiserpfalzEdv.vaadin.i18n.I18NHandler;
import de.kaiserpfalzEdv.vaadin.ui.mvp.Presenter;
import de.kaiserpfalzEdv.vaadin.ui.mvp.View;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;

/**
 * The base for all presenter.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 09.09.15 20:27
 */
public abstract class BasePresenter<V extends View> implements Presenter<V> {
    private static final long serialVersionUID = 8508599197132748369L;

    private static final Logger LOG = LoggerFactory.getLogger(BasePresenter.class);

    private V view;

    @Inject
    private EventBus bus;

    @Inject
    private I18NHandler i18n;


    public void setBus(EventBus bus) {
        this.bus = bus;
    }

    public void setI18NHandler(I18NHandler i18n) {
        this.i18n = i18n;
    }

    @Override
    public V getView() {
        return view;
    }

    @Override
    public void setView(V view) {
        this.view = view;
    }

    @Override
    public void navigateTo(String target) {
        LOG.trace("Starting navigation to: {}", target);

        bus.post(new NavigateToEvent(this, target));
    }

    @Override
    public void notate(String caption, String description) {
        NotificationPayload notification = new NotificationPayload(description).setCaption(caption);

        bus.post(new NotificationEvent(this, notification));
    }

    @Override
    public void notate(String caption, String description, Object... params) {
        NotificationPayload notification = new NotificationPayload(description).setCaption(caption).setParams(params);

        bus.post(new NotificationEvent(this, notification));
    }

    @Override
    public void notate(String description) {
        NotificationPayload notification = new NotificationPayload(description);

        bus.post(new NotificationEvent(this, notification));
    }

    @Override
    public void notate(String description, Object... params) {
        NotificationPayload notification = new NotificationPayload(description).setParams(params);

        bus.post(new NotificationEvent(this, notification));
    }


    @Override
    public void warn(String caption, String description) {
        NotificationPayload notification = new NotificationPayload(description).setCaption(caption);

        bus.post(new WarningEvent(this, notification));
    }

    @Override
    public void warn(String caption, String description, Object... params) {
        NotificationPayload notification = new NotificationPayload(description).setCaption(caption).setParams(params);

        bus.post(new WarningEvent(this, notification));
    }

    @Override
    public void warn(String description) {
        NotificationPayload notification = new NotificationPayload(description);

        bus.post(new WarningEvent(this, notification));
    }

    @Override
    public void warn(String description, Object... params) {
        NotificationPayload notification = new NotificationPayload(description).setParams(params);

        bus.post(new WarningEvent(this, notification));
    }


    @Override
    public void error(String caption, String description) {
        NotificationPayload notification = new NotificationPayload(description).setCaption(caption);

        bus.post(new ErrorNotificationEvent(this, notification));
    }

    @Override
    public void error(String caption, String description, Object... params) {
        NotificationPayload notification = new NotificationPayload(description).setCaption(caption).setParams(params);

        bus.post(new ErrorNotificationEvent(this, notification));
    }

    @Override
    public void error(String description) {
        NotificationPayload notification = new NotificationPayload(description);

        bus.post(new ErrorNotificationEvent(this, notification));
    }

    @Override
    public void error(String description, Object... params) {
        NotificationPayload notification = new NotificationPayload(description).setParams(params);

        bus.post(new ErrorNotificationEvent(this, notification));
    }


    public String translate(final String key) {
        return i18n.get(key);
    }

    public String translate(final String key, final Object... params) {
        return i18n.get(key, params);
    }
}
