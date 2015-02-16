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

package de.kaiserpfalzEdv.office.commons;

import de.kaiserpfalzEdv.office.commons.i18n.ErrorMessageDefinition;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 13.02.15 03:41
 */
@ErrorMessageDefinition
public enum ErrorMessage implements de.kaiserpfalzEdv.office.commons.i18n.ErrorMessage {
    CANT_GENERATE_UNIQUE_NUMBER("COMMON_1000", "Can not generate an unique number", false, null),
    ;

    private String code;
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

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public boolean needsData() {
        return needsData;
    }

    @Override
    public Class<?> getDataType() {
        return dataType;
    }
}
