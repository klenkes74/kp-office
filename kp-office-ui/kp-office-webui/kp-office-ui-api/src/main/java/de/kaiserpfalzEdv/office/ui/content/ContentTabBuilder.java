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

package de.kaiserpfalzEdv.office.ui.content;

import com.vaadin.ui.Component;
import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.ui.web.widgets.content.events.AddMainTabEvent;
import de.kaiserpfalzEdv.office.ui.web.widgets.content.events.RemoveMainTabEvent;
import de.kaiserpfalzEdv.office.ui.web.widgets.content.events.ReplaceMainTabEvent;
import org.apache.commons.lang3.builder.Builder;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 14:28
 */
public class ContentTabBuilder implements Builder<ContentTab> {
    private UUID      id;
    private String    title;
    private Component component;


    @Override
    public ContentTab build() {
        validate();

        return new ContentTabImpl(id, title, component);
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (id == null) failures.add("No content tab id set!");
        if (title == null) failures.add("No content tab title set!");
        if (component == null) failures.add("No content for the content tab set!");

        if (failures.size() > 0) {
            throw new BuilderException(failures);
        }
    }


    public AddMainTabEvent addMainTabEvent() {
        return new AddMainTabEvent(build());
    }

    public ReplaceMainTabEvent replaceMainTabEvent() {
        return new ReplaceMainTabEvent(build());
    }

    public RemoveMainTabEvent removeMainTabEvent() {
        if (id == null)
            throw new BuilderException("No content tab id set!");

        return new RemoveMainTabEvent(id);

    }


    public ContentTabBuilder withId(@NotNull final UUID id) {
        this.id = id;

        return this;
    }

    public ContentTabBuilder generateId() {
        this.id = UUID.randomUUID();

        return this;
    }

    public ContentTabBuilder withTitle(@NotNull final String title) {
        this.title = title;

        return this;
    }

    public ContentTabBuilder withComponent(@NotNull final Component component) {
        this.component = component;

        return this;
    }
}
