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

import java.io.File;
import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.HeuristicMixedException;
import javax.transaction.HeuristicRollbackException;
import javax.transaction.NotSupportedException;
import javax.transaction.RollbackException;
import javax.transaction.SystemException;
import javax.transaction.UserTransaction;
import javax.validation.constraints.NotNull;

import com.google.common.base.Preconditions;
import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;
import de.kaiserpfalzedv.commons.api.data.paging.Pageable;
import de.kaiserpfalzedv.commons.api.data.paging.PageableBuilder;
import de.kaiserpfalzedv.commons.api.data.paging.PagedListable;
import de.kaiserpfalzedv.commons.api.data.query.Predicate;
import de.kaiserpfalzedv.iam.access.api.roles.Entitlement;
import de.kaiserpfalzedv.iam.access.api.roles.EntitlementPredicate;
import de.kaiserpfalzedv.iam.access.jpa.roles.JPAEntitlement;
import de.kaiserpfalzedv.iam.access.jpa.roles.JPAEntitlementBuilder;
import de.kaiserpfalzedv.iam.access.jpa.roles.JPAEntitlementRepositoryImpl;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.Archive;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.EnterpriseArchive;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
@RunWith(Arquillian.class)
public class JPAEntitlementIT {
    private static final Logger LOG = LoggerFactory.getLogger(JPAEntitlementIT.class);

    @Inject
    private UserTransaction utx;

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

        File[] libraries = Maven
                .resolver()
                .resolve(
                        "org.apache.commons:commons-lang3:3.4",
                        "com.goole.guava:guava:23.0",
                        "de.kaiserpfalz-edv.commons:kp-commons-ejb:1.0.0-SNAPSHOT",
                        "de.kaiserpfalz-edv.iam:kp-iam-tenant:jar:api:1.0.0-SNAPSHOT",
                        "de.kaiserpfalz-edv.iam:kp-iam-tenant:jar:jpa:1.0.0-SNAPSHOT"
                )
                .withTransitivity()
                .asFile();
        LOG.trace("Libraries ({} files): {}", libraries.length, libraries);

        EnterpriseArchive ear = ShrinkWrap
                .create(EnterpriseArchive.class, "kp-iam.ear")
                .addAsModule(ejb)
                .addAsLibraries(libraries)
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
    public void shouldCreateEntitlementWhenTheEntitlementDoesNotExist() throws ObjectExistsException {
        logMethod("create-entitlement", "Creating a new entitlement");

        UUID id = UUID.randomUUID();

        JPAEntitlement data = new JPAEntitlementBuilder()
                .withId(id)
                .withDisplayName("new-entitlement")
                .withFullName("A new entitlement")
                .withDescriptionKey("de.kaiserpfalzedv.iam.permission.caption")
                .build();

        JPAEntitlement result = repository.create(data);
        LOG.trace("Result: {}", result);

        assertEquals(id, result.getId());
    }

    @Test
    public void shouldThrowExceptionWhenTheEntitlementAlreadyExists() {
        logMethod("create-existing-entitlement", "Create a duplicate of the entitlement");

        JPAEntitlement data = new JPAEntitlementBuilder()
                .withId(UUID.fromString("11111111-1111-1111-1111-111111111111"))
                .withDisplayName("new-entitlement")
                .withFullName("A new entitlement")
                .withDescriptionKey("de.kaiserpfalzedv.iam.permission.caption")
                .build();

        try {
            JPAEntitlement result = repository.create(data);

            fail("The creation should have failed due to duplicate id!");
        } catch (ObjectExistsException e) {
            // everything is fine.
        }
    }

    @Test
    public void shouldReturnTest1EntitlementWhenAskedForIt() {
        logMethod("retrieve-entitlement", "Retrieve entitlement with ID 1 ...");

        Optional<JPAEntitlement> result = repository.retrieve(UUID.fromString("11111111-1111-1111-1111-111111111111"));
        LOG.trace("Result: {}", result.isPresent() ? result.get() : "./.");

        assertTrue(result.isPresent());
    }

    @Test
    public void shouldReturnAnEmptyOptionalWhenAskedForANonExistingEntitlement() {
        logMethod("find-non-existing", "Trying to load a non-exsting entitlement");

        UUID id = UUID.fromString("12121212-1212-1212-1212-121212121212");

        Optional<JPAEntitlement> result = repository.retrieve(id);
        LOG.trace("Result: {}", result.isPresent() ? result.get() : "./.");

        assertFalse(result.isPresent());
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

    @Test
    public void shouldDeleteTheEntitlementWhenTheIdIsGiven() {
        logMethod("delete-entitlement", "Delete a preexisting entitlement.");

        UUID id = UUID.fromString("11111111-1111-1111-1111-111111111111");

        Optional<JPAEntitlement> data = repository.retrieve(id);
        LOG.trace("Data: {}", data.isPresent() ? data.get() : "./.");
        Preconditions.checkArgument(data.isPresent(), "The data should be there.");

        repository.delete(id);

        Optional<JPAEntitlement> result = repository.retrieve(id);
        LOG.trace("Result: {}", result.isPresent() ? result.get() : "./.");

        assertFalse(result.isPresent());
    }

    @Test
    public void shouldDeleteTheEntitlementWhenTheEntityIsGiven() {
        logMethod("delete-entitlement", "Delete a preexisting entitlement with full entity.");

        UUID id = UUID.fromString("44444444-4444-4444-4444-444444444444");

        Optional<JPAEntitlement> data = repository.retrieve(id);
        LOG.trace("Data: {}", data.isPresent() ? data.get() : "./.");
        Preconditions.checkArgument(data.isPresent(), "The data should be there.");

        repository.delete(data.get());

        Optional<JPAEntitlement> result = repository.retrieve(id);
        LOG.trace("Result: {}", result.isPresent() ? result.get() : "./.");

        assertFalse(result.isPresent());
    }

    @Test
    public void shouldDeleteTheEntitlementWhenANonExistingIdIsGiven() {
        logMethod("delete-non-existing-entitlement", "Delete a preexisting entitlement.");

        UUID id = UUID.fromString("14141414-1414-1414-1414-141414141414");

        Optional<JPAEntitlement> data = repository.retrieve(id);
        LOG.trace("Data: {}", data.isPresent() ? data.get() : "./.");
        Preconditions.checkArgument(!data.isPresent(), "The data should be not there.");

        repository.delete(id);

        Optional<JPAEntitlement> result = repository.retrieve(id);
        LOG.trace("Result: {}", result.isPresent() ? result.get() : "./.");

        assertFalse(result.isPresent());
    }

    @Test
    public void shouldReturnAPageOfOneElementWhenAskedForId4() {
        logMethod("predicate-query", "Creating a predicate-query and executing it ...");

        UUID id = UUID.fromString("44444444-4444-4444-4444-444444444444");

        Predicate<Entitlement> query = EntitlementPredicate.id().isEqualTo(id);

        Pageable page = new PageableBuilder()
                .withPage(0)
                .withSize(20)
                .build();

        PagedListable<JPAEntitlement> data = repository.retrieve(query, page);
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
