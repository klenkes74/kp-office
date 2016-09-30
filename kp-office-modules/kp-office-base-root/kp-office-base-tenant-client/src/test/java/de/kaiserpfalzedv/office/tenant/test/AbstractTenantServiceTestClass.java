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

package de.kaiserpfalzedv.office.tenant.test;

import java.util.Collection;
import java.util.UUID;

import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.TenantDoesNotExistException;
import de.kaiserpfalzedv.office.tenant.TenantExistsException;
import de.kaiserpfalzedv.office.tenant.TenantService;
import de.kaiserpfalzedv.office.tenant.impl.NullTenant;
import de.kaiserpfalzedv.office.tenant.impl.TenantBuilder;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.fail;

/**
 * The tests for the TenantService. This abstract class contains all tests that are run against the
 * {@link TenantService} as returned by the method {@link #createService()} that derived classes need to implement.
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-20
 */
public abstract class AbstractTenantServiceTestClass {
    private static final UUID TENANT_ID = UUID.randomUUID();
    private static final UUID ID = UUID.randomUUID();
    private static final String KEY = "KEY-006";
    private static final String DISPLAY_NAME = "display name";
    private static final String FULL_NAME = "full name";

    private TenantService service;

    @Test
    public void checkCreateNewTenant() throws TenantExistsException {
        Tenant data = createDefaultTenant();

        Tenant result = service.create(data);

        assertEquals(data, result);
        assertNotEquals(System.identityHashCode(result), System.identityHashCode(data));
        assertEquals(data.getTenant(), result.getTenant());
        assertEquals(data.getId(), result.getId());
        assertEquals(data.getDisplayName(), result.getDisplayName());
        assertEquals(data.getFullName(), result.getFullName());
    }

    private Tenant createDefaultTenant() {
        return new TenantBuilder()
                .withTenantId(TENANT_ID)
                .withId(ID)
                .withKey(KEY)
                .withDisplayName(DISPLAY_NAME)
                .withFullName(FULL_NAME)
                .build();
    }

    @Test
    public void checkCreateExistingTenant() throws TenantExistsException {
        Tenant data = createDefaultTenant();

        service.create(data);

        try {
            service.create(data);

            fail("The second create should have thrown a TenantExistsException!");
        } catch (TenantExistsException e) {
            // every thing worked out as expected!
        }

        // No assert needed. A failure will be thrown inside the try-catch-block if the expected exception is missing.
    }

    @Test
    public void checkCreateTenantWithExistingKey() throws TenantExistsException {
        Tenant data = createDefaultTenant();

        service.create(data);

        try {
            Tenant second = new TenantBuilder()
                    .withDisplayName("another name")
                    .withFullName("another full name")
                    .withId(UUID.randomUUID())
                    .withKey(KEY)
                    .build();

            service.create(second);

            fail("The second create should have thrown a TenantExistsException!");
        } catch (TenantExistsException e) {
            // every thing worked out as expected!
        }

        // No assert needed. A failure will be thrown inside the try-catch-block if the expected exception is missing.
    }

    @Test
    public void checkCreateTenantWithSameDisplayName() throws TenantExistsException {
        Tenant data = createDefaultTenant();
        service.create(data);

        try {
            Tenant second = new TenantBuilder()
                    .withDisplayName(DISPLAY_NAME)
                    .withFullName("another full name")
                    .withId(UUID.randomUUID())
                    .withKey(UUID.randomUUID().toString())
                    .build();

            service.create(second);

            fail("The second create should have thrown a TenantExistsException!");
        } catch (TenantExistsException e) {
            // every thing worked out as expected!
        }

        // No assert needed. A failure will be thrown inside the try-catch-block if the expected exception is missing.
    }

    @Test
    public void checkCreateTenantWithSameFullName() throws TenantExistsException {
        Tenant data = createDefaultTenant();
        service.create(data);

        try {
            Tenant second = new TenantBuilder()
                    .withDisplayName("another display name")
                    .withFullName(FULL_NAME)
                    .withId(UUID.randomUUID())
                    .withKey(UUID.randomUUID().toString())
                    .build();

            service.create(second);

            fail("The second create should have thrown a TenantExistsException!");
        } catch (TenantExistsException e) {
            // every thing worked out as expected!
        }

        // No assert needed. A failure will be thrown inside the try-catch-block if the expected exception is missing.
    }

    @Test
    public void checkRetrieveExistingTenant() throws TenantExistsException, TenantDoesNotExistException {
        Tenant data = createDefaultTenant();
        service.create(data);

        Tenant result = service.retrieve(data.getId());

        assertEquals(data, result);
        assertNotEquals(System.identityHashCode(result), System.identityHashCode(data));
        assertEquals(data.getTenant(), result.getTenant());
        assertEquals(data.getId(), result.getId());
        assertEquals(data.getDisplayName(), result.getDisplayName());
        assertEquals(data.getFullName(), result.getFullName());
    }

