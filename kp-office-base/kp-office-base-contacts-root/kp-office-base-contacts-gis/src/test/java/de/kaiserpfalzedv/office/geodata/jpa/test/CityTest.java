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

package de.kaiserpfalzedv.office.geodata.jpa.test;

import de.kaiserpfalzedv.office.geodata.api.Country;
import de.kaiserpfalzedv.office.geodata.jpa.JPAAdministrativeEntity;
import de.kaiserpfalzedv.office.geodata.jpa.JPACity;
import de.kaiserpfalzedv.office.geodata.jpa.JPAPosition;
import de.kaiserpfalzedv.office.geodata.jpa.JPAPostalCode;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-08
 */
public class CityTest {
    private static final Logger LOG = LoggerFactory.getLogger(CityTest.class);

    private static final Long ID = 1L;
    private static final Long VERSION = 0L;

    private static final Country COUNTRY = Country.DE;
    private static final String POSTAL_CODE = "12345";
    private static final JPAPostalCode FULL_POSTAL_CODE = new JPAPostalCode(COUNTRY, POSTAL_CODE);

    private static final String CITY_NAME = "City";

    private static final String STATE_CODE = "123";
    private static final String STATE_NAME = "State";
    private static final JPAAdministrativeEntity STATE = new JPAAdministrativeEntity(STATE_CODE, STATE_NAME);

    private static final String PROVINCE_CODE = "456";
    private static final String PROVINCE_NAME = "Province";
    private static final JPAAdministrativeEntity PROVINCE = new JPAAdministrativeEntity(PROVINCE_CODE, PROVINCE_NAME);

    private static final String COMMUNITY_CODE = "789";
    private static final String COMMUNITY_NAME = "Community";
    private static final JPAAdministrativeEntity COMMUNITY = new JPAAdministrativeEntity(COMMUNITY_CODE, COMMUNITY_NAME);

    private static final Double LONGITUDE = 30.0000;
    private static final Double LATITUDE = 30.000;
    private static final int ACCURACY = 1;
    private static final JPAPosition POSITION = new JPAPosition(LONGITUDE, LATITUDE, ACCURACY);


    private JPACity cut;

    @BeforeClass
    public static void setMDC() {
        MDC.put("test-class", "city-test");
    }

    @AfterClass
    public static void removeMDC() {
        MDC.remove("test-class");
        MDC.remove("test");
    }

    @Test
    public void checkCity() {
        MDC.put("test", "check-city-id");
        LOG.trace("Checking city data");

        assertEquals("Wrong country!", COUNTRY, cut.getCountry());
        assertEquals("Wrong postal code!", POSTAL_CODE, cut.getCode());
        assertEquals("Wrong city name!", CITY_NAME, cut.getName());
    }

    @Test
    public void checkPosition() {
        MDC.put("test", "check-position");
        LOG.trace("Checking city position data");

        assertEquals("Wrong longitude!", LONGITUDE, cut.getPosition().getLongitude());
        assertEquals("Worng latitude!", LATITUDE, cut.getPosition().getLatitude());
        assertEquals("Wrong accuracy!", ACCURACY, cut.getPosition().getAccuracy());
    }

    @Test
    public void checkState() {
        MDC.put("test", "check-state");
        LOG.trace("Checking city state data");

        assertEquals("Wrong state code!", STATE_CODE, cut.getState().getCode());
        assertEquals("Wrong state name!", STATE_NAME, cut.getState().getName());
    }

    @Test
    public void checkProvince() {
        MDC.put("test", "check-province");
        LOG.trace("Checking city province data");

        assertEquals("Wrong province code!", PROVINCE_CODE, cut.getProvince().getCode());
        assertEquals("Wrong province name!", PROVINCE_NAME, cut.getProvince().getName());
    }

    @Test
    public void checkCommunity() {
        MDC.put("test", "check-community");
        LOG.trace("Checking city communite data");

        assertEquals("Wrong community code!", COMMUNITY_CODE, cut.getCommunity().getCode());
        assertEquals("Wrong community name!", COMMUNITY_NAME, cut.getCommunity().getName());
    }

    @Test
    public void checkHashCode() {
        MDC.put("test", "check-hashcode");
        LOG.trace("Checking the hashcode calculation");

        assertEquals("Wrong hash code!", 1754510284L, cut.hashCode());
    }

    @Test
    public void checkToStringWithMinimumData() {
        MDC.put("test", "to-string-with-minimum-data");
        LOG.trace("Checking the toString() result with minimum data");

        cut = new JPACity(ID, VERSION, FULL_POSTAL_CODE, CITY_NAME, null, null, null, null);

        String expected = new StringBuilder()
                .append("JPACity[").append(System.identityHashCode(cut))
                .append(",id=").append(ID)
                .append(",version=").append(VERSION)
                .append(",").append(FULL_POSTAL_CODE.toString())
                .append(",name=").append(CITY_NAME)
                .append("]").toString();

        String result = cut.toString();
        LOG.debug("Result: {}", result);

        assertEquals("Wrong to-string representation!", expected, result);
    }

    @Test
    public void checkToStringWithFullData() {
        MDC.put("test", "to-string-full-data");
        LOG.trace("Checking the toString() result");

        String expected = new StringBuilder()
                .append("JPACity[").append(System.identityHashCode(cut))
                .append(",id=").append(ID)
                .append(",version=").append(VERSION)
                .append(",").append(FULL_POSTAL_CODE.toString())
                .append(",name=").append(CITY_NAME)
                .append(",").append(POSITION.toString())
                .append(",state=").append(STATE.toString())
                .append(",province=").append(PROVINCE.toString())
                .append(",community=").append(COMMUNITY.toString())
                .append("]").toString();

        String result = cut.toString();
        LOG.debug("Result: {}", result);

        assertEquals("Wrong to-string representation!", expected, result);
    }

