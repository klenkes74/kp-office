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

import com.fasterxml.jackson.databind.ObjectMapper;
import de.kaiserpfalzEdv.commons.service.VersionRange;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 29.03.15 08:32
 */
public class SoftwareVersionRangeJsonMapper extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(SoftwareVersionRangeJsonMapper.class);

    private static final String       JSON_VERSION_RANGE = "{\"start\":{\"state\":\"release\",\"version\":[1,0,0]},\"end\":{\"state\":\"release\",\"version\":[1,9999,9999]}}";
    private static final ObjectMapper jsonMapper         = getMapper();


    public SoftwareVersionRangeJsonMapper() {
        super(SoftwareVersionRangeJsonMapper.class, LOG);
    }

    private static ObjectMapper getMapper() {
        ObjectMapper result = new ObjectMapper();
        result.findAndRegisterModules();

        return result;
    }

    public void checkDeserialization() throws IOException {
        VersionRange range = jsonMapper.readValue(JSON_VERSION_RANGE, VersionRange.class);

        Assert.assertNotNull(range);
    }
}
