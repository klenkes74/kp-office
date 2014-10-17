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

package de.kaiserpfalzEdv.office.contacts.location;

import de.kaiserpfalzEdv.commons.BuilderValidationException;
import de.kaiserpfalzEdv.commons.test.CommonTestBase;
import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCode;
import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCodeDTO;
import de.kaiserpfalzEdv.office.contacts.address.postal.PostCode;
import de.kaiserpfalzEdv.office.contacts.address.postal.PostCodeDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.annotations.Test;

import java.util.HashSet;
import java.util.UUID;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Test
public class CityDTOTest extends CommonTestBase {
    private static final Logger LOG = LoggerFactory.getLogger(CityDTOTest.class);


    private static final Country COUNTRY = new CountryBuilder()
            .withIso2("DE")
            .withIso3("deu")
            .withPhoneCountryCode("49")
            .withPostalPrefix("D")
            .build();
    private static final HashSet<PostCode> POST_CODES = new HashSet<>();
    private static final HashSet<AreaCode> AREA_CODES = new HashSet<>();

    static {
        POST_CODES.add(new PostCodeDTO(UUID.randomUUID(), "D-12340", "12340", COUNTRY));
        POST_CODES.add(new PostCodeDTO(UUID.randomUUID(), "D-12341", "12341", COUNTRY));
        POST_CODES.add(new PostCodeDTO(UUID.randomUUID(), "D-12342", "12342", COUNTRY));
        POST_CODES.add(new PostCodeDTO(UUID.randomUUID(), "D-12343", "12343", COUNTRY));
        POST_CODES.add(new PostCodeDTO(UUID.randomUUID(), "D-12344", "12344", COUNTRY));
        POST_CODES.add(new PostCodeDTO(UUID.randomUUID(), "D-12345", "12345", COUNTRY));

        AREA_CODES.add(new AreaCodeDTO(UUID.randomUUID(), "+49-9190", "9190", COUNTRY.getCountryCode()));
        AREA_CODES.add(new AreaCodeDTO(UUID.randomUUID(), "+49-9179", "9179", COUNTRY.getCountryCode()));
    }

    public CityDTOTest() {
        super(CityDTOTest.class, LOG);
    }


    @Test(expectedExceptions = {BuilderValidationException.class})
    public void createWithoutData() {
        logMethod("no-data", "Creating city builder without data ...");

        new CityBuilder().build();
    }


    public void createMinimum() {
        logMethod("minimum-data", "Creating the city with minimal data set ...");

        City result = new CityBuilder()
                .withCountry(COUNTRY)
                .withName("Oebisfelde")
                .build();

        LOG.debug("Created: {}", result);

        assertNotNull(result.getId(), "The UUID should be not NULL!");
        assertEquals(result.getDisplayName(), "Oebisfelde");
        assertEquals(result.getDisplayNumber(), "DE-Oebisfelde");

        assertEquals(result.getCountry(), COUNTRY);
    }


    public void createBase() {
        logMethod("base-data", "Creating the city with base data set ...");

        City result = new CityBuilder()
                .withCountry(COUNTRY)
                .withName("Oebisfelde")
                .withNumber("DE-1")
                .build();

        LOG.debug("Created: {}", result);

        assertNotNull(result.getId(), "The UUID should be not NULL!");
        assertEquals(result.getDisplayName(), "Oebisfelde");
        assertEquals(result.getDisplayNumber(), "DE-1");

        assertEquals(result.getCountry(), COUNTRY);
    }


    public void createFull() {
        logMethod("base-data", "Creating the city with base data set ...");

        City result = new CityBuilder()
                .withCountry(COUNTRY)
                .withName("Oebisfelde")
                .withNumber("DE-1")
                .withPostCodes(POST_CODES)
                .withAreaCodes(AREA_CODES)
                .build();

        LOG.debug("Created: {}", result);

        assertNotNull(result.getId(), "The UUID should be not NULL!");
        assertEquals(result.getDisplayName(), "Oebisfelde");
        assertEquals(result.getDisplayNumber(), "DE-1");

        assertEquals(result.getCountry(), COUNTRY);
    }
}
