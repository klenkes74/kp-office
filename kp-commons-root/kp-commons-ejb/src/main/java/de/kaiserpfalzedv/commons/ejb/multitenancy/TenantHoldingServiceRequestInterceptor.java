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

package de.kaiserpfalzedv.commons.ejb.multitenancy;

import de.kaiserpfalzedv.commons.api.multitenancy.TenantExtractor;
import de.kaiserpfalzedv.commons.api.multitenancy.TenantHolder;
import de.kaiserpfalzedv.commons.api.multitenancy.TenantHoldingServiceRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;
import java.util.Optional;
import java.util.UUID;

/**
 * This interceptor extracts the tenant UUID from a request and stores it for later retrieval to the
 * {@link TenantHolder}.
 * <p>
 * The intercepted method or its declaring class must be annotated with {@link TenantHoldingServiceRequest} otherwise
 * the interceptor will just return.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-13
 */
@Interceptor
@TenantHoldingServiceRequest
public class TenantHoldingServiceRequestInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(TenantHoldingServiceRequestInterceptor.class);

    private TenantHolder holder;
    private TenantExtractor extractor;


    @Inject
    public TenantHoldingServiceRequestInterceptor(TenantHolder holder, final TenantExtractor extractor) {
        this.holder = holder;
        this.extractor = extractor;
    }


    @AroundInvoke
    public Object tenantInterceptor(final InvocationContext ctx) throws Exception {
        final Optional<UUID> oldValue = holder.getTenant();
        Optional<UUID> tenant = extractor.extract(ctx);

        try {
            holder.clearTenant();
            tenant.ifPresent(uuid -> holder.setTenant(uuid));
            LOG.debug("Tenant is set to: {}", holder.getTenant().orElse(null));

            return ctx.proceed();
        } finally {
            holder.clearTenant();
            oldValue.ifPresent(uuid -> holder.setTenant(uuid));

            LOG.debug("Tenant is set to: {}", holder.getTenant().orElse(null));
        }
    }
}
