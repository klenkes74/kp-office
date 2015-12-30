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

package de.kaiserpfalzedv.office.common.data;

import de.kaiserpfalzedv.office.common.BaseBusinessException;

import java.util.UUID;

/**
 * The object that should be created already exists in the dataset. This exception contains the existing object.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 29.12.15 20:08
 */
public class ObjectDoesNotExistException extends BaseBusinessException {
    private static final long serialVersionUID = -5695559745278478649L;


    private Class<?> clasz;
    private UUID     id;

    public ObjectDoesNotExistException(final Class<?> clasz, final UUID objectId) {
        super(generateMessage(clasz, objectId));

        this.clasz = clasz;
        this.id = objectId;
    }

    private static String generateMessage(final Class<?> clasz, final UUID id) {
        return "An object of type '" + clasz.getSimpleName() + "' with id '" + id.toString() + "' does not exists.";
    }


    public Class<?> getClasz() {
        return clasz;
    }

    public UUID getObjectId() {
        return id;
    }
}