    @Test
    public void checkToStringWithoutAdministrativeEntries() {
        MDC.put("test", "to-string-without-administrative-data");
        LOG.trace("Checking the toString() result without administrative data");

        cut = new JPACity(ID, VERSION, FULL_POSTAL_CODE, CITY_NAME, null, null, null, POSITION);

        String expected = new StringBuilder()
                .append("JPACity[").append(System.identityHashCode(cut))
                .append(",id=").append(ID)
                .append(",version=").append(VERSION)
                .append(",").append(FULL_POSTAL_CODE.toString())
                .append(",name=").append(CITY_NAME)
                .append(",").append(POSITION.toString())
                .append("]").toString();

        String result = cut.toString();
        LOG.debug("Result: {}", result);

        assertEquals("Wrong to-string representation!", expected, result);
    }

    @Test
    public void checkToStringWithStateData() {
        MDC.put("test", "to-string-state-data");
        LOG.trace("Checking the toString() result with state data");

        cut = new JPACity(ID, VERSION, FULL_POSTAL_CODE, CITY_NAME, STATE, null, null, POSITION);

        String expected = new StringBuilder()
                .append("JPACity[").append(System.identityHashCode(cut))
                .append(",id=").append(ID)
                .append(",version=").append(VERSION)
                .append(",").append(FULL_POSTAL_CODE.toString())
                .append(",name=").append(CITY_NAME)
                .append(",").append(POSITION.toString())
                .append(",state=").append(STATE.toString())
                .append("]").toString();

        String result = cut.toString();
        LOG.debug("Result: {}", result);

        assertEquals("Wrong to-string representation!", expected, result);
    }

    @Test
    public void checkToStringWithStateAndProvinceData() {
        MDC.put("test", "to-string-state-and-province-data");
        LOG.trace("Checking the toString() result with state and province data");

        cut = new JPACity(ID, VERSION, FULL_POSTAL_CODE, CITY_NAME, STATE, PROVINCE, null, POSITION);

        String expected = new StringBuilder()
                .append("JPACity[").append(System.identityHashCode(cut))
                .append(",id=").append(ID)
                .append(",version=").append(VERSION)
                .append(",").append(FULL_POSTAL_CODE.toString())
                .append(",name=").append(CITY_NAME)
                .append(",").append(POSITION.toString())
                .append(",state=").append(STATE.toString())
                .append(",province=").append(PROVINCE.toString())
                .append("]").toString();

        String result = cut.toString();
        LOG.debug("Result: {}", result);

        assertEquals("Wrong to-string representation!", expected, result);
    }

    @SuppressWarnings("EqualsWithItself")
    @Test
    public void checkEqualsWithSameObject() {
        MDC.put("test", "equals-same");
        LOG.trace("Checking equals() with same object");

        assertTrue(cut.equals(cut));
    }

    @SuppressWarnings("ObjectEqualsNull")
    @Test
    public void checkEqualsWithNull() {
        MDC.put("test", "equals-null");
        LOG.trace("Checking equals() with NULL");

        assertFalse(cut.equals(null));
    }

    @SuppressWarnings("EqualsBetweenInconvertibleTypes")
    @Test
    public void checkEqualsWithNonAssignableClass() {
        MDC.put("test", "equals-wrong-type");
        LOG.trace("Checking equals() with wrong type");

        assertFalse(cut.equals(this));
    }

    @Test
    public void checkEqualsWithSameId() {
        MDC.put("test", "equals-same-id");
        LOG.trace("Checking equals() with same JPAPostalCode");

        JPACity second = new JPACity(ID, VERSION, FULL_POSTAL_CODE, CITY_NAME, null, null, null, null);

        assertTrue(cut.equals(second));
    }

    @Test
    public void checkCityIdEqualsWithSame() {
        MDC.put("test", "city-id-equals-same");
        LOG.trace("Checking JPAPostalCode.equals() with same object");

        assertTrue(FULL_POSTAL_CODE.equals(FULL_POSTAL_CODE));
    }

    @Test
    public void checkCityIdEqualsWithNull() {
        MDC.put("test", "city-id-equals-with-null");
        LOG.trace("Checking JPAPostalCode.equals() with null");

        assertFalse(FULL_POSTAL_CODE.equals(null));
    }

    @Test
    public void checkCityIdEqualsWithWrongType() {
        MDC.put("test", "city-id-equals-with-wrong-type");
        LOG.trace("Checking JPAPostalCode.equals() with wrong type");

        assertFalse(FULL_POSTAL_CODE.equals(this));
    }

    @Test
    public void checkCityIdEqualsWithIdenticalObjet() {
        MDC.put("test", "city-id-equals-with-identical");
        LOG.trace("Checking JPAPostalCode.equals() with identical object");

        JPAPostalCode second = new JPAPostalCode(COUNTRY, POSTAL_CODE);

        assertTrue(FULL_POSTAL_CODE.equals(second));
    }


    @Before
    public void setUp() {
        cut = new JPACity(ID, VERSION, FULL_POSTAL_CODE, CITY_NAME, STATE, PROVINCE, COMMUNITY, POSITION);
    }

    @After
    public void tearDown() {
        MDC.remove("test");
    }
}
