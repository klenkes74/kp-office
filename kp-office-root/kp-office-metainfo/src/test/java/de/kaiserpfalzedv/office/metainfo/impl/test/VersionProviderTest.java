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

package de.kaiserpfalzedv.office.metainfo.impl.test;

import java.util.Optional;

import com.github.zafarkhaja.semver.Version;
import de.kaiserpfalzedv.office.metainfo.api.ManifestReader;
import de.kaiserpfalzedv.office.metainfo.impl.VersionProvider;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.junit.Assert.assertEquals;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-31
 */
@RunWith(MockitoJUnitRunner.class)
public class VersionProviderTest {
    private static final Logger LOG = LoggerFactory.getLogger(VersionProviderTest.class);

    @Mock
    private ManifestReader manifestReader;

    private VersionProvider sut;

    @BeforeClass
    public static void setUpMDC() {
        MDC.put("test-class", "version-provider");
    }

    @AfterClass
    public static void tearDownMDC() {
        MDC.remove("test-class");
        MDC.remove("test");
    }

    @Test
    public void shouldReturnVersion1_0_0_SNAPSHOTWhenCalledWithoutFurtherPreparation() {
        logMethod("version-working", "Check if the version is working.");

        Version result = sut.getSystemVersion();
        LOG.trace("Version is: {}", result);

        assertEquals(Version.valueOf("1.0.0-SNAPSHOT"), result);
    }

    private void logMethod(final String shortName, final String message, final Object... data) {
        MDC.put("test", shortName);

        LOG.info(message, data);
    }

    @Before
    public void setUp() {
        Mockito.when(manifestReader.read("KPO-Version")).thenReturn(Optional.of("1.0.0-SNAPSHOT"));

        sut = new VersionProvider(manifestReader);
        sut.init();
    }

    @After
    public void tearDown() {
        sut = null;
        MDC.remove("test");
    }
}
