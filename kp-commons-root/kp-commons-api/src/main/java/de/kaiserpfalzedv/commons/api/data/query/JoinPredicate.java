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

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-09
 */
public class JoinPredicate<T extends Serializable, J extends Serializable> implements Predicate<T> {
    private static final Logger LOG = LoggerFactory.getLogger(JoinPredicate.class);

    private String entity;
    private Predicate<J> predicates;

    public JoinPredicate(@NotNull final String entity, @NotNull final Predicate<J> predicates) {
        this.entity = entity;
        this.predicates = predicates;
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
    public String host(PredicateVisitor<T> visitor) {
        return visitor.visit(this);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEntity(), getPredicates());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof JoinPredicate)) return false;
        JoinPredicate<?, ?> that = (JoinPredicate<?, ?>) o;
        return Objects.equals(getEntity(), that.getEntity()) &&
                Objects.equals(getPredicates(), that.getPredicates());
    }

    public String getEntity() {
        return entity;
    }

    public Predicate<J> getPredicates() {
        return predicates;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("entity", entity)
                .append("predicates", predicates)
                .toString();
    }
}
