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
import de.kaiserpfalzEdv.office.cli.CliModule;
import de.kaiserpfalzEdv.office.cli.executor.events.CliCommand;
import de.kaiserpfalzEdv.office.cli.executor.events.GetModuleInformationCommand;
import de.kaiserpfalzEdv.office.cli.executor.events.ModuleInformationEvent;
import de.kaiserpfalzEdv.office.cli.executor.results.CliResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 03.03.15 01:20
 */
@Named
public class LogCommandModule implements CliModule {
    private static final Logger LOG = LoggerFactory.getLogger(LogCommandModule.class);


    private EventBusHandler bus;


    @Inject
    public LogCommandModule(EventBusHandler bus) {
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


    @SuppressWarnings("UnusedDeclaration") // Will be called via EventBus ...
    @Subscribe
    public void logAllCommands(final CliCommand event) {
        LOG.trace("Command: {}", event);
    }


    @SuppressWarnings("UnusedDeclaration") // Will be called via EventBus ...
    @Subscribe
    public void logAllResult(final CliResult event) {
        LOG.trace("Result: {}", event);
    }


    @SuppressWarnings("UnusedDeclaration") // Will be called via EventBus ...
    @Subscribe
    public void logModuleInformation(final ModuleInformationEvent event) {
        LOG.trace("Registered module: {}", event.getInfo().getDisplayName());
    }


    @SuppressWarnings("UnusedDeclaration") // Will be called via EventBus ...
    @Subscribe
    public void moduleInformation(final GetModuleInformationCommand event) {
        bus.post(new ModuleInformationEvent(this));
    }


    @Override
    public Versionable getVersion() {
        return new Versionable.Builder().withMajor(0).withMinor(2).build();
    }

    @Override
    public String getShortDescription() {
        return "A command logger";
    }

    @Override
    public String getDescription() {
        return "Logs all commands inside the programm.";
    }

    @Override
    public boolean isInitialized() {
        return true;
    }

    @Override
    public String getDisplayName() {
        return LogCommandModule.class.getSimpleName();
    }
}
