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

package de.kaiserpfalzedv.office.tenant.api.replies;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.kaiserpfalzedv.office.tenant.api.Tenant;
import de.kaiserpfalzedv.office.tenant.api.TenantImpl;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public abstract class TenantContainingBaseReply extends TenantBaseReply {
    private static final long serialVersionUID = 298502452426137159L;


    @JsonTypeInfo(defaultImpl = TenantImpl.class, use = JsonTypeInfo.Id.NAME, include = PROPERTY)
    private Tenant tenant;


    public TenantContainingBaseReply(
            @NotNull final UUID source,
            @NotNull final UUID commandId,
            @NotNull final UUID replyId,
            @NotNull final Tenant tenant
    ) {
        super(source, commandId, replyId);

        this.tenant = tenant;
    }

    public Tenant getTenant() {
        return tenant;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("tenant", tenant.getId())
                .toString();
    }
}
