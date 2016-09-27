/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.tenant.shared.converter;

import java.util.Map;
import java.util.UUID;

import de.kaiserpfalzedv.office.commons.shared.converter.impl.AbstractConverterImpl;
import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.commands.TenantCommandBuilder;
import de.kaiserpfalzedv.office.tenant.commands.TenantCreateCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public class TenantCreateCommandConverter extends AbstractConverterImpl<TenantCreateCommand> {
    private static final Logger LOG = LoggerFactory.getLogger(TenantCreateCommandConverter.class);

    @Override
    public TenantCreateCommand createConversionResult(Map<String, Object> params) {
        return (TenantCreateCommand) new TenantCommandBuilder<TenantCreateCommand>()
                .withTenant((Tenant) params.get("tenant"))
                .withId(UUID.fromString((String)params.get("id")))
                .withSource(UUID.fromString((String)params.get("source")))
                .build();
    }
}
