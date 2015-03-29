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

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import de.kaiserpfalzEdv.commons.service.Versionable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 29.03.15 08:38
 */
public class SoftwareVersionSerializer extends JsonSerializer<Versionable> {
    private static final Logger LOG = LoggerFactory.getLogger(SoftwareVersionSerializer.class);

    @Override
    public void serialize(Versionable value, JsonGenerator jgen, SerializerProvider provider) throws IOException {
        jgen.writeStartObject();

        jgen.writeArrayFieldStart("version");
        for (int v : value.getVersion()) {
            jgen.writeNumber(v);
        }
        jgen.writeEndArray();

        jgen.writeObjectField("state", value.getReleaseState());

        jgen.writeEndObject();
    }
}
