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

package de.kaiserpfalzedv.office.monitoring.jmx.impl.test;

import java.lang.management.ManagementFactory;

import javax.management.MBeanServer;

import de.kaiserpfalzedv.office.monitoring.jmx.impl.MBeanPlatformserverProvider;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.junit.Assert.assertEquals;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-15
 */
public class MBeanPlatformserverProviderTest {
    private static final Logger LOG = LoggerFactory.getLogger(MBeanPlatformserverProviderTest.class);

    @BeforeClass
    public static void addMDC() {
        MDC.put("test-class", "mbean-server-provider");
    }

    @AfterClass
    public static void removeMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void checkMBeanServerProvider() {
        MDC.put("test", "mbean-server-provider");
        LOG.debug("Checking the MBeanServer provider ...");

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        MBeanPlatformserverProvider cut = new MBeanPlatformserverProvider();

        MBeanServer result = cut.provideMBeanServer();
        LOG.trace("Result: {}", result);

        assertEquals(server, result);
    }
}
