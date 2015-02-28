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

package de.kaiserpfalzEdv.office.ui.web.mainScreen;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.HorizontalSplitPanel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.annotation.VaadinSessionScope;
import org.vaadin.spring.navigator.annotation.VaadinView;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@VaadinSessionScope
@VaadinView(name = MainScreenView.NAME)
public class MainScreenView extends HorizontalSplitPanel implements View {
    static final String NAME = "mainScreen";
    private static final long serialVersionUID = 4064381291310584490L;
    private final        Logger LOG              = LoggerFactory.getLogger(MainScreenView.class);


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


    public MainScreenView() {
        super();
        
        LOG.trace("Created: {}", this);
    }


    @PostConstruct
    private void init() {
        setSizeFull();
        setHeight(100f, Unit.PERCENTAGE);
        setWidth(100f, Unit.PERCENTAGE);
        setSplitPosition(20f, Unit.PERCENTAGE);

        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);

    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.debug("{} received: {}", this, event);
    }
}