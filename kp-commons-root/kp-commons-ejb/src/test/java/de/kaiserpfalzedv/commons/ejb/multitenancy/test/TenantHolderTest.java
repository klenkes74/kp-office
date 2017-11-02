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

package de.kaiserpfalzedv.commons.ejb.multitenancy.test;

import de.kaiserpfalzedv.commons.api.multitenancy.TenantHolder;
import de.kaiserpfalzedv.commons.ejb.multitenancy.TenantHolderImpl;
import de.kaiserpfalzedv.commons.ejb.multitenancy.ThereIsNoTenantException;
import org.junit.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-13
 */
public class TenantHolderTest {
    private static final Logger LOG = LoggerFactory.getLogger(TenantHolderTest.class);

    /**
     * The default tenant to be stored.
     */
    private static final UUID DEFAULT_TENANT = UUID.randomUUID();

    /**
     * Class under test.
     */
    private TenantHolder cut;

    @BeforeClass
    static public void setMDC() {
        MDC.put("test-class", "tenant-holder");

    }

    @AfterClass
    static public void removeMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void checkStorageOfTenant() {
        MDC.put("test", "storing-tenant");
        LOG.debug("Checking storing of tenant with: {}", DEFAULT_TENANT);
        cut.setTenant(DEFAULT_TENANT);

        UUID result = cut.getTenant().orElse(null);
        LOG.trace("Result: {}", result);

        assertEquals("Wrong tenant returned!", DEFAULT_TENANT, result);
    }

    @Test
    public void checkMultiThreadStorageOfTenant() {
        int runners = 10;
        MDC.put("test", "multi-thread-storage");
        LOG.debug("Checking multi-threaded storage of tenant with {} runners", runners);

        Set<MultipleTenantStorageTestRunner> testRunners = new HashSet<>(runners);
        for (int i = 0; i < runners; i++) {
            MultipleTenantStorageTestRunner runner = new MultipleTenantStorageTestRunner("runner-" + i, cut);
            testRunners.add(runner);

            new Thread(runner).start();
        }

        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
        }

        for (MultipleTenantStorageTestRunner r : testRunners) {
            if (!r.tenantMatches()) {
                fail("At least one runner returned the wrong tenant (" + r.getThreadName() + ")");
            }
        }
    }

    @Test
    public void checkClearingOfTenant() {
        MDC.put("test", "single-thread-removal");
        LOG.debug("Checking clearing of tenant with: {}", DEFAULT_TENANT);

        cut.setTenant(DEFAULT_TENANT);
        cut.clearTenant();
        Optional<UUID> result = cut.getTenant();

        assertFalse("After clearing the result should be NULL", result.isPresent());
    }

    @Test
    public void checkMultiThreadRemovalOfTenant() {
        int runners = 10;
        MDC.put("test", "multi-thread-removal");
        LOG.debug("Checking multi-threaded removal of tenant with {} runners.", runners);

        Set<MultipleTenantRemovalTestRunner> testRunners = new HashSet<>(runners);
        for (int i = 0; i < runners; i++) {
            MultipleTenantRemovalTestRunner runner = new MultipleTenantRemovalTestRunner("runner-" + i, cut);
            testRunners.add(runner);

            new Thread(runner).start();
        }

        try {
            Thread.sleep(10L);
        } catch (InterruptedException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
        }

        for (MultipleTenantRemovalTestRunner r : testRunners) {
            if (!r.tenantRemoved()) {
                fail("At least one runner returned the wrong tenant (" + r.getThreadName() + ")");
            }
        }
    }

    @Test
    public void checkSuccessfullProviderOfTenant() {
        MDC.put("test", "provider-success");
        LOG.debug("Checking the producer in case there is a tenant stored.");

        cut.setTenant(DEFAULT_TENANT);

        UUID result = ((TenantHolderImpl) cut).produceTenant();
        LOG.trace("Result: {}", result);

        assertEquals(DEFAULT_TENANT, result);
    }

    @Test
    public void checkFailedProviderOfTenant() {
        MDC.put("test", "provider-failure");
        LOG.debug("Checking the producer in case there is no tenant stored.");

        try {
            ((TenantHolderImpl) cut).produceTenant();

            fail("When there is no tenant, there should be an exception: " + ThereIsNoTenantException.class.getSimpleName());
        } catch (ThereIsNoTenantException e) {
            // everything is fine. We wanted that exception!
        }
    }

    @Before
    public void setUp() {
        cut = new TenantHolderImpl();
    }

    @After
    public void tearDown() {
        MDC.remove("test");
    }

    class MultipleTenantStorageTestRunner implements Runnable {
        private UUID tenant = UUID.randomUUID();
        private UUID result;

        private String threadName;
        private TenantHolder holder;

        MultipleTenantStorageTestRunner(final String threadName, TenantHolder tenantHolder) {
            this.holder = tenantHolder;
            this.threadName = threadName;
        }

        @Override
        public void run() {
            holder.setTenant(tenant);

            try {
                Thread.sleep(5L);
            } catch (InterruptedException e) {
                LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);
            }

            result = holder.getTenant().orElse(null);

            LOG.trace("MultiTenantTestRunner({}): tenant={}, result={}, matches={}",
                      threadName, tenant, result, tenantMatches()
            );
        }

        boolean tenantMatches() {
            return tenant.equals(result);
        }

        String getThreadName() {
            return threadName;
        }
    }


    class MultipleTenantRemovalTestRunner implements Runnable {
        private UUID tenant = UUID.randomUUID();
        private boolean result = false;

        private String threadName;
        private TenantHolder holder;

        MultipleTenantRemovalTestRunner(final String threadName, TenantHolder tenantHolder) {
            this.holder = tenantHolder;
            this.threadName = threadName;
        }

        String getThreadName() {
            return threadName;
        }

        @Override
        public void run() {
            holder.setTenant(tenant);
            UUID storedTenant = holder.getTenant().get();

            if (tenant.equals(storedTenant)) {
                holder.clearTenant();

                result = !holder.getTenant().isPresent();
            } else {
                LOG.error("Tenant has been overwritten: expected={}, actual={}", tenant, storedTenant);
            }


            LOG.trace("MultiTenantTestRunner({}): tenant={}, result={}",
                      threadName, tenant, tenantRemoved()
            );
        }

        boolean tenantRemoved() {
            return result;
        }


    }
}

