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

package de.kaiserpfalzedv.office.finance.accounting;

import de.kaiserpfalzedv.office.finance.accounting.accounts.Account;
import de.kaiserpfalzedv.office.finance.accounting.accounts.CostCenter;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.time.OffsetDateTime;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 27.12.15 16:18
 */
public interface BasePostingRecord {
    OffsetDateTime getEntryDate();

    LocalDate getAccountingDate();

    LocalDate getValutaDate();

    CostCenter getCostCenter1();

    CostCenter getCostCenter2();

    DocumentInformation getDocument();

    String getNotice1();

    String getNotice2();

    Account getAccountDebitted();

    Account getAccountCreditted();

    MonetaryAmount getAmount();
}
