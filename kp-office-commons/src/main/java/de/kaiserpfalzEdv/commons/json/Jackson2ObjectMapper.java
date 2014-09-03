/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.commons.json;

import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import java.util.Collection;
import java.util.HashSet;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class Jackson2ObjectMapper extends ObjectMapper {
    private static final Logger LOG = LoggerFactory.getLogger(Jackson2ObjectMapper.class);

    /** Scan for Jackson Datatype Modules. (default=true) */
    private boolean scanForModules = true;

    /** Serializes dates as timestamp integer values. (default=false) */
    private boolean datesAsTimestampes = false;

    /** Modules to configure. */
    private final HashSet<Module> modules = new HashSet<Module>();


    public boolean isDatesAsTimestampes() {
        return datesAsTimestampes;
    }

    public void setDatesAsTimestampes(boolean datesAsTimestampes) {
        this.datesAsTimestampes = datesAsTimestampes;
    }


    public boolean isScanForModules() {
        return scanForModules;
    }

    public void setScanForModules(final boolean value) {
        scanForModules = value;
    }


    public void setModules(final Collection<? extends Module> modules) {
        this.modules.clear();

        if (modules != null) {
            this.modules.addAll(modules);
        }
    }

    @PostConstruct
    public void init() {
        if (scanForModules) {
            LOG.debug("Scanning for Jackson Datatype Modules ...");
            findAndRegisterModules();
        } else {
            LOG.debug("Scanning for Jackson Datatype Modules deactived.");
        }

        if (modules.size() > 0) {
            LOG.debug("Registering configured {} Jackson Datatype Modules ...", modules.size());
            for (Module module : modules) {
                LOG.trace("Registering Jackson Datatype Module: {}", module.getModuleName());

                registerModules(modules);
            }
        } else {
            LOG.debug("No Jackson Datatype Modules configured.");
        }

        configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, datesAsTimestampes);
    }
}
