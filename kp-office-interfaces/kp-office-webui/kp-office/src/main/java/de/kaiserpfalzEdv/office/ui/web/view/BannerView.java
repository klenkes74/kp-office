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
import com.vaadin.ui.Button;
import com.vaadin.ui.GridLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Panel;
import de.kaiserpfalzEdv.office.ui.web.presenter.LogoutLinkListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.vaadin.spring.UIScope;
import org.vaadin.spring.navigator.VaadinView;

import javax.inject.Inject;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@UIScope
@VaadinView(name = BannerView.NAME)
public class BannerView extends Panel implements View {
    private static final long serialVersionUID = -8597063488807270783L;
    private final Logger LOG = LoggerFactory.getLogger(MainView.class);
    public static final String NAME = "banner";

    @Inject
    Environment env;

    @Inject
    private LogoutLinkListener logoutListener;

    public void setUser(String username) {
        setContent(buildRightArea(username));
    }

    protected Layout buildUserArea(String username) {
        HorizontalLayout userArea = new HorizontalLayout();
        String id = env.getProperty("app.security.scheme", "BASIC");
        if (id.equals("FORM")) {
            Button signOut = new Button("Sign Out");
            signOut.addClickListener(logoutListener);
            userArea.addComponent(signOut);
        }
        Label loggedInUser = new Label();
        loggedInUser.setValue(username);
        loggedInUser.setSizeUndefined();
        userArea.addComponent(loggedInUser);
        return userArea;
    }

    protected GridLayout buildRightArea(String username) {
        GridLayout right = new GridLayout(1, 1);
        right.setWidth(100f, Unit.PERCENTAGE);
        Layout loggedInUser = buildUserArea(username);
        right.addComponent(loggedInUser, 0, 0);
        right.setComponentAlignment(loggedInUser, Alignment.MIDDLE_RIGHT);
        return right;
    }

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        LOG.trace("ChangeEvent: {}", event);
    }
}