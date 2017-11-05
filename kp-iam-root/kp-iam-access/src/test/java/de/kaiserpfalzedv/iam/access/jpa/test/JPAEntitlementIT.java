/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.iam.access.jpa.test;

import com.google.common.base.Preconditions;
import de.kaiserpfalzedv.iam.access.jpa.roles.JPAEntitlement;
import de.kaiserpfalzedv.iam.access.jpa.roles.JPAEntitlementRepositoryImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import javax.validation.constraints.NotNull;
import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(Arquillian.class)
public class JPAEntitlementIT {
    private static final Logger LOG = LoggerFactory.getLogger(JPAEntitlementIT.class);

    @Inject
    UserTransaction utx;

    @PersistenceContext(name = "ACCESS")
    private EntityManager em;

    @Inject
    private JPAEntitlementRepositoryImpl repository;


    @Deployment
    public static Archive deploy() {
        MDC.put("test-class", "entitlement-repo");
        MDC.put("test", "prepare-deploy");

        JavaArchive ejb = ShrinkWrap
                .create(JavaArchive.class, "kp-iam-access.jar")
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/ejb-jar.xml", "META-INF/ejb-jar.xml")
                .addAsResource("META-INF/beans.xml", "META-INF/beans.xml")
                .addPackages(true, "de.kaiserpfalzedv.iam")
                .addManifest();
        LOG.trace("Created EJB Archive: {}", ejb);

        JavaArchive commons = ShrinkWrap
                .create(JavaArchive.class, "kp-commons.jar")
                .addPackages(
                        true,
                        "de.kaiserpfalzedv.commons.jpa",
                        "de.kaiserpfalzedv.commons.api.data",
                        "de.kaiserpfalzedv.commons.api.multitenancy",
                        "de.kaiserpfalzedv.commons.api.init"
                )
                .addClasses(
                        de.kaiserpfalzedv.commons.api.BaseBusinessException.class,
                        de.kaiserpfalzedv.commons.api.BaseSystemException.class,
                        de.kaiserpfalzedv.commons.api.BuilderException.class,
                        de.kaiserpfalzedv.commons.api.Logging.class
                )
                .addManifest();
        LOG.trace("Created 'kp-commons.jar': {}", commons);

        JavaArchive tenant = ShrinkWrap
                .create(JavaArchive.class, "kp-tenant.jar")
                .addClass(de.kaiserpfalzedv.iam.tenant.api.Tenant.class)
                .addManifest();
        LOG.trace("Created 'kp-tenant.jar': {}", tenant);

        EnterpriseArchive ear = ShrinkWrap
                .create(EnterpriseArchive.class, "kp-iam.ear")
                .addAsLibrary(commons)
                .addAsLibrary(tenant)
                .addAsModule(ejb)
                .addManifest();
        LOG.trace("Created EAR: {}", ear);


        MDC.remove("test");
        return ear;
    }

    @AfterClass
    public static void tearDownMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void shouldBeDeployed() {
        logMethod("deployment", "Everything should be deployed correctly");

        assertNotNull(utx);
        assertNotNull(em);
        assertNotNull(repository);
    }


    @Test
    public void shouldReturnTest1EntitlementWhenAskedForIt() {
        logMethod("retrieve-entitlement", "Retrieve entitlement with ID 1 ...");

        Optional<JPAEntitlement> result = repository.retrieve(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        LOG.trace("Result: {}", result.isPresent() ? result.get() : "./.");

        assertTrue(result.isPresent());
    }

    @Test
    public void shouldGetTheNewNameWhenTheNameHasBeenChanged() {
        logMethod("update-name", "Change name of the entitlement.");

        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");

        Optional<JPAEntitlement> data = repository.retrieve(id);
        LOG.trace("Data: {}", data.isPresent() ? data.get() : "./.");
        Preconditions.checkArgument("test1".equals(data.get().getName()), "The name should be 'test1'.");

        JPAEntitlement changed = data.get();
        changed.setName("changed name 1");

        repository.update(changed);

        Optional<JPAEntitlement> result = repository.retrieve(id);
        LOG.trace("Result: {}", result.isPresent() ? result.get() : "./.");

        assertEquals("changed name 1", result.get().getName());
    }


    private void logMethod(@NotNull final String shortName, @NotNull final String message, Object... data) {
        MDC.put("test", shortName);
        LOG.info(message, data);
    }

    @Before
    public void setUp() throws SystemException, NotSupportedException {
        utx.begin();
        em.joinTransaction();
    }

    @After
    public void tearDown() throws HeuristicRollbackException, RollbackException, HeuristicMixedException, SystemException {
        utx.commit();

        MDC.remove("test");
    }
}
