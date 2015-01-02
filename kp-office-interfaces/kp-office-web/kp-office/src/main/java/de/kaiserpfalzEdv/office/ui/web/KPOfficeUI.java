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
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalSplitPanel;
import com.vaadin.ui.Label;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinUI;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

/**
 *
 */
@Theme("valo")
@Widgetset("KPOfficeWidgetset")
@VaadinUI(path = "/")
public class KPOfficeUI extends UI {
    private static final long serialVersionUID = 1L;

    private static final Logger LOG = LoggerFactory.getLogger(KPOfficeUI.class);


    @Inject
    private Component mainWindow;


    public KPOfficeUI() {
    }


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        LOG.info("Starting for request: {}", vaadinRequest);

        setContent(mainWindow);
    }

    @WebServlet(urlPatterns = "/*", name = "KPOfficeServlet", asyncSupported = true)
    @VaadinServletConfiguration(ui = KPOfficeUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}




@UIScope
class KPOfficeMainWindow extends VerticalLayout {
    private KPOfficeModuleBar moduleBar;
    private KPOfficeMain mainView;

    private HorizontalSplitPanel content;


    public KPOfficeMainWindow() {
        setSizeFull();
        addComponent(new Label("test"));
    }


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
}


@UIScope
class KPOfficeMain extends VerticalLayout {
    public KPOfficeMain() {
        setCaption("Main Window");

        setSizeFull();
    }
}