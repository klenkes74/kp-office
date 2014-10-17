/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.contacts.address.location;

import de.kaiserpfalzEdv.commons.BuilderValidationException;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Test
public class CountryDTOTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(CountryDTOTest.class);


    public CountryDTOTest() {
        super(CountryDTOTest.class, LOG);
    }


    @Test(expectedExceptions = {BuilderValidationException.class})
    public void createWithoutData() {
        logMethod("no-data", "Creating country builder without data ...");

        new CountryBuilder().build();
    }


    public void createMinimum() {
        logMethod("minimum-data", "Creating the country with minimal data set ...");
        Country result = new CountryBuilder()
                .withIso2("DE")
                .withIso3("deu")
                .withPhoneCountryCode("49")
                .withPostalPrefix("D")
                .build();

        assertNotNull(result.getId(), "The UUID should be not NULL!");
        assertEquals(result.getDisplayName(), "DE");
        assertEquals(result.getDisplayNumber(), "DE");

        assertEquals(result.getIso2(), "DE");
        assertEquals(result.getIso3(), "deu");
        assertEquals(result.getPhoneCountryCode(), "49");
        assertEquals(result.getPostalPrefix(), "D");
    }


    public void createFull() {
        logMethod("full-data", "Creating the country with full data set ...");

        Country result = new CountryBuilder()
                .withDisplayName("Deutschland")
                .withDisplayNumber("49-DEU")
                .withIso2("DE")
                .withIso3("deu")
                .withPhoneCountryCode("49")
                .withPostalPrefix("D")
                .build();

        assertNotNull(result.getId(), "The UUID should be not NULL!");
        assertEquals(result.getDisplayName(), "Deutschland");
        assertEquals(result.getDisplayNumber(), "49-DEU");

        assertEquals(result.getIso2(), "DE");
        assertEquals(result.getIso3(), "deu");
        assertEquals(result.getPhoneCountryCode(), "49");
        assertEquals(result.getPostalPrefix(), "D");
    }


    @Test(expectedExceptions = {BuilderValidationException.class})
    public void createWithoutIso2() {
        logMethod("without-iso2", "Creating country without iso-2 code ...");

        new CountryBuilder()
                .withIso3("deu")
                .withPhoneCountryCode("49")
                .withPostalPrefix("D")
                .build();
    }


    @Test(expectedExceptions = {BuilderValidationException.class})
    public void createWithoutIso3() {
        logMethod("without-iso3", "Creating country without iso-3 code ...");

        new CountryBuilder()
                .withIso2("DE")
                .withPhoneCountryCode("49")
                .withPostalPrefix("D")
                .build();
    }


    @Test(expectedExceptions = {BuilderValidationException.class})
    public void createWithoutPhoneCountryCode() {
        logMethod("without-phoneCC", "Creating country without phone country code ...");

        new CountryBuilder()
                .withIso2("DE")
                .withIso3("deu")
                .withPostalPrefix("D")
                .build();
    }


    @Test(expectedExceptions = {BuilderValidationException.class})
    public void createWithoutPostalPrefix() {
        logMethod("without-postal", "Creating country without postal prefix code ...");

        new CountryBuilder()
                .withIso2("DE")
                .withIso3("deu")
                .withPhoneCountryCode("49")
                .build();
    }
}
