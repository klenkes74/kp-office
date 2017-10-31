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

import java.util.Optional;
import java.util.UUID;

import javax.interceptor.InvocationContext;

import de.kaiserpfalzedv.office.common.api.multitenancy.TenantExtractor;
import de.kaiserpfalzedv.office.common.api.multitenancy.TenantHolder;
import de.kaiserpfalzedv.office.common.ejb.multitenancy.TenantHoldingServiceRequestInterceptor;
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
import static org.powermock.api.easymock.PowerMock.createMock;
import static org.powermock.api.easymock.PowerMock.expectLastCall;
import static org.powermock.api.easymock.PowerMock.replay;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-13
 */
@RunWith(PowerMockRunner.class)
public class TenantHoldingServiceRequestInterceptorTest {
    private static final Logger LOG = LoggerFactory.getLogger(TenantHoldingServiceRequestInterceptorTest.class);

    private static final UUID DEFAULT_TENANT = UUID.randomUUID();

    /**
     * Class under test.
     */
    private TenantHoldingServiceRequestInterceptor cut;

    /**
     * The mocked holder.
     */
    private TenantHolder holder;
    /**
     * The mocked extractor.
     */
    private TenantExtractor extractor;
    /**
     * The mocked context.
     */
    private InvocationContext ctx;

    @BeforeClass
    static public void setMDC() {
        MDC.put("test-class", "tenant-interceptor");
    }

    @AfterClass
    static public void removeMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void checkInvocationWithTenantWithoutOldValue() throws Exception {
        MDC.put("test", "w/-tenant-w/o-oldvalue");
        LOG.debug("Checking a call with tenant: {}", DEFAULT_TENANT);

        expect(extractor.extract(ctx)).andReturn(Optional.of(DEFAULT_TENANT));

        expect(holder.getTenant()).andReturn(Optional.empty());

        holder.setTenant(DEFAULT_TENANT);
        expectLastCall();

        expect(holder.getTenant()).andReturn(Optional.of(DEFAULT_TENANT));

        holder.clearTenant();
        expectLastCall().times(2);

        expect(holder.getTenant()).andReturn(Optional.empty());

        expect(ctx.proceed()).andReturn("ok");

        replay(holder, extractor, ctx);

        cut.tenantInterceptor(ctx);
    }

    @Test
    public void checkInvocationWithTenantWithOldValue() throws Exception {
        MDC.put("test", "w/-tenant-w/-oldvalue");
        UUID oldValue = UUID.randomUUID();
        LOG.debug("Checking a call with: tenant={}, oldValue={}", DEFAULT_TENANT, oldValue);

        expect(extractor.extract(ctx)).andReturn(Optional.of(DEFAULT_TENANT));

        expect(holder.getTenant()).andReturn(Optional.of(oldValue));

        holder.setTenant(DEFAULT_TENANT);
        expectLastCall();

        expect(holder.getTenant()).andReturn(Optional.of(DEFAULT_TENANT));

        holder.clearTenant();
        expectLastCall().times(2);

        holder.setTenant(oldValue);
        expectLastCall();

        expect(holder.getTenant()).andReturn(Optional.of(oldValue));

        expect(ctx.proceed()).andReturn("ok");

        replay(holder, extractor, ctx);

        cut.tenantInterceptor(ctx);
    }

    @Test
    public void checkInvocationWithoutTenantWithoutOldValue() throws Exception {
        MDC.put("test", "w/o-tenant-w/o-oldvalue");
        LOG.debug("Checking a call without data");

        expect(extractor.extract(ctx)).andReturn(Optional.empty());

        expect(holder.getTenant()).andReturn(Optional.empty());

        expect(holder.getTenant()).andReturn(Optional.empty());

        holder.clearTenant();
        expectLastCall().times(2);

        expect(holder.getTenant()).andReturn(Optional.empty());

        expect(ctx.proceed()).andReturn("ok");

        replay(holder, extractor, ctx);

        cut.tenantInterceptor(ctx);
    }

    @Test
    public void checkInvocationWithoutTenantWithOldValue() throws Exception {
        MDC.put("test", "w/o-tenant-w/-oldvalue");
        LOG.debug("Checking a call with oldValue: {}", DEFAULT_TENANT);

        expect(extractor.extract(ctx)).andReturn(Optional.empty());

        expect(holder.getTenant()).andReturn(Optional.of(DEFAULT_TENANT));

        expect(holder.getTenant()).andReturn(Optional.empty());

        holder.clearTenant();
        expectLastCall().times(2);

        holder.setTenant(DEFAULT_TENANT);
        expectLastCall();

        expect(holder.getTenant()).andReturn(Optional.of(DEFAULT_TENANT));

        expect(ctx.proceed()).andReturn("ok");

        replay(holder, extractor, ctx);

        cut.tenantInterceptor(ctx);
    }

    @Before
    public void setUp() {
        holder = createMock(TenantHolder.class);
        extractor = createMock(TenantExtractor.class);
        ctx = createMock(InvocationContext.class);

        cut = new TenantHoldingServiceRequestInterceptor(holder, extractor);
    }

    @After
    public void tearDown() {
        MDC.remove("test");
    }
}
