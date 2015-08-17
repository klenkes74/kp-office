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

package de.kaiserpfalzEdv.office.contacts.geodb;

import de.kaiserpfalzEdv.office.commons.i18n.ErrorMessageDefinition;

/**
 * The I18N capable error messages for the GEO DB subsystem.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 25.02.15 13:56
 */
@ErrorMessageDefinition
public enum ErrorMessage implements de.kaiserpfalzEdv.office.commons.i18n.ErrorMessage {
    NO_PROVIDER_FOUND("GEODB_1000", "No provider for GEO DB queries found", false, null),
    INVALID_POSTCODE_QUERY("GEODB_2000", "Invalid query to retrieve a post code", true, String.class),
    NO_SUCH_DATA("GEODB_2001", "The database contains no matching data", true, String.class),;

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

    @Override
    public String getCode() {
        return code;
    }

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
