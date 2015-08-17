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

package de.kaiserpfalzEdv.office.cli.executor.impl;

import com.google.common.eventbus.Subscribe;
import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import de.kaiserpfalzEdv.office.cli.executor.ClientModuleInitializer;
import de.kaiserpfalzEdv.office.cli.executor.events.ConfigureCommand;
import de.kaiserpfalzEdv.office.cli.executor.events.InitializeCommand;
import de.kaiserpfalzEdv.office.cli.executor.events.ReadConfigurationCommand;
import de.kaiserpfalzEdv.office.cli.executor.results.CliConfigurationResult;
import de.kaiserpfalzEdv.office.cli.executor.results.CliFailureResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.Map;
import java.util.Properties;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 05.03.15 08:12
 */
@Named
public class ModuleConfigurator implements ClientModuleInitializer {
    private static final Logger                          LOG      = LoggerFactory.getLogger(ModuleConfigurator.class);
    private final        HashSet<CliConfigurationResult> results  = new HashSet<>(20);
    private final        HashSet<CliFailureResult>       failures = new HashSet<>(20);
    private ApplicationContext context;
    private EventBusHandler    bus;


    @Inject
    public ModuleConfigurator(final ApplicationContext context, final EventBusHandler bus) {
        LOG.trace("Created: {}", this);

        this.context = context;
        this.bus = bus;
    }

    @PostConstruct
    public void init() {
        LOG.trace("   {} application context: {}", this, this.context);
        LOG.trace("   {} event bus: {}", this, this.bus);

        bus.register(this);

        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        bus.unregister(this);

        LOG.trace("Destroyed: {}", this);
    }


    @Override
    public void initialializeModules(String[] commandLine) {
        ReadConfigurationCommand command = generateConfigurationReadingCommand(commandLine);
        LOG.info("Command Line: {}", commandLine);
        String action = commandLine.length >= 1 ? commandLine[0] : "help";
        Properties configuration = readConfiguration(command);

        ConfigureCommand configurationCommand = new ConfigureCommand(this, action, configuration);
        bus.post(configurationCommand);

        InitializeCommand intializationCommand = new InitializeCommand(this, context);
        bus.post(intializationCommand);
    }

    private ReadConfigurationCommand generateConfigurationReadingCommand(String[] commandLine) {
        Map<String, String> environment = System.getenv();
        Properties properties = System.getProperties();

        return new ReadConfigurationCommand(this, environment, properties, commandLine);
    }


    private Properties readConfiguration(final ReadConfigurationCommand command) {
        LOG.trace("Reading configuration ...");
        Properties result = new Properties();

        bus.post(command);

        results.forEach(r -> r.getProperties().forEach(result::put));
        results.clear();

        failures.forEach(
                f -> LOG.warn(
                        "Failure received from {}: {} [{}]",
                        f.getModuleInfo().getDisplayName(),
                        f.getMessageKey().getMessage(),
                        f.getMessageKey().getCode()
                )
        );
        failures.clear();

        LOG.debug("Configuration read.");
        return result;
    }


    @SuppressWarnings("UnusedDeclaration") // Called via EventBus
    @Subscribe
    public void readConfigurations(CliConfigurationResult configurationResult) {
        this.results.add(configurationResult);
    }

    @SuppressWarnings("UnusedDeclaration") // Called via EventBus
    @Subscribe
    public void readConfigurations(CliFailureResult failure) {
        this.failures.add(failure);
    }
}
