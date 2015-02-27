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

package de.kaiserpfalzEdv.office.accounting.primaNota.impl;

import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.postingRecord.impl.PostingRecordBuilder;

import javax.money.MonetaryAmount;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 27.02.15 11:28
 */
public class PrimaNotaEntryBuilder extends PostingRecordBuilder<PrimaNotaEntryImpl> {
    public PrimaNotaEntryImpl createPostingRecord(
            @NotNull final UUID id,
            @NotNull final String entryId,
            @NotNull final OffsetDateTime entryDate,
            @NotNull final LocalDate documentDate,
            @NotNull final String documentNumber,
            @NotNull final MonetaryAmount documentAmount,
            @NotNull final Account accountDebitted,
            @NotNull final Account accountCreditted,
            @NotNull final MonetaryAmount accountingAmount
    ) {
        return new PrimaNotaEntryImpl(id, entryId, entryDate, documentNumber, documentDate, documentAmount, accountDebitted, accountCreditted, accountingAmount);
    }
}
