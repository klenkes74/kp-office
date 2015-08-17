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

import de.kaiserpfalzEdv.office.commons.i18n.ErrorMessageDefinition;

import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 13.02.15 03:41
 */
@ErrorMessageDefinition
public enum ErrorMessage implements de.kaiserpfalzEdv.office.commons.i18n.ErrorMessage {
    GENERIC_SECURITY("SECURITY_1000", "Generic licensing failure", true, String.class),
    INVALID_LOGIN("SECURITY_2000", "Invalid Login.", true, String.class),
    NO_SUCH_ACCOUNT("SECURITY_2001", "No such account.", true, String.class),
    INVALID_TICKET("SECURITY_3000", "Invalid ticket.", true, UUID.class),
    NO_SUCH_TICKET("SECURITY_3001", "No such ticket.", true, UUID.class),
    CANT_CREATE_PASSWORD_SALT("SECURITY_4001", "Can not create password salt.", false, null),
    CANT_ENCRYPT_PASSWORD("SECURITY_4002", "Can not encrypt the password.", false, null),;

    private String   code;
    private String   message;
    private boolean  needsData;
    private Class<?> dataType;

    ErrorMessage(String code, String message, boolean needsData, Class<?> dataType) {
        this.code = code;
        this.message = message;
        this.needsData = needsData;
        this.dataType = dataType;
    }

    public String getCode() { return code; }

    public String getMessage() {
        return message;
    }

    public boolean needsData() {
        return needsData;
    }

    public Class<?> getDataType() {
        return dataType;
    }
}
