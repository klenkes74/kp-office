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

package de.kaiserpfalzedv.commons.api.data;

/**
 * The retrieving method for retrieving objects via their business keys.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-30
 */
public interface BusinessKeyBaseService<T> {
    /**
     * Retrieves an object via its business key.
     *
     * @param businessKey The human readable business key. Commonly a number is used.
     *
     * @return The object associated with the business key.
     *
     * @throws ObjectDoesNotExistException If no object of the class has the business number.
     */
    T retrieve(String businessKey) throws ObjectDoesNotExistException;
}
