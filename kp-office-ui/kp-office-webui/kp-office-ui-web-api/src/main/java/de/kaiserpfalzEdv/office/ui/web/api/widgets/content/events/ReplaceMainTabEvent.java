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

package de.kaiserpfalzEdv.office.ui.web.api.widgets.content.events;

import com.vaadin.ui.Component;
import de.kaiserpfalzEdv.office.ui.web.api.content.ContentTab;
import de.kaiserpfalzEdv.office.ui.web.api.content.ContentTabBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 07:08
 */
public class ReplaceMainTabEvent extends AbstractMainTabEvent {
    private ContentTab tab;


    @Deprecated
    public ReplaceMainTabEvent(final Object source, final UUID id, final Component component) {
        super(source, id);

        tab = new ContentTabBuilder()
                .withId(id)
                .withComponent(component)
                .withTitle("old title")
                .build();
    }

    public ReplaceMainTabEvent(@NotNull final Object source, @NotNull final ContentTab tab) {
        super(source, tab.getId());

        this.tab = tab;
    }


    public ContentTab getTab() {
        return tab;
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
