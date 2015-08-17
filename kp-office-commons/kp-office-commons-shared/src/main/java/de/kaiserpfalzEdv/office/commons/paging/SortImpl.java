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

package de.kaiserpfalzEdv.office.commons.paging;

import de.kaiserpfalzEdv.commons.jee.paging.Sort;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 16.08.15 10:08
 */
public class SortImpl implements Sort {
    private static final long serialVersionUID = -6003544636544539613L;


    private final ArrayList<Order> orders = new ArrayList<>();


    public SortImpl(String... properties) {
        this(Direction.ASC, properties);
    }


    public SortImpl(Direction direction, String... properties) {
        for (String p : properties) {
            orders.add(new OrderImpl(p, direction));
        }
    }

    private SortImpl() {}

    @Override
    public Sort and(Sort sort) {
        SortImpl result = new SortImpl();

        result.orders.addAll(orders);

        sort.forEach(result.orders::add);

        return result;
    }

    @Override
    public Order getOrderFor(String property) {
        for (Order o : orders) {
            if (o.getProperty().equals(property))
                return o;
        }

        return null;
    }

    @Override
    public Iterator<Order> iterator() {
        return orders.iterator();
    }

    @Override
    public void forEach(Consumer<? super Order> action) {
        orders.forEach(action);
    }

    @Override
    public Spliterator<Order> spliterator() {
        return orders.spliterator();
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof SortImpl)) return false;

        SortImpl sort = (SortImpl) o;

        return new EqualsBuilder()
                .append(orders, sort.orders)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(orders)
                .toHashCode();
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("orders", orders)
                .toString();
    }


    public class OrderImpl implements Sort.Order {
        private static final long serialVersionUID = -3928205208652733842L;


        private final String       property;
        private final Direction    direction;
        private final NullHandling nullHandling;
        private final boolean      ignoreCase;


        OrderImpl(final String property, final Direction direction) {
            this(property, direction, NullHandling.NATIVE, false);
        }


        OrderImpl(final String property, final Direction direction, final NullHandling nullHandling, final boolean ignoreCase) {
            this.property = property;
            this.direction = direction;
            this.nullHandling = nullHandling;
            this.ignoreCase = ignoreCase;
        }

        @Override
        public Direction getDirection() {
            return direction;
        }

        @Override
        public Order with(NullHandling nullHandling) {
            return new OrderImpl(property, direction, nullHandling, ignoreCase);
        }

        @Override
        public Sort withProperties(String... properties) {
            return new SortImpl(direction, properties);
        }

        @Override
        public Order nullsLast() {
            return with(NullHandling.NULLS_LAST);
        }

        @Override
        public String getProperty() {
            return property;
        }

        @Override
        public NullHandling getNullHandling() {
            return nullHandling;
        }

        @Override
        public Order ignoreCase() {
            return new OrderImpl(property, direction, nullHandling, true);
        }

        @Override
        public boolean isAscending() {
            return direction.equals(Direction.ASC);
        }

        @Override
        public boolean isIgnoreCase() {
            return ignoreCase;
        }

        @Override
        public Order nullsFirst() {
            return new OrderImpl(property, direction, NullHandling.NULLS_FIRST, ignoreCase);
        }

        @Override
        public Order with(Direction order) {
            return new OrderImpl(property, order, nullHandling, ignoreCase);
        }

        @Override
        public Order nullsNative() {
            return new OrderImpl(property, direction, NullHandling.NATIVE, ignoreCase);
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
            OrderImpl rhs = (OrderImpl) obj;
            return new EqualsBuilder()
                    .append(this.property, rhs.property)
                    .append(this.direction, rhs.direction)
                    .append(this.nullHandling, rhs.nullHandling)
                    .append(this.ignoreCase, rhs.ignoreCase)
                    .isEquals();
        }

        @Override
        public int hashCode() {
            return new HashCodeBuilder()
                    .append(property)
                    .append(direction)
                    .append(nullHandling)
                    .append(ignoreCase)
                    .toHashCode();
        }


        @Override
        public String toString() {
            return new ToStringBuilder(this)
                    .append("property", property)
                    .append("direction", direction)
                    .append("nullHandling", nullHandling)
                    .append("ignoreCase", ignoreCase)
                    .toString();
        }
    }
}
