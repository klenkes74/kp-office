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

package de.kaiserpfalzedv.office.common.impl.config;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;

import de.kaiserpfalzedv.office.common.api.BuilderException;
import de.kaiserpfalzedv.office.common.api.config.ConfigReader;
import de.kaiserpfalzedv.office.common.api.config.NoSuchPropertyException;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Creates a new Config Reader. The file name can be specified directly (normally a default configuration file), via
 * an environment variable name or a system property.
 * <p>
 * The precedence of the three are:
 * <ol>
 * <li>Take the file specified by system property. If it does not exist or is not specified ...</li>
 * <li>... take the file specified by the environment variable. If it does not exist or is not specified ...</li>
 * <li>... take the specified file name directly.</li>
 * </ol>
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-21
 */
public class ConfigReaderBuilder implements Builder<ConfigReader> {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigReaderBuilder.class);

    private static final String DEFAULT_CONFIG_FILE_NAME = "kp-office.properties";

    private Properties properties;
    private String systemPropertyName;
    private String environmentVariableName;
    private String fileName;

    @Override
    public ConfigReader build() {
        if (properties != null) {
            LOG.debug("Software loaded configuration with in-memory-properties.");

            return new LoadTimePropertyConfigReaderImpl(properties);
        }

        String propertyFileName = whichFileToUse();

        if (isNotBlank(propertyFileName)) {
            LoadTimePropertyConfigReaderImpl result = buildPropertyFileConfigReader(propertyFileName);

            printProperties(result);

            return result;
        }

        throw new BuilderException(
                ConfigReaderBuilder.class,
                new String[]{"Don't know which type of property reader to create!"}
        );
    }

    private String whichFileToUse() {
        String result = getSystemPropertyFileName();

        if (result == null) {
            result = getEnvironmentFileName();
        }

        if (result == null) {
            result = getDefaultFileName();
        }

        if (result == null) {
            result = DEFAULT_CONFIG_FILE_NAME;

            LOG.debug("Loading default configuration from: {}", DEFAULT_CONFIG_FILE_NAME);
        }

        return result;
    }

    private LoadTimePropertyConfigReaderImpl buildPropertyFileConfigReader(final String propertyFileName) {
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

    private void printProperties(final LoadTimePropertyConfigReaderImpl config) {
        if (!LOG.isTraceEnabled()) {
            LOG.info("To display configuration, enable TRACE logging for {}", getClass().getCanonicalName());
            return;
        }

        config.getEntryKeys().forEach(k -> {
            try {
                LOG.trace("KP Office Configuration: {}={}", k, config.getEntry(k));
            } catch (NoSuchPropertyException e) {
                LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

                throw new IllegalStateException("System unstable: can't retrieve config property '{}' previosly reported as present!", e);
            }
        });
    }

    private String getSystemPropertyFileName() {
        String result = null;

        if (isNotBlank(systemPropertyName)) {
            result = checkPropertyFile(
                    systemPropertyName,
                    System.getProperty(systemPropertyName),
                    "The configuration file '{}' pointed to by the system property '{}' does not exist.",
                    "Loading configuration via system property '{}': {}"
            );
        }

        return result;
    }

    private String getEnvironmentFileName() {
        String result = null;

        if (isNotBlank(environmentVariableName)) {
            result = checkPropertyFile(
                    environmentVariableName,
                    System.getenv(environmentVariableName),
                    "The configuration file '{}' pointed to by the environment variable '{}' does not exist.",
                    "Loading configuration via environment variable '{}': {}"
            );
        }

        return result;
    }

    private String getDefaultFileName() {
        String result = null;

        if (isNotBlank(fileName)) {
            result = checkPropertyFile(
                    "DEFAULT",
                    fileName,
                    "The configuration file '{}' given from the executing programm does not exist.",
                    "Loading configuration as specified by the software: {}"
            );
        }

        return result;
    }

    private String checkPropertyFile(
            String key,
            String result,
            final String nonExistingFileTemplate,
            final String loadingTemplate
    ) {
        try {
            //noinspection ConstantConditions
            File file = new File(getClass().getClassLoader().getResource(result).getFile());

            if (!file.exists()) {
                LOG.warn(nonExistingFileTemplate, result, key);

                result = null;
            } else {
                LOG.debug(loadingTemplate, key, result);
            }
        } catch (NullPointerException e) {
            LOG.warn(nonExistingFileTemplate, result, key);
            result = null;
        }

        return result;
    }

    public ConfigReaderBuilder withProperties(final Properties properties) {
        this.properties = properties;

        return this;
    }

    public ConfigReaderBuilder withEnvironmentPropertyFileName(final String propertyName) {
        this.environmentVariableName = propertyName;

        return this;
    }

    public ConfigReaderBuilder withSystemPropertyFileName(final String propertyName) {
        this.systemPropertyName = propertyName;

        return this;
    }


    public ConfigReaderBuilder withPropertyFile(final String propertyFileName) {
        this.fileName = propertyFileName;

        return this;
    }
}
