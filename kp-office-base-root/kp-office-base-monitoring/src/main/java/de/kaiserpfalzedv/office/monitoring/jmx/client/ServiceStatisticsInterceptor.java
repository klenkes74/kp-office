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

package de.kaiserpfalzedv.office.monitoring.jmx.client;

import java.time.Instant;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import de.kaiserpfalzedv.office.common.api.cdi.OfficeService;
import de.kaiserpfalzedv.office.monitoring.jmx.api.StatisticsCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-15
 */
@Interceptor
@OfficeService
public class ServiceStatisticsInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(ServiceStatisticsInterceptor.class);
    private static final Logger OPLOG = LoggerFactory.getLogger("operations");

    private static final String MEASUREMENT_REGION = "services";
    private static final long FAST_MS = 500L;
    private static final long MEDIUM_MS = 2000L;
    private static final long SLOW_MS = 5000L;

    private StatisticsCollector collector;

    @Inject
    public ServiceStatisticsInterceptor(
            StatisticsCollector collector
    ) {
        this.collector = collector;
    }

    @SuppressWarnings("UnusedReturnValue")
    @AroundInvoke
    public Object interceptServiceCall(final InvocationContext ctx) throws Exception {
        Instant started = Instant.now();
        String service = ctx.getMethod().getName();

        try {
            LOG.trace("Started service: {}", service);
            return ctx.proceed();
        } finally {
            Instant ended = Instant.now();

            long duration = ended.toEpochMilli() - started.toEpochMilli();
            LOG.trace("Service stopped: {} ({} ms)", service, duration);

            String measurementName;
            if (duration >= SLOW_MS) {
                OPLOG.error("{\"service\":\"{}\"; \"duration\":\"{}\";}", service, duration);
                measurementName = service + ".TO_SLOW";
            } else if (duration >= MEDIUM_MS) {
                OPLOG.warn("{\"service\":\"{}\"; \"duration\":\"{}\";}", service, duration);
                measurementName = service + ".SLOW";
            } else if (duration >= FAST_MS) {
                OPLOG.info("{\"service\":\"{}\"; \"duration\":\"{}\";}", service, duration);
                measurementName = service + ".MEDIUM";
            } else {
                OPLOG.info("{\"service\":\"{}\"; \"duration\":\"{}\";}", service, duration);
                measurementName = service + ".FAST";
            }

            collector.add(MEASUREMENT_REGION, measurementName);
        }
    }
}
