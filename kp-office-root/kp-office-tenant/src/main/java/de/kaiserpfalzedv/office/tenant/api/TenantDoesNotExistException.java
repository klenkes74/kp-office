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

import java.util.UUID;

import de.kaiserpfalzedv.office.common.api.data.ObjectDoesNotExistException;

/**
 * The tenant with the given ID does not exists. Can't be loaded/modified. Should not be thrown while deleting the
 * tenant.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-04
 */
public class TenantDoesNotExistException extends ObjectDoesNotExistException {
    private static final long serialVersionUID = 2194497152285502736L;

    public TenantDoesNotExistException(final UUID id) {
        super(Tenant.class, id);
    }

    public TenantDoesNotExistException(final String key) {
        super(Tenant.class, key);
    }
}
