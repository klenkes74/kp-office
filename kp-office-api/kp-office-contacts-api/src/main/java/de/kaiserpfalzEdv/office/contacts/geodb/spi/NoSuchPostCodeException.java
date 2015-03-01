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

import static de.kaiserpfalzEdv.office.contacts.geodb.ErrorMessage.NO_SUCH_DATA;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 26.02.15 07:43
 */
public class NoSuchPostCodeException extends GeoDBBusinessException {
    private static final long serialVersionUID = -1766206947623052856L;


    private PostCodeQuery data;


    public NoSuchPostCodeException(@NotNull final PostCodeQuery query) {
        super(NO_SUCH_DATA);
    }


    public PostCodeQuery getData() {
        return data;
    }
}
