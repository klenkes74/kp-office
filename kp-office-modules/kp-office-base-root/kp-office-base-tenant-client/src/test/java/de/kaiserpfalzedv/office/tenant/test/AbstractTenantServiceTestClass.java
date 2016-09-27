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
    private static final String DISPLAY_NAME = "display name";
    private static final String FULL_NAME = "full name";

    private TenantService service;

    @Test
    public void checkCreateNewTenant() throws TenantExistsException {
        Tenant data = createDefaultTenant();

        Tenant result = service.createTenant(data);

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
                .withDisplayName(DISPLAY_NAME)
                .withFullName(FULL_NAME)
                .build();
    }

    @Test
    public void checkCreateExistingTenant() throws TenantExistsException {
        Tenant data = createDefaultTenant();

        service.createTenant(data);

        try {
            service.createTenant(data);

            fail("The second createTenant should have thrown a TenantExistsException!");
        } catch (TenantExistsException e) {
            // every thing worked out as expected!
        }

        // No assert needed. A failure will be thrown inside the try-catch-block if the expected exception is missing.
    }

    @Test
    public void checkCreateTenantWithSameDisplayName() throws TenantExistsException {
        Tenant data = createDefaultTenant();
        service.createTenant(data);

        try {
            Tenant second = new TenantBuilder()
                    .withDisplayName(DISPLAY_NAME)
                    .withFullName("another full name")
                    .withId(UUID.randomUUID())
                    .build();

            service.createTenant(second);

            fail("The second createTenant should have thrown a TenantExistsException!");
        } catch (TenantExistsException e) {
            // every thing worked out as expected!
        }

        // No assert needed. A failure will be thrown inside the try-catch-block if the expected exception is missing.
    }

    @Test
    public void checkCreateTenantWithSameFullName() throws TenantExistsException {
        Tenant data = createDefaultTenant();
        service.createTenant(data);

        try {
            Tenant second = new TenantBuilder()
                    .withDisplayName("another display name")
                    .withFullName(FULL_NAME)
                    .withId(UUID.randomUUID())
                    .build();

            service.createTenant(second);

            fail("The second createTenant should have thrown a TenantExistsException!");
        } catch (TenantExistsException e) {
            // every thing worked out as expected!
        }

        // No assert needed. A failure will be thrown inside the try-catch-block if the expected exception is missing.
    }

    @Test
    public void checkRetrieveExistingTenant() throws TenantExistsException, TenantDoesNotExistException {
        Tenant data = createDefaultTenant();
        service.createTenant(data);

        Tenant result = service.retrieveTenant(data.getId());

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
        service.createTenant(data);

        UUID randomId = UUID.randomUUID();

        try {
            service.retrieveTenant(randomId);
            fail("There should be no tenant with Id #" + randomId.toString() + "!");
        } catch (TenantDoesNotExistException e) {
            // every thing worked out as expected!
        }

        // No assert needed. A failure will be thrown inside the try-catch-block if the expected exception is missing.
    }

    @Test
    public void checkRetrieveNullTenant() throws TenantDoesNotExistException {
        Tenant nullTenant = new NullTenant();

        Tenant result = service.retrieveTenant(nullTenant.getId());

        assertEquals(result, nullTenant);
    }

    @Test
    public void checkRetrieveAllTenants() throws TenantExistsException {
        for (int i=1001; i <= 1050; i++) {
            Tenant data = new TenantBuilder()
                    .withDisplayName("Tenant #" + i)
                    .withFullName("Tenant Nr. " + i)
                    .build();

            service.createTenant(data);
        }

        Collection<Tenant> result = service.retrieveTenants();

        assertEquals(result.size(), 51); // The null tenant will be returned in any case.
    }

    @Test
    public void checkUpdateExistingTenant() throws TenantExistsException, TenantDoesNotExistException {
        Tenant orig = createDefaultTenant();
        service.createTenant(orig);

        Tenant change = new TenantBuilder().withTenant(orig).withDisplayName("new display name").build();
        Tenant result = service.updateTenant(change);

        assertEquals(result, orig);
        assertNotEquals(result.getDisplayName(), orig.getDisplayName());
    }

    @Test
    public void checkUpdateTenantWithDisplayName() throws TenantExistsException, TenantDoesNotExistException {
        Tenant orig = createDefaultTenant();
        service.createTenant(orig);

        Tenant change = new TenantBuilder().withTenant(orig).withDisplayName("new display name").build();
        service.updateTenant(change);

        Tenant second = new TenantBuilder().withTenant(orig).withFullName("another full name").withId(UUID.randomUUID()).build();
        Tenant result = service.createTenant(second);

        assertEquals(result, second);
        assertEquals(result.getDisplayName(), second.getDisplayName());
    }

    @Test
    public void checkUpdateTenantWithFullName() throws TenantExistsException, TenantDoesNotExistException {
        Tenant orig = createDefaultTenant();
        service.createTenant(orig);

        Tenant change = new TenantBuilder()
                .withTenant(orig)
                .withFullName("new full name")
                .build();
        service.updateTenant(change);

        Tenant second = new TenantBuilder()
                .withTenant(orig)
                .withDisplayName("another display name")
                .withId(UUID.randomUUID())
                .build();
        Tenant result = service.createTenant(second);

        assertEquals(result, second);
        assertEquals(result.getFullName(), second.getFullName());
    }

    @Test
    public void checkUpdateNonexistingTenant() throws TenantExistsException {
        Tenant change = createDefaultTenant();

        try {
            service.updateTenant(change);

            fail("The TenantDoesNotExistException should have been thrown while updating a non-existant tenant!");
        } catch (TenantDoesNotExistException e) {
            // every thing worked out as expected!
        }

        // No assert needed. A failure will be thrown inside the try-catch-block if the expected exception is missing.
    }

    @Test
    public void checkDeleteExistingTenant() throws TenantExistsException {
        Tenant data = createDefaultTenant();
        service.createTenant(data);

        service.deleteTenant(data.getId());

        try {
            service.retrieveTenant(data.getId());

            fail("There should be no tenant with id #" + data.getId());
        } catch (TenantDoesNotExistException e) {
            // every thing worked out as expected!
        }

        // No assert needed. A failure will be thrown inside the try-catch-block if the expected exception is missing.
    }

    @Test
    public void checkDeleteAndRecreateTenantWithSameData() throws TenantExistsException, TenantDoesNotExistException {
        Tenant orig = createDefaultTenant();
        service.createTenant(orig);

        service.deleteTenant(orig.getId());

        Tenant result = service.createTenant(orig);

        assertEquals(result, orig);
        assertEquals(result.getDisplayName(), orig.getDisplayName());
    }

    @Test
    public void checkDeleteNonexistingTenant() throws TenantExistsException {
        UUID randomId = UUID.randomUUID();

        service.deleteTenant(randomId);

        try {
            service.retrieveTenant(randomId);

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
