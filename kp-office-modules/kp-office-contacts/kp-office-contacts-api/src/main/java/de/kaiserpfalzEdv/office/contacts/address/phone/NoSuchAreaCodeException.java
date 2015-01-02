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

package de.kaiserpfalzEdv.office.contacts.address.phone;

import de.kaiserpfalzEdv.office.contacts.location.City;
import de.kaiserpfalzEdv.office.core.Entity;
import de.kaiserpfalzEdv.office.core.NoSuchEntityException;

import javax.validation.constraints.NotNull;

/**
 * @author klenkes
 * @since 2014Q
 */
public class NoSuchAreaCodeException extends NoSuchEntityException {
    private static final Class<? extends Entity> clasz = City.class.asSubclass(Entity.class);

    public NoSuchAreaCodeException(@NotNull final String message) {
        super(clasz, message);
    }

    public NoSuchAreaCodeException(@NotNull final Throwable cause) {
        super(clasz, cause);
    }

    public NoSuchAreaCodeException(@NotNull final String message, @NotNull final Throwable cause) {
        super(clasz, message, cause);
    }
}
