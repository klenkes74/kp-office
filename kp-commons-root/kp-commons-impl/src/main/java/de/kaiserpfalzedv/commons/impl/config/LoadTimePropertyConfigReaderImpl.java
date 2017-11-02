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

package de.kaiserpfalzedv.commons.impl.config;

import de.kaiserpfalzedv.commons.api.config.ConfigReader;
import de.kaiserpfalzedv.commons.api.config.NoSuchPropertyException;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * The load time property config reader is the simplest {@link ConfigReader}. It is given the properties that
 * can be looked up and thats it.
 * <p>
 * It won't reload the properties if the file changes, it can't save any changed properties. Only plain property reader
 * without any fancy stuff.
 *
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
        if (!config.containsKey(property)) {
            throw new NoSuchPropertyException(property, "There is no configuration for '" + property + "'.");
        }

        return config.getProperty(property);
    }

    @Override
    public String getEntry(String property, String defaultValue) {
        return config.getProperty(property, defaultValue);
    }

    public Set<String> getEntryKeys() {
        HashSet<String> result = new HashSet<>();

        Enumeration<String> names = (Enumeration<String>) config.propertyNames();
        while (names.hasMoreElements()) {
            result.add(names.nextElement());
        }

        return result;
    }

    @Override
    public void close() {
    }
}
