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

package de.kaiserpfalzEdv.office.contacts.contact;

import de.kaiserpfalzEdv.office.contacts.address.Address;
import de.kaiserpfalzEdv.office.tenants.Tenant;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class PersonDTO extends ContactDTO implements Person {
    private PersonalName name;

    public PersonDTO(@NotNull final Tenant tenant,
                     @NotNull final UUID id,
                     @NotNull final String number,
                     @NotNull final PersonalName name,
                     @NotNull final ContactType type,
                     @NotNull final Collection<Address> addresses,
                     @NotNull final Collection<Contact> contacts) {
        super(id, name.getDisplayName(), number, tenant, type, addresses, contacts);

        setName(name);
    }

    @Override
    public PersonalName getName() {
        return name;
    }

    public void setName(@NotNull final PersonalName name) {
        //noinspection deprecation
        setDisplayName(name);

        this.name = name;
    }
}
