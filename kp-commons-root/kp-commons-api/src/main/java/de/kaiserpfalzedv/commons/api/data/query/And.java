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

import javax.validation.constraints.NotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-08
 */
public class And<T extends Serializable> extends AbstractBasePredicateAction<T> {
    private static final Logger LOG = LoggerFactory.getLogger(And.class);

    public And(@NotNull final Predicate<T> left, @NotNull final Predicate<T> right) {
        super(left, right);
    }

    @Override
    public String generateQuery(PredicateQueryGenerator<T> visitor) {
        return visitor.generateQuery(this);
    }

    @Override
    public List<QueryParameter<T>> generateParameter(PredicateParameterGenerator<T> visitor) {
        return visitor.generateParameters(this);
    }


}
