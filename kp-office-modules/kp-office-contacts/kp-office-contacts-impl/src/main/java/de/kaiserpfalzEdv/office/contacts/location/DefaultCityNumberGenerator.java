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

package de.kaiserpfalzEdv.office.contacts.location;

import de.kaiserpfalzEdv.office.commons.DisplayNumberGenerator;
import de.kaiserpfalzEdv.office.commons.NumberGenerationFailureException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
public class DefaultCityNumberGenerator implements DisplayNumberGenerator {
    private static final Logger LOG = LoggerFactory.getLogger(DefaultCityNumberGenerator.class);

    @Override
    public String generate(Object... data) throws NumberGenerationFailureException {
        try {
            return ((Country) data[3]).getIso2() + "-" + data[1];
        } catch (Exception e) {
            return data[0].toString();
        }
    }
}
