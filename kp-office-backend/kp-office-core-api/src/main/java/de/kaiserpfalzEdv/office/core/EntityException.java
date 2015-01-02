/*
 * Copyright 2015 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.core;

import de.kaiserpfalzEdv.office.OfficeBusinessException;

import javax.validation.constraints.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author klenkes
 * @since 2014Q
 */
public class EntityException extends OfficeBusinessException {
    private static final long serialVersionUID = 8182699830994095057L;

    private Entity entity;

    public EntityException(@NotNull final Entity entity) {
        super(createMessage(entity));

        setEntity(entity);
    }

    public EntityException(@NotNull final Entity entity, @NotNull final Throwable cause) {
        super(createMessage(entity), cause);

        setEntity(entity);
    }

    protected static String createMessage(@NotNull final Entity entity) {
        return new StringBuilder("Problem Entity: ").append(entity).toString();
    }

    public Entity getEntity() {
        return entity;
    }

    protected void setEntity(@NotNull final Entity entity) {
        checkArgument(entity != null, "Can't create an entity exception without an entity!");

        this.entity = entity;
    }
}
