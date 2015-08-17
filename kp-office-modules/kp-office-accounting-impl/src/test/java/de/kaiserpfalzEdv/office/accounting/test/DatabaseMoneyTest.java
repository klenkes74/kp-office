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

package de.kaiserpfalzEdv.office.accounting.test;

import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.accounting.server.DatabaseMoney;
import org.javamoney.moneta.Money;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.math.BigDecimal;

import static org.testng.Assert.assertEquals;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 10.08.15 23:24
 */
@Test
public class DatabaseMoneyTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(DatabaseMoneyTest.class);


    private static final BigDecimal VALUE    = BigDecimal.valueOf(100.99);
    private static final String     CURRENCY = "EUR";


    private DatabaseMoney service;


    public DatabaseMoneyTest() {
        super(DatabaseMoneyTest.class, LOG);
    }


    public void checkDatabaseMoneyValue() {
        logMethod("databasemoney-value", "Checking the DatabaseMoney JPA value ...");

        assertEquals(service.getValue(), VALUE);
    }

    public void checkDatabaseMoneyCurrencyCode() {
        logMethod("databasemoney-currencycode", "Checking the DatabaseMoney JPA currency code ...");

        assertEquals(service.getCurrencyCode(), CURRENCY);
    }

    public void checkMoneyValue() {
        logMethod("money-value", "Checking the Money value ...");

        assertEquals(service.getNumber().toString(), VALUE.toString());
    }

    public void checkMoneyCurrencyCode() {
        logMethod("money-currencycode", "Checking the Money currency code ...");

        assertEquals(service.getCurrencyCode(), CURRENCY);
    }


    public void checkChangeValue() {
        logMethod("databasemoney-change-value", "Checking changing the value of database money ...");

        BigDecimal newValue = BigDecimal.valueOf(9.99);

        service.setValue(newValue);

        assertEquals(service.getValue(), newValue);
        assertEquals(service.getNumber().toString(), newValue.toString());
    }

    public void checkChangeCurrency() {
        logMethod("databasemoney-change-currency", "Checking changing the currency code of database money ...");

        LOG.trace("Old money: {}", service);
        service.setCurrencyCode("USD");
        LOG.trace("New money: {}", service);

        assertEquals(service.getCurrencyCode(), "USD");
        assertEquals(service.getCurrency().getCurrencyCode(), "USD");
    }


    @BeforeMethod
    protected void setupService() {
        service = new DatabaseMoney(Money.of(VALUE, CURRENCY));
        LOG.trace("Created service: {}", service);
    }
}
