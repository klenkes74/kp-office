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

package de.kaiserpfalzEdv.office.ui.web.view;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.navigator.VaadinView;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@UIScope
@VaadinView(name = MainView.NAME)
public class MainView extends VerticalLayout implements View {
    private static final long serialVersionUID = 2969812303192236717L;
    private final Logger LOG = LoggerFactory.getLogger(MainView.class);
    public static final String NAME = "main";


    @PostConstruct
    private void init() {
        setMargin(true);
        setSpacing(true);
        setSizeFull();
        
        LOG.trace("Created: {}", this);
    }
    
   @PreDestroy
   public void close() {
       LOG.trace("Destroyed: {}", this);
       
   }


    public void setBanner(Component banner) {
        LOG.debug("Setting banner: {}", banner);
        addComponent(banner);
    }

    public void setHeader(Component header) {
        LOG.debug("Setting header: {}", header);
        
        addComponent(header);
        setExpandRatio(header, 1);
    }

    public void setBody(Component body) {
        LOG.debug("Setting body: {}", body);
        
        addComponent(body);
        setExpandRatio(body, 10);
    }

    public void setFooter(Component footer) {
        LOG.debug("Setting footer: {}", footer);
        
        addComponent(footer);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.trace("ViewChangeEvent: {}", event);
    }

}
