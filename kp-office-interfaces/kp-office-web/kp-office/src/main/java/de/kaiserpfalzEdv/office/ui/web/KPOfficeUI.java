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

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.annotations.Widgetset;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.server.VaadinRequest;
import com.vaadin.shared.ui.ui.Transport;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinUI;
import org.vaadin.spring.navigator.VaadinView;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

/**
 *
 */
@Theme("valo")
@Title("Kaiserpfalz Office")
@Widgetset("KPOfficeWidgetset")
@VaadinUI
@VaadinView(name = Views.HOME, ui=KPOfficeUI.class)
@Push(transport = Transport.LONG_POLLING)
public class KPOfficeUI extends UI implements View {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(KPOfficeUI.class);


    @Inject
    private KPOfficeMainWindow mainWindow;


    public KPOfficeUI() {
        LOG.trace("Created: {}", this);
    }
    
    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        LOG.info("Starting for request: {}", vaadinRequest);

        setContent(mainWindow);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.debug("Received event: {}", event);
    }


/*
    @WebServlet(urlPatterns = "/ui", name = "KPOfficeServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = KPOfficeUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
*/
}




@UIScope
class KPOfficeMainWindow extends VerticalLayout {
    @Inject
    private KPOfficeModuleBar moduleBar;
    
    @Inject
    private KPOfficeMain mainView;

    private HorizontalSplitPanel content;


    public KPOfficeMainWindow() {
        setSizeFull();
        addComponent(new Label("test"));
    }


/*
    public KPOfficeMainWindow(KPOfficeModuleBar moduleBar, KPOfficeMain mainView) {
        this.moduleBar = moduleBar;
        this.mainView = mainView;

        addComponent(content);
        setSizeFull();

        content = new HorizontalSplitPanel(moduleBar, mainView);
        content.setMinSplitPosition(2.0f, Unit.CM);
        content.setMaxSplitPosition(10.0f, Unit.CM);

        content.setCaption("KP Office");
    }
*/
}


@UIScope
class KPOfficeMain extends VerticalLayout {
    public KPOfficeMain() {
        setCaption("Main Window");

        setSizeFull();
    }
}