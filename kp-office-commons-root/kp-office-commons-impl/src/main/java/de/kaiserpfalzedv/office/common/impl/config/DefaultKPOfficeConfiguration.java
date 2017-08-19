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

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;

import de.kaiserpfalzedv.office.common.api.config.ConfigReader;
import de.kaiserpfalzedv.office.common.api.config.Properties;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-24
 */
@ApplicationScoped
public class DefaultKPOfficeConfiguration {
    private static ConfigReader config = new ConfigReaderBuilder()
            .withEnvironmentPropertyFileName("KP_OFFICE_CONFIGURATION")
            .withSystemPropertyFileName("de.kaiserpfalzedv.office.configuration")
            .build();

    @Produces
    @Properties
    public ConfigReader getConfig() {
        return config;
    }
}
