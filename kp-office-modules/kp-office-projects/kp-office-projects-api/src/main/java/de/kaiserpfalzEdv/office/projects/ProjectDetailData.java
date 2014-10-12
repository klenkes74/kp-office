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

import de.kaiserpfalzEdv.office.contacts.PersonDTO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public class ProjectDetailData extends ProjectBaseData implements Project {
    private final HashMap<String, List<PersonDTO>> contacts = new HashMap<>();

    public ProjectDetailData(final String baseUrl, final UUID id, final String number, final String title, final Map<String, List<PersonDTO>> contacts) {
        super(baseUrl, id, number, title, contacts);
    }
}
