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

package de.kaiserpfalzedv.office.tenant.commands;

import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.kaiserpfalzedv.office.common.commands.CrudCommands;
import de.kaiserpfalzedv.office.tenant.Tenant;
import de.kaiserpfalzedv.office.tenant.impl.TenantImpl;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As.PROPERTY;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-25
 */
public abstract class TenantContainingBaseCommand extends TenantBaseCommand {
    private static final long serialVersionUID = 669911125527091539L;

    @JsonTypeInfo(defaultImpl = TenantImpl.class, use = JsonTypeInfo.Id.NAME, include = PROPERTY)
    protected Tenant tenant;

    protected TenantContainingBaseCommand(
            @NotNull final CrudCommands type,
            @NotNull final UUID source,
            @NotNull final UUID commandId,
            @NotNull final Tenant tenant
    ) {
        super(type, source, commandId);

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
