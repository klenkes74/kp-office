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

package de.kaiserpfalzEdv.office.contacts.contact;

import de.kaiserpfalzEdv.office.core.Entity;
import de.kaiserpfalzEdv.office.core.EntityNotRemovedException;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class ContactNotRemovedException extends EntityNotRemovedException {
    private static final long serialVersionUID = -23784189650430656L;


    public ContactNotRemovedException(Entity entity) {
        super(entity);
    }

    public ContactNotRemovedException(Entity entity, Throwable cause) {
        super(entity, cause);
    }
}
