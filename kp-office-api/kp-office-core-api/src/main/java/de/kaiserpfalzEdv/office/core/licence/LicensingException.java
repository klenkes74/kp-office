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

package de.kaiserpfalzEdv.office.core.licence;

import de.kaiserpfalzEdv.office.commons.OfficeBusinessException;

import javax.validation.constraints.NotNull;

/**
 * Failure while checking the licenses. Normally one of the subclasses is thrown. This is the root class to ease the
 * handling of licensing failures.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 08.02.15 21:55
 */
public class LicensingException extends OfficeBusinessException {
    private static final long serialVersionUID = 8841311843568944237L;

    public LicensingException(@NotNull final ErrorMessage key) {
        super(key);
    }

    public LicensingException(@NotNull final ErrorMessage key, @NotNull final String message) {
        super(key, message);
    }

    public LicensingException(@NotNull final ErrorMessage key, Throwable cause) {
        super(key, cause);
    }

    public LicensingException(@NotNull final ErrorMessage key, String message, Throwable cause) {
        super(key, message, cause);
    }
}
