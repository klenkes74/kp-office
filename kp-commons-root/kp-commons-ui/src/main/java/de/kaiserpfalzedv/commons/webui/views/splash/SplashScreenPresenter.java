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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.addon.cdimvp.AbstractMVPPresenter;
import org.vaadin.addon.cdimvp.AbstractMVPPresenter.ViewInterface;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-07-03
 */
@ViewInterface(SplashScreenView.class)
public class SplashScreenPresenter extends AbstractMVPPresenter<SplashScreenView> {
    public static final String VIEW_ENTER = "SplashScreenPresenter_ve";
    private static final Logger LOG = LoggerFactory.getLogger(SplashScreenPresenter.class);

    @Override
    public void viewEntered() {
        LOG.info("Activated view: {}", view.getClass().getSimpleName());
    }
}
