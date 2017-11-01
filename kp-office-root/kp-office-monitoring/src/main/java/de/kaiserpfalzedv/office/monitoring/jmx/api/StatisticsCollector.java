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

package de.kaiserpfalzedv.office.monitoring.jmx.api;

import java.util.Set;

/**
 * The statistics collector is a centralized collector for all statical request data. It contains of meassurements of
 * type {@literal Long} that are grouped in <em>regions</em>. For small systems the
 * {@link #DEFAULT_REGION} (with value {@value #DEFAULT_REGION}) can be used.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-15
 */
public interface StatisticsCollector {
    /**
     * The default region for the generic methods {@link #put(String, Long)}, {@link #add(String)},
     * {@link #decrease(String)} and {@link #get(String)}.
     */
    String DEFAULT_REGION = "main";

    /**
     * Sets the value of the meassurement to the given value. It operates on the {@link #DEFAULT_REGION}.
     *
     * @param meassurement the meassurement point to be changed.
     * @param value        the new value of the meassurement point.
     *
     * @return the new value of the meassurement point.
     */
    Long put(String meassurement, Long value);

    /**
     * Increases the current value of the meassurement point. It operates on the {@link #DEFAULT_REGION}.
     *
     * @param meassurement the meassurement point to be changed.
     *
     * @return the new value of the meassurement point.
     */
    Long add(String meassurement);

    /**
     * Decreases the current value of the meassurement point. It operates on the {@link #DEFAULT_REGION}.
     *
     * @param meassurement the meassurement point to be changed.
     *
     * @return the new value of the meassurement point.
     */
    Long decrease(String meassurement);

    /**
     * Retrieves the current value of the meassurement point. It operates on the {@link #DEFAULT_REGION}.
     *
     * @param meassurement the messurement point to be retrieved.
     *
     * @return the current value of the meassurement point.
     */
    Long get(String meassurement);

    /**
     * Lists all meassurements in the region. It operates on the {@link #DEFAULT_REGION}.
     *
     * @return the name of the meassurements.
     */
    Set<String> listMeassurements();


    /**
     * Sets the value of the meassurement to the given value.
     *
     * @param region       the region of the meassurements.
     * @param meassurement the meassurement point to be changed.
     * @param value        the new value of the meassurement point.
     *
     * @return the new value of the meassurement point.
     */
    Long put(String region, String meassurement, Long value);

    /**
     * Increases the current value of the meassurement point.
     *
     * @param region       the region of the meassurements.
     * @param meassurement the meassurement point to be changed.
     *
     * @return the new value of the meassurement point.
     */
    Long add(String region, String meassurement);

    /**
     * Decreases the current value of the meassurement point.
     *
     * @param region       the region of the meassurements.
     * @param meassurement the meassurement point to be changed.
     *
     * @return the new value of the meassurement point.
     */
    Long decrease(String region, String meassurement);

    /**
     * Retrieves the current value of the meassurement point.
     *
     * @param region       the region of the meassurements.
     * @param meassurement the messurement point to be retrieved.
     *
     * @return the current value of the meassurement point.
     */
    Long get(String region, String meassurement);

    /**
     * Lists all regions available in the statistics collector.
     *
     * @return the name of the regions.
     */
    Set<String> listRegions();

    /**
     * Lists all meassurements in the region.
     *
     * @param region the region to list all meassurements.
     *
     * @return the name of the meassurements.
     */
    Set<String> listMeassurements(String region);
}
