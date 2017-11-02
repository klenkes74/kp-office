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

package de.kaiserpfalzedv.office.tenant.api;

import de.kaiserpfalzedv.commons.api.data.ObjectExistsException;

/**
 * The tenant already exists. It can't be created a second time.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-04
 */
public class TenantExistsException extends ObjectExistsException {
    private static final long serialVersionUID = 849675901640319608L;

    public TenantExistsException(final Tenant existingObject) {
        super(Tenant.class, existingObject.getId(), existingObject);
    }
}
