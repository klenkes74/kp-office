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
import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.commons.api.BuilderException;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-09
 */
public class PredicateBuilder<T extends Serializable, V extends Serializable> implements Builder<Predicate<T>> {
    private static final Logger LOG = LoggerFactory.getLogger(PredicateBuilder.class);

    private String entity;
    private Class<?> clasz;
    private Predicate.Comparator comparator;
    private V value;
    private Collection<V> values;

    public PredicateBuilder<T, V> withAttribute(@NotNull final String entity) {
        this.entity = entity;
        this.clasz = String.class;
        return this;
    }

    public Predicate<T> isLowerAs(@NotNull final V value) {
        this.value = value;
        this.comparator = Predicate.Comparator.LOWER_AS;
        this.clasz = (UUID.class.equals(value.getClass())) ? String.class : value.getClass();
        return build();
    }

    @Override
    public Predicate<T> build() {
        validate();

        return new AttributePredicate<T, V>(
                this.clasz,
                this.entity,
                this.comparator,
                this.value,
                this.values
        );
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (Predicate.Comparator.IN.equals(comparator) && (values == null || values.size() == 0)) {
            failures.add("The IN comparator needs at least one element in the values collection!");
        }

        if (!Predicate.Comparator.IN.equals(comparator) && value == null) {
            failures.add("The comparator " + comparator + " needs a value!");
        }


        if (failures.size() > 0) {
            throw new BuilderException(clasz, failures);
        }
    }

    public Predicate<T> isLowerAsOrEqualTo(@NotNull final V value) {
        this.value = value;
        this.comparator = Predicate.Comparator.LOWER_AS_OR_EQUALS;
        this.clasz = (UUID.class.equals(value.getClass())) ? String.class : value.getClass();
        return build();
    }

    public Predicate<T> isEqualTo(@NotNull final V value) {
        this.value = value;
        this.comparator = Predicate.Comparator.EQUALS;
        this.clasz = (UUID.class.equals(value.getClass())) ? String.class : value.getClass();
        return build();
    }

    public Predicate<T> isNotEqualTo(@NotNull final V value) {
        this.value = value;
        this.comparator = Predicate.Comparator.NOT_EQUALS;
        this.clasz = (UUID.class.equals(value.getClass())) ? String.class : value.getClass();
        return build();
    }

    public Predicate<T> isBiggerAsOrEqualTo(@NotNull final V value) {
        this.value = value;
        this.comparator = Predicate.Comparator.BIGGER_AS_OR_EQUALS;
        this.clasz = (UUID.class.equals(value.getClass())) ? String.class : value.getClass();
        return build();
    }

    public Predicate<T> isBiggerAs(@NotNull final V value) {
        this.value = value;
        this.comparator = Predicate.Comparator.BIGGER_AS;
        this.clasz = (UUID.class.equals(value.getClass())) ? String.class : value.getClass();
        return build();
    }

    public Predicate<T> in(@NotNull final Collection<V> values) {
        this.values = values;
        this.comparator = Predicate.Comparator.IN;
        this.clasz = (UUID.class.equals(values.iterator().next().getClass())) ? String.class : values.iterator()
                                                                                                     .next()
                                                                                                     .getClass();

        return build();
    }
}
