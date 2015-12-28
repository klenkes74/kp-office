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

package de.kaiserpfalzedv.office.finance.impl.accounts;

import de.kaiserpfalzedv.office.common.WritableIdentifyable;
import de.kaiserpfalzedv.office.finance.accounting.accounts.Account;

import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 28.12.15 07:13
 */
public class AccountImpl implements Account, WritableIdentifyable {
    private static final long serialVersionUID = 1911392434073856543L;

    private UUID   tenantId;
    private UUID   id;
    private String displayName;
    private String fullName;


    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getDisplayname() {
        return displayName;
    }

    @Override
    public String getFullname() {
        return fullName;
    }

    @Override
    public UUID getTenantId() {
        return tenantId;
    }

    public void setTenantId(UUID id) {
        this.tenantId = id;
    }

    @Override
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
