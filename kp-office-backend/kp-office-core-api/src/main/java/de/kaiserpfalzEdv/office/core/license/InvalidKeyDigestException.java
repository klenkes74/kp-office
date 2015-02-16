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

package de.kaiserpfalzEdv.office.core.license;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 09.02.15 20:37
 */
public class InvalidKeyDigestException extends CantLoadKeyException {
    private static final long         serialVersionUID = 7811569613476538249L;
    private static final ErrorMessage INVALID_DIGEST   = ErrorMessage.LICENSE_2002;

    public InvalidKeyDigestException(final String fileName, final Throwable cause) {
        super(INVALID_DIGEST, fileName, cause);
    }
}
