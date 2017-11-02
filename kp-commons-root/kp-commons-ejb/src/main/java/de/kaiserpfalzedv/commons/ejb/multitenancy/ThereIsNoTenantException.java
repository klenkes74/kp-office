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

package de.kaiserpfalzedv.commons.ejb.multitenancy;

import de.kaiserpfalzedv.commons.api.BaseSystemException;
import de.kaiserpfalzedv.commons.api.multitenancy.Tenant;
import de.kaiserpfalzedv.commons.api.multitenancy.TenantHolder;
import de.kaiserpfalzedv.commons.api.multitenancy.TenantHoldingServiceRequest;

/**
 * If the {@link TenantHolderImpl} should provide a tenant but no tenant is set in this thread, then it can't set one
 * automagically. So throw this exception and the developer will find out during integration tests. Hehe.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @see Tenant
 * @see TenantHolder
 * @see TenantHoldingServiceRequest
 * @since 2017-10-31
 */
public class ThereIsNoTenantException extends BaseSystemException {
    private static final long serialVersionUID = 7463642672753310526L;

    public ThereIsNoTenantException() {
        super("There is no tenant set. Can't provide one out of fresh air.");
    }
}
