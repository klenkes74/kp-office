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

package de.kaiserpfalzEdv.office.security;

import java.io.Serializable;
import java.time.Period;
import java.util.Collection;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public interface SecurityClient {
    /**
     * Logs in a user.
     *
     * @param principal   The principal to be logged in.
     * @param credentials The credentials to be logged in with.
     * @return The security ticket.
     * @throws OfficeAuthenticationException If the login was not possible.
     */
    public OfficeTicket login(final OfficePrincipal principal, final Serializable credentials) throws OfficeAuthenticationException;

    public boolean refreshTicket(OfficeTicket ticket) throws InvalidTicketException, TicketNotRefreshableException;

    public boolean isValidTicket(OfficeTicket ticket) throws InvalidTicketException;

    public OfficeSubject createSubject(OfficeTicket ticket) throws InvalidTicketException;

    public OfficeSubject createSubject(OfficePrincipal principal) throws OfficeAuthenticationException;

    public OfficeTicket createLongLifeTicket(OfficeTicket ticket, Period period, Collection<String> permissions) throws InvalidTicketException, TicketNotRefreshableException, NoLongTermTicketAllowedException;
}
