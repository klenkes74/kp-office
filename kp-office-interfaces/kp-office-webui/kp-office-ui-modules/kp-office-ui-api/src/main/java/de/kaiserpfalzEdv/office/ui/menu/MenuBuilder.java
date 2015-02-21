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

import com.vaadin.ui.Component;
import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.ui.web.widgets.menu.events.AddMenuEvent;
import de.kaiserpfalzEdv.office.ui.web.widgets.menu.events.RemoveMenuEvent;
import de.kaiserpfalzEdv.office.ui.web.widgets.menu.events.ReplaceMenuEvent;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 13:10
 */
public class MenuBuilder implements Builder<Menu> {
    private static final Logger LOG = LoggerFactory.getLogger(MenuBuilder.class);

    public static int SORT_ORDER_MIN     = 0;
    public static int SORT_ORDER_MAX     = 100;
    public static int SORT_ORDER_DEFAULT = 50;

    private UUID   id;
    private String title;
    private int sortOrder = SORT_ORDER_DEFAULT;
    private Component component;


    public Menu build() {
        validate();

        return new MenuImpl(id, title, sortOrder, component);
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (id == null) failures.add("No menu id set!");
        if (isBlank(title)) failures.add("No title for the menu set!");
        if (component == null) failures.add("No menu!");

        if (sortOrder < SORT_ORDER_MIN || sortOrder > SORT_ORDER_MAX)
            failures.add("Sort order has to be between " + SORT_ORDER_MIN + " and " + SORT_ORDER_MAX + "!");

        if (failures.size() > 0) {
            LOG.error("Builder failed: {}", failures);
            throw new BuilderException(failures);
        }
    }

    public AddMenuEvent addMenuEvent() {
        return new AddMenuEvent(build());
    }

    public ReplaceMenuEvent replaceMenuEvent() {
        return new ReplaceMenuEvent(build());
    }

    public RemoveMenuEvent removeMenuEvent() {
        if (id == null)
            throw new BuilderException("No menu id set!");

        return new RemoveMenuEvent(id);
    }


    public MenuBuilder withId(@NotNull final UUID id) {
        this.id = id;

        return this;
    }

    public MenuBuilder generateId() {
        this.id = UUID.randomUUID();

        return this;
    }


    public MenuBuilder withTitle(@NotNull final String title) {
        this.title = title;

        return this;
    }


    public MenuBuilder withSortOrder(final int sortOrder) {
        this.sortOrder = sortOrder;

        return this;
    }


    public MenuBuilder withComponent(@NotNull final Component component) {
        this.component = component;

        return this;
    }
}
