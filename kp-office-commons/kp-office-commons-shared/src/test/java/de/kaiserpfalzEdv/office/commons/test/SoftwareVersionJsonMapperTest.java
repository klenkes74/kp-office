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
import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.commons.SoftwareVersion;
import de.kaiserpfalzEdv.office.commons.jackson.VersionableJacksonModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.io.IOException;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 29.03.15 08:32
 */
@Test
public class SoftwareVersionJsonMapperTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(SoftwareVersionJsonMapperTest.class);

    private static final String       JSON_VERSION                      = "{\"version\":[1,0,0],\"state\":\"alpha\"}";
    private static final String       JSON_DEFAULT_RELEASESTATE_VERSION = "{\"version\":[1,0,0]}";
    private static final int[]        WANTED_VERSION                    = {1, 0, 0};
    private static final ObjectMapper jsonMapper                        = getMapper();


    public SoftwareVersionJsonMapperTest() {
        super(SoftwareVersionJsonMapperTest.class, LOG);
    }

    private static ObjectMapper getMapper() {
        ObjectMapper result = new ObjectMapper();
        result.findAndRegisterModules();

        result.registerModule(new VersionableJacksonModule());

        return result;
    }

    public void checkDefaultReleaseStateDeserialization() throws IOException {
        logMethod("deserialization-default-state", "Checks deserializing a version with 'release' state ...");

        Versionable result = jsonMapper.readValue(JSON_DEFAULT_RELEASESTATE_VERSION, Versionable.class);
        LOG.trace("Deserialized {}: {}", JSON_VERSION, result);

        assertArrayEquals(result.getVersion(), WANTED_VERSION);
        assertEquals(result.getReleaseState(), Versionable.ReleaseState.release);
    }

    public void checkDefaultReleaseStateSerialization() throws JsonProcessingException {
        logMethod("seriaization-default-state", "Checks serializing a version with 'release' state ...");

        Versionable version = new SoftwareVersion(Versionable.ReleaseState.release, 1, 0, 0);
        String result = new String(jsonMapper.writeValueAsBytes(version));
        LOG.trace("Serialized {}: {}", version, result);

        assertEquals(result, JSON_DEFAULT_RELEASESTATE_VERSION);
    }

    public void checkDeserialization() throws IOException {
        logMethod("deserialization", "Checks deserializing a version ...");

        Versionable result = jsonMapper.readValue(JSON_VERSION, Versionable.class);
        LOG.trace("Deserialized {}: {}", JSON_VERSION, result);

        assertArrayEquals(result.getVersion(), WANTED_VERSION);
        assertEquals(result.getReleaseState(), Versionable.ReleaseState.alpha);
    }

    public void checkSerialization() throws JsonProcessingException {
        logMethod("seriaization", "Checks serializing a version ...");

        Versionable version = new SoftwareVersion(Versionable.ReleaseState.alpha, 1, 0, 0);
        String result = new String(jsonMapper.writeValueAsBytes(version));
        LOG.trace("Serialized {}: {}", version, result);

        assertEquals(result, JSON_VERSION);
    }
}
