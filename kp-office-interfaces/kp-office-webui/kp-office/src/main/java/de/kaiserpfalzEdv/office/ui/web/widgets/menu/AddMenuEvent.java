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

package de.kaiserpfalzEdv.office.ui.web.widgets.menu;

import com.vaadin.ui.Component;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 09:30
 */
public class AddMenuEvent extends AbstractMenuEvent {
    private String    title;
    private Component menu;


    public AddMenuEvent(final UUID menuId, final String title, final Component menu) {
        super(menuId);

        this.title = title;
        this.menu = menu;
    }

    public String getTitle() {
        return title;
    }

    public Component getMenu() {
        return menu;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("title", title)
                .append("menu", menu)
                .toString();
    }
}
