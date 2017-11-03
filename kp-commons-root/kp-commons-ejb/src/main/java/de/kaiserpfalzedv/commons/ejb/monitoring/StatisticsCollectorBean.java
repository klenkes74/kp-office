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

package de.kaiserpfalzedv.commons.ejb.monitoring;

import com.google.common.util.concurrent.AtomicLongMap;
import de.kaiserpfalzedv.commons.api.monitoring.StatisticsCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.management.*;
import java.util.HashMap;
import java.util.Set;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-15
 */
@Singleton
@Startup
public class StatisticsCollectorBean implements StatisticsCollector {
    private static final Logger LOG = LoggerFactory.getLogger(StatisticsCollectorBean.class);

    private final HashMap<String, AtomicLongMap<String>> statistics = new HashMap<>();

    @Inject
    private MBeanServer server;
    private ObjectName name;

    @SuppressWarnings("unused")
    @Inject
    public StatisticsCollectorBean(
            final MBeanServer server
    ) throws MalformedObjectNameException {
        this();

        this.server = server;
    }

    @SuppressWarnings({"unused", "WeakerAccess"})
    public StatisticsCollectorBean() throws MalformedObjectNameException {
        name = new ObjectName("de.kaiserpfalzedv.office.monitoring:type=" + getClass().getName());
    }

    @PostConstruct
    public void registerJMX() {
        try {
            // Needs to register itself to the MBeanServer. So this can't be avoided.
            //noinspection EjbThisExpressionInspection
            server.registerMBean(this, name);
        } catch (NotCompliantMBeanException | MBeanRegistrationException | InstanceAlreadyExistsException e) {
            LOG.error(e.getClass().getSimpleName() + " caught during JMX bean registration: " + e.getMessage(), e);
        }

        LOG.info("Started JMX Statistics Collector");
    }

    @PreDestroy
    public void unregisterJMX() {
        try {
            server.unregisterMBean(name);
        } catch (InstanceNotFoundException | MBeanRegistrationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught during JMX bean de-registration: " + e.getMessage(), e);
        }

        LOG.info("Stopped JMX Statistics Collector");
    }


    @Override
    public Long put(String meassurement, Long value) {
        return put(DEFAULT_REGION, meassurement, value);
    }

    @Override
    public Long add(String meassurement) {
        return add(DEFAULT_REGION, meassurement);
    }

    @Override
    public Long decrease(String meassurement) {
        return decrease(DEFAULT_REGION, meassurement);
    }

    @Override
    public Long get(String meassurement) {
        return get(DEFAULT_REGION, meassurement);
    }

    @Override
    public Set<String> listMeassurements() {
        return listMeassurements(DEFAULT_REGION);
    }

    @Override
    public Long put(final String region, final String meassurement, final Long value) {
        LOG.trace("Setting statistic: region={}, measurement={}, value={}", region, meassurement, value);

        createRegionIfNeeded(region);

        return statistics.get(region).put(meassurement, value);
    }

    @Override
    public Long add(final String region, final String meassurement) {
        LOG.trace("Increasing by one for statistic: region={}, measurement={}", region, meassurement);

        createRegionIfNeeded(region);
        return statistics.get(region).incrementAndGet(meassurement);
    }

    @Override
    public Long decrease(final String region, final String meassurement) {
        LOG.trace("Decreasing by one for statistic: region={}, measurement={}", region, meassurement);

        createRegionIfNeeded(region);
        return statistics.get(region).decrementAndGet(meassurement);
    }

    @Override
    public Long get(final String region, final String meassurement) {
        return statistics.getOrDefault(region, AtomicLongMap.create()).get(meassurement);
    }

    @Override
    public Set<String> listRegions() {
        return statistics.keySet();
    }

    @Override
    public Set<String> listMeassurements(final String region) {
        return statistics.getOrDefault(region, AtomicLongMap.create()).asMap().keySet();
    }

    private void createRegionIfNeeded(final String region) {
        if (!statistics.containsKey(region)) {
            synchronized (statistics) {
                if (!statistics.containsKey(region)) {
                    statistics.put(region, AtomicLongMap.create());
                }
            }

            LOG.debug("Created statistics region: {}", region);
        }
    }
}
