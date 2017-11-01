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

package de.kaiserpfalzedv.office.access.api.users.impl;

import java.util.UUID;

import de.kaiserpfalzedv.office.access.api.users.OfficeEntitlement;
import de.kaiserpfalzedv.office.common.api.data.Identifiable;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-11
 */
public class OfficeEntitlementImpl implements OfficeEntitlement, Identifiable {
    private static final long serialVersionUID = 5335949778988132237L;

    private UUID id;
    private String displayName;
    private String fullName;


    public OfficeEntitlementImpl(final UUID id, final String displayName, final String fullName) {
        this.id = id;
        this.displayName = displayName;
        this.fullName = fullName;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String getFullName() {
        return fullName;
    }

    @Override
    public UUID getId() {
        return id;
    }


    @Override
    public String getName() {
        return displayName;
    }
}
