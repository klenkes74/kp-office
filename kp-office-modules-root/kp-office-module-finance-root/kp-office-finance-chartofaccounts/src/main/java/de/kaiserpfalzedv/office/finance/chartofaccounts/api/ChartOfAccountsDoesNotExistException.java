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

/**
 * @author klenkes
 * @version 2015Q1
 * @since 04.01.16 04:42
 */
public class ChartOfAccountsDoesNotExistException extends ChartOfAccountsException {
    private static final long serialVersionUID = 7157694522881852785L;

    private String chartOfAccounts;

    public ChartOfAccountsDoesNotExistException(final String chartOfAccounts) {
        super("Chart of account '" + chartOfAccounts + "' does not exist.");

        this.chartOfAccounts = chartOfAccounts;
    }

    public String getChartOfAccounts() {
        return chartOfAccounts;
    }
}
