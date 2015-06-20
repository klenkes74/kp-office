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

package de.kaiserpfalzEdv.office.ui.web;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import de.kaiserpfalzEdv.office.ui.OfficeModule;
import de.kaiserpfalzEdv.office.ui.web.mainScreen.MainScreenPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.HashSet;
import java.util.ServiceLoader;

/**
 * The main UI of the application
 */
@Theme("valo")
@Widgetset("KPOfficeWidgetset")
@SpringUI
public class KPOfficeUI extends UI implements ApplicationContextAware {
    private static final long serialVersionUID = 3187407073643645462L;
    private static final Logger LOG              = LoggerFactory.getLogger(KPOfficeUI.class);


    /**
     * Available modules to load and work with.
     */
    private final HashSet<OfficeModule> officeModules = new HashSet<>(10);


    @Inject
    private EventBusHandler eventBus;

    @Inject
    private MainScreenPresenter presenter;


    private ApplicationContext context;


    /**
     * root window. Since the main content may not be changed this is the main content. Then everything may be changed
     * within this layout.
     */
    private CssLayout mainLayout;


    public KPOfficeUI() {
        LOG.trace("Created: {}", this);
    }


    @PostConstruct
    public void init() {
        scanOfficeModules();
        initializeModules();

        eventBus.register(this);

        LOG.trace("Initialized: {}", this);
    }

    private void scanOfficeModules() {
        ServiceLoader<OfficeModule> coreLoader = ServiceLoader.load(OfficeModule.class);

        coreLoader.forEach(e -> officeModules.add(e));
    }

    private void initializeModules() {
        for (OfficeModule module : officeModules) {
            LOG.debug("Initializing module: {}", module);

            context.getBean(module.getClass()).startModule();
        }

    }


    @PreDestroy
    public void close() {
        eventBus.unregister(this);

        LOG.trace("Destroyed: {}", this);
    }


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        mainLayout = new CssLayout();
        mainLayout.setSizeFull();
        mainLayout.setHeight(100f, Unit.PERCENTAGE);
        mainLayout.setWidth(100f, Unit.PERCENTAGE);
        setContent(mainLayout);

        mainLayout.addComponent(presenter.getView());

        officeModules.forEach(
                e -> LOG.info(
                        "Module found: {} ({})", e.getDisplayName(), e.getVersion()
                                                                      .getBuildDescriptor()
                )
        );
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}