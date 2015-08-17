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

package de.kaiserpfalzEdv.office.core.queries;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.validation.constraints.NotNull;

/**
 * A generic period of compareable objects.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 08.08.15 22:45
 */
public class Period<T extends Comparable> {
    private T first;
    private T last;


    public Period(final T first, final T last) {
        this.first = first;
        this.last = last;
    }

    /**
     * @deprecated Only for JAX-B, Jackson and JPA and such crappy interfaces.
     */
    @Deprecated
    public Period() {}


    public boolean isWithin(@NotNull final Period<T> other) {
        return isWithin(other.getFirst()) && isWithin(other.getLast());
    }

    public boolean isWithin(@NotNull final T other) {
        return last.compareTo(other) >= 0 && first.compareTo(other) <= 0;
    }

    public boolean overlaps(@NotNull final Period<T> other) {
        return overlapStart(other) || overlapLast(other);
    }


    public boolean overlapStart(@NotNull final Period<T> other) {
        return isWithin(other.getLast()) && (!isWithin(other.getFirst()) || other.getFirst().equals(first));
    }

    public boolean overlapLast(@NotNull final Period<T> other) {
        return isWithin(other.getFirst()) && (!isWithin(other.getLast()) || other.getLast().equals(last));
    }


    public T getFirst() {
        return first;
    }

    public void setFirst(@NotNull T first) {
        this.first = first;
    }


    public T getLast() {
        return last;
    }

    public void setLast(@NotNull T last) {
        this.last = last;
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
        Period rhs = (Period) obj;
        return new EqualsBuilder()
                .append(this.first, rhs.getFirst())
                .append(this.last, rhs.getLast())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(first)
                .append(last)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append(first)
                .append(last)
                .toString();
    }
}
