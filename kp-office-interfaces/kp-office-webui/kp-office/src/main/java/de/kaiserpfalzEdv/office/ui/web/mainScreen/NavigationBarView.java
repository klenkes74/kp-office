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
import com.vaadin.ui.GridLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.annotation.VaadinSessionScope;
import org.vaadin.spring.navigator.annotation.VaadinView;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.02.15 20:34
 */
@VaadinSessionScope
@VaadinView(name = NavigationBarView.NAME)
public class NavigationBarView extends GridLayout implements View {
    private static final Logger LOG = LoggerFactory.getLogger(NavigationBarView.class);
    static final String NAME = "MAIN.NAVIGATION";

    
    public NavigationBarView() {
        super(1, 3);
    }

    
    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.debug("{} received: {}", this, event);
    }
}
