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

package de.kaiserpfalzEdv.office.ui.web.widgets.content.events;

import com.vaadin.ui.Component;
import de.kaiserpfalzEdv.office.ui.content.ContentTab;
import de.kaiserpfalzEdv.office.ui.content.ContentTabBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 07:08
 */
public class AddMainTabEvent extends AbstractMainTabEvent {
    private ContentTab tab;


    @Deprecated
    public AddMainTabEvent(final UUID id, final String title, final Component component) {
        super(UUID.randomUUID());

        tab = new ContentTabBuilder()
                .withId(id)
                .withTitle(title)
                .withComponent(component)
                .build();
    }

    public AddMainTabEvent(@NotNull final ContentTab tab) {
        super(UUID.randomUUID());

        this.tab = tab;
    }


    public ContentTab getTab() {
        return tab;
    }


    public String getTitle() {
        return tab.getTitle();
    }

    public Component getComponent() {
        return tab.getComponent();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append(tab)
                .toString();
    }
}
