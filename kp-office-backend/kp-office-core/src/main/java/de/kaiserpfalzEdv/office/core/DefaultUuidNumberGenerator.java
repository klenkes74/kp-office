/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.core;

/**
 * The default display number generator. Will return the UUID of the object as string.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public class DefaultUuidNumberGenerator implements DisplayNumberGenerator {
    /**
     * returns the string representation of the UUID (given as first element of the data parameter).
     *
     * @param data parameter list. Only the first element is counted and returned as string.
     * @return the UUID of the object.
     * @throws NumberGenerationFailureException If the number can not be generated.
     */
    @Override
    public String generate(Object... data) throws NumberGenerationFailureException {
        return data[0].toString();
    }
}
