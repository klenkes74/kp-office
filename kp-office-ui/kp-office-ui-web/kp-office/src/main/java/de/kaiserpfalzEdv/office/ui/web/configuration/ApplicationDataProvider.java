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

package de.kaiserpfalzEdv.office.ui.web.configuration;

import de.kaiserpfalzEdv.commons.jee.servlet.model.ApplicationMetaData;
import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.office.commons.SoftwareVersion;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 06:10
 */
@Configuration
@ConfigurationProperties(prefix = "info.app")
public class ApplicationDataProvider {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationDataProvider.class);

    private String id;
    private String name;
    private String version;
    private String hostId;


    public ApplicationDataProvider() {
        LOG.trace("***** Created: {}", this);
    }

    @PostConstruct
    public void init() {
        LOG.trace("*   *   application id: {}", id);
        LOG.trace("*   *   application name: {}", name);
        LOG.trace("*   *   application version: {}", version);
        LOG.trace("*   *   application instance: {}", hostId);
        LOG.trace("*   *   application version: {}", version);
        LOG.trace("***** Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Bean
    public ApplicationMetaData applicationData() {
        LOG.warn("Application Meta Data is created ...");

        return new ApplicationMetaData.Builder()
                .withId(id)
                .withName(name)
                .withVersion(version)
                .withInstance(hostId)
                .build();
    }

    @Bean
    public Versionable getVersion() {
        return new SoftwareVersion(version);
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setHostId(String hostId) {
        this.hostId = hostId;
    }
}
