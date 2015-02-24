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

package de.kaiserpfalzEdv.office.accounting.postingRecord.impl;

import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.accounting.chartsofaccounts.Account;
import de.kaiserpfalzEdv.office.accounting.postingRecord.PostingRecord;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.money.MonetaryAmount;
import javax.money.convert.CurrencyConversion;
import javax.money.convert.MonetaryConversions;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 18.02.15 21:05
 */
public class PostingRecordBuilder implements Builder<PostingRecord> {
    private static final Logger             LOG                = LoggerFactory.getLogger(PostingRecordBuilder.class);
    private static final CurrencyConversion DEFAULT_CONVERSION = MonetaryConversions.getConversion("EUR", "ECB");

    private CurrencyConversion conversion = DEFAULT_CONVERSION;


    private UUID      id;
    private String    entryId;
    private LocalDate entryDate;
    private LocalDate accountingDate;
    private LocalDate valutaDate;

    private Account        accountDebitted;
    private Account        accountCreditted;
    private MonetaryAmount accountingAmount;

    private String         documentNumber;
    private LocalDate      documentDate;
    private MonetaryAmount documentAmount;

    private String notice;

    @Override
    public PostingRecord build() {
        validate();

        PostingRecordImpl result = new PostingRecordImpl(
                id,
                entryId,
                entryDate,
                documentNumber,
                documentDate,
                documentAmount,
                accountDebitted,
                accountCreditted,
                accountingAmount
        );

        if (isNotBlank(notice)) result.setNotice(notice);
        if (valutaDate != null) result.setValutaDate(valutaDate);
        if (accountingDate != null) result.setAccountingDate(accountingDate);

        return result;
    }

    public void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (id == null) failures.add("No id for primaNota entry given.");
        if (isBlank(entryId)) failures.add("No entry id for the primaNota given.");
        if (entryDate == null) failures.add("No entry date for the primaNota given.");

        if (isBlank(documentNumber)) failures.add("No document number given.");
        if (documentDate == null) failures.add("No document date given.");

        if (accountCreditted == null) failures.add("No creditted account given.");
        if (accountDebitted == null) failures.add("No debitted account given.");
        if (accountingAmount == null) failures.add("No amount given.");

        if (failures.size() > 0) {
            LOG.warn("Can't build primaNota entry: {}", failures);
            throw new BuilderException(failures);
        }
    }


    public PostingRecordBuilder withEntry(@NotNull final PostingRecord entry) {
        withId(entry.getId());

        withEntryId(entry.getDisplayNumber());
        withEntryDate(entry.getEntryDate());
        withAccountingDate(entry.getAccountingDate());
        withValutaDate(entry.getValutaDate());

        withDocumentNumber(entry.getDocumentNumber());
        withDocumentDate(entry.getDocumentDate());
        withDocumentAmount(entry.getDocumentAmount());

        withNotice(entry.getNotice());

        withAccountDebitted(entry.getAccountDebitted());
        withAccountCreditted(entry.getAccountCreditted());
        withAmount(entry.getPostingAmount());

        return this;
    }


    public PostingRecordBuilder withId(@NotNull final UUID id) {
        this.id = id;

        return this;
    }

    public PostingRecordBuilder generateId() {
        this.id = UUID.randomUUID();

        return this;
    }

    public PostingRecordBuilder withEntryDate(final LocalDate entryDate) {
        this.entryDate = entryDate;

        return this;
    }

    public PostingRecordBuilder withAccountingDate(final LocalDate accountingDate) {
        this.accountingDate = accountingDate;

        return this;
    }

    public PostingRecordBuilder withValutaDate(final LocalDate valutaDate) {
        this.valutaDate = valutaDate;

        return this;
    }

    public PostingRecordBuilder withEntryId(final String entryId) {
        this.entryId = entryId;

        return this;
    }


    public PostingRecordBuilder withDocumentDate(final LocalDate documentDate) {
        this.documentDate = documentDate;

        return this;
    }

    public PostingRecordBuilder withDocumentNumber(final String documentNumber) {
        this.documentNumber = documentNumber;

        return this;
    }

    public PostingRecordBuilder withDocumentAmount(final MonetaryAmount amount) {
        this.documentAmount = amount;
        this.accountingAmount = documentAmount.with(conversion);

        return this;
    }


    public PostingRecordBuilder withNotice(final String notice) {
        this.notice = notice;

        return this;
    }


    public PostingRecordBuilder withAccountDebitted(final Account accountDebitted) {
        this.accountDebitted = accountDebitted;

        return this;
    }


    public PostingRecordBuilder withAccountCreditted(final Account accountCreditted) {
        this.accountCreditted = accountCreditted;

        return this;
    }

    public PostingRecordBuilder withAmount(final MonetaryAmount amount) {
        this.accountingAmount = amount;

        return this;
    }


    /**
     * Creates a new booking within this entry.
     *
     * @param accountDebitted  The account that gets debitted.
     * @param accountCreditted The account that gets creditted.
     * @param amount           The amount of the booking.
     *
     * @return the builder itself.
     */
    public PostingRecordBuilder createBooking(
            final Account accountDebitted,
            final Account accountCreditted,
            final MonetaryAmount amount
    ) {
        this.accountDebitted = accountDebitted;
        this.accountCreditted = accountCreditted;

        this.documentAmount = amount;

        if (amount.getCurrency().getNumericCode() != conversion.getCurrency().getNumericCode()) {
            this.accountingAmount = amount.with(MonetaryConversions.getConversion(conversion.getCurrency(), "ECB"));
        } else {
            this.accountingAmount = amount;
        }

        return this;
    }


    public PostingRecordBuilder withDefaultCurrency(@NotNull final String currency) {
        conversion = MonetaryConversions.getConversion(currency, "ECB");

        return this;
    }
}
