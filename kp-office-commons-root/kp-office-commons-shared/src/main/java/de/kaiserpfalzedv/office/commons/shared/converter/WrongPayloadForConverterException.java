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

package de.kaiserpfalzedv.office.commons.shared.converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public class WrongPayloadForConverterException extends ConverterBusinessException {
    private static final Logger LOG = LoggerFactory.getLogger(WrongPayloadForConverterException.class);

    public WrongPayloadForConverterException(final Class<?> expected, final Object payload) {
        super(expected, "Wrong payload of type '" + payload.getClass().getCanonicalName()
                + "' (expected type: '" + ((expected != null) ? expected.getCanonicalName() : "-unknown-") + "').");
    }

    public WrongPayloadForConverterException(final Class<?> expected, final Object payload, final Throwable cause) {
        super(expected, "Wrong payload of type '" + payload.getClass().getCanonicalName()
                + "' (expected type: '" + ((expected != null) ? expected.getCanonicalName() : "-unknown-") + "').", cause);
    }
}
