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

package de.kaiserpfalzedv.office.contacts.api;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-10
 */
public enum OrganizationType {
    EK("E.K.", "%s E.K."),
    EWIV("E.W.I.V.", "%s EWIV"),
    EV("e.V.", "%s e.V."),
    GbR("GbR", "%s GbR"),
    OHG("OHG", "%s OHG"),
    GMBH_U_CO_OHG("GmbH & Co. OHG", "%s GmbH & Co. OHG"),
    AG_U_CO_OHG("AG & Co. OHG", "%s AG & Co OHG"),
    KG("KG", "%s KG"),
    GMBH_U_CO_KG("GmbH & Co. KG", "%s GmbH & Co. KG"),
    AG_U_CO_KG("AG & Co KG", "%s AG & Co. KG"),
    EG("e.G.", "%s e.G."),
    VVAG("V.V.a.G.", "%s V.V.a.G."),
    UG("UG", "%s UG"),
    GMBH("GmbH", "%s GmbH"),
    MBH("Gmbh", "%s mbH"),
    GGMBH("gGmbH", "%s gGmbH"),
    PARTG("PartG", "%s PartG"),
    KGAA("KGaA", "%s KGaA"),
    GMBH_U_CO_KGAA("GmbH & Co KGaA", "%s GmbH & Co. GaA"),
    AG_U_CO_KGAA("AG & Co KGaA", "%s AG & Co. KGaA"),
    AG("AG", "%s AG"),
    KDOeR("KdöR", "%s"),
    ADOeR("AdöR", "%s");

    private String display;
    private String format;

    OrganizationType(final String display, final String format) {
        this.display = display;
        this.format = format;
    }

    public String getDisplayName() {
        return display;
    }

    public String format(final String name) {
        return String.format(format, name);
    }
}
