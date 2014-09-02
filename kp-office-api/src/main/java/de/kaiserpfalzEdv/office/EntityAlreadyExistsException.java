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

import static com.google.common.base.Preconditions.checkArgument;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class EntityAlreadyExistsException extends OfficeBusinessException {
    private static final long serialVersionUID = 7808554001104127951L;


    private KPOEntity entity;


    public EntityAlreadyExistsException(KPOEntity entity) {
        super(createMessage(entity));

        setEntity(entity);
    }

    public EntityAlreadyExistsException(KPOEntity entity, Throwable cause) {
        super(createMessage(entity), cause);

        setEntity(entity);
    }


    private static String createMessage(KPOEntity entity) {
        return new StringBuilder("Entity already exists: ").append(entity).toString();
    }


    public KPOEntity getEntity() {
        return entity;
    }

    private void setEntity(final KPOEntity entity) {
        checkArgument(entity != null, "Can't create an entity already exist exception without an entity!");

        this.entity = entity;
    }
}
