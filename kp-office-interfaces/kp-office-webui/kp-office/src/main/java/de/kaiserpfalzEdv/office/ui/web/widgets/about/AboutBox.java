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

package de.kaiserpfalzEdv.office.ui.web.widgets.about;

import com.vaadin.ui.Button;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Window;
import de.kaiserpfalzEdv.office.core.license.OfficeLicense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.vaadin.spring.annotation.VaadinUIScope;
import org.vaadin.spring.navigator.annotation.VaadinView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.02.15 06:21
 */
@VaadinUIScope
@VaadinView(name = "AboutBox")
public class AboutBox extends Window {
    private static final Logger LOG = LoggerFactory.getLogger(AboutBox.class);

    private HorizontalLayout layout;
    
    @Value("${info.app.name")
    private String appName;
    
    @Value("${info.app.version")
    private String appVersion;
    
    @Inject
    private OfficeLicense license;
    
    public AboutBox() {
        super("About");
        
        layout = new HorizontalLayout();
        
        LOG.trace("Created: {}", this);
    }
    
    @PostConstruct
    public void init() {
        layout.addComponent(new Label(appName));
        layout.addComponent(new Label(appVersion));
        layout.addComponent(new Label(license.getIssuer()));
        layout.addComponent(new Label(license.getLicensee()));
        layout.addComponent(new Label(license.getId().toString()));
        layout.addComponent(new Label(license.getExpiry().format(DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.GERMANY))));

        layout.addComponent(new Button("Close", event -> {
            LOG.debug("Clicked on: {}", event.getButton().getId());
            close();
        }));
        
        LOG.trace("Initilized: {}", this);
    } 
}
