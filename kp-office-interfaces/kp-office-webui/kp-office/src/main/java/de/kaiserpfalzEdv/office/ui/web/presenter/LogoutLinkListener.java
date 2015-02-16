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

package de.kaiserpfalzEdv.office.ui.web.presenter;

import com.vaadin.server.VaadinServlet;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button.ClickListener;
import de.kaiserpfalzEdv.office.ui.web.view.BannerView;
import org.springframework.core.env.Environment;
import org.springframework.security.core.context.SecurityContextHolder;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.VaadinComponent;

import javax.inject.Inject;

/**
 * Facilitates logout.  Destroys the current session and redirects user to a login page.
 * @author Chris Phillipson (fastnsilver@gmail.com)
 *
 */
@UIScope
@VaadinComponent
public class LogoutLinkListener implements ClickListener {

    @Inject
    private Environment env;

    
    @Override
    public void buttonClick(ClickEvent event) {
        SecurityContextHolder.clearContext();
        
        BannerView banner = (BannerView) event.getComponent().getParent();
        String contextPath = VaadinServlet.getCurrent().getServletContext().getContextPath();
        String urlMapping = env.getProperty("vaadin.servlet.urlMapping");
        String uiPath = urlMapping.substring(0, urlMapping.length() - 2);
        String location = contextPath.concat(uiPath);
        banner.getUI().getPage().setLocation(location);
    }
}
