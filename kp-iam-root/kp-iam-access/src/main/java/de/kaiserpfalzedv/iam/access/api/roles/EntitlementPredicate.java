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

package de.kaiserpfalzedv.iam.access.api.roles;

import java.util.UUID;

import de.kaiserpfalzedv.commons.api.data.query.PredicateBuilder;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-09
 */
public class EntitlementPredicate {
    public static PredicateBuilder<Entitlement, UUID> id() {
        return new PredicateBuilder<Entitlement, UUID>().withAttribute("id");
    }

    public static PredicateBuilder<Entitlement, String> name() {
        return new PredicateBuilder<Entitlement, String>().withAttribute("name");
    }

    public static PredicateBuilder<Entitlement, String> displayName() {
        return new PredicateBuilder<Entitlement, String>().withAttribute("displayName");
    }

    public static PredicateBuilder<Entitlement, String> fullName() {
        return new PredicateBuilder<Entitlement, String>().withAttribute("fullName");
    }

    public static PredicateBuilder<Entitlement, String> descriptionKey() {
        return new PredicateBuilder<Entitlement, String>().withAttribute("descriptionKey");
    }
}
