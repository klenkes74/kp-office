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

package de.kaiserpfalzedv.office.license.impl.test;

import java.util.UUID;

import javax.ejb.Stateless;

import de.kaiserpfalzedv.office.common.api.cdi.OfficeService;
import de.kaiserpfalzedv.office.common.api.multitenancy.Tenant;
import de.kaiserpfalzedv.office.common.api.multitenancy.TenantHoldingServiceRequest;
import de.kaiserpfalzedv.office.license.api.Licensed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-18
 */
@Stateless
@Licensed("interceptor")
@OfficeService
public class InterceptorTestBean {
    private static final Logger LOG = LoggerFactory.getLogger(InterceptorTestBean.class);

    public void first() {
        LOG.info("first() called.");

        System.out.println("first() called.");
    }

    @TenantHoldingServiceRequest
    public void second(@Tenant final UUID tenant) {
        LOG.info("second() called with tenant: {}", tenant);

        System.out.println("second() called with tenant: " + tenant);
    }

    public void third(final String message) {
        LOG.info("Called with message: {}", message);

        System.out.println("third() called: " + message);
    }
}
