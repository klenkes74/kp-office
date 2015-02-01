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
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import de.kaiserpfalzEdv.office.ui.web.presenter.ControlsContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.navigator.VaadinView;

import javax.annotation.PostConstruct;
import java.util.List;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@UIScope
@VaadinView(name = HeaderView.NAME)
public class HeaderView extends HorizontalLayout implements View {
    private static final long serialVersionUID = -8332740438088374140L;
    private final Logger LOG = LoggerFactory.getLogger(HeaderView.class);
    public static final String NAME = "header";

    @PostConstruct
    private void init() {
        setWidth("100%");
    }

    public void setContext(ControlsContext context) {
        if (getComponentCount() > 0) {
            removeAllComponents();
        }
        addComponent(buildControlsArea(context));
    }

    protected HorizontalLayout buildControlsArea(ControlsContext context) {
        HorizontalLayout left = new HorizontalLayout();
        left.setSpacing(true);
        left.setDefaultComponentAlignment(Alignment.MIDDLE_CENTER);

        // ---- DYNAMIC HEADER ELEMENTS ----
        // Rendering depends on what tab was selected
        List<Component> controls = context.getControls();
        LayoutIntegrator.addComponents(left, controls.toArray(new Component[controls.size()]));

        return left;
    }


    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.trace("ChangeEvent: {}", event);
    }
}