    @Test
    public void checkRetrieveNonExistingTenant() throws TenantExistsException {
        Tenant data = createDefaultTenant();
        service.create(data);

        UUID randomId = UUID.randomUUID();

        try {
            service.retrieve(randomId);
            fail("There should be no tenant with Id #" + randomId.toString() + "!");
        } catch (TenantDoesNotExistException e) {
            // every thing worked out as expected!
        }

        // No assert needed. A failure will be thrown inside the try-catch-block if the expected exception is missing.
    }

    @Test
    public void checkRetrieveNullTenant() throws TenantDoesNotExistException {
        Tenant nullTenant = new NullTenant();

        Tenant result = service.retrieve(nullTenant.getId());

        assertEquals(result, nullTenant);
    }

    @Test
    public void checkRetrieveAllTenants() throws TenantExistsException {
        for (int i=1001; i <= 1050; i++) {
            Tenant data = new TenantBuilder()
                    .withKey("K" + i)
                    .withDisplayName("Tenant #" + i)
                    .withFullName("Tenant Nr. " + i)
                    .build();

            service.create(data);
        }

        Collection<Tenant> result = service.retrieve();

        assertEquals(result.size(), 51); // The null tenant will be returned in any case.
    }

    @Test
    public void checkUpdateExistingTenant() throws TenantExistsException, TenantDoesNotExistException {
        Tenant orig = createDefaultTenant();
        service.create(orig);

        Tenant change = new TenantBuilder().withTenant(orig).withDisplayName("new display name").build();
        Tenant result = service.update(change);

        assertEquals(result, orig);
        assertNotEquals(result.getDisplayName(), orig.getDisplayName());
    }

    @Test
    public void checkUpdateTenantWithDisplayName() throws TenantExistsException, TenantDoesNotExistException {
        Tenant orig = createDefaultTenant();
        service.create(orig);

        Tenant change = new TenantBuilder().withTenant(orig).withDisplayName("new display name").build();
        service.update(change);

        Tenant second = new TenantBuilder().withTenant(orig).withFullName("another full name")
                                           .withId(UUID.randomUUID()).withKey(UUID.randomUUID().toString())
                                           .build();
        Tenant result = service.create(second);

        assertEquals(result, second);
        assertEquals(result.getDisplayName(), second.getDisplayName());
    }

    @Test
    public void checkUpdateTenantWithFullName() throws TenantExistsException, TenantDoesNotExistException {
        Tenant orig = createDefaultTenant();
        service.create(orig);

        Tenant change = new TenantBuilder()
                .withTenant(orig)
                .withFullName("new full name")
                .build();
        service.update(change);

        Tenant second = new TenantBuilder()
                .withTenant(orig)
                .withDisplayName("another display name")
                .withId(UUID.randomUUID())
                .withKey(UUID.randomUUID().toString())
                .build();
        Tenant result = service.create(second);

        assertEquals(result, second);
        assertEquals(result.getFullName(), second.getFullName());
    }

    @Test
    public void checkUpdateNonexistingTenant() throws TenantExistsException {
        Tenant change = createDefaultTenant();

        try {
            service.update(change);

            fail("The TenantDoesNotExistException should have been thrown while updating a non-existant tenant!");
        } catch (TenantDoesNotExistException e) {
            // every thing worked out as expected!
        }

        // No assert needed. A failure will be thrown inside the try-catch-block if the expected exception is missing.
    }

    @Test
    public void checkDeleteExistingTenant() throws TenantExistsException {
        Tenant data = createDefaultTenant();
        service.create(data);

        service.delete(data.getId());

        try {
            service.retrieve(data.getId());

            fail("There should be no tenant with id #" + data.getId());
        } catch (TenantDoesNotExistException e) {
            // every thing worked out as expected!
        }

        // No assert needed. A failure will be thrown inside the try-catch-block if the expected exception is missing.
    }

    @Test
    public void checkDeleteAndRecreateTenantWithSameData() throws TenantExistsException, TenantDoesNotExistException {
        Tenant orig = createDefaultTenant();
        service.create(orig);

        service.delete(orig.getId());

        Tenant result = service.create(orig);

        assertEquals(result, orig);
        assertEquals(result.getDisplayName(), orig.getDisplayName());
    }

    @Test
    public void checkDeleteNonexistingTenant() throws TenantExistsException {
        UUID randomId = UUID.randomUUID();

        service.delete(randomId);

        try {
            service.retrieve(randomId);

            fail("There should be no tenant with id #" + randomId);
        } catch (TenantDoesNotExistException e) {
            // every thing worked out as expected!
        }

        // No assert needed. A failure will be thrown inside the try-catch-block if the expected exception is missing.
    }

    @Before
    public void setUpService() {
        service = createService();
    }

    /**
     * Creates the tenant service implementation to be tested against the tests in this class.
     *
     * @return The TenantService to be checked.
     */
    abstract public TenantService createService();
}
