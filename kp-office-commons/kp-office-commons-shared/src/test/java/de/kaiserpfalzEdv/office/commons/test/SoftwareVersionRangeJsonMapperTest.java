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

package de.kaiserpfalzEdv.office.commons.test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.kaiserpfalzEdv.commons.service.VersionRange;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.commons.SoftwareVersion;
import de.kaiserpfalzEdv.office.commons.SoftwareVersionRange;
import de.kaiserpfalzEdv.office.commons.jackson.VersionableJacksonModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 29.03.15 08:32
 */
@Test
public class SoftwareVersionRangeJsonMapperTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(SoftwareVersionRangeJsonMapperTest.class);

    private static final String JSON_VERSION_RANGE = "{\"start\":{\"version\":[1,0,0]},\"end\":{\"version\":[1,9999,9999]}}";
    private static final ObjectMapper jsonMapper         = getMapper();


    public SoftwareVersionRangeJsonMapperTest() {
        super(SoftwareVersionRangeJsonMapperTest.class, LOG);
    }

    private static ObjectMapper getMapper() {
        ObjectMapper result = new ObjectMapper();

        result.findAndRegisterModules();
        result.registerModule(new VersionableJacksonModule());

        return result;
    }

    public void checkDeserialization() throws IOException {
        logMethod("deserialization", "Checks deserialization of a software range ...");

        VersionRange result = jsonMapper.readValue(JSON_VERSION_RANGE, VersionRange.class);
        LOG.trace("Deserialized {}: {}", JSON_VERSION_RANGE, result);

        assertNotNull(result);
    }

    public void checkSerialization() throws JsonProcessingException {
        logMethod("serialization", "Checks serialization of a software range ...");

        VersionRange range = new SoftwareVersionRange(new SoftwareVersion(1, 0, 0), new SoftwareVersion(1, 9999, 9999));

        String result = new String(jsonMapper.writeValueAsBytes(range));
        LOG.trace("Serialized {}: {}", range, result);

        assertEquals(result, JSON_VERSION_RANGE);
    }
}
