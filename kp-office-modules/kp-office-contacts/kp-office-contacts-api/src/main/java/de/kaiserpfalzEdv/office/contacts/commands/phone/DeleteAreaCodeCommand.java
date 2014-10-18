/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.contacts.commands.phone;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public class DeleteAreaCodeCommand extends AreaCodeBaseCommand {
    private static final long serialVersionUID = 1L;


    private UUID areaCodeId;


    @Deprecated // Only for Jackson, JAX-B and JPA!
    public DeleteAreaCodeCommand() {
    }

    @SuppressWarnings("deprecation")
    public DeleteAreaCodeCommand(@NotNull final UUID id) {
        setAreaCodeId(id);
    }


    public UUID getAreaCodeId() {
        return areaCodeId;
    }

    @Deprecated // Only for Jackson JAX-B and JPA!
    public void setAreaCodeId(@NotNull final UUID areaCodeId) {
        this.areaCodeId = areaCodeId;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("areaCode", areaCodeId)
                .toString();
    }
}
