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

package de.kaiserpfalzedv.iam.tenant.api.replies;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.kaiserpfalzedv.iam.tenant.api.Tenant;

import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public class TenantRetrieveAllReply extends TenantBaseReply {
    private static final long serialVersionUID = 8344737895442602079L;

    private HashSet<Tenant> tenants;

    @JsonCreator
    public TenantRetrieveAllReply(
            @NotNull @JsonProperty("source") final UUID source,
            @NotNull @JsonProperty("command") final UUID commandId,
            @NotNull @JsonProperty("reply") final UUID replyId,
            @NotNull @JsonProperty("tenants") final HashSet<Tenant> tenants
    ) {
        super(source, commandId, replyId);

        this.tenants = new HashSet<>(tenants.size());
        this.tenants.addAll(tenants);
    }

    public Set<Tenant> getTenants() {
        return tenants;
    }
}
