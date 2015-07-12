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

package de.kaiserpfalzEdv.office.ui.web.widgets.menu.events;

import com.vaadin.ui.Component;
import de.kaiserpfalzEdv.office.ui.menu.Menu;
import de.kaiserpfalzEdv.office.ui.menu.MenuBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 07:08
 */
public class ReplaceMenuEvent extends AbstractMenuEvent {
    private Menu menu;


    @Deprecated
    public ReplaceMenuEvent(final Object source, final UUID id, final Component component) {
        super(source, id);

        menu = new MenuBuilder().withId(id).withComponent(component).withTitle("old title").build();
    }

    public ReplaceMenuEvent(final Object source, final Menu menu) {
        super(source, menu.getId());

        this.menu = menu;
    }


    public Menu getMenu() {
        return menu;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(menu)
                .toString();
    }
}
