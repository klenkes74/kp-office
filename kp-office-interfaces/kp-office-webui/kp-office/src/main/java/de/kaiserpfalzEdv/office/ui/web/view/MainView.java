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
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalSplitPanel;
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
public class MainView extends HorizontalSplitPanel implements View {
    private static final long serialVersionUID = 2969812303192236717L;
    private final Logger LOG = LoggerFactory.getLogger(MainView.class);
    public static final String NAME = "main";


    /**
     * +-------+--------------------------------+
     * | TITLE | TABS                           |
     * |       +--------------------------------+
     * +-------+ BODY                           |
     * | NAV   |                                |
     * |       |                                |
     * |       |                                |
     * |       |                                |
     * +-------+                                |
     * | COPY  |                                |
     * +-------+--------------------------------+
     */

    private GridLayout titleAndNavigation;
    private GridLayout tabsAndBody;


    public MainView() {
        super();
    }


    @PostConstruct
    private void init() {
        setSizeFull();
        setSplitPosition(20f, Unit.PERCENTAGE);
        setStyleName(Styles.SPLITPANEL_SMALL);
        
        titleAndNavigation = new GridLayout(1, 3);
        tabsAndBody = new GridLayout(1, 2);
        
        setFirstComponent(titleAndNavigation);
        setSecondComponent(tabsAndBody);

        LOG.trace("Created: {}", this);
    }
    
   @PreDestroy
   public void close() {
       LOG.trace("Destroyed: {}", this);
       
   }

    
    public void setTitle(Component title) {
        LOG.debug("Setting title: {}", title);
        
        titleAndNavigation.removeComponent(0, 0);
        titleAndNavigation.addComponent(title, 0, 0);
    }

    public void setNavigation(Component navigation) {
        LOG.debug("Setting navivation: {}", navigation);
        
        titleAndNavigation.removeComponent(0, 1);
        titleAndNavigation.addComponent(navigation, 0, 1);
    }
    
    public void setCopyright(Component copyright) {
        LOG.debug("Setting copyright: {}", copyright);
        
        titleAndNavigation.removeComponent(0, 2);
        titleAndNavigation.addComponent(copyright, 0, 2);
        
    }

    
    public void setTabs(Component tabs) {
        LOG.debug("Setting tabs: {}", tabs);
        
        tabsAndBody.removeComponent(0, 0);
        tabsAndBody.addComponent(tabs, 0, 0);
    }
    
    public void setBody(Component body) {
        LOG.debug("Setting body: {}", body);

        tabsAndBody.removeComponent(0, 1);
        tabsAndBody.addComponent(body, 0, 1);
    }
    

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.trace("ViewChangeEvent: {}", event);
    }

}
