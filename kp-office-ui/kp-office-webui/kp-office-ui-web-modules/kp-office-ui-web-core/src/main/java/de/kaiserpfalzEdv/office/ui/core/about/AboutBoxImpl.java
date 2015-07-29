/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.ui.core.about;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.spring.annotation.SpringView;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import de.kaiserpfalzEdv.office.ui.api.Presenter;
import de.kaiserpfalzEdv.office.ui.menu.MenuEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.02.15 06:21
 */
@UIScope
@SpringView(name = AboutBoxImpl.VIEW_NAME)
public class AboutBoxImpl extends VerticalLayout implements AboutBox, View, Component, MenuEntry {
    public static final  String VIEW_NAME = "AboutBox";
    private static final Logger LOG       = LoggerFactory.getLogger(AboutBoxImpl.class);
    @Inject
    private Presenter<AboutBox> presenter;

    @Inject
    private AboutContent about;

    public AboutBoxImpl() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        addComponent((Component) about);

        presenter.setView(this);

        LOG.trace("Initilized: {}", this);
    }

    @Override
    public String getViewName() {
        return VIEW_NAME;
    }

    @Override
    public int getSortOrder() {
        return MenuEntry.LOWEST;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.trace("Received: {}", event);
        LOG.trace("Entering view: {}", this);
    }


    @Override
    public void setPresenter(Presenter<?> presenter) {
        this.presenter = (AboutBoxPresenter) presenter;
    }
}
