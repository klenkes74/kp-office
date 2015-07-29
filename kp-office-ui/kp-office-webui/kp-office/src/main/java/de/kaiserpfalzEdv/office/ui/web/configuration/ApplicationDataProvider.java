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
import org.springframework.beans.factory.annotation.Value;
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
public class ApplicationDataProvider {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationDataProvider.class);

    @Value("${info.app.id}")
    private String applicationId;

    @Value("${info.app.name}")
    private String applicationName;

    @Value("${info.app.version}")
    private String applicationVersion;

    @Value("${info.app.hostId}")
    private String applicationInstance;

    private Versionable version;

    /**
     * Application data built from the variables {@link #applicationId}, {@link #applicationName},
     * {@link #applicationVersion} and {@link #applicationInstance}.
     */
    private ApplicationMetaData applicationData;


    public ApplicationDataProvider() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        applicationData = new ApplicationMetaData.Builder()
                .withId(applicationId)
                .withName(applicationName)
                .withVersion(applicationVersion)
                .withInstance(applicationInstance)
                .build();

        version = new SoftwareVersion(applicationVersion);

        LOG.trace("Initialized: {}", this);
        LOG.trace("  application id: {}", applicationId);
        LOG.trace("  application name: {}", applicationName);
        LOG.trace("  application version: {}", applicationVersion);
        LOG.trace("  application instance: {}", applicationInstance);
        LOG.trace("  application data: {}", applicationData);
        LOG.trace("  application version: {}", version);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Bean
    public ApplicationMetaData getApplicationData() {
        return applicationData;
    }

    @Bean
    public Versionable getApplicationVersion() {
        return version;
    }
}
