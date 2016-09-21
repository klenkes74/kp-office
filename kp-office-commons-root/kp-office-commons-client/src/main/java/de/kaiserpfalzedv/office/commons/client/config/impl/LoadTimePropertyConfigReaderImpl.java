/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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
 *
 */

package de.kaiserpfalzedv.office.commons.client.config.impl;

import de.kaiserpfalzedv.office.commons.client.config.ConfigReader;
import de.kaiserpfalzedv.office.commons.client.config.NoSuchPropertyException;

import java.io.IOException;
import java.util.Properties;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-21
 */
public class LoadTimePropertyConfigReaderImpl implements ConfigReader {
    private Properties config;

    LoadTimePropertyConfigReaderImpl(final Properties config) {
        this.config = config;
    }

    @Override
    public String getEntry(String property) throws NoSuchPropertyException {
        if (! config.containsKey(property)) {
            throw new NoSuchPropertyException(property, "There is no configuration for '" + property + "'.");
        }

        return config.getProperty(property);
    }

    @Override
    public String getEntry(String property, String defaultValue) {
        return config.getProperty(property, defaultValue);
    }


    @Override
    public void close() throws IOException {
    }
}
