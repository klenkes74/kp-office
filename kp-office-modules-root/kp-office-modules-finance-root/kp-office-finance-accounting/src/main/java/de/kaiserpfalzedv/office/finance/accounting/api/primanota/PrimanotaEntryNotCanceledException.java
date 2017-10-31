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

package de.kaiserpfalzedv.office.finance.accounting.api.primanota;

import javax.security.auth.login.AccountException;

/**
 * This exception is thrown, if you try to load the cancelation entry to a not canceled prima nota entry.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 31.12.15 07:04
 */
public class PrimanotaEntryNotCanceledException extends AccountException {
    private static final long serialVersionUID = -2567303075322117844L;

    public PrimanotaEntryNotCanceledException(PrimaNotaEntry entry) {
        super("Prima Nota Entry '" + entry.getPN() + "' is not canceled!");
    }
}
