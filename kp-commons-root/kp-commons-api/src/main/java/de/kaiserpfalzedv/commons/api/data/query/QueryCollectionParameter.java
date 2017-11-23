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

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;

import javax.persistence.TypedQuery;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.data.base.Identifiable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-11
 */
public class QueryCollectionParameter<T extends Identifiable> implements QueryParameter<T> {
    private final HashSet<T> value = new HashSet<>();
    private String name;

    public QueryCollectionParameter(@NotNull final String name, @NotNull final Collection<T> value) {
        this.name = name;
        this.value.addAll(value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getValue());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof QueryCollectionParameter)) return false;
        QueryCollectionParameter<?> that = (QueryCollectionParameter<?>) o;
        return Objects.equals(getName(), that.getName()) &&
                Objects.equals(getValue(), that.getValue());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("name", name)
                .append("value", value)
                .toString();
    }

    public String getName() {
        return name;
    }

    public <P extends Identifiable> TypedQuery<P> setParameterToQuery(@NotNull TypedQuery<P> query) {
        return query.setParameter(name, value);
    }

    public Collection<T> getValue() {
        return value;
    }
}
