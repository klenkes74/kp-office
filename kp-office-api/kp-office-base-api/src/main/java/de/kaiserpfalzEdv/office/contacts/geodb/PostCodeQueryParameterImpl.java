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

package de.kaiserpfalzEdv.office.contacts.geodb;

import de.kaiserpfalzEdv.office.contacts.geodb.spi.PostCodeQuery;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 25.02.15 22:31
 */
public class PostCodeQueryParameterImpl implements PostCodeQuery.QueryParameter {
    private static final long serialVersionUID = -6523156119507876680L;


    private String key;
    private String value;


    @Deprecated // Only for Jackson, JAX-B, JPA, ...
    protected PostCodeQueryParameterImpl() {}

    @SuppressWarnings("deprecation")
    PostCodeQueryParameterImpl(@NotNull final String key, @NotNull final String value) {
        setKey(key);
        setValue(value);
    }


    @Override
    public String getKey() {
        return key;
    }

    @Deprecated // Only for Jackson, JAX-B, JPA, ...
    public void setKey(@NotNull final String key) {
        this.key = key;
    }


    @Override
    public String getValue() {
        return value;
    }

    @Deprecated // Only for Jackson, JAX-B, JPA, ...
    public void setValue(@NotNull final String value) {
        this.value = value;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!PostCodeQuery.QueryParameter.class.isAssignableFrom(obj.getClass())) {
            return false;
        }

        PostCodeQuery.QueryParameter rhs = (PostCodeQuery.QueryParameter) obj;
        return new EqualsBuilder()
                .append(this.key, rhs.getKey())
                .append(this.value, rhs.getValue())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(key)
                .append(value)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(key, value)
                .toString();
    }
}
