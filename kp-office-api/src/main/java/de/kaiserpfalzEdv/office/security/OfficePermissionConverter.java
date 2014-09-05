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

package de.kaiserpfalzEdv.office.security;

import com.google.common.base.Converter;
import de.kaiserpfalzEdv.office.core.OfficeAction;
import de.kaiserpfalzEdv.office.core.OfficeActionDTO;
import de.kaiserpfalzEdv.office.core.OfficeModule;
import de.kaiserpfalzEdv.office.core.OfficeModuleDTO;
import de.kaiserpfalzEdv.office.tenants.Tenant;
import de.kaiserpfalzEdv.office.tenants.UnknownTenant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Named;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Named
public class OfficePermissionConverter extends Converter<OfficePermission, String> {
    private static final Logger LOG = LoggerFactory.getLogger(OfficePermissionConverter.class);

    @Override
    protected String doForward(final OfficePermission officePermission) {
        String result = new StringBuilder(officePermission.getModule().getDisplayName())
                .append(":")
                .append(officePermission.getTenant() != null ? officePermission.getTenant().getId() : "*")
                .append(":")
                .append(officePermission.getAction().getDisplayName())
                .toString();

        LOG.trace("Converted {}: {}", officePermission, result);
        return result;
    }

    @Override
    protected OfficePermission doBackward(String s) {
        String[] parts = splitPermissionString(s);

        OfficeModule module = new OfficeModuleDTO(parts[0]);
        Tenant tenant = "*".equals(parts[1]) ? null : new UnknownTenant(UUID.fromString(parts[1]));
        OfficeAction action = new OfficeActionDTO(parts[2]);

        return new OfficePermissionDTO(module, action, tenant);
    }

    private String[] splitPermissionString(String s) {
        String[] parts = s.split(":", 3);

        if (parts.length != 3)
            throw new IllegalStateException("Sorry, a permission string needs 3 parts!");


        return parts;
    }
}
