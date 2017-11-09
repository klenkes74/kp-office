/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.commons.api.data.query;

import java.io.Serializable;
import java.util.Objects;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-09
 */
public class AttributeType<T extends Serializable> {
    private static final Logger LOG = LoggerFactory.getLogger(AttributeType.class);

    private Class<?> clasz;
    private String name;

    public AttributeType(Class<?> clasz, String name) {
        this.clasz = clasz;
        this.name = name;
    }

    public String quote(Serializable data) {
        if (String.class.isAssignableFrom(clasz)) {
            return String.format("\"%s\"", data);
        }

        return String.format("%s", data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getClasz(), getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributeType)) return false;
        AttributeType<?> that = (AttributeType<?>) o;
        return Objects.equals(getClasz(), that.getClasz()) &&
                Objects.equals(getName(), that.getName());
    }

    public Class<?> getClasz() {
        return clasz;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("clasz", clasz)
                .append("name", name)
                .toString();
    }
}
