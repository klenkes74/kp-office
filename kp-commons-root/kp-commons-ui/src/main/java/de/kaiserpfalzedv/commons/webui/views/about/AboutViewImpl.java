/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.commons.webui.views.about;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.themes.ValoTheme;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addon.cdimvp.AbstractMVPView;
import org.vaadin.addon.cdiproperties.annotation.CssLayoutProperties;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-02
 */
@CDIView("about")
@PermitAll
@ViewScoped
public class AboutViewImpl extends AbstractMVPView implements View, AboutView {
    private static final Logger LOG = LoggerFactory.getLogger(AboutViewImpl.class);

    @Inject
    @CssLayoutProperties(sizeFull = true, styleName = {ValoTheme.UI_WITH_MENU})
    private CssLayout screen;

    private Component libraries;
    private Component schemaChanges;

    @Override
    public void enter(ViewChangeListener.ViewChangeEvent event) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("Opening {}: {}", event.getViewName(), this);
        }

        enter();
    }

    @Override
    public void enter() {
        super.enter();

        screen = new CssLayout();
        setCompositionRoot(screen);

        if (libraries != null) {
            screen.addComponent(libraries);
        }

        if (schemaChanges != null) {
            screen.addComponent(schemaChanges);
        }
    }

    @Override
    public void setLibraries(SoftwareDisclaimersContainer libraries) {
        if (this.libraries != null) {
            screen.removeComponent(this.libraries);
        }

        this.libraries = libraries;
        screen.addComponent(this.libraries);
    }

    @Override
    public void setSchemaChanges(DatabaseVersionContainer schemaChanges) {
        if (this.schemaChanges != null) {
            screen.removeComponent(this.schemaChanges);
        }

        this.schemaChanges = schemaChanges;
        screen.addComponent(this.schemaChanges);
    }


}
