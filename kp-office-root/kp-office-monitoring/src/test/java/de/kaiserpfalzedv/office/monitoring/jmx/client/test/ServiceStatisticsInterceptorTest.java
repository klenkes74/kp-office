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

package de.kaiserpfalzedv.office.monitoring.jmx.client.test;

import de.kaiserpfalzedv.commons.api.cdi.OfficeService;
import de.kaiserpfalzedv.office.monitoring.jmx.client.ServiceStatisticsInterceptor;
import de.kaiserpfalzedv.office.monitoring.jmx.impl.StatisticsCollectorBean;
import org.junit.*;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.interceptor.InvocationContext;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import java.lang.reflect.Method;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-15
 */
@RunWith(PowerMockRunner.class)
public class ServiceStatisticsInterceptorTest {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceStatisticsInterceptorTest.class);

    private ServiceStatisticsInterceptor cut;

    private MBeanServer mBeanServer;
    private StatisticsCollectorBean collector;

    @Mock
    private InvocationContext ctx;

    @BeforeClass
    public static void setMDC() {
        MDC.put("test-class", "service-statistics-interceptor");
    }

    @AfterClass
    public static void removeMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void checkAll() throws Exception {
        MDC.put("test", "check-all-times");
        LOG.debug("Checking all times");

        Method method = getClass().getMethod("timing", Long.class);
        expect(ctx.getMethod()).andReturn(method).times(4);
        expect(ctx.proceed()).andAnswer(() -> timing(0L));
        expect(ctx.proceed()).andAnswer(() -> timing(750L));
        expect(ctx.proceed()).andAnswer(() -> timing(2250L));
        expect(ctx.proceed()).andAnswer(() -> timing(5250L));

        replay(ctx);

        cut.interceptServiceCall(ctx);
        cut.interceptServiceCall(ctx);
        cut.interceptServiceCall(ctx);
        cut.interceptServiceCall(ctx);

        Set<String> measurements = collector.listMeassurements("services");
        LOG.debug("existing measurements: {}", measurements);

        for (String m : measurements) {
            long result = collector.get("services", m);
            LOG.trace("Result: (services, {})={}", m, result);
            assertEquals(1L, result);
        }
    }

    @OfficeService
    public Long timing(Long waitTime) throws InterruptedException {
        LOG.trace("Called timing method: {} ms", waitTime);

        Thread.sleep(waitTime);

        return waitTime;
    }

    @Test
    public void checkDifferentMethods() throws Exception {
        MDC.put("test", "check-all-times");
        LOG.debug("Checking all times");

        Method first = getClass().getMethod("timing", Long.class);
        Method second = getClass().getMethod("second");
        expect(ctx.getMethod()).andReturn(first);
        expect(ctx.proceed()).andAnswer(() -> timing(0L));
        expect(ctx.getMethod()).andReturn(second);
        expect(ctx.proceed()).andReturn(null);

        replay(ctx);

        cut.interceptServiceCall(ctx);
        cut.interceptServiceCall(ctx);

        Set<String> measurements = collector.listMeassurements("services");
        LOG.debug("existing measurements: {}", measurements);

        for (String m : measurements) {
            long result = collector.get("services", m);
            LOG.trace("Result: (services, {})={}", m, result);
            assertEquals(1L, result);
        }
    }

    @OfficeService
    public void second() {
        LOG.trace("Method called: second");
    }

    @Before
    public void setUp() throws MalformedObjectNameException {
        collector = new StatisticsCollectorBean(mBeanServer);
        cut = new ServiceStatisticsInterceptor(collector);
    }

    @After
    public void tearDown() {
        verify(ctx);

        MDC.remove("test");
    }
}
