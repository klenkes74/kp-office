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

package de.kaiserpfalzEdv.office.commons.data;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * The base data of every KP Office Entity. Every entity has an ID, a display name and a display number.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public abstract class KPOEntity implements Entity {
    private static final long serialVersionUID = -6143928839834376900L;

    private UUID id;

    private String displayName;

    private String displayNumber;

    private Boolean hidden = false;

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public KPOEntity() {
    }

    @SuppressWarnings("deprecation")
    public KPOEntity(@NotNull final UUID id, @NotNull final String displayName, @NotNull final String displayNumber) {
        setId(id);
        setDisplayName(displayName);
        setDisplayNumber(displayNumber);
    }

    public UUID getId() {
        return id;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setId(UUID id) {
        this.id = id;
    }


    public String getDisplayName() {
        return displayName;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setDisplayName(@NotNull String displayName) {
        this.displayName = displayName;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setDisplayName(@NotNull DisplayNameHolder nameHolder) {
        this.displayName = nameHolder.getDisplayName();
    }

    public String getDisplayNumber() {
        return displayNumber;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setDisplayNumber(@NotNull String displayNumber) {
        this.displayNumber = displayNumber;
    }


    public boolean isHidden() {
        return hidden == null || hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (KPOEntity.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        KPOEntity rhs = (KPOEntity) obj;
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
        ToStringBuilder result = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("id", id);

        if (hidden != null && !hidden) {
            result.append("displayName", displayName)
                  .append("displayNumber", displayNumber);
        } else {
            result.append("hidden", true);
        }

        return result.toString();
    }
}