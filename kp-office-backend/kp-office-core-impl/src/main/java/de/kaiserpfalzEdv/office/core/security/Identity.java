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

package de.kaiserpfalzEdv.office.core.security;

import de.kaiserpfalzEdv.office.core.KPOTenantHoldingEntity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Index;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Entity
@Table(
    catalog = "SECURITY",
    name = "IDENTITIES",
    indexes = {
        @Index(name = "IDENTITIES_TENANTS_UK", columnList = "TENANT_,DISPLAY_NUMBER_", unique = true)
    }
)
public class Identity extends KPOTenantHoldingEntity {
        @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true, mappedBy = "identity")
        final Set<Account> accounts = new HashSet<>();


        @Deprecated
        public Identity() {}


        public Identity(@NotNull final UUID tenantId,
                        @NotNull final UUID id,
                        @NotNull final String name,
                        @NotNull final String number) {
            super(id, name, number, tenantId);
        }


        public void addAccount(final Account account) {
                this.accounts.add(account);
        }


        public void removeAccount(final Account account) {
                this.accounts.remove(account);
        }
}
