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

import com.fasterxml.jackson.databind.module.SimpleModule;
import de.kaiserpfalzEdv.commons.service.VersionRange;
import de.kaiserpfalzEdv.commons.service.Versionable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 29.03.15 08:55
 */
public class VersionableJacksonModule extends SimpleModule {
    private static final Logger LOG = LoggerFactory.getLogger(VersionableJacksonModule.class);
    private static final long serialVersionUID = 9008524273826778753L;

    public VersionableJacksonModule() {
        super(PackageVersion.VERSION);

        LOG.debug("Software Version jackson mapper module is loaded!");

        addSerializer(Versionable.class, new SoftwareVersionSerializer());
        addDeserializer(Versionable.class, new SoftwareVersionDeserializer());

        addSerializer(VersionRange.class, new SoftwareVersionRangeSerializer());
        addDeserializer(VersionRange.class, new SoftwareVersionRangeDeserializer());
    }
}
