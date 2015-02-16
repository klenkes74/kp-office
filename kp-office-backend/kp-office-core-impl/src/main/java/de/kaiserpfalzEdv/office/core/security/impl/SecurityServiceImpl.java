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

import de.kaiserpfalzEdv.commons.service.BackendService;
import de.kaiserpfalzEdv.office.core.security.InvalidLoginException;
import de.kaiserpfalzEdv.office.core.security.InvalidTicketException;
import de.kaiserpfalzEdv.office.core.security.NoSuchAccountException;
import de.kaiserpfalzEdv.office.core.security.NoSuchTicketException;
import de.kaiserpfalzEdv.office.core.security.OfficeLoginTicket;
import de.kaiserpfalzEdv.office.core.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;

import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Named
@BackendService
public class SecurityServiceImpl implements SecurityService {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityServiceImpl.class);

    private IdentityRepository identityRepository;
    private AccountRepository accountRepository;
    private RoleRepository roleRepository;
    private SecurityTicketRepository ticketRepository;


    @Inject
    public SecurityServiceImpl(final IdentityRepository identityRepository,
                               final AccountRepository accountRepository,
                               final RoleRepository roleRepository,
                               final SecurityTicketRepository ticketRepository) {
        this.identityRepository = identityRepository;
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
        this.ticketRepository = ticketRepository;
    }


    @SuppressWarnings("deprecation")
    @Transactional
    @Override
    public OfficeLoginTicket login(@NotNull final String accountName, @NotNull final String password) throws InvalidLoginException, NoSuchAccountException {
        Account account = accountRepository.findByAccountName(accountName);

        try {
            if (account == null) {
                throw new NoSuchAccountException(accountName);
            }

            if (! account.checkPassword(password)) {
                throw new InvalidLoginException(accountName);
            }
        } catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new InvalidLoginException(accountName);
        }

        SecurityTicket token = ticketRepository.findByAccount(account);

        if (token == null) {
            token = new SecurityTicket(account);
        }

        LOG.trace("Token: {}", token);

        token.renew();
        token = ticketRepository.save(token);

        OfficeLoginTicket ticket = new OfficeLoginTicket(token.getId(), token.getAccountName(), token.getDisplayName(),
                token.getValidity());

        ticket.setRoles(token.getRoles());
        ticket.setEntitlements(token.getEntitlements());

        LOG.info("Created new ticket: {}", ticket);
        return ticket;
    }

    @SuppressWarnings("deprecation")
    @Transactional
    @Override
    public OfficeLoginTicket check(@NotNull OfficeLoginTicket ticket)
            throws InvalidTicketException, NoSuchTicketException {
        LOG.debug("Checking ticket: {}", ticket);

        SecurityTicket token = ticketRepository.findOne(ticket.getTicketId().toString());

        if (token == null) {
            throw new NoSuchTicketException(ticket.getTicketId());
        }

        if (!token.isValid()) {
            ticketRepository.delete(ticket.getTicketId().toString());
            
            throw new InvalidTicketException(ticket.getTicketId());
        }

        token.renew();
        token = ticketRepository.save(token);

        ticket = new OfficeLoginTicket(token.getId(), token.getAccountName(), token.getDisplayName(),
                token.getValidity());

        ticket.setRoles(token.getRoles());
        ticket.setEntitlements(token.getEntitlements());

        LOG.info("Checked and renewed ticket: {}", ticket);
        return ticket;
    }

    @Transactional
    @Override
    public void logout(@NotNull OfficeLoginTicket ticket) {
        try {
            ticketRepository.delete(ticket.getTicketId().toString());
        } catch (EmptyResultDataAccessException e) {
            LOG.debug("Ticket does not exist: {}", ticket);
        }

        LOG.info("Invalidated ticket: {}", ticket);
    }


    public void createIdentity(@NotNull final UUID tenant, @NotNull final UUID id, @NotNull final String name, @NotNull final String number) {
        Identity identity = new Identity(tenant, id, name, number);

        identity = identityRepository.save(identity);

        LOG.info("Created identity: {}", identity);
    }




    public void createAccount(@NotNull final UUID id,
                              @NotNull UUID identityId,
                              @NotNull final String accountName,
                              @NotNull final String name,
                              @NotNull final String password) {
        Identity identity = identityRepository.findOne(identityId.toString());

        Account account = new UserAccount(id, identity, accountName, name, password);

        account = accountRepository.save(account);

        LOG.info("Created account: {}", account);
    }


    public void createRole(@NotNull final UUID id,
                           @NotNull final UUID tenant,
                           @NotNull final String roleName,
                           @NotNull final String name) {
        Role role = new Role(id, tenant, name, roleName);

        role = roleRepository.save(role);

        LOG.info("Created role: {}", role);
    }
}
