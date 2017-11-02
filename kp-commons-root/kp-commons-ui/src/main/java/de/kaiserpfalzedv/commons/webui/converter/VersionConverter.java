/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.commons.webui.converter;

import com.github.zafarkhaja.semver.ParseException;
import com.github.zafarkhaja.semver.Version;
import com.vaadin.data.Converter;
import com.vaadin.data.Result;
import com.vaadin.data.ValueContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A converter for the {@link Version} datatype.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class VersionConverter implements Converter<String, Version> {
    private static final Logger LOG = LoggerFactory.getLogger(VersionConverter.class);

    @Override
    public Result<Version> convertToModel(String value, ValueContext context) {
        SimpleResult<Version> result;

        try {
            Version data = Version.valueOf(value);
            LOG.trace("Parsed version from string: {}", value);

            result = new SimpleResult<>(data);
        } catch (IllegalArgumentException | ParseException e) {
            LOG.warn("Could not parse version string: {}", value);

            result = new SimpleResult<>("Could not parse string to version: " + value);
        }

        return result;
    }

    @Override
    public String convertToPresentation(Version value, ValueContext context) {
        return value.toString();
    }
}

