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

package de.kaiserpfalzedv.office.commons.client.config.test;

import de.kaiserpfalzedv.office.commons.client.config.ConfigReader;
import de.kaiserpfalzedv.office.commons.client.config.impl.ConfigReaderBuilder;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-21
 */
public class LoadTimePropertyConfigReaderTest extends AbstractConfigReaderTestClass {
    @Override
    public ConfigReader createReader(final String key) {
        return new ConfigReaderBuilder()
                .withPropertyFile(key)
                .build();
    }

    @Override
    public ConfigReader createReaderFromEnvironmentVariable(String key) {
        return new ConfigReaderBuilder()
                .withEnvironmentPropertyFileName(key)
                .build();
    }

    @Override
    public ConfigReader createReaderFromSystemProperty(String key) {
        return new ConfigReaderBuilder()
                .withSystemPropertyFileName(key)
                .build();
    }

    @Override
    public ConfigReader createWithAllMethods(String defaultFileName, String environmentVariableName, String systemPropertyName) {
        ConfigReaderBuilder builder = new ConfigReaderBuilder();

        if (systemPropertyName != null)
            builder.withSystemPropertyFileName(systemPropertyName);

        if (environmentVariableName != null)
                builder.withEnvironmentPropertyFileName(environmentVariableName);

        if (defaultFileName != null)
            builder.withPropertyFile(defaultFileName);

        return builder.build();
    }
}
