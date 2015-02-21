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
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.02.15 14:22
 */
public class ContentTabImpl implements ContentTab {
    private static final long serialVersionUID = -6176881495355133079L;


    private UUID      id;
    private String    title;
    private Component component;


    ContentTabImpl(
            @NotNull final UUID id,
            @NotNull final String title,
            @NotNull final Component component
    ) {
        this.id = id;
        this.title = title;
        this.component = component;
    }


    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public Component getComponent() {
        return component;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        ContentTabImpl rhs = (ContentTabImpl) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("id", id)
                .append("title", title)
                .toString();
    }
}
