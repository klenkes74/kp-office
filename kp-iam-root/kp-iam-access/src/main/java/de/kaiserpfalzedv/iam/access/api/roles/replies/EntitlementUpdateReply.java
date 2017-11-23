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

package de.kaiserpfalzedv.iam.access.api.roles.replies;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.kaiserpfalzedv.iam.access.api.roles.Entitlement;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-19
 */
public class EntitlementUpdateReply extends EntitlementBaseReply {
    private static final long serialVersionUID = -6617507144298546231L;

    private Entitlement data;


    @JsonCreator
    public EntitlementUpdateReply(
            @NotNull @JsonProperty("source") final Object source,
            @NotNull @JsonProperty("command") final UUID command,
            @NotNull @JsonProperty("reply") final UUID reply,
            @NotNull @JsonProperty("entitlement") final Entitlement role
    ) {
        super(source, command, reply);

        data = role;
    }

    public Entitlement getEntitlements() {
        return data;
    }
}
