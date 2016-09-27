/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.tenant.adapter.data.jpa;

import java.util.Set;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.TenantExistsException;
import de.kaiserpfalzedv.office.tenant.adapter.data.TenantDataAdapter;
import de.kaiserpfalzedv.office.tenant.impl.TenantBuilder;
import de.kaiserpfalzedv.office.tenant.impl.TenantImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-24
 */
public class TenantJpaDataAdapterTest {
    private static final Logger LOG = LoggerFactory.getLogger(TenantJpaDataAdapterTest.class);

    private static final UUID TENANT_ID = UUID.randomUUID();
    private static final UUID ID = UUID.randomUUID();
    private static final String DISPLAY_NAME = "Display Name";
    private static final String FULL_NAME = "Full Name";

    private static EntityManagerFactory emf;
    private static TenantImpl data;

    static {
        if (!SLF4JBridgeHandler.isInstalled()) {
            SLF4JBridgeHandler.install();
        }
    }

    private EntityManager em;
    private TenantDataAdapter service;

    @BeforeClass
    public static void createEntityManagerFactory() {
        emf = Persistence.createEntityManagerFactory("tenant");

        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();

        data = (TenantImpl) new TenantBuilder()
                .withId(ID)
                .withTenantId(TENANT_ID)
                .withDisplayName(DISPLAY_NAME)
                .withFullName(FULL_NAME)
                .build();
        em.persist(data);
        em.getTransaction().commit();
    }

    @AfterClass
    public static void destroyEntityManagerFactory() {
        if (emf != null) {
            emf.close();
        }
    }

    @Test
    public void checkCreateNew() throws TenantExistsException {
        Tenant data = new TenantBuilder()
                .withId(UUID.randomUUID())
                .withTenantId(TENANT_ID)
                .withFullName(FULL_NAME + " 2")
                .withDisplayName(DISPLAY_NAME + " 2")
                .build();


        Tenant result = service.create(data);


        LOG.debug("Data: {}", data);
        LOG.debug("Result: {}", result);

        assertTrue(data.equals(result));
        assertTrue(result.equals(data));
        assertTrue(TenantImpl.class.isAssignableFrom(result.getClass()));

        assertEquals(data.getTenant(), result.getTenant());
        assertEquals(data.getId(), result.getId());
        assertEquals(data.getDisplayName(), result.getDisplayName());
        assertEquals(data.getFullName(), result.getFullName());
    }

    @Test
    public void checkRetrieveExisting() throws TenantExistsException, TenantDoesNotExistException {
        Tenant result = service.retrieve(ID);
        LOG.debug("Result: {}", result);

        assertEquals(TENANT_ID, result.getTenant());
        assertEquals(DISPLAY_NAME, result.getDisplayName());
        assertEquals(FULL_NAME, result.getFullName());
    }

    @Test
    public void checkRetrieveNonExisting() {
        try {
            service.retrieve(UUID.randomUUID());

            fail("A TenantDoesNotExistException should have been thrown!");
        } catch (TenantDoesNotExistException e) {
            // Nothing to do. Everything as expected!
        }

        // No asserts. We are checking the exception!
    }

    @Test
    public void checkRetrieveAll() {
        Set<Tenant> list = service.retrieve();
        Tenant result = list.iterator().next();
        LOG.debug("Result: {}", result);

        assertTrue(result.equals(data));
    }

    @Test
    public void checkUpdateExisting() throws TenantExistsException, TenantDoesNotExistException {
        Tenant data = new TenantBuilder()
                .withTenant(TenantJpaDataAdapterTest.data)
                .withFullName("Fullname changed!")
                .build();

        Tenant result = service.update(data);
        LOG.debug("Result: {}", result);

        assertTrue(TenantJpaDataAdapterTest.data.equals(result));
        assertTrue(TenantJpaDataAdapterTest.data.equals(data));
        assertNotEquals(result.getFullName(), TenantJpaDataAdapterTest.data.getFullName());
    }

    @Test
    public void checkUpdateNotExisting() throws TenantExistsException {
        Tenant data = new TenantBuilder()
                .withDisplayName("A completely new display name")
                .withFullName("Fullname changed!")
                .build();

        try {
            service.update(data);

            fail("A TenantDoesNotExistException should have been thrown!");
        } catch (TenantDoesNotExistException e) {
            // Nothing to do. Everything as expected!
        }

        // No asserts. We are checking the exception!
    }

    @Test
    public void checkDeleteExisting() throws TenantDoesNotExistException {
        service.delete(ID);
    }

    @Test
    public void checkDeleteNotExisting() {
        try {
            service.delete(UUID.randomUUID());

            fail("A TenantDoesNotExistException should have been thrown!");
        } catch (TenantDoesNotExistException e) {
            // Nothing to do. Everything as expected!
        }

        // No asserts. We are checking the exception!
    }

    @Before
    public void setupService() throws TenantExistsException {
        em = emf.createEntityManager();
        em.getTransaction().begin();

        service = new TenantJpaDataAdapterImpl(em);
    }

    @After
    public void teardownService() {
        try {
            service.delete(ID);
        } catch (TenantDoesNotExistException e) {
            // it's ok, if it not there any more!
        }

        if (em != null) {
            em.getTransaction().rollback();
            em.close();
        }

        service = null;
    }
}
