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

package de.kaiserpfalzedv.office.finance.accounting.impl;

import java.time.LocalDate;

import javax.money.MonetaryAmount;

import de.kaiserpfalzedv.office.finance.accounting.api.DocumentInformation;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 28.12.15 07:17
 */
public class DocumentInformationImpl implements DocumentInformation {
    private static final Logger LOG = LoggerFactory.getLogger(DocumentInformationImpl.class);

    private LocalDate date;
    private String    number1;
    private String    number2;
    private Money     amount;

    @Override
    public LocalDate getDate() {
        return date;
    }

    @Override
    public String getNumber1() {
        return number1;
    }

    @Override
    public String getNumber2() {
        return number2;
    }

    @Override
    public MonetaryAmount getAmount() {
        return amount;
    }
}
