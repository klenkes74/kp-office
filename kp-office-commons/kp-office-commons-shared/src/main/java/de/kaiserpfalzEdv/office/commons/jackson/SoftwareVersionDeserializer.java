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

package de.kaiserpfalzEdv.office.commons.jackson;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.ObjectCodec;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.office.commons.SoftwareVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 29.03.15 08:38
 */
public class SoftwareVersionDeserializer extends JsonDeserializer<Versionable> {
    private static final Logger LOG = LoggerFactory.getLogger(SoftwareVersionDeserializer.class);

    @Override
    public Versionable deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        ArrayList<JsonNode> versionArray = new ArrayList<>(3);
        String state;

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        node.get("version").forEach(versionArray::add);
        state = node.get("release").textValue();


        int[] version = new int[versionArray.size()];
        for (int i = 0; i < versionArray.size(); i++) {
            version[i] = versionArray.get(i).intValue();
        }

        return new SoftwareVersion(Versionable.ReleaseState.valueOf(state), version);
    }
}
