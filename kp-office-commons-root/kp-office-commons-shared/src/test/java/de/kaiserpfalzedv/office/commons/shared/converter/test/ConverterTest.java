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

import de.kaiserpfalzedv.office.commons.shared.converter.Converter;
import de.kaiserpfalzedv.office.commons.shared.converter.ConverterRegistry;
import de.kaiserpfalzedv.office.commons.shared.converter.NoMatchingConverterFoundException;
import de.kaiserpfalzedv.office.commons.shared.converter.impl.ConverterRegistryImpl;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public class ConverterTest {
    private static final Logger LOG = LoggerFactory.getLogger(ConverterTest.class);

    private ConverterRegistry service;


    @Test
    public void checkMarshal() throws NoMatchingConverterFoundException {
        TestObject data = new TestObject(1, "test");

        Converter<TestObject> converter = service.borrowConverter("test");

        String result = converter.marshal(data);
        LOG.debug("Result: {}", result);
        service.returnConverter("test", converter);

        assertEquals(result, "{\"id\":1,\"data\":\"test\"}");
    }


    @Test
    public void checkUnmarshal() throws NoMatchingConverterFoundException {
        String json = "{\"id\":1,\"data\":\"test\"}";

        Converter<TestObject> converter = service.borrowConverter("test");

        TestObject result = converter.unmarshal(json);
        LOG.debug("Result: {}", result);
        service.returnConverter("test", converter);

        assertEquals(result.getId(), 1);
        assertEquals(result.getData(), "test");
    }


    @Before
    public void setupService() {
        service = new ConverterRegistryImpl();
        service.registerConverter("test", new TestConverterGenerator());
    }
}
