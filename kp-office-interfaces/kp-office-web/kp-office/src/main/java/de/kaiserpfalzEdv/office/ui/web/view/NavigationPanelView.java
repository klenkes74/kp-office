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
import com.vaadin.ui.Panel;
import com.vaadin.ui.Tree;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.navigator.VaadinView;

import javax.annotation.PostConstruct;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@UIScope
@VaadinView(name = NavigationPanelView.NAME)
public class NavigationPanelView extends Panel implements View {
    private static final Logger LOG = LoggerFactory.getLogger(NavigationPanelView.class);
    private static final long serialVersionUID = 1L;

    public static final String NAME = "navPanel";

//    @Inject
//    private NavigationPanelListener listener;

    private final Tree tree;

    public NavigationPanelView() {
        tree = new Tree();
    }

    @PostConstruct
    private void init() {
//        tree.addItemClickListener(listener);
        tree.setSizeUndefined();
        tree.setImmediate(true);
        setContent(tree);
        setSizeFull();
    }

//    public void setData(List<NavElement> navElements) {
//        NavContainerFactory nc = new NavContainerFactory(navElements);
//        tree.setContainerDataSource(nc.constructNavigationHierarchy());
//
//        // Expand whole tree
//        for (final Object id : tree.rootItemIds()) {
//            tree.expandItemsRecursively(id);
//        }
//    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {

    }

}