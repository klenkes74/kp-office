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

package de.kaiserpfalzEdv.office.accounting.chartsofaccounts.impl;

import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.AccountMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 22:12
 */
public class AccountMappingLocator {
    private static final Logger LOG = LoggerFactory.getLogger(AccountMappingLocator.class);

    private final HashMap<String, AccountMapping> mappingsByName = new HashMap<>();
    private final HashMap<UUID, AccountMapping>   mappingsByUUID = new HashMap<>();


    public AccountMappingLocator() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
        LOG.trace("  available chart of accounts: {}", mappingsByName.keySet());
    }

    @PreDestroy
    public void close() {
        mappingsByName.clear();

        LOG.trace("Destroyed: {}", this);
    }

    public void addMapping(final AccountMapping mapping) {
        LOG.debug("Adding mapping '{}' ({}) ...", mapping.getDisplayName(), mapping.getId());

        this.mappingsByName.put(mapping.getDisplayName(), mapping);
        this.mappingsByUUID.put(mapping.getId(), mapping);
    }

    public Map<String, AccountMapping> getMappings() {
        return Collections.unmodifiableMap(mappingsByName);
    }

    public void setMappings(final List<AccountMapping> mappings) {
        mappings.forEach(m -> addMapping(m));
    }

    public AccountMapping getMapping(@NotNull final String mappingName) {
        if (!mappingsByName.containsKey(mappingName)) {
            throw new IllegalArgumentException("No mapping with name '" + mappingName + "' defined.");
        }

        return mappingsByName.get(mappingName);
    }

    public AccountMapping getMapping(@NotNull final UUID mappingId) {
        if (!mappingsByUUID.containsKey(mappingId)) {
            throw new IllegalArgumentException("No mapping with id '" + mappingId + "' definied.");
        }

        return mappingsByUUID.get(mappingId);
    }
}
