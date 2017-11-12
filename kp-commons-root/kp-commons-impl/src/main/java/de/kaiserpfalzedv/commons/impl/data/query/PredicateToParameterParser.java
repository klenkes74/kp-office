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
import java.util.ArrayList;
import java.util.List;

import de.kaiserpfalzedv.commons.api.data.query.And;
import de.kaiserpfalzedv.commons.api.data.query.AttributePredicate;
import de.kaiserpfalzedv.commons.api.data.query.JoinPredicate;
import de.kaiserpfalzedv.commons.api.data.query.Or;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.commons.api.data.query.PredicateParameterGenerator;
import de.kaiserpfalzedv.commons.api.data.query.QueryParameter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-09
 */
public class PredicateToParameterParser<T extends Serializable> implements PredicateParameterGenerator<T> {
    private static final Logger LOG = LoggerFactory.getLogger(PredicateToParameterParser.class);

    @Override
    public List<QueryParameter> generateParameters(Predicate<T> predicate) {
        return predicate.generateParameter(this);
    }

    @Override
    public List<QueryParameter> generateParameters(And<T> predicate) {
        ArrayList<QueryParameter> result = new ArrayList<>();

        result.addAll(generateParameters(predicate.getLeft()));
        result.addAll(generateParameters(predicate.getRight()));

        return result;
    }

    @Override
    public List<QueryParameter> generateParameters(Or<T> predicate) {
        ArrayList<QueryParameter> result = new ArrayList<>();

        result.addAll(generateParameters(predicate.getLeft()));
        result.addAll(generateParameters(predicate.getRight()));

        return result;
    }

    @Override
    public <V extends Serializable> List<QueryParameter> generateParameters(AttributePredicate<T, V> predicate) {
        ArrayList<QueryParameter> result = new ArrayList<>();

        result.add(new QueryParameter(predicate.getName()
                                              + "_" + predicate.getComparator().toString(), predicate.getValue()));

        return result;
    }

    @Override
    public <J extends Serializable> List<QueryParameter> generateParameters(JoinPredicate<T, J> predicate) {
        PredicateToParameterParser<J> secondParser = new PredicateToParameterParser<>();

        ArrayList<QueryParameter> result = new ArrayList<>();

        result.addAll(secondParser.generateParameters(predicate.getPredicates()));

        return result;
    }
}
