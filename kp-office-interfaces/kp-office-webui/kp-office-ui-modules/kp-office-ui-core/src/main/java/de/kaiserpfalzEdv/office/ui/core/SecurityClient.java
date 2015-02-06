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

package de.kaiserpfalzEdv.office.ui.core;

import de.kaiserpfalzEdv.commons.service.BackendService;
import de.kaiserpfalzEdv.commons.service.FrontendService;
import de.kaiserpfalzEdv.office.core.security.InvalidLoginException;
import de.kaiserpfalzEdv.office.core.security.InvalidTicketException;
import de.kaiserpfalzEdv.office.core.security.NoSuchAccountException;
import de.kaiserpfalzEdv.office.core.security.NoSuchTicketException;
import de.kaiserpfalzEdv.office.core.security.OfficeLoginTicket;
import de.kaiserpfalzEdv.office.core.security.SecurityService;
import de.kaiserpfalzEdv.office.core.security.SecurityServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

/**
 * The frontend implementation of the security service. It will call the backend service for the real work.
 * *
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Named
@FrontendService
public class SecurityClient implements SecurityService {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityClient.class);
    
    private SecurityService service;
    
    
    @Inject
    public SecurityClient(@BackendService SecurityServiceImpl service) {
        this.service = service;
        
        LOG.trace("Created: {}", this);
    }
    
    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }
    

    @Override
    public OfficeLoginTicket login(@NotNull String account, @NotNull String password) throws InvalidLoginException {
        LOG.debug("Trying to log in: {}", account);
        
        try {
            return service.login(account, password);
        } catch (NoSuchAccountException e) {
            throw new InvalidLoginException(account);
        }
    }

    @Override
    public OfficeLoginTicket check(@NotNull OfficeLoginTicket ticket) throws InvalidTicketException {
        LOG.debug("Checking ticket: {}", ticket);
        
        try {
            return service.check(ticket);
        } catch (NoSuchTicketException e) {
            throw new InvalidTicketException();
        }
    }

    @Override
    public void logout(@NotNull OfficeLoginTicket ticket) {
        LOG.debug("Logout ticket: {}", ticket);
        
        service.logout(ticket);
    }
}
