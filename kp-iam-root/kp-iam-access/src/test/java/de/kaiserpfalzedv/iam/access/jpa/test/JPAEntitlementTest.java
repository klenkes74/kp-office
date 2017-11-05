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

import de.kaiserpfalzedv.iam.access.jpa.roles.JPAEntitlementRepository;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.*;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.*;
import javax.validation.constraints.NotNull;
import java.io.File;

import static org.junit.Assert.assertNotNull;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(Arquillian.class)
public class JPAEntitlementTest {
    private static final Logger LOG = LoggerFactory.getLogger(JPAEntitlementTest.class);
    @Inject
    UserTransaction utx;
    @PersistenceContext(name = "ACCESS")
    private EntityManager em;
    @Inject
    private JPAEntitlementRepository repository;

    @Deployment
    public static Archive deploy() {
        JavaArchive ejb = ShrinkWrap
                .create(JavaArchive.class, "kp-iam-access.jar")
                .addAsResource("META-INF/test-persistence.xml", "META-INF/persistence.xml")
                .addAsResource("META-INF/ejb-jar.xml", "META-INF/ejb-jar.xml")
                .addAsResource("META-INF/beans.xml", "META-INF/beans.xml")
                .addPackages(true, "de.kaiserpfalzedv.iam")
                .addManifest();
        LOG.trace("Created EJB Archive: {}", ejb.toString(true));

        JavaArchive commons = ShrinkWrap
                .create(JavaArchive.class, "kp-commons-api.jar")
                .addPackages(true, "de.kaiserpfalzedv.commons.jpa")
                .addManifest();
        LOG.trace("KP Commonets 'jar': {}", commons);

        File[] mavenDependencies = Maven
                .resolver()
                .loadPomFromFile("pom.xml")
                .resolve("de.kaiserpfalz-edv.commons:kp-commons-api")
                .withTransitivity()
                .asFile();

        LOG.trace("Adding libraries to archive: {}", mavenDependencies);

        EnterpriseArchive ear = ShrinkWrap
                .create(EnterpriseArchive.class, "kp-iam.ear")
                .addAsLibraries(mavenDependencies)
                .addAsLibrary(commons)
                .addAsModule(ejb)
                .addManifest();
        LOG.trace("Created EAR: {}", ear.toString(true));

        return ear;
    }

    @BeforeClass
    public static void setupMDC() {
        MDC.put("test-class", "entitlement-repo");
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
