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

import de.kaiserpfalzedv.commons.api.data.base.Identifiable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-08
 */
public abstract class AbstractBasePredicateAction<T extends Identifiable> implements Predicate<T> {

    private Predicate<T> left;
    private Predicate<T> right;

    public AbstractBasePredicateAction(@NotNull final Predicate<T> left, @NotNull final Predicate<T> right) {
        this.left = left;
        this.right = right;
    }


    public Predicate<T> getLeft() {
        return left;
    }

    public Predicate<T> getRight() {
        return right;
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
    public int hashCode() {
        return Objects.hash(left, right);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof AbstractBasePredicateAction)) return false;
        AbstractBasePredicateAction<?> that = (AbstractBasePredicateAction<?>) o;
        return Objects.equals(left, that.left) &&
                Objects.equals(right, that.right);
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("left", left)
                .append("right", right)
                .toString();
    }
}
