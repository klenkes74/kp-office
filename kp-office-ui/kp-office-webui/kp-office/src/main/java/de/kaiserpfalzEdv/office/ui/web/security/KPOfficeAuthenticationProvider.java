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

package de.kaiserpfalzEdv.office.ui.web.security;

import de.kaiserpfalzEdv.office.commons.KPO;
import de.kaiserpfalzEdv.office.core.security.InvalidLoginException;
import de.kaiserpfalzEdv.office.core.security.InvalidTicketException;
import de.kaiserpfalzEdv.office.core.security.NoSuchAccountException;
import de.kaiserpfalzEdv.office.core.security.NoSuchTicketException;
import de.kaiserpfalzEdv.office.core.security.OfficeLoginTicket;
import de.kaiserpfalzEdv.office.core.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;

import static de.kaiserpfalzEdv.office.commons.KPO.Type.Client;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Named
public class KPOfficeAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider {
    private static final Logger LOG = LoggerFactory.getLogger(KPOfficeAuthenticationProvider.class);
    
    
    private SecurityService service;
    
    @Inject
    public KPOfficeAuthenticationProvider(@KPO(Client) final SecurityService service) {
        this.service = service;
    }
    
    @PostConstruct
    public void init() {
        LOG.trace("Created: {}", this);
    }
    
    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
        
    }
    
    
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        if (KPOfficeUserDetail.class.isAssignableFrom(userDetails.getClass())) {
            try {
                service.check(((KPOfficeUserDetail) userDetails).getTicket());
                
                LOG.info("Checked: {}", userDetails);
            } catch (NoSuchTicketException | InvalidTicketException e) {
                throw new BadCredentialsException("Wrong credentials for '" + userDetails.getUsername() + "'.");
            }
        }
    }

    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {
        KPOfficeUserDetail result;
        
        try {
            OfficeLoginTicket ticket = service.login(username, (String)authentication.getCredentials());
            
            result = new KPOfficeUserDetail(ticket);
        } catch (InvalidLoginException e) {
            throw new UsernameNotFoundException("Username '" + username + "' not found.");
        } catch (NoSuchAccountException e) {
            throw new BadCredentialsException("Wrong password for '" + username + "'.");
        }
        
        LOG.info("Created: {}", result);
        return result;
    }
}
