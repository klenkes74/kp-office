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

package de.kaiserpfalzedv.office.geodata.api;

import java.util.Locale;

/**
 * The country code of the postal code database.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-08
 */
public enum Country {
    AT(new Locale("de", "AT"), "AUSTRIA"),
    BE(new Locale("fr", "BE"), "BELGIUM"),
    CH(new Locale("de", "CH"), "SWISS"),
    DE(Locale.GERMANY, "GERMANY"),
    DK(new Locale("dk", "DK"), "DENMARK"),
    FR(Locale.FRANCE, "FRANCE"),
    GB(Locale.UK, "UNITED KINGDOM"),
    LI(new Locale("de", "LI"), "LIECHTENSTEIN"),
    LU(new Locale("fr", "LU"), "LUXEMBOURG"),
    PL(new Locale("pl", "PL"), "POLAND");


    private Locale locale;
    private String postalName;

    /**
     * @param locale     The locale for this country.
     * @param postalName The name as given in an address field.
     */
    Country(final Locale locale, final String postalName) {
        this.locale = locale;
        this.postalName = postalName;
    }

    /**
     * @return The locale for the given country.
     */
    public Locale getLocale() {
        return locale;
    }

    /**
     * @return The name as given in an address field.
     */
    public String getPostalName() {
        return postalName;
    }
}
