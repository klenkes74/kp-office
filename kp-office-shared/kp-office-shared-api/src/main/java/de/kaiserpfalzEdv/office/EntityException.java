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

package de.kaiserpfalzEdv.office;

import de.kaiserpfalzEdv.office.core.KPOEntity;

import javax.validation.constraints.NotNull;

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author klenkes
 * @since 2014Q
 */
public class EntityException extends OfficeBusinessException {
    private static final long serialVersionUID = 7334565012607247114L;

    private KPOEntity entity;

    public EntityException(@NotNull final KPOEntity entity) {
        super(createMessage(entity));

        setEntity(entity);
    }

    public EntityException(@NotNull final KPOEntity entity, @NotNull final Throwable cause) {
        super(createMessage(entity), cause);

        setEntity(entity);
    }

    protected static String createMessage(@NotNull final KPOEntity entity) {
        return new StringBuilder("Problem Entity: ").append(entity).toString();
    }

    public KPOEntity getEntity() {
        return entity;
    }

    protected void setEntity(@NotNull final KPOEntity entity) {
        checkArgument(entity != null, "Can't create an entity exception without an entity!");

        this.entity = entity;
    }
}
