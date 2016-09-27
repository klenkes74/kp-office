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

import de.kaiserpfalzedv.office.common.BaseSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
public class ConverterSystemException extends BaseSystemException {
    private static final Logger LOG = LoggerFactory.getLogger(ConverterSystemException.class);

    private Class<?> type;

    public ConverterSystemException(Class<?> type, String message) {
        super(message);

        this.type = type;
    }

    public ConverterSystemException(Class<?> type, String message, Throwable cause) {
        super(message, cause);

        this.type = type;
    }

    public ConverterSystemException(Class<?> type, Throwable cause) {
        super(cause);

        this.type = type;
    }

    public Class<?> getType() {
        return type;
    }
}
