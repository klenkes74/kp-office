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

package de.kaiserpfalzEdv.office.cli.amqp;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.kaiserpfalzEdv.office.commons.jackson.VersionableJacksonModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * @author klenkes
 * @version 2015Q1
 * @since 22.03.15 08:18
 */
@Configuration
public class JacksonConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(JacksonConfiguration.class);

    private ObjectMapper jsonMapper;

    @Bean
    public ObjectMapper jsonObjectMapper() {
        if (jsonMapper != null) return jsonMapper;

        synchronized (this) {
            if (jsonMapper != null) return jsonMapper;

            jsonMapper = new ObjectMapper();
            jsonMapper.findAndRegisterModules();

            jsonMapper.registerModule(new VersionableJacksonModule());
        }

        return jsonMapper;
    }

}
