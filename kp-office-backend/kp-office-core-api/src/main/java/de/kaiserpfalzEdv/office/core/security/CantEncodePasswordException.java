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

package de.kaiserpfalzEdv.office.core.security;

import de.kaiserpfalzEdv.office.commons.OfficeSystemException;

import javax.validation.constraints.NotNull;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 13.02.15 05:35
 */
public class CantEncodePasswordException extends OfficeSystemException {
    private static final long serialVersionUID = 5451578037079514255L;


    public CantEncodePasswordException(@NotNull final Throwable cause) {
        super(ErrorMessage.CANT_ENCRYPT_PASSWORD, cause);
    }
}
