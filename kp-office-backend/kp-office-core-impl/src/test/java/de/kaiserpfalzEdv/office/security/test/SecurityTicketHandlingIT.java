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

package de.kaiserpfalzEdv.office.security.test;

import de.kaiserpfalzEdv.office.security.Account;
import de.kaiserpfalzEdv.office.security.AccountRepository;
import de.kaiserpfalzEdv.office.security.InvalidLoginException;
import de.kaiserpfalzEdv.office.security.InvalidTicketException;
import de.kaiserpfalzEdv.office.security.NoSuchAccountException;
import de.kaiserpfalzEdv.office.security.NoSuchTicketException;
import de.kaiserpfalzEdv.office.security.OfficeLoginTicket;
import de.kaiserpfalzEdv.office.security.SecurityService;
import de.kaiserpfalzEdv.office.security.SecurityTicket;
import de.kaiserpfalzEdv.office.security.SecurityTicketHousekeeping;
import de.kaiserpfalzEdv.office.security.SecurityTicketRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTransactionalTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkState;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertEqualsNoOrder;
import static org.testng.Assert.assertNull;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Test
@ContextConfiguration("/beans.xml")
public class SecurityTicketHandlingIT extends AbstractTransactionalTestNGSpringContextTests {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityTicketHandlingIT.class);

    @Inject
    private SecurityService service;

    @Inject
    private SecurityTicketHousekeeping housekeeping;


    @Inject
    private SecurityTicketRepository repository;
    @Inject
    private AccountRepository accountRepository;



    public void loginValidUser() throws InvalidLoginException, NoSuchAccountException {
        OfficeLoginTicket result = service.login("admin", "test");

        assertEquals(result.getAccountName(), "admin");
        assertEquals(result.getDisplayName(), "Main Administrator");
        assertEqualsNoOrder(result.getRoles().toArray(), new Object[]{"ADMIN", "USER"});

        LOG.debug("Logged in: {}", result);
    }


    public void checkTicket() throws InvalidLoginException, InvalidTicketException,
            NoSuchAccountException, NoSuchTicketException {
        OfficeLoginTicket result = service.login("admin", "test");

        LOG.info("Ticket created: {}", result);

        result = service.check(result);

        LOG.info("Ticket checked OK: {}", result);
    }


    @Test(
            expectedExceptions = NoSuchTicketException.class
    )
    public void logoutTicket() throws InvalidLoginException, InvalidTicketException, NoSuchTicketException {
        OfficeLoginTicket result = null;

        try {
            result = service.login("admin", "test");

            service.logout(result);

        } catch (InvalidLoginException e) {
            Assert.fail("Login and logout should not fail!");
        } catch (NoSuchAccountException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
        }

        service.check(result);
    }


    public void doubleLogin() throws InvalidLoginException, NoSuchAccountException {
        OfficeLoginTicket ticket1 = service.login("admin", "test");
        OfficeLoginTicket ticket2 = service.login("admin", "test");

        assertEquals(ticket1, ticket2);
    }


    public void testHousekeepingOfOldTickets() {
        Account account = accountRepository.findByAccountName("test");
        SecurityTicket ticket = repository.findByAccount(account);
        checkState(ticket != null, "There has to be a ticket for account 'test'");
        checkState(!ticket.isValid(), "The ticket for 'test' has to be invalid!");

        housekeeping.deleteOldTickets();

        ticket = repository.findByAccount(account);

        assertNull(ticket, "There should be no ticket for account 'test'!");
    }



    private void startTest(final String testName, final String message) {
        MDC.put("test", testName);

        LOG.debug(message);
    }


    private void startTest(final String testName, final String message, final Object... args) {
        MDC.put("test", testName);

        LOG.debug(message, args);
    }

    @BeforeMethod
    protected void addUniqueTestId() {
        MDC.remove("test");
        MDC.put("id", UUID.randomUUID().toString());
    }

    @AfterMethod
    protected void removeUniqueTestId() {
        MDC.remove("id");
        MDC.remove("test");
    }


    @BeforeClass
    protected void brabbleStart() {
        MDC.remove("id");
        MDC.remove("test");

        MDC.put("test-class", SecurityTicketHandlingIT.class.getSimpleName());

        LOG.info("Checking of class: {}", SecurityTicketHandlingIT.class.getSimpleName());
    }

    @AfterClass
    protected void brabbleEnd() {
        MDC.remove("id");
        MDC.remove("test");

        LOG.info("Finished checking of class: {}", SecurityTicketHandlingIT.class.getSimpleName());

        MDC.remove("test-class");
    }
}
