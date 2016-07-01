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

package de.kaiserpfalzedv.office.webui.ui;

import com.vaadin.annotations.Push;
import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.annotations.Viewport;
import com.vaadin.annotations.Widgetset;
import com.vaadin.cdi.CDIUI;
import com.vaadin.cdi.access.AccessControl;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import de.kaiserpfalzedv.vaadin.Menu;
import org.vaadin.addon.cdiproperties.TextBundle;
import org.vaadin.addon.cdiproperties.annotation.CssLayoutProperties;
import org.vaadin.addon.cdiproperties.annotation.HorizontalLayoutProperties;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Viewport("user-scalable=no,initial-scale=1.0")
@Push
@Theme("mytheme")
@Widgetset("de.kaiserpfalzedv.office.OfficeWidgetset")
@CDIUI
public class KPOfficeUI extends UI {
    @Inject
    private AccessControl accessControl;

    @Inject
    private ViewProvider viewProvider;

    @Inject
    private TextBundle i18n;

    @Inject
    private Menu menu;


    @Override
    protected void init(VaadinRequest vaadinRequest) {
        Responsive.makeResponsive(this);
        setLocale(vaadinRequest.getLocale());
        getPage().setTitle(i18n.getText("application.name"));

        showMainView();
    }

    @Inject
    @HorizontalLayoutProperties(styleName = {"main-screen"}, sizeFull = true)
    private HorizontalLayout screen;

    @Inject
    @CssLayoutProperties(styleName = {"valo-content"}, sizeFull = true)
    private CssLayout viewContainer;


    protected void showMainView() {
        viewContainer.setSizeFull();

        screen.addComponent(menu);
        screen.addComponent(viewContainer);
        screen.setExpandRatio(viewContainer, 1);

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);

        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(screen);

        if (isNotBlank(getNavigator().getState())) {
            getNavigator().navigateTo(getNavigator().getState());
        }

        menu.generate();
    }


    @WebServlet(value = {"/office/*", "/VAADIN/*"}, name = "KPOffice", asyncSupported = true)
    @VaadinServletConfiguration(ui = de.kaiserpfalzedv.office.webui.ui.KPOfficeUI.class, productionMode = false)
    public static class MyUIServlet extends VaadinServlet {
    }
}
