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

import java.io.Serializable;
import java.util.Iterator;
import java.util.Locale;
import java.util.Spliterator;
import java.util.function.Consumer;

/**
 * @version 0.1.0
 * @since 0.1.0
 */
public interface Sort extends Serializable {

    Sort and(Sort sort);

    Sort.Order getOrderFor(String property);

    Iterator<Sort.Order> iterator();

    void forEach(Consumer<? super Sort.Order> action);

    Spliterator<Sort.Order> spliterator();



    public static enum Direction {
        ASC, DESC;

        public static Direction fromString(String value) {

            try {
                return Direction.valueOf(value.toUpperCase(Locale.US));
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format(
                        "Invalid value '%s' for orders given! Has to be either 'desc' or 'asc' (case insensitive).", value), e);
            }
        }

        public static Direction fromStringOrNull(String value) {

            try {
                return fromString(value);
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    public static enum NullHandling {
        /**
         * Lets the data store decide what to do with nulls.
         */
        NATIVE,

        /**
         * A hint to the used data store to order entries with null values before non null entries.
         */
        NULLS_FIRST,

        /**
         * A hint to the used data store to order entries with null values after non null entries.
         */
        NULLS_LAST;


        public static NullHandling fromString(String value) {
            try {
                return NullHandling.valueOf(value.toUpperCase(Locale.US));
            } catch (Exception e) {
                throw new IllegalArgumentException(String.format(
                        "Invalid value '%s' for orders given! Has to be either 'NATIVE', 'NULLS_FIRST' or 'NULLS_LAST' (case insensitive).", value), e);
            }
        }
    }

    /**
     * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
     * @version 0.1.0
     * @since 0.1.0
     */
    public interface Order extends Serializable {
        Sort.Direction getDirection();

        Sort.Order with(Sort.NullHandling nullHandling);

        Sort withProperties(String... properties);

        Sort.Order nullsLast();

        String getProperty();

        Sort.NullHandling getNullHandling();

        Sort.Order ignoreCase();

        boolean isAscending();

        boolean isIgnoreCase();

        Sort.Order nullsFirst();

        Sort.Order with(Sort.Direction order);

        Sort.Order nullsNative();
    }
}