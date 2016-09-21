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
 *
 */

package de.kaiserpfalzedv.office.common.init;

import de.kaiserpfalzedv.office.common.BaseBusinessException;

/**
 * This exception is thrown if the initalization of a class failes.
 *
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-21
 */
public class InitializationException extends BaseBusinessException {
    private static final long serialVersionUID = 8446577869768548756L;

    private Class<?> clasz;

    public InitializationException(Class<?> clasz, String message) {
        super(message);

        this.clasz = clasz;
    }

    public InitializationException(Class<?> clasz, String message, Throwable cause) {
        super(message, cause);

        this.clasz = clasz;
    }

    public InitializationException(Class<?> clasz, Throwable cause) {
        super(cause);

        this.clasz = clasz;
    }


    public Class<?> getClasz() {
        return clasz;
    }
}
