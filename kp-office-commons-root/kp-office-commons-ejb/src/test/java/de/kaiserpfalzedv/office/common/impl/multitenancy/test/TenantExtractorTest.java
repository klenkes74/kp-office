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

package de.kaiserpfalzedv.office.common.impl.multitenancy.test;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.UUID;

import javax.interceptor.InvocationContext;

import de.kaiserpfalzedv.office.common.api.multitenancy.Tenant;
import de.kaiserpfalzedv.office.common.api.multitenancy.TenantExtractor;
import de.kaiserpfalzedv.office.common.api.multitenancy.TenantHoldingServiceRequest;
import de.kaiserpfalzedv.office.common.impl.multitenancy.TenantExtractorImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.easymock.EasyMock.expect;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.replay;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-13
 */
@RunWith(PowerMockRunner.class)
public class TenantExtractorTest {
    private static final Logger LOG = LoggerFactory.getLogger(TenantExtractorTest.class);

    private static final UUID DEFAULT_TENANT = UUID.randomUUID();

    /**
     * Class under test.
     */
    private TenantExtractor cut;

    private InvocationContext ctx;

    @BeforeClass
    static public void setMDC() {
        MDC.put("test-class", "tenant-extractor");
    }

    @AfterClass
    static public void removeMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void checkNoTenant() throws NoSuchMethodException {
        MDC.put("test", "no-tenant");
        LOG.debug("Checking w/o a tenant.");

        Object[] parameters = new Object[]{
                5L,
                UUID.randomUUID()
        };

        expect(ctx.getParameters()).andReturn(parameters);
        Method method = getClass().getMethod("noTenantParameter", Integer.class, UUID.class);
        expect(ctx.getMethod()).andReturn(method);

        replay(ctx);

        Optional<UUID> result = cut.extract(ctx);
        LOG.trace("Result: {}", result.orElse(null));

        assertFalse(result.isPresent());
    }

    @TenantHoldingServiceRequest
    public void noTenantParameter(Integer i, UUID a) {
        LOG.trace("Method called: noTenantParameter");
    }

    @Test
    public void checkValidTenant() throws NoSuchMethodException {
        MDC.put("test", "valid-tenant");
        LOG.debug("Checking w/ a valid tenant: {}", DEFAULT_TENANT);

        Object[] parameters = new Object[]{
                5L,
                DEFAULT_TENANT
        };

        expect(ctx.getParameters()).andReturn(parameters);
        Method method = getClass().getMethod("withTenantParameter", Integer.class, UUID.class);
        expect(ctx.getMethod()).andReturn(method);

        replay(ctx);

        Optional<UUID> result = cut.extract(ctx);
        LOG.trace("Result: {}", result.orElse(null));

        assertEquals(DEFAULT_TENANT, result.orElse(null));
    }

    @TenantHoldingServiceRequest
    public void withTenantParameter(Integer i, @Tenant UUID tenant) {
        LOG.trace("Method called: withTenantParameter");
    }

    @Test
    public void checkWrongTenantType() throws NoSuchMethodException {
        MDC.put("test", "wrong-tenant-type");
        LOG.debug("Checking w/ wrong tenant type: String");

        Object[] parameters = new Object[]{
                5L,
                DEFAULT_TENANT.toString()
        };

        expect(ctx.getParameters()).andReturn(parameters);
        Method method = getClass().getMethod("wrongTenantParameterType", Integer.class, String.class);
        expect(ctx.getMethod()).andReturn(method);

        replay(ctx);

        Optional<UUID> result = cut.extract(ctx);
        LOG.trace("Result: {}", result.orElse(null));

        assertFalse(result.isPresent());
    }

    @TenantHoldingServiceRequest
    public void wrongTenantParameterType(Integer i, @Tenant String tenant) {
        LOG.trace("Method called: wrongTenantParameterType");

    }

    @Before
    public void setUp() {
        ctx = createMock(InvocationContext.class);

        cut = new TenantExtractorImpl();
    }

    @After
    public void tearDown() {
        MDC.remove("test");
    }
}
