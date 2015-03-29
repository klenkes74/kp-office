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

package de.kaiserpfalzEdv.office.core.licence.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 28.03.15 23:20
 */
@Test
public class OfficeLicenceJsonConverterTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(OfficeLicenceJsonConverterTest.class);

    private static final String       JSON_LICENCE = "{\"licence\":{\"id\":\"87e7cc68-f844-401a-ac80-2f9d34ed69b9\",\"issued\":[2015,2,1],\"issuer\":\"Kaiserpfalz EDV-Service\",\"licensee\":\"Roland T. Lichti\",\"starts\":[2015,2,1],\"expires\":[2034,1,31],\"software\":\"KP Office\",\"range\":{\"start\":{\"state\":\"release\",\"version\":[1,0,0]},\"end\":{\"state\":\"release\",\"version\":[1,9999,9999]}},\"modules\":[\"core\",\"projects\",\"documents\",\"accounting\",\"contacts\"]}}";
    private static final ObjectMapper jsonMapper   = getMapper();


    public OfficeLicenceJsonConverterTest() {
        super(OfficeLicenceJsonConverterTest.class, LOG);
    }

    private static ObjectMapper getMapper() {
        ObjectMapper result = new ObjectMapper();
        result.findAndRegisterModules();

        return result;
    }

    public void checkDeserialization() throws IOException {
        OfficeLicence licence = jsonMapper.readValue(JSON_LICENCE, OfficeLicence.class);

        Assert.assertNotNull(licence);
    }
}
