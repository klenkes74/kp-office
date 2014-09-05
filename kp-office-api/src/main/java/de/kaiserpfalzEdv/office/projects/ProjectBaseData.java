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

package de.kaiserpfalzEdv.office.projects;

import de.kaiserpfalzEdv.office.core.Link;
import de.kaiserpfalzEdv.office.tenants.Tenant;
import de.kaiserpfalzEdv.office.address.Person;
import de.kaiserpfalzEdv.office.address.PersonDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public class ProjectBaseData implements Project {
    private Tenant tenant;
    private UUID id;

    private String baseUrl;

    private String number;
    private String title;

    private final HashMap<String, List<PersonDTO>> contacts = new HashMap<>();

    public ProjectBaseData(final String baseUrl, final UUID id, final String number, final String title, final Map<String, List<PersonDTO>> contacts) {
        this.baseUrl = baseUrl;

        this.contacts.putAll(contacts);

        setId(id);
        setNumber(number);
        setTitle(title);
    }


    @Override
    public Tenant getTenant() {
        return tenant;
    }

    @Override
    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    @Override
    public String getDisplayNumber() {
        return number;
    }

    public void setNumber(final String number) {
        this.number = number;
    }

    @Override
    public String getDisplayName() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    @Override
    public Set<Person> getContacts(String type) {
        return new HashSet<Person>(contacts.get(type));
    }


    @Override
    public Map<String, List<Link>> getLinks() {
        HashMap<String, List<Link>> result = new HashMap<>();

        for (String key : this.contacts.keySet()) {
            ArrayList<Link> links = new ArrayList<>(contacts.get(key).size());

            for (PersonDTO value : contacts.get(key)) {
                links.add(new Link(baseUrl + "person/" + value.getDisplayNumber(), value.getDisplayName()));
            }

            result.put(key, links);
        }

        return Collections.unmodifiableMap(result);
    }
}
