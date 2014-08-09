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

package de.kaiserpfalzEdv.office.tenant;

import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public class TenantDTO implements Tenant {
    private UUID id;

    private String displayNumber;
    private String displayName;


    public TenantDTO(final UUID id, final String number, final String name) {
        setId(id);

        setDisplayNumber(number);
        setDisplayName(name);
    }


    @Override
    public UUID getId() {
        return id;
    }

    public void setId(final UUID id) {
        this.id = id;
    }

    @Override
    public String getDisplayNumber() {
        return displayNumber;
    }

    public void setDisplayNumber(final String displayNumber) {
        this.displayNumber = displayNumber;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
}
