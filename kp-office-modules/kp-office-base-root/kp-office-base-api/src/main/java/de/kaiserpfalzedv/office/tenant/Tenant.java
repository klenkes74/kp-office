/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.tenant;

import de.kaiserpfalzedv.office.common.data.Identifyable;

/**
 * A tenant is used for data based access rights. All data belongs to a certain tenant. The security will check the
 * tenant for access authorization while doing data retrieval or manipulation.
 * <p>
 * The tenant within the other system is only a UUID. To get the name and the organizational structure, the
 * {@link TenantService} needs to be queried.
 * <p>
 * The tenant holds only very small data.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-04
 */
public interface Tenant extends Identifyable {}
