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
import de.kaiserpfalzEdv.office.accounting.DatabaseMoney;
import de.kaiserpfalzEdv.office.accounting.postingRecord.DocumentInformation;
import org.apache.commons.lang3.builder.Builder;
import org.javamoney.moneta.Money;

import javax.money.MonetaryAmount;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 15.08.15 08:03
 */
public class DocumentInformationBuilder implements Builder<DocumentInformationImpl> {
    private static final String DEFAULT_CURRENCY = "EUR";

    private String        number1;
    private String        number2;
    private LocalDate     date;
    private DatabaseMoney amount;

    @Override
    public DocumentInformationImpl build() {
        calculateDefaultValues();
        validate();

        return new DocumentInformationImpl(number1, number2, date, amount);
    }

    private void calculateDefaultValues() {
        if (date == null) date = LocalDate.now();

        if (amount == null) amount = new DatabaseMoney(Money.of(0, DEFAULT_CURRENCY));
    }

    private void validate() {
        ArrayList<String> errors = new ArrayList<>();

        if (!errors.isEmpty()) {
            throw new BuilderException(errors);
        }
    }


    public DocumentInformationBuilder withDocument(final DocumentInformation original) {
        withNumber1(original.getDocumentNumber1());
        withNumber2(original.getDocumentNumber2());
        withDate(original.getDocumentDate());
        withAmount(original.getDocumentAmount());

        return this;
    }


    public DocumentInformationBuilder withNumber1(String number1) {
        this.number1 = number1;
        return this;
    }

    public DocumentInformationBuilder withNumber2(String number2) {
        this.number2 = number2;
        return this;
    }

    public DocumentInformationBuilder withDate(LocalDate date) {
        this.date = date;
        return this;
    }

    public DocumentInformationBuilder withAmount(DatabaseMoney amount) {
        this.amount = amount;
        return this;
    }

    public DocumentInformationBuilder withAmount(MonetaryAmount amount) {
        return withAmount(new DatabaseMoney(amount));
    }
}
