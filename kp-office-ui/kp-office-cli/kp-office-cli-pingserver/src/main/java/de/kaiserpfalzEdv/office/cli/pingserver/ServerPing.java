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

package de.kaiserpfalzEdv.office.cli.pingserver;

import com.google.common.eventbus.Subscribe;
import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.office.cli.CliModule;
import de.kaiserpfalzEdv.office.cli.executor.events.ConfigureCommand;
import de.kaiserpfalzEdv.office.cli.executor.events.ExecutionCommand;
import de.kaiserpfalzEdv.office.cli.executor.events.InitializeCommand;
import de.kaiserpfalzEdv.office.clients.core.LicenceClient;
import de.kaiserpfalzEdv.office.commons.SoftwareVersion;
import de.kaiserpfalzEdv.office.core.licence.impl.NullLincenceImpl;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 03.03.15 12:10
 */
@Named
public class ServerPing implements CliModule {
    private static final Logger          LOG     = LoggerFactory.getLogger(ServerPing.class);
    private static final SoftwareVersion VERSION = new SoftwareVersion("0.2.0-SNAPSHOT");


    private LicenceClient client;
    private boolean       initialized;
    private String action;


    private EventBusHandler bus;


    @Inject
    public ServerPing(final LicenceClient client, final EventBusHandler bus) {
        this.client = client;
        this.bus = bus;

        LOG.trace("Created: {}", this);
        LOG.trace("{} licence client: {}", this.client);
        LOG.trace("{} event bus: {}", this.bus);
    }

    @PostConstruct
    public void init() {
        bus.register(this);

        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        bus.unregister(this);
        initialized = false;
        bus = null;

        LOG.trace("Destroyed: {}", this);
    }




    @SuppressWarnings("UnusedDeclaration") // Called via EventBus
    @Subscribe
    public void execute(ExecutionCommand event) {
        LOG.trace("Executing event: {}", event);

        if ("ping".equalsIgnoreCase(action)) {
            try {
                OfficeLicence licence = client.getLicence();

                if (NullLincenceImpl.class.isAssignableFrom(licence.getClass())) {
                    throw new IllegalStateException("Licence could not be loaded. No communication with server possible.");
                }
            } catch (Exception e) {
                LOG.error("Failure (" + e.getClass().getSimpleName() + ") while pinging server: " + e.getMessage(), e);

                System.out.println("Could not ping server!");
            }
        }

        if ("help".equalsIgnoreCase(action)) {
            System.out.println(String.format("%-14s %s", "ping", "Retrieves the licence of the server to check availability."));
        }
    }


    @SuppressWarnings("UnusedDeclaration") // Called via EventBus
    @Subscribe
    public void configure(ConfigureCommand event) {
        action = event.getAction();

        LOG.trace("Configured: {}", this);
        LOG.trace("   action: {}", action);
    }

    @SuppressWarnings("UnusedDeclaration") // Called via EventBus
    @Subscribe
    public void initialize(InitializeCommand event) {
        initialized = true;
    }


    @Override
    public Versionable getVersion() {
        return VERSION;
    }

    @Override
    public String getShortDescription() {
        return "Pings the server to test its functionality.";
    }

    @Override
    public String getDescription() {
        return "The server gets called and must answer this call. Used for checking the\n" +
                "availability of the server components.";
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public String getDisplayName() {
        return ServerPing.class.getSimpleName();
    }
}
