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
import com.vaadin.ui.TabSheet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.navigator.SpringViewProvider;
import org.vaadin.spring.navigator.VaadinView;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@UIScope
@VaadinView(name = TabPanelView.NAME)
public class TabPanelView extends TabSheet implements View {
        public static final String NAME = "tabPanel";
        private static Logger LOG = LoggerFactory.getLogger(TabPanelView.class);
        @Inject
        private SpringViewProvider viewProvider;

//        @Inject
//        private TabSelectedListener selectedListener;

        @PostConstruct
        private void init() {
            setVisible(false);
            setSizeFull();
//            addSelectedTabChangeListener(selectedListener);
        }

//        public void setOrigin(NavElement origin) {
//            String tabCollectionId = String.valueOf(origin.getId().intValue());
//            addCaptions(origin, getNavElements(tabCollectionId));
//        }
//
//        protected List<NavElement> getNavElements(String id) {
//            NavElementFactory factory = new NavElementFactory();
//            return factory.getNavElements("screens/" + id + "/tabs.json");
//        }
//
//        protected void addCaptions(NavElement origin, List<NavElement> targets) {
//            if (getComponentCount() > 0) {
//                removeAllComponents();
//            }
//            if (CollectionUtils.isNotEmpty(targets)) {
//                setVisible(true);
//                Component c;
//                String viewName;
//                for (NavElement target: targets) {
//                    viewName = getViewName(origin, target);
//                    c = generateTab(viewName);
//                    if (c != null) {
//                        addTab(c, target.getName());
//                    } else {
//                        LOG.warn("View [{}] not available (or not yet implemented).", viewName);
//                        addTab(DataGrid.emptyGrid(), target.getName());
//                    }
//                }
//            } else {
//                setVisible(false);
//            }
//        }

        private Component generateTab(String viewName) {
            return (Component) viewProvider.getView(viewName);
        }

//        private String getViewName(NavElement origin, NavElement target) {
//            StringBuilder builder = new StringBuilder();
//            builder.append(origin.getAlias());
//            builder.append("/");
//            builder.append(target.getAlias());
//            return builder.toString();
//        }

        @Override
        public void enter(ViewChangeListener.ViewChangeEvent event) {

        }

    }