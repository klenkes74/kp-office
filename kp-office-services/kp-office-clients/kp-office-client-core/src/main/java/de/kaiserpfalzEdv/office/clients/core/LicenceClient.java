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

package de.kaiserpfalzEdv.office.clients.core;

import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.core.licence.LicenceService;
import de.kaiserpfalzEdv.office.core.licence.NoLicenceLoadedException;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import de.kaiserpfalzEdv.office.core.licence.commands.GetLicenceCommand;
import de.kaiserpfalzEdv.office.core.licence.impl.NullLincenceImpl;
import de.kaiserpfalzEdv.office.core.licence.notifications.LicenceDataNotification;
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
public class LicenceClient implements LicenceService {
    private static final Logger LOG = LoggerFactory.getLogger(LicenceClient.class);


    private static final String MESSAGE_EXCHANGE = "kpo.core";
    private static final String ROUTING_KEY = "core.licence";


    private RabbitTemplate sender;
    private OfficeLicence  licence;


    @Inject
    public LicenceClient(final RabbitTemplate sender) {
        this.sender = sender;

        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {

        LOG.trace("Initialized: {}", this);
    }


    private void loadLicence() throws NoLicenceLoadedException {
        if (licence != null)
            return;

        synchronized (this) {
            if (licence != null)
                return;

            LicenceDataNotification result
                    = (LicenceDataNotification) sender.convertSendAndReceive(MESSAGE_EXCHANGE, ROUTING_KEY, new GetLicenceCommand());

            if (result == null) {
                throw new NoLicenceLoadedException();
            }

            licence = result.getLicence();
            LOG.trace("Licence: {}", this.licence);

        }
    }


    @Override
    public OfficeLicence getLicence() {
        try {
            loadLicence();
        } catch (NoLicenceLoadedException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            return new NullLincenceImpl();
        }

        return licence;
    }
}
