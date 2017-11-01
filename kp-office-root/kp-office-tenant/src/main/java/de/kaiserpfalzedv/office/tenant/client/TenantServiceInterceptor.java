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

package de.kaiserpfalzedv.office.tenant.client;

import java.util.Optional;
import java.util.UUID;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import de.kaiserpfalzedv.office.common.api.multitenancy.TenantExtractor;
import de.kaiserpfalzedv.office.common.api.multitenancy.TenantHoldingServiceRequest;
import de.kaiserpfalzedv.office.monitoring.jmx.api.StatisticsCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-15
 */
@Interceptor
@TenantHoldingServiceRequest
public class TenantServiceInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(TenantServiceInterceptor.class);

    private static final String TENANT_REGION = "tenant-calls";

    private StatisticsCollector collector;
    private TenantExtractor extractor;

    @Inject
    public TenantServiceInterceptor(
            final StatisticsCollector collector,
            final TenantExtractor extractor
    ) {
        this.collector = collector;
        this.extractor = extractor;
    }


    @AroundInvoke
    public Object invoke(InvocationContext ctx) throws Exception {
        Optional<UUID> data = extractor.extract(ctx);

        if (data.isPresent()) {
            UUID tenant = data.get();
            Long callNumber = collector.add(TENANT_REGION, tenant.toString());

            LOG.trace("Call counted: tenant={}, call={}", tenant, callNumber);
        }

        return ctx.proceed();
    }
}
