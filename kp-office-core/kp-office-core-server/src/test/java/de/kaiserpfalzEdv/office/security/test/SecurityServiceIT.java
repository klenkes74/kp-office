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

import de.kaiserpfalzEdv.office.security.InvalidLogin;
import de.kaiserpfalzEdv.office.security.InvalidTicket;
import de.kaiserpfalzEdv.office.security.OfficeLoginTicket;
import de.kaiserpfalzEdv.office.security.SecurityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import javax.inject.Inject;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Test
@ContextConfiguration("/beans.xml")
public class SecurityServiceIT extends AbstractTestNGSpringContextTests {
    private static final Logger LOG = LoggerFactory.getLogger(SecurityServiceIT.class);

    @Inject
    private SecurityService service;


    public void loginValidUser() throws InvalidLogin {
        OfficeLoginTicket result = service.login("admin", "test");

        LOG.debug("Logged in: {}", result);
    }


    public void checkTicket() throws InvalidLogin, InvalidTicket {
        OfficeLoginTicket result = service.login("admin", "test");

        result = service.check(result);

        LOG.debug("Logged in: {}", result);
    }


    @Test(
            expectedExceptions = InvalidTicket.class
    )
    public void logoutTicket() throws InvalidLogin, InvalidTicket {
        OfficeLoginTicket result = null;

        try {
            result = service.login("admin", "test");

            service.logout(result);

        } catch (InvalidLogin e) {
            Assert.fail("Login and logout should not fail!");
        }

        service.check(result);
    }


    public void doubleLogin() throws InvalidLogin {
        OfficeLoginTicket ticket1 = service.login("admin", "test");
        OfficeLoginTicket ticket2 = service.login("admin", "test");

        Assert.assertEquals(ticket1, ticket2);
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

        MDC.put("test-class", SecurityServiceIT.class.getSimpleName());

        LOG.info("Checking of class: {}", SecurityServiceIT.class.getSimpleName());
    }

    @AfterClass
    protected void brabbleEnd() {
        MDC.remove("id");
        MDC.remove("test");

        LOG.info("Finished checking of class: {}", SecurityServiceIT.class.getSimpleName());

        MDC.remove("test-class");
    }
}
