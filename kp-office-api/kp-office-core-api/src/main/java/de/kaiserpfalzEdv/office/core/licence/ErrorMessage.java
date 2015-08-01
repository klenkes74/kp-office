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

package de.kaiserpfalzEdv.office.core.licence;

import de.kaiserpfalzEdv.office.commons.i18n.ErrorMessageDefinition;

import java.time.LocalDate;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 13.02.15 03:41
 */
@ErrorMessageDefinition
public enum ErrorMessage implements de.kaiserpfalzEdv.office.commons.i18n.ErrorMessage {
    LICENSE_1000("LICENSE_1000", "Generic licensing failure", true, String.class),
    LICENSE_1001("LICENSE_1001", "No licence is loaded", false, null),
    LICENSE_2001("LICENSE_2001", "Can not load licencing key file", true, String.class),
    LICENSE_2002("LICENSE_2002", "Licencing key file not valid", true, String.class),
    LICENSE_3001("LICENSE_3001", "Can not load licencing file", true, String.class),
    LICENSE_3002("LICENSE_3002", "Licence file invalid", true, String.class),
    LICENSE_4001("LICENSE_4001", "Licence expired", true, LocalDate.class),
    LICENSE_4002("LICENSE_4002", "Licence software does not match", true, String.class),
    LICENSE_4003("LICENSE_4003", "Licence issuer is invalid", true, String.class),
    LICENSE_4004("LICENSE_4004", "Licencee is invalid", true, String.class),
    LICENSE_4005("LICENSE_4005", "Feature is not licenced", true, String.class);

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