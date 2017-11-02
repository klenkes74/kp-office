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

package de.kaiserpfalzedv.commons.webui.views;

import com.vaadin.navigator.View;
import de.kaiserpfalzedv.commons.webui.events.NotificationPayload;
import de.kaiserpfalzedv.commons.webui.events.PresentationHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The base for all presenter.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 09.09.15 20:27
 */
public abstract class BasePresenter<V extends View> implements Presenter<V> {
    private static final long serialVersionUID = -9179107213119112315L;

    private static final Logger LOG = LoggerFactory.getLogger(BasePresenter.class);

    private V view;

    private PresentationHelper helper;

    public BasePresenter(
            final PresentationHelper helper
    ) {
        this.helper = helper;
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
        helper.navigateTo(target);
    }

    @Override
    public void notate(String caption, String description) {
        helper.notate(new NotificationPayload(description).setCaption(caption));
    }

    @Override
    public void notate(String caption, String description, Object... params) {
        helper.notate(new NotificationPayload(description).setCaption(caption).setParams(params));
    }

    @Override
    public void notate(String description) {
        helper.notate(new NotificationPayload(description));
    }

    @Override
    public void notate(String description, Object... params) {
        helper.notate(new NotificationPayload(description).setParams(params));
    }


    @Override
    public void warn(String caption, String description) {
        helper.warn(new NotificationPayload(description).setCaption(caption));
    }

    @Override
    public void warn(String description) {
        helper.warn(new NotificationPayload(description));
    }

    @Override
    public void warn(String caption, String description, Object... params) {
        helper.warn(new NotificationPayload(description).setCaption(caption).setParams(params));
    }

    @Override
    public void warn(String description, Object... params) {
        helper.warn(new NotificationPayload(description).setParams(params));
    }


    @Override
    public void error(String caption, String description) {
        helper.error(new NotificationPayload(description).setCaption(caption));
    }

    @Override
    public void error(String description) {
        helper.error(new NotificationPayload(description));
    }

    @Override
    public void error(String caption, String description, Object... params) {
        helper.error(new NotificationPayload(description).setCaption(caption).setParams(params));
    }

    @Override
    public void error(String description, Object... params) {
        helper.error(new NotificationPayload(description).setParams(params));
    }


    public String translate(final String key) {
        return helper.translate(key);
    }

    public String translate(final String key, final Object... params) {
        return helper.translate(key, params);
    }
}
