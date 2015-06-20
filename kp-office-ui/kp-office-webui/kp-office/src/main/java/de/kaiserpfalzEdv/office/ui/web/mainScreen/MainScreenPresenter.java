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

import com.vaadin.spring.annotation.UIScope;
import de.kaiserpfalzEdv.office.ui.web.widgets.content.ContentPresenter;
import de.kaiserpfalzEdv.office.ui.web.widgets.menu.NavigationPresenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.vaadin.spring.navigator.Presenter;

import javax.inject.Inject;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 17.02.15 20:29
 */
@UIScope
public class MainScreenPresenter extends Presenter<MainScreenView> {
    private static final Logger LOG = LoggerFactory.getLogger(MainScreenPresenter.class);


    @Inject
    private NavigationPresenter navigation;

    @Inject
    private ContentPresenter content;
}
