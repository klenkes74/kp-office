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

import de.kaiserpfalzEdv.office.security.OfficePrincipal;
import de.kaiserpfalzEdv.office.security.OfficePrincipalDTO;
import de.kaiserpfalzEdv.office.security.OfficeSubjectDTO;
import de.kaiserpfalzEdv.office.security.OfficeTicket;
import de.kaiserpfalzEdv.office.security.shiro.loginAuthorization.OfficeLoginRealm;
import de.kaiserpfalzEdv.office.security.shiro.loginAuthorization.OfficeLoginToken;
import de.kaiserpfalzEdv.office.security.shiro.session.OfficeSubjectImpl;
import de.kaiserpfalzEdv.office.security.shiro.ticketAuthorization.OfficeTicketRealm;
import de.kaiserpfalzEdv.office.security.shiro.ticketAuthorization.OfficeTicketToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.UUID;

import static com.google.common.base.Preconditions.checkState;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

/**
 * @author klenkes
 * @since 2014Q
 */
@Test
@ContextConfiguration("/ShiroServerSideIT-applicationContext.xml")
public class ShiroServerSideIT extends AbstractTestNGSpringContextTests {
    private static final Logger LOG = LoggerFactory.getLogger(ShiroServerSideIT.class);

    @Inject
    private SecurityManager service;

    @Inject
    private MockSecurityClient securityClient;

    @Inject
    @Named("user-klenkes")
    private OfficeSubjectDTO klenkes;

    @Inject
    private OfficeLoginRealm loginRealm;

    @Inject
    private OfficeTicketRealm ticketRealm;


    @PostConstruct
    @Test(enabled = false)
    public void init() {
        LOG.debug("SecurityManager: {}", service);
    }


    public void checkSimpleAuthenticationLogin() {
        SubjectContext sc = new DefaultSubjectContext();
        sc.setSecurityManager(service);
        OfficeLoginToken token = new OfficeLoginToken(new OfficePrincipalDTO("klenkes74", "test"), "abc1234");
        sc.setAuthenticationToken(token);

        Subject subject = service.createSubject(sc);
        subject = service.login(subject, token);

        OfficeTicket ticket = null;
        int counter = 0;
        for (Object p : subject.getPrincipals().asSet()) {
            if (p instanceof OfficeTicket) {
                ticket = (OfficeTicket) p;
                LOG.debug("Found ticket: {}", ticket);
                counter++;
            }
        }
        assertEquals(counter, 1, "There should be only one ticket!");

        assertEquals(ticket.getTicket().toString(), subject.getSession().getId(), "Session ID should be ticket id!");
        assertTrue(subject.hasRole("USER"), "Should be in role 'USER'!");
        assertFalse(subject.hasRole("ABC"), "Should not be in role 'ABC'!");

        assertTrue(subject.isPermitted("Test:*:test"), "'Test:*:test' should be permitted!");
        assertTrue(subject.isPermitted("Test:abc:test"), "'Test:*:test' should be permitted!");
        assertFalse(subject.isPermitted("abc:abc:abc"), "'abc:abc:abc' should not be permitted!");
    }


    public void checkTicketLogin() {
        SubjectContext sc = new DefaultSubjectContext();
        sc.setSecurityManager(service);
        OfficeTicketToken token = new OfficeTicketToken(securityClient.getTicketForSubject(klenkes));
        sc.setAuthenticationToken(token);

        Subject subject = service.createSubject(sc);
        subject = service.login(subject, token);

        assertEquals(subject.getSession().getId(), token.getCredentials().getTicket().toString(), "Session ID should be the ticket ID");
        assertTrue(subject.hasRole("USER"), "Should be in role 'USER'!");
        assertFalse(subject.hasRole("ABC"), "Should not be in role 'ABC'!");

        assertTrue(subject.isPermitted("Test:*:test"), "'Test:*:test' should be permitted!");
        assertTrue(subject.isPermitted("Test:abc:test"), "'Test:*:test' should be permitted!");
        assertFalse(subject.isPermitted("abc:abc:abc"), "'abc:abc:abc' should not be permitted!");
    }


    public void checkSessionCreation() {
        SubjectContext sc = new DefaultSubjectContext();
        sc.setSecurityManager(service);
        checkState(sc.getSecurityManager().equals(service), "Security manager is not set!");

        OfficePrincipal principal = new OfficePrincipalDTO("klenkes74", "test");
        PrincipalCollection principals = new SimplePrincipalCollection(principal, loginRealm.getName());
        OfficeLoginToken token = new OfficeLoginToken(principal, "abc1234");
        sc.setAuthenticationToken(token);

        OfficeSubjectImpl subject = new OfficeSubjectImpl("test", null, principals, false, "localhost", null, true, service);
        OfficeSubjectImpl loggedIn = (OfficeSubjectImpl) service.login(subject, token);

        Session session = loggedIn.getSession();
        session.setTimeout(3600000L);
        UUID sessionId = UUID.fromString((String) session.getId());


        OfficeTicket ticket = null;
        for (Object p : loggedIn.getPrincipals().asSet()) {
            LOG.trace("Principal: {}", p);
            if (p instanceof OfficeTicket) {
                ticket = (OfficeTicket) p;
            }
        }
        assertEquals(sessionId, ticket.getTicket(), "Ticket ID and Session ID should match!");

        session.setAttribute("test", "hubbabubba");

        assertEquals(session.getAttribute("test"), "hubbabubba");
    }

    @Test(enabled = false)
    public void checkSessionTakeover() {
        SubjectContext sc = new DefaultSubjectContext();
        sc.setSecurityManager(service);
        checkState(sc.getSecurityManager().equals(service), "Security manager is not set!");

        OfficePrincipal principal = new OfficePrincipalDTO("klenkes74", "test");
        PrincipalCollection principals = new SimplePrincipalCollection(principal, loginRealm.getName());
        OfficeLoginToken token = new OfficeLoginToken(principal, "abc1234");
        sc.setAuthenticationToken(token);

        OfficeSubjectImpl subject = new OfficeSubjectImpl("test", null, principals, false, "localhost", null, true, service);
        OfficeSubjectImpl loggedIn = (OfficeSubjectImpl) service.login(subject, token);

        Session session = loggedIn.getSession();
        session.setTimeout(3600000L);
        UUID sessionId = UUID.fromString((String) session.getId());
        session.setAttribute("test", "hubbabubba");
        LOG.debug("Original Session-ID: {}", sessionId);


        OfficeTicket ticket = securityClient.getTicketForSubject(klenkes);
        OfficeTicketToken ticketToken = new OfficeTicketToken(ticket);

        principals = new SimplePrincipalCollection(ticket, ticketRealm.getName());
        OfficeSubjectImpl ticketSubject = new OfficeSubjectImpl("test", ticket, principals, false, "localhost", null, true, service);
        OfficeSubjectImpl ticketLoggedIn = (OfficeSubjectImpl) service.login(ticketSubject, ticketToken);
        Session ticketSession = ticketLoggedIn.getSession();
        UUID ticketSessionId = UUID.fromString((String) ticketSession.getId());

        LOG.debug("Subject: {}", ticketLoggedIn);
        LOG.debug("Session-ID: {}", ticketSessionId);

        assertEquals(ticketSessionId, ticket.getTicket(), "Ticket ID and Session ID should match!");
        assertEquals(ticketSessionId, sessionId, "Both session ids should match!");
        assertEquals(ticketSession.getAttribute("test"), "hubbabubba", "The data of the session failed!");
    }
}