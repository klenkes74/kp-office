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

package de.kaiserpfalzEdv.office.cli.executor;

import com.google.common.eventbus.Subscribe;
import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.office.cli.ApplicationVersion;
import de.kaiserpfalzEdv.office.cli.CliModule;
import de.kaiserpfalzEdv.office.cli.executor.events.ReadConfigurationCommand;
import de.kaiserpfalzEdv.office.cli.executor.results.CliConfigurationResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.Properties;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 19.03.15 22:31
 */
@Named
public class ConfigurationReader implements CliModule {
    private static final Logger LOG = LoggerFactory.getLogger(ConfigurationReader.class);


    private EventBusHandler bus;


    @Inject
    public ConfigurationReader(EventBusHandler bus) {
        this.bus = bus;
        LOG.trace("Created: {}", this);
        LOG.trace("{} event bus: {}", this, this.bus);
    }


    @PostConstruct
    public void init() {
        bus.register(this);

        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        bus.unregister(this);

        LOG.trace("Destroyed: {}", this);
    }


    @Subscribe
    public void readConfiguration(final ReadConfigurationCommand command) {
        Properties configuration = command.getProperties();

        CliConfigurationResult result = new CliConfigurationResult(this, configuration);

        bus.post(result);
    }


    @Override
    public Versionable getVersion() {
        return ApplicationVersion.INSTANCE;
    }

    @Override
    public String getShortDescription() {
        return "Reads the configuration for this CLI Applicaiton";
    }

    @Override
    public String getDescription() {
        return "Reads the configuration from system properties and command line.";
    }

    @Override
    public boolean isInitialized() {
        return true;
    }

    @Override
    public String getDisplayName() {
        return ConfigurationReader.class.getSimpleName();
    }
}
