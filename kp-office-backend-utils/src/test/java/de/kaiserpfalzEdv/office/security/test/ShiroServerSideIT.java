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

import de.kaiserpfalzEdv.office.security.OfficeSubjectDTO;
import de.kaiserpfalzEdv.office.security.shiro.OfficeToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
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


    @PostConstruct
    @Test(enabled = false)
    public void init() {
        LOG.debug("SecurityManager: {}", service);
    }


    public void checkLogin() {
        SubjectContext sc = new DefaultSubjectContext();
        OfficeToken token = new OfficeToken("klenkes74", "test", securityClient.getTicketForSubject(klenkes));
        sc.setAuthenticationToken(token);

        Subject subject = service.createSubject(sc);
        subject = service.login(subject, token);

        assertTrue(subject.hasRole("USER"), "Should be in role 'USER'!");
        assertFalse(subject.hasRole("ABC"), "Should not be in role 'ABC'!");

        assertTrue(subject.isPermitted("Test:*:test"), "'Test:*:test' should be permitted!");
        assertTrue(subject.isPermitted("Test:abc:test"), "'Test:*:test' should be permitted!");
        assertFalse(subject.isPermitted("abc:abc:abc"), "'abc:abc:abc' should not be permitted!");
    }




    public void checkSession() {
        SubjectContext sc = new DefaultSubjectContext();
        OfficeToken token = new OfficeToken("klenkes74", "test", securityClient.getTicketForSubject(klenkes));
        sc.setAuthenticationToken(token);

        Subject subject = service.createSubject(sc);
        Subject loggedIn = service.login(subject, token);

        Session session = loggedIn.getSession();
        session.setTimeout(3600000L);
        UUID sessionId = UUID.fromString((String) session.getId());

        LOG.debug("Subject: {}", loggedIn);
        LOG.debug("Session-ID: {}", session.getId());
    }
}
