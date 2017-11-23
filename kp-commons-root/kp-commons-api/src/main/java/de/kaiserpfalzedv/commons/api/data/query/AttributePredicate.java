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
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-08
 */
public class AttributePredicate<T extends Serializable, V extends Serializable> implements Predicate<T> {
    private AttributeType<T> attributeType;
    private Comparator comparator;
    private V value;
    private Collection<V> values;

    AttributePredicate(
            @NotNull final Class<?> clasz,
            @NotNull final String name,
            @NotNull final Comparator comparator,
            final V value,
            final Collection<V> values
    ) {
        this.attributeType = new AttributeType<>(clasz, name);
        this.comparator = comparator;
        this.value = value;
        this.values = values;
    }

    @Override
    public Predicate<T> and(Predicate<T> join) {
        return new And<>(this, join);
    }

    @Override
    public Predicate<T> or(Predicate<T> join) {
        return new Or<>(this, join);
    }

    @Override
    public String generateQuery(PredicateQueryGenerator<T> visitor) {
        return visitor.generateQuery(this);
    }

    @Override
    public List<QueryParameter<T>> generateParameter(PredicateParameterGenerator<T> visitor) {
        return visitor.generateParameters(this);
    }

    public String getName() {
        return attributeType.getName();
    }

    public String quote() {
        return attributeType.quote(value);
    }

    public String getComparatorNotation() {
        return comparator.getNotation();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAttributeType(), getComparator(), getValue(), getValues());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AttributePredicate)) return false;
        AttributePredicate<T, V> predicate = (AttributePredicate<T, V>) o;
        return Objects.equals(getAttributeType(), predicate.getAttributeType()) &&
                getComparator() == predicate.getComparator() &&
                Objects.equals(getValue(), predicate.getValue()) &&
                Objects.equals(getValues(), predicate.getValues());
    }

    public AttributeType<T> getAttributeType() {
        return attributeType;
    }

    public Comparator getComparator() {
        return comparator;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        ToStringBuilder result = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("attributeType", attributeType)
                .append("comparator", comparator);

        if (value != null) {
            result.append("value", value);
        }

        if (values != null) {
            result.append("values", values);
        }


        return result.toString();
    }

    public Collection<V> getValues() {
        return values;
    }
}
