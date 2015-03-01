/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.core.licence.test;

import de.kaiserpfalzEdv.commons.test.SpringTestNGBase;
import de.kaiserpfalzEdv.office.core.licence.LicensingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 11.02.15 21:37
 */
@Named
@Test
@ContextConfiguration("/licensingInterceptorTest-beans.xml")
public class LicenceManagerTest extends SpringTestNGBase {
    private static final Logger LOG = LoggerFactory.getLogger(LicenceManagerTest.class);

    @Inject
    @Named("validModuleImpl")
    private TestModuleService validService;

    @Inject
    @Named("invalidModuleImpl")
    private TestModuleService invalidService;

    public LicenceManagerTest() {
        super(LicenceManagerTest.class, LOG);
    }

    @PostConstruct
    protected void init() {
        LOG.info("Injected valid service: {}", validService);
        LOG.info("Injected invalid service: {}", invalidService);
    }


    public void testInterceptorWithValidLicense() throws LicensingException {
        logMethod("valid-licence", "Check with valid licence.");

        validService.serviceCall();
    }

    @Test(
            expectedExceptions = LicensingException.class,
            expectedExceptionsMessageRegExp = "Feature 'not-existing-feature' not licensed in licence: 87e7cc68-f844-401a-ac80-2f9d34ed69b9"
    )
    public void testInterceptorWithInvalidLicense() throws LicensingException {
        logMethod("invalid-licence", "Check with invalid licence.");

        invalidService.serviceCall();
    }
}
