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

package de.kaiserpfalzedv.commons.webui.views.splash;

import javax.inject.Inject;

import com.vaadin.cdi.CDIView;
import com.vaadin.cdi.ViewScoped;
import com.vaadin.navigator.View;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.themes.ValoTheme;
import de.steinwedel.messagebox.MessageBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addon.cdimvp.AbstractMVPView;
import org.vaadin.addon.cdiproperties.annotation.CssLayoutProperties;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-07-03
 */
@CDIView("splash")
@ViewScoped
public class SplashScreenViewImpl extends AbstractMVPView implements View, SplashScreenView {
    private static final Logger LOG = LoggerFactory.getLogger(SplashScreenViewImpl.class);

    @Inject
    @CssLayoutProperties(sizeFull = true, styleName = {ValoTheme.UI_WITH_MENU})
    private CssLayout screen;

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

        setCompositionRoot(new Label("Test"));

        MessageBox
                .createInfo()
                .withCaption("Info")
                .withMessage("Hello World!")
                .withOkButton(() -> LOG.info("Ok pressed."))
                .open();
    }
}
