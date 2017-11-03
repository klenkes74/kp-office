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

package de.kaiserpfalzedv.commons.ejb.monitoring.test;

import de.kaiserpfalzedv.commons.api.monitoring.StatisticsCollector;
import de.kaiserpfalzedv.commons.ejb.monitoring.StatisticsCollectorBean;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.internal.util.collections.Sets;
import org.powermock.api.easymock.annotation.Mock;
import org.powermock.modules.junit4.PowerMockRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.management.*;
import java.util.Set;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-15
 */
@RunWith(PowerMockRunner.class)
public class StatisticsCollectorTest {
    private static final Logger LOG = LoggerFactory.getLogger(StatisticsCollectorTest.class);

    private static final String DEFAULT_MEASSUREMENT = "measurement";

    private StatisticsCollector cut;

    @Mock
    private MBeanServer mBeanServer;
    private boolean unregisterBean;

    @BeforeClass
    public static void setMDC() {
        MDC.put("test-class", "statistics-collector");
    }

    @AfterClass
    public static void removeMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void checkPutDefault() {
        logTestMethod("default-put", "Checking a default put ...");

        cut.put(DEFAULT_MEASSUREMENT, 5L);

        Long result = cut.get(DEFAULT_MEASSUREMENT);
        LOG.trace("Result: {}", result);

        assertEquals(5L, result.longValue());
    }

    private void logTestMethod(final String key, final String message) {
        logTestMethod(key, message, null);
    }

    private void logTestMethod(final String key, final String message, final Object... objects) {
        MDC.put("test", key);
        LOG.debug(message, objects);
    }

    @Test
    public void checkAddDefault() {
        logTestMethod("default-add", "Checking a default add ...");

        cut.add(DEFAULT_MEASSUREMENT);

        Long result = cut.get(DEFAULT_MEASSUREMENT);
        LOG.trace("Result: {}", result);

        assertEquals(1L, result.longValue());
    }

    @Test
    public void checkAddFromBaselineDefault() {
        logTestMethod("default-add-baseline", "Checking a baseline add ...");
        cut.put(DEFAULT_MEASSUREMENT, 5L);

        cut.add(DEFAULT_MEASSUREMENT);

        Long result = cut.get(DEFAULT_MEASSUREMENT);
        LOG.trace("Result: {}", result);

        assertEquals(6L, result.longValue());
    }

    @Test
    public void checkDecreaseDefault() {
        logTestMethod("default-decrease", "Checking a default decrease ...");

        cut.decrease(DEFAULT_MEASSUREMENT);

        Long result = cut.get(DEFAULT_MEASSUREMENT);
        LOG.trace("Result: {}", result);

        assertEquals(-1L, result.longValue());
    }

    @Test
    public void checkDecreaseFromBaselineDefault() {
        logTestMethod("default-decrease-baseline", "Checking a baseline add ...");
        cut.put(DEFAULT_MEASSUREMENT, 5L);

        cut.decrease(DEFAULT_MEASSUREMENT);

        Long result = cut.get(DEFAULT_MEASSUREMENT);
        LOG.trace("Result: {}", result);

        assertEquals(4L, result.longValue());
    }

    @Test
    public void checkMultipleReasonsAreIndependent() {
        logTestMethod("multiple-regions", "Checking if multiple regions are independent ...");

        cut.put("first", DEFAULT_MEASSUREMENT, 5L);
        cut.decrease("first", DEFAULT_MEASSUREMENT);
        cut.put("second", DEFAULT_MEASSUREMENT, 5L);
        cut.add("second", DEFAULT_MEASSUREMENT);

        Long first = cut.get("first", DEFAULT_MEASSUREMENT);
        Long second = cut.get("second", DEFAULT_MEASSUREMENT);

        assertEquals(4L, first.longValue());
        assertEquals(6L, second.longValue());
    }

    @Test
    public void checkListOfRegions() {
        Set<String> regions = Sets.newSet("first", "second");
        logTestMethod("list-of-regions", "Checking if all regions exist: {}", regions);

        regions.forEach(r -> cut.add(r, DEFAULT_MEASSUREMENT));

        Set<String> result = cut.listRegions();
        LOG.trace("Result: {}", result);

        assertEquals(regions, result);
    }

    @Test
    public void checkListOfMeasurements() {
        Set<String> measurements = Sets.newSet("first", "second");
        logTestMethod("list-of-measurements", "Checking if all regions exist: {}", measurements);


        measurements.forEach(m -> cut.add(m));

        Set<String> result = cut.listMeassurements();
        LOG.trace("Result: {}", result);

        assertEquals(measurements, result);
    }

    @Test
    public void checkErrorHandlingOfJmxRegistration() throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InstanceNotFoundException {
        logTestMethod("jmx-registration-failed", "Checking handling of JMX registration failures ...");

        unregisterBean = false; // we don't need the default unregistration check ...

        MBeanServer mBeanServer = mock(MBeanServer.class);
        cut = new StatisticsCollectorBean(mBeanServer);

        ObjectName name = new ObjectName("de.kaiserpfalzedv.office.monitoring:type="
                                                 + StatisticsCollectorBean.class.getName());
        expect(mBeanServer.registerMBean(cut, name)).andThrow(new NotCompliantMBeanException());

        replay(mBeanServer);


        ((StatisticsCollectorBean) cut).registerJMX();

        // The exception is only logged and then ignored. That's like it should be.
    }

    @Test
    public void checkErrorHandlingOfJmxDeregistration() throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InstanceNotFoundException {
        logTestMethod("jmx-registration-failed", "Checking handling of JMX registration failures ...");

        unregisterBean = false; // we don't need the default unregistration check ...

        MBeanServer mBeanServer = mock(MBeanServer.class);
        cut = new StatisticsCollectorBean(mBeanServer);

        ObjectName name = new ObjectName("de.kaiserpfalzedv.office.monitoring:type="
                                                 + StatisticsCollectorBean.class.getName());

        mBeanServer.unregisterMBean(name);
        expectLastCall().andThrow(new InstanceNotFoundException());

        replay(mBeanServer);


        ((StatisticsCollectorBean) cut).unregisterJMX();

        // The exception is only logged and then ignored. That's like it should be.
    }

    @Before
    public void setUp() throws MalformedObjectNameException, NotCompliantMBeanException, InstanceAlreadyExistsException, MBeanRegistrationException, InstanceNotFoundException {
        cut = new StatisticsCollectorBean(mBeanServer);


        ObjectName name = new ObjectName("de.kaiserpfalzedv.office.monitoring:type="
                                                 + StatisticsCollectorBean.class.getName());
        expect(mBeanServer.registerMBean(cut, name)).andReturn(null);

        mBeanServer.unregisterMBean(name);
        expectLastCall();

        replay(mBeanServer);

        ((StatisticsCollectorBean) cut).registerJMX();
        unregisterBean = true;
    }

    @After
    public void tearDown() {
        if (unregisterBean) {
            ((StatisticsCollectorBean) cut).unregisterJMX();

            verify(mBeanServer);
        }

        MDC.remove("test");
    }
}
