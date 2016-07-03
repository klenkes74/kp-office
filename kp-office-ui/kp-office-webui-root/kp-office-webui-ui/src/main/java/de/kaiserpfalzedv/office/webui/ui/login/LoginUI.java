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

import com.vaadin.cdi.CDIUI;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewProvider;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.themes.ValoTheme;
import de.kaiserpfalzedv.vaadin.I18NHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addon.cdiproperties.annotation.CssLayoutProperties;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-06-12
 */
@CDIUI("login")
public class LoginUI extends UI {
    private static final Logger LOG = LoggerFactory.getLogger(LoginUI.class);

    @Inject
    private I18NHandler i18n;

    @Inject
    private ViewProvider viewProvider;


    @Inject
    @CssLayoutProperties(styleName = {"valo-content"}, sizeFull = true)
    private CssLayout viewContainer;


    @Override
    protected void init(VaadinRequest request) {
        Responsive.makeResponsive(this);
        setLocale(request.getLocale());
        i18n.setLocale(request.getLocale());
        getPage().setTitle(translate("application.name"));

        Navigator navigator = new Navigator(this, viewContainer);
        navigator.addProvider(viewProvider);

        addStyleName(ValoTheme.UI_WITH_MENU);
        setContent(viewContainer);

        if (isNotBlank(getNavigator().getState())) {
            getNavigator().navigateTo(getNavigator().getState());
        } else {
            getNavigator().navigateTo("login");
        }
    }

    private String translate(final String key) {
        return i18n.get(key);
    }
}
