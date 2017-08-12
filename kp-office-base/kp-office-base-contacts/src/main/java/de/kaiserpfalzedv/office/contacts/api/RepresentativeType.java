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

import de.kaiserpfalzedv.office.contacts.api.names.Title;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-10
 */
public enum RepresentativeType implements Title {
    GES_VERTRETER("Gesetzl. Vertreter", "Gesetzl. Vertreterin", "Gesetzl. Vertretung"),
    INHABER("Inhaber", "Inhaberin", "Inhaber"),
    GESCHAEFTSFUEHRER("Geschäftsführer", "Geschäftsführerin", "Geschäftsführung"),
    VORSTAND("Vorstand", "Vorständin", "Vorstehend"),
    VORSTANDSSPRECHER("Vorstandssprecher", "Vorstandssprecherin", "Vorstandssprecher");

    private String male;
    private String female;
    private String neutral;

    RepresentativeType(final String male, final String female, final String neutral) {
        this.male = male;
        this.female = female;
        this.neutral = neutral;
    }

    @Override
    public String getFormat(final Gender gender) {
        return format(gender, male, female, neutral);
    }
}
