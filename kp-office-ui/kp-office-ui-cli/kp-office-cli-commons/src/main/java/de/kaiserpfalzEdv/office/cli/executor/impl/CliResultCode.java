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

package de.kaiserpfalzEdv.office.cli.executor.impl;

import de.kaiserpfalzEdv.office.commons.i18n.ErrorMessage;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 03.03.15 00:40
 */
public enum CliResultCode implements ErrorMessage {
    OK("200", "Ok", false, null),
    NOTHING_TO_DO("204", "Ok", false, null),
    FAILED("400", "Error", false, null),
    NOT_FOUND("404", "Not found", false, null),
    BAD_GATEWAY("502", "Bad Module", false, null);

    private String   code;
    private String   message;
    private boolean  needsData;
    private Class<?> dataType;

    CliResultCode(String code, String message, boolean needsData, Class<?> dataType) {
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
