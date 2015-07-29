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

package de.kaiserpfalzEdv.office.ui.menu;

import com.vaadin.navigator.View;
import com.vaadin.server.Resource;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 13:08
 */
public interface MenuEntry extends View {
    int HIGHEST = 90;
    int HIGH    = 70;
    int MEDIUM  = 50;
    int LOW     = 30;
    int LOWEST  = 10;

    String getViewName();

    String getCaption();

    Resource getIcon();

    int getSortOrder();
}
