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

package de.kaiserpfalzEdv.office.contacts.geodb.spi;

import de.kaiserpfalzEdv.office.contacts.geodb.GeoDBBusinessException;

import javax.validation.constraints.NotNull;

import static de.kaiserpfalzEdv.office.contacts.geodb.ErrorMessage.INVALID_POSTCODE_QUERY;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 25.02.15 22:52
 */
public class InvalidQueryException extends GeoDBBusinessException {


    private PostCodeQuery query;


    public InvalidQueryException(@NotNull final PostCodeQuery query) {
        super(INVALID_POSTCODE_QUERY);

        this.query = query;
    }


    public PostCodeQuery getData() {
        return query;
    }
}
