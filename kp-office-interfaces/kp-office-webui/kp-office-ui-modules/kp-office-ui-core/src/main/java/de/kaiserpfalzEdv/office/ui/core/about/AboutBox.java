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

import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.annotation.VaadinUIScope;
import org.vaadin.spring.navigator.annotation.VaadinView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.02.15 06:21
 */
@VaadinUIScope
@VaadinView(name = "AboutBox")
public class AboutBox extends Window {
    private static final Logger LOG = LoggerFactory.getLogger(AboutBox.class);

    @Inject
    private AboutContent about;

    private VerticalLayout layout;

    public AboutBox() {
        super("About");

        layout = new VerticalLayout();
        
        LOG.trace("Created: {}", this);
    }
    
    @PostConstruct
    public void init() {
        setContent(layout);

        layout.addComponent(about);

        layout.addComponent(new Button("Close", event -> {
            LOG.debug("Clicked on: {}", event.getButton().getId());
            close();
        }));
        
        LOG.trace("Initilized: {}", this);
    } 
}
