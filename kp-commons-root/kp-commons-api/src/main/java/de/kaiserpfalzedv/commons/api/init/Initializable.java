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

package de.kaiserpfalzedv.commons.api.init;

import java.util.Properties;

/**
 * The default class initialization.
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-21
 */
public interface Initializable {
    /**
     * Initializes the class.
     *
     * @throws InitializationException       If the initialization failes.
     * @throws UnsupportedOperationException If the propertyless initialization is not supported.
     */
    void init() throws InitializationException;

    /**
     * Initializes the class with the properties given.
     *
     * @param properties Properties needed for initializing the class.
     *
     * @throws InitializationException       If the initialization failes.
     * @throws UnsupportedOperationException If the initialization with properties is not supported.
     */
    void init(Properties properties) throws InitializationException;
}
