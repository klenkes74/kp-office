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

package de.kaiserpfalzEdv.office.contacts.geodb;

import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.contacts.geodb.spi.PostCodeQuery;
import org.apache.commons.lang3.builder.Builder;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 25.02.15 22:42
 */
public class PostCodeQueryBuilder implements Builder<PostCodeQuery> {
    private final Map<String, PostCodeQuery.QueryParameter> parameters = new LinkedHashMap<>(5);


    @Override
    public PostCodeQuery build() {
        validate();

        return new PostCodeQueryImpl(parameters.values());
    }

    public void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (parameters.size() < 1) failures.add("No parameter for the post code query given.");

        if (failures.size() > 0) {
            throw new BuilderException(failures);
        }
    }


    public PostCodeQueryBuilder clear() {
        parameters.clear();

        return this;
    }


    public PostCodeQueryBuilder withCity(final String city) {
        parameters.put("city", new PostCodeQueryParameterImpl("city", city));

        return this;
    }

    public PostCodeQueryBuilder withId(final int locationId) {
        parameters.put("locationId", new PostCodeQueryParameterImpl("loc_id", Integer.valueOf(locationId).toString()));

        return this;
    }
}
