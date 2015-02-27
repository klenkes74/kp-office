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

package de.kaiserpfalzEdv.office.contacts.geodb.impl;

import com.mysema.query.types.Predicate;
import com.mysema.query.types.expr.BooleanExpression;
import de.kaiserpfalzEdv.office.contacts.geodb.spi.InvalidQueryException;
import de.kaiserpfalzEdv.office.contacts.geodb.spi.PostCodeQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 25.02.15 23:08
 */
public class PostCodeQueryParser {
    private static final Logger                   LOG          = LoggerFactory.getLogger(PostCodeQueryParser.class);
    private static final QKPOInternalPostCodeImpl PostCodeImpl = QKPOInternalPostCodeImpl.kPOInternalPostCodeImpl;


    public static Predicate withQuery(@NotNull final PostCodeQuery query) throws InvalidQueryException {
        if (query == null || query.getParameters().size() == 0) {
            throw new InvalidQueryException(query);
        }

        Predicate result = null;

        for (PostCodeQuery.QueryParameter param : query.getParameters()) {
            String key = param.getKey();
            String value = param.getValue();


            BooleanExpression add;
            switch (key) {
                case "city":
                    add = (BooleanExpression) withCity(value);
                    break;
                case "loc_id":
                    add = (BooleanExpression) withLocationId(Integer.valueOf(value));
                    break;
                default:
                    throw new InvalidQueryException(query);
            }

            if (result == null) {
                result = add;
            } else {
                result = ((BooleanExpression) result).and(add);
            }
        }

        return result;
    }


    public static Predicate withCity(@NotNull final String city) {
        return PostCodeImpl.city.startsWith(city);
    }

    public static Predicate withLocationId(final int locationId) {
        return PostCodeImpl.id.eq(locationId);
    }
}
