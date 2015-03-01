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

package de.kaiserpfalzEdv.office.contacts.geodb;

/**
 * <p>This enum holds the type codes of the OpenGEODB data set. The I18N code to look for is assembled from the static
 * part <code>office.contacts.geodb.</code> and the <code>type_id</code> definied by OpenGEODB.</p>
 * <p>
 * <p>Don't calculate it by yourself. This definition may change later on and you are lost then. Use this enum to
 * retrieve the I18N key by calling {@link #getI18nCode()}.</p>
 *
 * @author klenkes
 * @version 2015Q1
 * @since 25.02.15 13:24
 */
public enum I18NCodes {
    COUNTRY(100200000),
    STATE(100300000),
    ADMINISTRATIVE_REGION(100400000),
    POLITICAL_DIVISION(100500000),
    TOWN(100600000),
    POST_CODE_AREA(100700000),
    DISTRICT(100800000),
    PART_OF(400100000),
    LEVEL(400200000),
    TYPE(400300000),
    NAME(500100000),
    SORTING_NAME(500100002),
    POST_CODE(500300000),
    PREFIX(500400000),
    LICENCE_PLATE_NUMBER(500500000),
    DISTRICT_ADMINISTRATION_CODE(500600000),
    ADMINISTRATIONAL_ASSOCIATION(500700000),
    POPULATION(600700000),
    AREA(610000000);

    private int    geoDbCode;
    private String i18nCode;

    private I18NCodes(int code) {
        geoDbCode = code;
        i18nCode = String.format("office.contacts.geodb.%i", geoDbCode);
    }

    /**
     * @return The internal db code defined by OpenGEODB.
     */
    public int getGeoDbCode() {
        return geoDbCode;
    }

    /**
     * @return The Kaiserpfalz Office I18N key to be looked up in the translations database.
     */
    public String getI18nCode() {
        return i18nCode;
    }
}
