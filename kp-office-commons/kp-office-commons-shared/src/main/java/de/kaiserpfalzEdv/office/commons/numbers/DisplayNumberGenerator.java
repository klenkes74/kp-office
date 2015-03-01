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

package de.kaiserpfalzEdv.office.commons.numbers;

/**
 * This interface is for creating number generators for the different kind of entities within the database. These
 * generators are custom generators needed for customizing for the special needs of the clients.
 * <p>
 * The uniqueness is defined as "unique within tenant". That means, that unique number 1234 may be valid for tenant A
 * and valid for tenant B. But not two times for tenant A.
 *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public interface DisplayNumberGenerator {
    /**
     * Generates the unique number for this object. The algorithm is client dependent. It may be a database sequence
     * or a special case resulting in something like "II'14-1231 A 13". Your mileage may vary.
     *
     * @param data The data to generate the display number with.
     *
     * @return A unique number for this object.
     *
     * @throws NumberGenerationFailureException The number could not be generated with the data given.
     */
    public String generate(Object... data) throws NumberGenerationFailureException;
}
