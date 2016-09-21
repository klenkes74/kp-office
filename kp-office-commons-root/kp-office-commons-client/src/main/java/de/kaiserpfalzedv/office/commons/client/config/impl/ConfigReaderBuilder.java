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

import de.kaiserpfalzedv.office.common.BuilderException;
import de.kaiserpfalzedv.office.commons.client.config.ConfigReader;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Creates a new Config Reader. The file name can be specified directly (normally a default configuration file), via
 * an environment variable name or a system property.
 *
 * The precedence of the three are:
 * <ol>
 *     <li>Take the file specified by system property. If it does not exist or is not specified ...</li>
 *     <li>... take the file specified by the environment variable. If it does not exist or is not specified ...</li>
 *     <li>... take the specified file name directly.</li>
 * </ol>
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-21
 */
public class ConfigReaderBuilder implements Builder<ConfigReader> {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigReaderBuilder.class);

    private String propertyFileName;

    @Override
    public ConfigReader build() {
        if (isNotBlank(propertyFileName))
            return buildPropertyFileConfigReader();

        throw new BuilderException(
                ConfigReaderBuilder.class,
                new String[] {"Don't know which type of property reader to create!"}
        );
    }

    private ConfigReader buildPropertyFileConfigReader() {
        ArrayList<String> failures = new ArrayList<>();

        try {
            Properties result = new Properties();

            InputStream is = getClass().getClassLoader().getResourceAsStream(propertyFileName);
            if (is == null) {
                failures.add("The file " + propertyFileName + "' is no valid resource!");
            } else {
                result.load(is);

                LOG.debug("Loaded properties from: {}", propertyFileName);
                return new LoadTimePropertyConfigReaderImpl(result);
            }
        } catch (FileNotFoundException e) {
            LOG.error("The property file '" + propertyFileName + "' does not exist!", e);

            failures.add("The file '" + propertyFileName + "' does not exist!");
        } catch (IOException e) {
            LOG.error("The property file '" + propertyFileName + "' can not be read!", e);

            failures.add("The file '" + propertyFileName + "' can't be read!");
        }

        throw new BuilderException(LoadTimePropertyConfigReaderImpl.class, failures.toArray(new String[1]));
    }

    public ConfigReaderBuilder withEnvironmentPropertyFileName(final String propertyName) {
        LOG.debug("Loading properties file name from environment variable: {}", propertyName);

        String propertyFileName = System.getenv(propertyName);

        if (isBlank(propertyFileName)) {
            LOG.error("The environment variable '{}' does not contain a file name for reading properties from.", propertyName);
        } else {
           this.propertyFileName = propertyFileName;
        }

        return this;
    }

    public ConfigReaderBuilder withSystemPropertyFileName(final String propertyName) {
        LOG.debug("Loading properties file name from system property: {}", propertyName);

        String propertyFileName = System.getProperty(propertyName);

        if (isBlank(propertyFileName)) {
            LOG.error("The system property '{}' does not contain a file name for reading properties from.", propertyName);
        } else {
            this.propertyFileName = propertyFileName;
        }

        return this;
    }


    public ConfigReaderBuilder withPropertyFile(final String propertyFileName) {
        LOG.debug("Loading properties from file: {}", propertyFileName);

        this.propertyFileName = propertyFileName;

        return this;
    }
}
