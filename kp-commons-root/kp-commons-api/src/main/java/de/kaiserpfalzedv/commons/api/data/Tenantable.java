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

package de.kaiserpfalzedv.commons.api.data;

import java.io.Serializable;
import java.util.UUID;

/**
 * All objects implementing this interface can be used in a multi tenant data structure.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 27.12.15 11:36
 */
public interface Tenantable extends Serializable {
    /**
     * Returns only the id of the tenant. The data of the tenant may be retrieved by the appropriate service.
     *
     * @return The unique tenant id of the owning tenant.
     */
    UUID getTenant();
}
