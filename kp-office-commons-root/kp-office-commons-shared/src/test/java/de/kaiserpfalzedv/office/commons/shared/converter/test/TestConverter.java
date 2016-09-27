/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.commons.shared.converter.test;

import java.util.Map;

import de.kaiserpfalzedv.office.commons.shared.converter.Converter;
import de.kaiserpfalzedv.office.commons.shared.converter.impl.AbstractConverterImpl;

import static org.apache.commons.lang3.Validate.notNull;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
class TestConverter extends AbstractConverterImpl<TestObject> implements Converter<TestObject> {
    @Override
    public TestObject createConversionResult(Map<String, Object> map) {
        notNull(map.get("id"), "No Id for the object given!");
        notNull(map.get("data"), "No data for the object given!");

        return new TestObject((Integer) map.get("id"), (String) map.get("data"));
    }
}
