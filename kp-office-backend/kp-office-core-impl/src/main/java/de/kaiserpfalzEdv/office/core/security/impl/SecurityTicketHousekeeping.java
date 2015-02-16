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

package de.kaiserpfalzEdv.office.core.security.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import javax.inject.Inject;
import javax.inject.Named;
import java.time.OffsetDateTime;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Named
public class SecurityTicketHousekeeping {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityTicketHousekeeping.class);

    @Inject
    private SecurityTicketRepository ticketRepository;


    @Scheduled(fixedRate = 60000L, initialDelay = 60000L)
    public void deleteOldTickets() {
        LOG.info("Removing invalid tickets.");

        Iterable<SecurityTicket> tickets = retrieveInvalideTickets();

        tickets.forEach(t -> removeInvalidTicket(t));
    }

    private Iterable<SecurityTicket> retrieveInvalideTickets() {
        OffsetDateTime now = OffsetDateTime.now();
        LOG.debug("Checking tickets before: {}", now);

        return ticketRepository.findByValidityLessThan(now);
    }

    private void removeInvalidTicket(SecurityTicket ticket) {
        ticketRepository.delete(ticket.getId().toString());
        LOG.trace("Removed invalid ticket: {}", ticket);
    }
}
