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

import java.io.Serializable;
import java.util.Set;

/**
 * A generic interface for enabling the lookup via providers with different queries.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 25.02.15 13:53
 */
public interface PostCodeQuery extends Serializable {
    /**
     * @return The set of parameters to be searched for.
     */
    Set<QueryParameter> getParameters();

    public interface QueryParameter extends Serializable {
        String getKey();

        String getValue();
    }
}
