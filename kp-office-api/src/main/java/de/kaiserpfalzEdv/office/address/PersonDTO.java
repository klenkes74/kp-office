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

package de.kaiserpfalzEdv.office.address;

import de.kaiserpfalzEdv.office.projects.Project;
import de.kaiserpfalzEdv.office.tenants.Tenant;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class PersonDTO implements Person {
    private Tenant tenant;
    private UUID id;

    private String number;
    private String name;

    private final HashSet<Address> addresses = new HashSet<>(5);
    private final HashSet<Project> projects = new HashSet<>();
    private final HashSet<Person> contacts = new HashSet<>();


    public PersonDTO(final Tenant tenant, final UUID id, final String number, final String name) {
        this.tenant = tenant;
        this.id = id;
        this.number = number;
        this.name = name;
    }

    public UUID getId() {
        return id;
    }

    public String getDisplayNumber() {
        return number;
    }

    public String getDisplayName() {
        return name;
    }


    @Override
    public Set<Address> getAddresses() {
        return Collections.unmodifiableSet(addresses);
    }

    @Override
    public Set<Project> getProjects() {
        return Collections.unmodifiableSet(projects);
    }

    @Override
    public Set<Person> getContacts() {
        return Collections.unmodifiableSet(contacts);
    }

    @Override
    public Tenant getTenant() {
        return tenant;
    }
}
