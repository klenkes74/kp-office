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

import de.kaiserpfalzedv.office.common.BuilderException;
import de.kaiserpfalzedv.office.commons.client.config.ConfigReader;
import de.kaiserpfalzedv.office.commons.client.config.NoSuchPropertyException;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-21
 */
public abstract class AbstractConfigReaderTestClass {
    private static final String PROPERTY_FILE = "test.properties";
    private static final String ENV_PROPERTY_FILE = "test.env.properties";
    private static final String PROP_PROPERTY_FILE = "test.props.properties";

    private static final String ENV_VARIABLE_NAME = "KPO_TEST_PROPERTIES";
    private static final String SYSTEM_PROPERTY_VARIABLE_NAME = "de.kaiserpfalzedv.offce.commons.client.conf.test";

    private static final String PROPERTY_KEY = "property1";
    private static final String PROPERTY_VALUE = "This is the first property";

    private ConfigReader service;

    @Test
    public void checkProperty1() throws NoSuchPropertyException {
        String result = service.getEntry(PROPERTY_KEY);

        assertEquals(result, "This is the first property");
    }

    @Test
    public void checkDefaultProperty() {
        String result = service.getEntry("PropertyNotExisting", "This is the default value");

        assertEquals("This is the default value", result);
    }

    @Test
    public void checkNotExistingProperty() {
        try {
            service.getEntry("PropertyNotExisting");

            fail("A NoSuchPropertyException should have been thrown!");
        } catch (NoSuchPropertyException e) {
            // Nothing. Everything is as expected if the exception is thrown.
        }

        // No assert is needed. We are checking for the exception
    }


    @Test
    public void checkSystemPropertyReaderBuilder() throws NoSuchPropertyException {

        try {
            setupSystemProperty(PROP_PROPERTY_FILE);
            service = createReaderFromSystemProperty(SYSTEM_PROPERTY_VARIABLE_NAME);

            assertEquals("props", service.getEntry("type"));
        } finally {
            removeSystemProperty();
        }
    }

    @Test
    public void checkNotExistingSystemPropertyReaderBuilder() throws NoSuchPropertyException {
        service = createReaderFromSystemProperty(SYSTEM_PROPERTY_VARIABLE_NAME);

        assertEquals("default", service.getEntry("type"));
    }


    @Test
    public void checkEnvPropertyReaderBuilder() throws NoSuchPropertyException {
        try {
            setupEnvironmentVariable(PROPERTY_FILE);

            service = createReaderFromEnvironmentVariable(ENV_VARIABLE_NAME);

            assertEquals(service.getEntry(PROPERTY_KEY), PROPERTY_VALUE);
        } finally {
            removeEnvironmentVariable();
        }
    }

    @Test
    public void checkNotExistingEnvPropertyReaderBuilder() throws NoSuchPropertyException {
        service = createReaderFromEnvironmentVariable(ENV_VARIABLE_NAME);

        assertEquals("default", service.getEntry("type"));
    }


    @Test
    public void checkFilePrecedenceFull() throws NoSuchPropertyException {
        try {
            setupEnvironmentVariable(ENV_PROPERTY_FILE);
            setupSystemProperty(PROP_PROPERTY_FILE);

            service = createWithAllMethods(PROPERTY_FILE, ENV_VARIABLE_NAME, SYSTEM_PROPERTY_VARIABLE_NAME);

            String result = service.getEntry("type");

            assertEquals("props", result);
        } finally {
            removeEnvironmentVariable();
            removeSystemProperty();
        }
    }


    private void setupSystemProperty(String propPropertyFile) {
        System.getProperties().put(SYSTEM_PROPERTY_VARIABLE_NAME, propPropertyFile);
    }

    private void removeSystemProperty() {
        System.getProperties().remove(SYSTEM_PROPERTY_VARIABLE_NAME);
    }

    private void setupEnvironmentVariable(String envPropertyFile) {
        HashMap<String, String> env = new HashMap<>(System.getenv());
        env.put(ENV_VARIABLE_NAME, envPropertyFile);
        setEnv(env);
    }

    private void removeEnvironmentVariable() {
        HashMap<String, String> env = new HashMap<>(System.getenv());
        env.remove(ENV_VARIABLE_NAME);
        setEnv(env);
    }


    /**
     * A small hack taken from
     * <a href="http://stackoverflow.com/questions/318239/how-do-i-set-environment-variables-from-java">stack overflow</a>
     * to change the system environment.
     *
     * @param newenv The new system environment to be used.
     */
    @SuppressWarnings("unchecked")
    private void setEnv(Map<String, String> newenv)
    {
        try
        {
            Class<?> processEnvironmentClass = Class.forName("java.lang.ProcessEnvironment");
            Field theEnvironmentField = processEnvironmentClass.getDeclaredField("theEnvironment");
            theEnvironmentField.setAccessible(true);
            Map<String, String> env = (Map<String, String>) theEnvironmentField.get(null);
            env.putAll(newenv);
            Field theCaseInsensitiveEnvironmentField = processEnvironmentClass.getDeclaredField("theCaseInsensitiveEnvironment");
            theCaseInsensitiveEnvironmentField.setAccessible(true);
            Map<String, String> cienv = (Map<String, String>)     theCaseInsensitiveEnvironmentField.get(null);
            cienv.putAll(newenv);
        }
        catch (NoSuchFieldException e)
        {
            try {
                Class[] classes = Collections.class.getDeclaredClasses();
                Map<String, String> env = System.getenv();
                for(Class cl : classes) {
                    if("java.util.Collections$UnmodifiableMap".equals(cl.getName())) {
                        Field field = cl.getDeclaredField("m");
                        field.setAccessible(true);
                        Object obj = field.get(env);
                        Map<String, String> map = (Map<String, String>) obj;
                        map.clear();
                        map.putAll(newenv);
                    }
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }


    @Before
    public void setupService() {
        service = createReader(PROPERTY_FILE);
    }

    /**
     * Creates a configuration reader directly from a given file name.
     *
     * @param key The name of the file.
     * @return a ConfigReader
     */
    abstract public ConfigReader createReader(final String key);

    /**
     * Creates a configuration reader from a file name given via environment variable.
     *
     * @param key The environment variable to read the file name from.
     * @return a ConfigReader
     */
    abstract public ConfigReader createReaderFromEnvironmentVariable(final String key);

    /**
     * Creates a configuration reader from a given system property.
     *
     * @param key The system property to read the file name from
     * @return a ConfigReader
     */
    abstract public ConfigReader createReaderFromSystemProperty(final String key);

    /**
     * Creates a configuration reader with the following prioritization:
     * <ul>
     *     <li>system property</li>
     *     <li>environment variable</li>
     *     <li>default file name</li>
     * </ul>
     * @param defaultFileName The default file name (last resort)
     * @param environmentVariableName The name of the environment variable to scan for the property file.
     * @param systemPropertyName The system property to scan for the name of the property file.
     * @return the ConfigReader with the data of the found file.
     */
    abstract public ConfigReader createWithAllMethods(
            final String defaultFileName,
            final String environmentVariableName,
            final String systemPropertyName
    );
}
