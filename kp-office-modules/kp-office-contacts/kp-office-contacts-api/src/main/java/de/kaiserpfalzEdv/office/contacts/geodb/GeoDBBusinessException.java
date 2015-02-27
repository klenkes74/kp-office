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

package de.kaiserpfalzEdv.office.contacts.geodb;

import de.kaiserpfalzEdv.office.commons.OfficeBusinessException;
import de.kaiserpfalzEdv.office.commons.i18n.ErrorMessage;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 25.02.15 13:55
 */
public class GeoDBBusinessException extends OfficeBusinessException {
    public GeoDBBusinessException(@NotNull ErrorMessage msg) {
        super(msg);
    }

    public GeoDBBusinessException(@NotNull ErrorMessage msg, @NotNull Throwable cause) {
        super(msg, cause);
    }

    public GeoDBBusinessException(@NotNull ErrorMessage msg, @NotNull String message) {
        super(msg, message);
    }

    public GeoDBBusinessException(@NotNull ErrorMessage msg, @NotNull String message, @NotNull Throwable cause) {
        super(msg, message, cause);
    }

    public GeoDBBusinessException(@NotNull ErrorMessage msg, @NotNull List<? extends Serializable> data) {
        super(msg, data);
    }

    public GeoDBBusinessException(@NotNull ErrorMessage msg, @NotNull List<? extends Serializable> data, @NotNull Throwable cause) {
        super(msg, data, cause);
    }

    public GeoDBBusinessException(@NotNull ErrorMessage msg, @NotNull String message, @NotNull List<? extends Serializable> data) {
        super(msg, message, data);
    }

    public GeoDBBusinessException(@NotNull ErrorMessage msg, @NotNull String message, @NotNull List<? extends Serializable> data, @NotNull Throwable cause) {
        super(msg, message, data, cause);
    }
}
