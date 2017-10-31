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

package de.kaiserpfalzedv.office.access.api;


import de.kaiserpfalzedv.office.common.api.BaseSystemException;

/**
 * The abstract base of all runtime exceptions of the security APIs.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-03-14
 */
public abstract class SecurityRuntimeException extends BaseSystemException {
    private static final long serialVersionUID = -95739639109592203L;

    /**
     * @param message the failure message.
     */
    public SecurityRuntimeException(final String message) {
        super(message);
    }

    /**
     * @param message the failure message.
     * @param cause   the failure cause.
     */
    public SecurityRuntimeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    /**
     * @param message            the failure message.
     * @param cause              the failure cause.
     * @param enableSuppression  whether or not suppression is enabled
     *                           or disabled
     * @param writableStackTrace whether or not the stack trace should
     *                           be writable
     */
    public SecurityRuntimeException(
            final String message, final Throwable cause,
            final boolean enableSuppression, final boolean writableStackTrace
    ) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
