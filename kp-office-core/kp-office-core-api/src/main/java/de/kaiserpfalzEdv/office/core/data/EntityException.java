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

package de.kaiserpfalzEdv.office.core.data;

import de.kaiserpfalzEdv.office.commons.Entity;
import de.kaiserpfalzEdv.office.commons.OfficeBusinessException;

import javax.validation.constraints.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * Base exception for all entity exceptions within the Kaiserpfalz Office.
 *
 * @author klenkes
 * @since 0.1.0
 */
public class EntityException extends OfficeBusinessException {
    private static final long serialVersionUID = -2870717951669804525L;

    private Entity entity;

    
    public EntityException(@NotNull final ErrorMessage msg, @NotNull final Entity entity) {
        super(msg);

        setEntity(entity);
    }

    public EntityException(@NotNull final ErrorMessage msg, @NotNull final Entity entity, @NotNull final Throwable cause) {
        super(msg, cause);

        setEntity(entity);
    }

    public Entity getEntity() {
        return entity;
    }

    protected void setEntity(@NotNull final Entity entity) {
        checkArgument(entity != null, "Can't create an entity exception without an entity!");

        this.entity = entity;
    }
}
