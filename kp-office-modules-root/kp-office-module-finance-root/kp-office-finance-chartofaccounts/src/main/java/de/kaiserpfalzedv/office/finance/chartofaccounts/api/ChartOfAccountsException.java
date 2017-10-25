/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.finance.chartofaccounts.api;

import de.kaiserpfalzedv.office.common.api.BaseBusinessException;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 31.12.15 07:05
 */
public class ChartOfAccountsException extends BaseBusinessException {
    private static final long serialVersionUID = 3886757548875148239L;


    public ChartOfAccountsException(String message) {
        super(message);
    }

    public ChartOfAccountsException(String message, Throwable cause) {
        super(message, cause);
    }

    public ChartOfAccountsException(Throwable cause) {
        super(cause);
    }
}
