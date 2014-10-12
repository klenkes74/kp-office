/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.security.test;

import de.kaiserpfalzEdv.office.security.InvalidTicketException;
import de.kaiserpfalzEdv.office.security.NoLongTermTicketAllowedException;
import de.kaiserpfalzEdv.office.security.OfficeAuthenticationException;
import de.kaiserpfalzEdv.office.security.OfficePrincipal;
import de.kaiserpfalzEdv.office.security.OfficeSubject;
import de.kaiserpfalzEdv.office.security.OfficeTicket;
import de.kaiserpfalzEdv.office.security.OfficeTicketDTO;
import de.kaiserpfalzEdv.office.security.SecurityClient;
import de.kaiserpfalzEdv.office.security.TicketNotRefreshableException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.time.Period;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public class MockSecurityClient implements SecurityClient {
    private static final Logger LOG = LoggerFactory.getLogger(MockSecurityClient.class);

    private final Set<OfficeSubject> configuredSubjects = new HashSet<OfficeSubject>();
    private final HashMap<OfficeTicket, OfficeSubject> subjects = new HashMap<OfficeTicket, OfficeSubject>();

    public OfficeTicket getTicketForSubject(OfficeSubject subject) {
        LOG.debug("Checking ticket for subject: {}", subject);

        for (Map.Entry<OfficeTicket, OfficeSubject> e : subjects.entrySet()) {
            for (OfficePrincipal p : subject.getAllPrincipal()) {
                if (e.getValue().getAllPrincipal().contains(p))
                    return e.getKey();
            }
        }

        OfficeTicket result = new OfficeTicketDTO(UUID.randomUUID());
        subjects.put(result, subject);

        return result;
    }

    @Override
    public OfficeTicket login(OfficePrincipal principal, Serializable credentials) throws OfficeAuthenticationException {
        LOG.debug("Checking principals: {}", principal);
        LOG.debug("Checking credential: {}", credentials);

        OfficeSubject subject = createSubject(principal);

        LOG.info("Logged in: {}", subject);
        OfficeTicket ticket = null;
        for (OfficePrincipal p : subject.getAllPrincipal()) {
            if (p instanceof OfficeTicket) {
                ticket = (OfficeTicket) p;
            }
        }
        if (ticket == null) {
            ticket = new OfficeTicketDTO(UUID.randomUUID());
            subject.getAllPrincipal().add(ticket);
        }

        subjects.put(ticket, subject);
        return ticket;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean refreshTicket(OfficeTicket ticket) throws InvalidTicketException, TicketNotRefreshableException {
        if (!isValidTicket(ticket)) throw new InvalidTicketException(ticket);

        return true;
    }

    @Override
    public boolean isValidTicket(OfficeTicket ticket) throws InvalidTicketException {
        return getTickets().contains(new OfficeTicketDTO(ticket.getTicket()));
    }

    @Override
    public OfficeSubject createSubject(OfficeTicket ticket) throws InvalidTicketException {
        if (!isValidTicket(ticket)) throw new InvalidTicketException(ticket);

        OfficeSubject result = subjects.get(ticket);
        if (!result.getAllPrincipal().contains(ticket)) {
            LOG.info("Adding ticket {} to principal set ...", ticket);
            result.getAllPrincipal().add(ticket);
        }

        LOG.info("Creating from ticket {}: {}", ticket, result);
        return result;
    }

    @Override
    public OfficeSubject createSubject(OfficePrincipal principal) throws OfficeAuthenticationException {
        LOG.debug("Checking {} configured subjects.", configuredSubjects.size());

        for (OfficeSubject subject : subjects.values()) {
            LOG.trace("Checking configured subject: {}", subject);

            if (subject.getAllPrincipal().contains((principal))) {
                return subject;
            }
        }

        for (OfficeSubject subject : configuredSubjects) {
            if (subject.getAllPrincipal().contains(principal)) {
                return subject;
            }
        }

        throw new OfficeAuthenticationException();
    }

    @Override
    public OfficeTicket createLongLifeTicket(OfficeTicket ticket, Period period, Collection<String> permissions) throws InvalidTicketException, TicketNotRefreshableException, NoLongTermTicketAllowedException {
        throw new NoLongTermTicketAllowedException(ticket);
    }

    private Set<OfficeTicket> getTickets() {
        return subjects.keySet();
    }


    public Set<OfficeSubject> getConfiguredSubjects() {
        return configuredSubjects;
    }

    public void setConfiguredSubjects(Collection<? extends OfficeSubject> subjects) {
        this.configuredSubjects.clear();

        if (subjects != null) {
            this.configuredSubjects.addAll(subjects);
        }

        LOG.info("MockSecurityClient loaded {} subjects from configuration.", this.configuredSubjects.size());
    }
}
