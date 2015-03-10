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
import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.office.cli.CliModule;
import de.kaiserpfalzEdv.office.cli.executor.events.ConfigureCommand;
import de.kaiserpfalzEdv.office.cli.executor.events.ExecutionCommand;
import de.kaiserpfalzEdv.office.cli.executor.events.InitializeCommand;
import de.kaiserpfalzEdv.office.clients.core.LicenceClient;
import de.kaiserpfalzEdv.office.commons.SoftwareVersion;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Configurable;

import javax.inject.Inject;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 03.03.15 12:10
 */
@Configurable
public class ServerPing implements CliModule {
    private static final Logger          LOG     = LoggerFactory.getLogger(ServerPing.class);
    private static final SoftwareVersion VERSION = new SoftwareVersion("0.2.0-SNAPSHOT");


    private LicenceClient client;
    private boolean       initialized;


    @Inject
    public ServerPing(final LicenceClient client) {
        this.client = client;

        LOG.trace("Created: {}", this);
        LOG.trace("{} licence client: {}", this.client);
    }


    @SuppressWarnings("UnusedDeclaration") // Called via EventBus
    @Subscribe
    public void execute(ExecutionCommand event) {
        try {
            OfficeLicence licence = client.getLicence();
        } catch (Exception e) {
            LOG.error("Failure while pinging server: {}", e.getMessage());

            throw e;
        }
    }


    @SuppressWarnings("UnusedDeclaration") // Called via EventBus
    @Subscribe
    public void configure(ConfigureCommand event) {

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
