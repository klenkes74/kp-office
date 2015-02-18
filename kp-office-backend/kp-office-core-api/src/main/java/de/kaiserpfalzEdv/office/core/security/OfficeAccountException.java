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

import javax.validation.constraints.NotNull;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 13.02.15 04:56
 */
public class OfficeAccountException extends OfficeSecurityException {
    private static final long serialVersionUID = -1255832589672604690L;
    private final String account;


    public OfficeAccountException(@NotNull final ErrorMessage msg, @NotNull final String account) {
        super(msg);

        this.account = account;
    }


    public String getAccount() {
        return account;
    }
}