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
import de.kaiserpfalzEdv.commons.service.VersionRange;
import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.office.commons.SoftwareVersionRange;

import java.io.IOException;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 29.03.15 08:38
 */
public class SoftwareVersionRangeDeserializer extends JsonDeserializer<VersionRange> {
    @Override
    public VersionRange deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonDeserializer<Object> versionDeserializer = ctxt.findRootValueDeserializer(ctxt.constructType(Versionable.class));

        ObjectCodec oc = jp.getCodec();
        JsonNode node = oc.readTree(jp);

        Versionable start = (Versionable) versionDeserializer.deserialize(node.get("start").traverse(oc), ctxt);
        Versionable end = (Versionable) versionDeserializer.deserialize(node.get("end").traverse(oc), ctxt);

        return new SoftwareVersionRange(start, end);
    }
}
