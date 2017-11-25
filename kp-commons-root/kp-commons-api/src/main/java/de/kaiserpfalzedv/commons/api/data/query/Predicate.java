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
import java.util.List;

import de.kaiserpfalzedv.commons.api.data.base.Identifiable;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public interface Predicate<T extends Identifiable> extends Serializable {
    Predicate<T> and(Predicate<T> join);

    Predicate<T> or(Predicate<T> join);

    String generateQuery(PredicateQueryGenerator<T> visitor);

    List<QueryParameter<T>> generateParameter(PredicateParameterGenerator<T> visitor);

    enum Comparator {
        LOWER_AS("<"),
        LOWER_AS_OR_EQUALS("<="),
        EQUALS("="),
        NOT_EQUALS("<>"),
        BIGGER_AS_OR_EQUALS(">="),
        BIGGER_AS(">"),
        IN(" IN ");

        private String sign;

        Comparator(final String sign) {
            this.sign = sign;
        }

        public String getNotation() {
            return sign;
        }
    }

}
