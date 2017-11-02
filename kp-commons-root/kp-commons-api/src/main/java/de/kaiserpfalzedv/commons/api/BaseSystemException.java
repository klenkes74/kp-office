/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.commons.api;

import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 27.12.15 13:21
 */
public class BaseSystemException extends RuntimeException {
    private UUID id = UUID.randomUUID();

    public BaseSystemException(final String message) {
        super(message);
    }

    public BaseSystemException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public BaseSystemException(final Throwable cause) {
        super(cause.getMessage(), cause);
    }

    public BaseSystemException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }


    /**
     * @return The unique ID of this exception.
     */
    public UUID getId() {
        return id;
    }
}
