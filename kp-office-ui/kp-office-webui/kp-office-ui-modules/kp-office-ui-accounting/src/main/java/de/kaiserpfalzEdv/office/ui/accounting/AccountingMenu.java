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

package de.kaiserpfalzEdv.office.ui.accounting;

import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.VerticalLayout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.annotation.VaadinSessionScope;
import org.vaadin.spring.navigator.annotation.VaadinView;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 16:13
 */
@VaadinSessionScope
@VaadinView(name = AccountingMenu.NAME)
public class AccountingMenu extends VerticalLayout implements View {
    static final         String NAME = "accounting.menu";
    private static final Logger LOG  = LoggerFactory.getLogger(AccountingMenu.class);


    public AccountingMenu() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    public void addButton(Button button) {
        addComponent(button);
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.trace("{} received: {}", this, event);
    }
}
