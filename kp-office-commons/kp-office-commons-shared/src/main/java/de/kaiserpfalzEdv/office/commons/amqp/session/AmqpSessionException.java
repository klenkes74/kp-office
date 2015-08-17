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

package de.kaiserpfalzEdv.office.commons.amqp.session;

import de.kaiserpfalzEdv.commons.exceptions.BaseBusinessException;
import de.kaiserpfalzEdv.commons.exceptions.BaseSystemException;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 15:05
 */
public class AmqpSessionException extends BaseSystemException {
    private static final long serialVersionUID = -5139573351731546552L;


    public AmqpSessionException(String message) {
        super(message);
    }

    public AmqpSessionException(BaseBusinessException cause) {
        super(cause);
    }

    public AmqpSessionException(BaseSystemException cause) {
        super(cause);
    }

    public AmqpSessionException(Throwable cause) {
        super(cause);
    }

    public AmqpSessionException(String message, Throwable cause) {
        super(message, cause);
    }
}
