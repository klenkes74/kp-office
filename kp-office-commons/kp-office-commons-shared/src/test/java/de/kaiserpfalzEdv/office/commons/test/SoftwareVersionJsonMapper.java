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
import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 29.03.15 08:32
 */
@Test
public class SoftwareVersionJsonMapper extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(SoftwareVersionJsonMapper.class);

    private static final String       JSON_VERSION = "{\"state\":\"release\",\"version\":[1,0,0]}";
    private static final ObjectMapper jsonMapper   = getMapper();


    public SoftwareVersionJsonMapper() {
        super(SoftwareVersionJsonMapper.class, LOG);
    }

    private static ObjectMapper getMapper() {
        ObjectMapper result = new ObjectMapper();
        result.findAndRegisterModules();

        return result;
    }

    public void checkDeserialization() throws IOException {
        Versionable version = jsonMapper.readValue(JSON_VERSION, Versionable.class);

        Assert.assertNotNull(version);
    }
}
