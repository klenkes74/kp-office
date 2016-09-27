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

import java.util.UUID;

import de.kaiserpfalzedv.office.commons.shared.converter.Converter;
import de.kaiserpfalzedv.office.commons.shared.converter.NoMatchingConverterFoundException;
import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.commands.TenantCommandBuilder;
import de.kaiserpfalzedv.office.tenant.commands.TenantCreateCommand;
import de.kaiserpfalzedv.office.tenant.shared.TenantConverterRegistry;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.Assert.assertEquals;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public class TenantCreateCommandConverterTest {
    private static final Logger LOG = LoggerFactory.getLogger(TenantCreateCommandConverterTest.class);

    private static final UUID SOURCE_ID = UUID.randomUUID();

    private TenantConverterRegistry service;


    @Test
    public void checkConvertTenantCreateCommandToJson() throws NoMatchingConverterFoundException {
        Tenant data = createTenant();
        TenantCreateCommand command = (TenantCreateCommand) new TenantCommandBuilder<TenantCreateCommand>()
                .withTenant(data)
                .withSource(SOURCE_ID)
                .create()
                .build();

        Converter<TenantCreateCommand> converter = service.borrowConverter(command.getActionType());
        String result = converter.marshal(command);
        service.returnConverter(command.getActionType(), converter);
        LOG.debug("Result: {}", result);

        assertEquals("{\"source\":\"" + SOURCE_ID.toString()
                             + "\",\"commandId\":\"" + command.getCommand().toString()
                             +"\",\"crudType\":\"CREATE\",\"tenant\":{\"id\":\"" +  data.getId()
                             + "\",\"displayName\":\"-\",\"tenantId\":\"" + data.getTenant()
                             + "\",\"fullName\":\"-\"},\"actionType\":\"de.kaiserpfalzedv.office.tenant.commands.TenantCreateCommand\"}", result);
    }


    @Before
    public void setupService() {
        service = new TenantConverterRegistry();
    }

    private Tenant createTenant() {
        return new Tenant() {
            private final UUID id = UUID.randomUUID();

            @Override
            public UUID getId() {
                return id;
            }

            @Override
            public String getDisplayName() {
                return "-";
            }

            @Override
            public String getFullName() {
                return "-";
            }

            @Override
            public UUID getTenant() {
                return id;
            }
        };
    }
}
