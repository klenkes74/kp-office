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

package de.kaiserpfalzedv.office.contacts.api.names;

import de.kaiserpfalzedv.office.contacts.api.Gender;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-10
 */
public enum HeraldicTitles implements Title {
    KAISER("Kaiser", "Kaiserin"),
    ZAR("Zar", "Zarin"),
    KOENIG("König", "Königin"),
    ERZHERZOG("Erzherzog", "Erzherzogin"),
    GROSSHERZOG("Großherzog", "Großherzogin"),
    KURFUERST("Kurfürst", "Kurfürstin"),
    HERZOG("Herzog", "Herzog"),
    LANDGRAF("Landgraf", "Landgräfin"),
    PFALZGRAF("Pfalzgraf", "Pfalzgräfin"),
    MARKGRAF("Markgraf", "Markgraf"),
    FUERST("Fürst", "Fürstin"),
    GRAF("Graf", "Gräfin"),
    FREIHERR("Freiherr", "Freifrau"),
    BARON("Baron", "Baronin"),
    RITTER("Ritter", "Ritter"),
    EDLER("Edler", "Edle"),
    JUNKER("Junker", "Junkfrau"),
    LANDMANN("Landmann", "Frau"),

    BURGGRAAF("Burggraf", "Burggräfin"),
    VICOMTE("Burggraf", "Burggräfin"),
    GRAAF("Graf", "Gräfin"),
    COMTE("Graf", "Gräfin"),
    MARKIES("Marquis", "Marquis"),
    MARQUIS("Marquis", "Marquis"),
    PRINS("Prinz", "Prinzessin"),
    PRINCE("Prinz", "Prinzessin"),
    HERTOG("Herzog", "Herzogin"),
    DUC("Herzog", "Herzogin"),

    JONKHEER("Junker", "Junkfrau"),
    RIDDER("Ritter", "Ritter"),;

    private String male;
    private String female;
    private String neutral;

    HeraldicTitles(final String male, final String female, final String neutral) {
        this.male = male;
        this.female = female;
        this.neutral = neutral;
    }


    HeraldicTitles(final String male, final String female) {
        this.male = male;
        this.female = female;
        this.neutral = male;
    }

    @Override
    public String getFormat(final Gender gender) {
        return format(gender, male, female, neutral);
    }
}
