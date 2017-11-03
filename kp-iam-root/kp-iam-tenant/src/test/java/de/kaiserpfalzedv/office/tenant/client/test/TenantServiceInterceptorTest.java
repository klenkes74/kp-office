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

package de.kaiserpfalzedv.office.tenant.client.test;

import de.kaiserpfalzedv.commons.api.monitoring.StatisticsCollector;
import de.kaiserpfalzedv.commons.api.multitenancy.TenantExtractor;
import de.kaiserpfalzedv.office.tenant.client.TenantServiceInterceptor;
import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.interceptor.InvocationContext;
import java.util.Optional;
import java.util.UUID;

import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.powermock.api.easymock.PowerMock.verify;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-15
 */
@RunWith(PowerMockRunner.class)
public class TenantServiceInterceptorTest {
    private static final Logger LOG = LoggerFactory.getLogger(TenantServiceInterceptorTest.class);

    private static final UUID DEFAULT_TENANT = UUID.randomUUID();

    private TenantServiceInterceptor cut;

    @Mock
    private StatisticsCollector collector;

    @Mock
    private TenantExtractor extractor;

    @Mock
    private InvocationContext ctx;

    @BeforeClass
    public static void setMDC() {
        MDC.put("test-class", "tenant-service-interceptor");
    }

    @AfterClass
    public static void removeMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void checkInvokeWithTenant() throws Exception {
        MDC.put("test", "invoke-w/-tenant");
        LOG.debug("Checking interceptor with tenant holding call: {}", DEFAULT_TENANT);

        expect(extractor.extract(ctx)).andReturn(Optional.of(DEFAULT_TENANT));
        expect(collector.add("tenant-calls", DEFAULT_TENANT.toString())).andReturn(1L);
        expect(ctx.proceed()).andReturn(null);
        replay(extractor, collector);

        cut.invoke(ctx);
    }

    @Test
    public void checkInvokeWithoutTenant() throws Exception {
        MDC.put("test", "invoke-w/o-tenant");
        LOG.debug("Checking interceptor with no tenant.");

        expect(extractor.extract(ctx)).andReturn(Optional.empty());
        expect(ctx.proceed()).andReturn(null);
        replay(extractor, collector);

        cut.invoke(ctx);
    }

    @Before
    public void setUp() {
        cut = new TenantServiceInterceptor(collector, extractor);
    }

    @After
    public void tearDown() {
        verify(collector, extractor);

        MDC.remove("test");
    }
}
