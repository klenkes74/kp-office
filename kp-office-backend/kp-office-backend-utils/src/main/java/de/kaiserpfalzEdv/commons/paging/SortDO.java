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

package de.kaiserpfalzEdv.commons.paging;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.function.Consumer;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public class SortDO implements Sort {
    private org.springframework.data.domain.Sort sort;


    public SortDO(org.springframework.data.domain.Sort sort) {
        this.sort = sort;
    }


    public org.springframework.data.domain.Sort getSort() {
        return sort;
    }


    public Sort and(Sort sort) {
        return new SortDO(this.sort.and(((SortDO) sort).sort));
    }

    @Override
    public Sort.Order getOrderFor(String property) {
        return new OrderDO(sort.getOrderFor(property));
    }

    @Override
    public Iterator<Sort.Order> iterator() {
        ArrayList<Sort.Order> result = new ArrayList<>();

        sort.forEach(o -> result.add(new OrderDO(o)));

        return result.iterator();
    }

    @Override
    public void forEach(Consumer<? super Sort.Order> action) {
        iterator().forEachRemaining(action);
    }

    @Override
    public Spliterator<Sort.Order> spliterator() {
        return Spliterators.spliteratorUnknownSize(iterator(), 0);
    }


    public static class OrderDO implements Sort.Order {
        private org.springframework.data.domain.Sort.Order order;


        public OrderDO(org.springframework.data.domain.Sort.Order order) {
            this.order = order;
        }

        @Override
        public Sort.Direction getDirection() {
            return Direction.fromString(order.getDirection().toString());
        }

        @Override
        public Sort.Order with(Sort.NullHandling nullHandling) {
            return new OrderDO(order.with(org.springframework.data.domain.Sort.NullHandling.valueOf(nullHandling.toString())));
        }

        @Override
        public Sort withProperties(String... properties) {
            return new SortDO(order.withProperties(properties));
        }

        @Override
        public Sort.Order nullsLast() {
            return new OrderDO(order.nullsLast());
        }

        @Override
        public String getProperty() {
            return order.getProperty();
        }

        @Override
        public Sort.NullHandling getNullHandling() {
            return NullHandling.fromString(order.getNullHandling().toString());
        }

        @Override
        public Sort.Order ignoreCase() {
            return new OrderDO(order.ignoreCase());
        }

        @Override
        public boolean isAscending() {
            return order.isAscending();
        }

        @Override
        public boolean isIgnoreCase() {
            return order.isIgnoreCase();
        }

        @Override
        public Sort.Order nullsFirst() {
            return new OrderDO(order.nullsFirst());
        }

        @Override
        public Sort.Order with(Sort.Direction order) {
            return new OrderDO(this.order.with(org.springframework.data.domain.Sort.Direction.fromStringOrNull(order.toString())));
        }

        @Override
        public Sort.Order nullsNative() {
            return new OrderDO(order.nullsNative());
        }
    }
}
