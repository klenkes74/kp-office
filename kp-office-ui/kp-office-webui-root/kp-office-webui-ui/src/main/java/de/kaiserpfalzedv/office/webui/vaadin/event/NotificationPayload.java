/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.vaadin.event;

import com.vaadin.server.FontAwesome;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * The data for a notification. It consists at least of a description but can be decorated by a caption and
 * an icon from {@link com.vaadin.server.FontAwesome} (just give the string representation).
 *
 * @author klenkes
 * @version 2015Q1
 * @since 09.09.15 20:06
 */
public class NotificationPayload implements Serializable {
    private static final long serialVersionUID = 269910610492153834L;

    private String      caption;
    private String      description;
    private FontAwesome icon;
    private Object[]    params;

    public NotificationPayload(String description) {
        this.description = description;
    }

    public String getCaption() {
        return caption;
    }

    public NotificationPayload setCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public FontAwesome getIcon() {
        return icon;
    }

    public NotificationPayload setIcon(String icon) {
        this.icon = FontAwesome.valueOf(icon);
        return this;
    }

    public Object[] getParams() {
        return params;
    }

    public NotificationPayload setParams(Object... params) {
        this.params = params;
        return this;
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
        NotificationPayload rhs = (NotificationPayload) obj;
        return new EqualsBuilder()
                .append(this.caption, rhs.caption)
                .append(this.description, rhs.description)
                .append(this.icon, rhs.icon)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(caption)
                .append(description)
                .append(icon)
                .toHashCode();
    }

    @Override
    public String toString() {
        ToStringBuilder result = new ToStringBuilder(this);

        if (isNotBlank(caption))
            result.append("caption", caption);

        result.append("description", description);

        if (icon != null)
            result.append("icon", icon);

        if (params != null)
            result.append("params", params);

        return result.toString();
    }
}