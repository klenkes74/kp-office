/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.common.client.messaging;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-23
 */
public class ResponseOfWrongTypeException extends MessagingException {
    public ResponseOfWrongTypeException(final String correlationId) {
        super("Response for correlation-id '" + correlationId + "' is of wrong type!");
    }

    public ResponseOfWrongTypeException(final String correlationId, final Throwable cause) {
        super("Response for correlation-id '" + correlationId + "' is of wrong type!", cause);
    }
}
