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

import de.kaiserpfalzEdv.office.KPOEntity;
import de.kaiserpfalzEdv.office.projects.Project;
import de.kaiserpfalzEdv.office.tenants.TenantHolder;

import java.util.Set;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public interface Person extends KPOEntity, TenantHolder {
    public Set<Address> getAddresses();
    public Set<Project> getProjects();
    public Set<Person> getContacts();
}
