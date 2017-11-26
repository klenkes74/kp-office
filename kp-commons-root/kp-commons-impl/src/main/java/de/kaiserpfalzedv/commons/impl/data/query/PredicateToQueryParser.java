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

package de.kaiserpfalzedv.commons.impl.data.query;

import java.io.Serializable;

import de.kaiserpfalzedv.commons.api.data.base.Identifiable;
import de.kaiserpfalzedv.commons.api.data.query.And;
import de.kaiserpfalzedv.commons.api.data.query.AttributePredicate;
import de.kaiserpfalzedv.commons.api.data.query.JoinPredicate;
import de.kaiserpfalzedv.commons.api.data.query.Or;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.commons.api.data.query.PredicateQueryGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-09
 */
public class PredicateToQueryParser<T extends Identifiable> implements PredicateQueryGenerator<T> {
    private static final Logger LOG = LoggerFactory.getLogger(PredicateToQueryParser.class);

    @Override
    public String generateQuery(Predicate<T> predicate) {
        return predicate.generateQuery(this);
    }

    @Override
    public String generateQuery(And<T> predicate) {
        return new StringBuilder("(")
                .append(generateQuery(predicate.getLeft()))
                .append(") and (")
                .append(generateQuery(predicate.getRight()))
                .append(")").toString();
    }

    @Override
    public String generateQuery(Or<T> predicate) {
        return new StringBuilder("(")
                .append(generateQuery(predicate.getLeft()))
                .append(") or (")
                .append(generateQuery(predicate.getRight()))
                .append(")").toString();
    }

    @Override
    public <V extends Identifiable> String generateQuery(AttributePredicate<T, V> predicate) {
        return new StringBuilder(predicate.getName())
                .append(predicate.getComparatorNotation())
                .append(":" + predicate.getName() + "_" + predicate.getComparator().toString())
                .toString();
    }

    @Override
    public <J extends Identifiable> String generateQuery(JoinPredicate<T, J> predicate) {
        PredicateToQueryParser<J> secondParser = new PredicateToQueryParser<>();

        return new StringBuilder()
                .append(secondParser.generateQuery(predicate.getPredicates()))
                .toString();
    }
}
