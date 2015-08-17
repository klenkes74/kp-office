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

package de.kaiserpfalzEdv.office.cli.executor.events;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 03.03.15 07:00
 */
public class ReadConfigurationCommand extends AbstractBaseCommand {
    private static final long serialVersionUID = -7071556326432281941L;

    private final HashMap<String, String> environment = new HashMap<>();
    private Properties properties;
    private String[]   args;

    public ReadConfigurationCommand(final Object source, final Map<String, String> environment, final Properties properties, final String[] args) {
        super(source);

        if (environment != null) this.environment.putAll(environment);
        this.properties = properties;
        this.args = args;
    }

    @Override
    public String getDisplayName() {
        return ReadConfigurationCommand.class.getSimpleName();
    }


    public Map<String, String> getEnvironment() {
        return environment;
    }

    public Properties getProperties() {
        return properties;
    }

    public String[] getArgs() {
        return args;
    }
}
