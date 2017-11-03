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

package de.kaiserpfalzedv.office.access.api.users;

import java.util.Optional;
import java.util.UUID;

/**
 * The single tenant user query service.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-04-07
 */
public interface UserQueryService {
    /**
     * Retrieves the user for the given unique id.
     *
     * @param uniqueId The unique id of the user to be retrieved.
     *
     * @return The user with the given unique id.
     */
    Optional<? extends OfficePrincipal> retrieve(UUID uniqueId);

    /**
     * Retrieves the user with the given user name.
     *
     * @param login The login of the user.
     *
     * @return The user with the unique name.
     */
    Optional<? extends OfficePrincipal> retrieve(String login);
}
