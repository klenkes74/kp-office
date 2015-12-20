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

package de.kaiserpfalzEdv.office.clients.core.license.impl;

import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.core.license.LicenseService;
import de.kaiserpfalzEdv.office.core.license.NoLicenseLoadedException;
import de.kaiserpfalzEdv.office.core.license.OfficeLicense;
import de.kaiserpfalzEdv.office.core.license.commands.GetLicenseCommand;
import de.kaiserpfalzEdv.office.core.license.impl.NullLincenseImpl;
import de.kaiserpfalzEdv.office.core.license.notifications.LicenseDataNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Client;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 22:40
 */
@Named
@KPO(Client)
public class LicenseClient implements LicenseService {
    private static final Logger LOG = LoggerFactory.getLogger(LicenseClient.class);


    private static final String MESSAGE_EXCHANGE = "kpo.core";
    private static final String ROUTING_KEY = "core.license";


    private RabbitTemplate sender;
    private OfficeLicense licence;


    @Inject
    public LicenseClient(final RabbitTemplate sender) {
        this.sender = sender;

        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {

        LOG.trace("Initialized: {}", this);
    }


    private void loadLicence() throws NoLicenseLoadedException {
        if (licence != null)
            return;

        synchronized (this) {
            if (licence != null)
                return;

            LicenseDataNotification result
                    = (LicenseDataNotification) sender.convertSendAndReceive(MESSAGE_EXCHANGE, ROUTING_KEY, new GetLicenseCommand());

            if (result == null) {
                throw new NoLicenseLoadedException();
            }

            licence = result.getLicence();
            LOG.trace("Licence: {}", this.licence);

        }
    }


    @Override
    public OfficeLicense getLicense() {
        try {
            loadLicence();
        } catch (NoLicenseLoadedException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            return new NullLincenseImpl();
        }

        return licence;
    }
}
