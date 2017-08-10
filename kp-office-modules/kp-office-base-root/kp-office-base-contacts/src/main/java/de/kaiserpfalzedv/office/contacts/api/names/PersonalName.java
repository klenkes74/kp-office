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

import java.util.List;

/**
 * The name of the person.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-10
 */
public interface PersonalName extends Name {
    /**
     * @return The prefix to the full name.
     */
    String getNamePrefix();

    /**
     * @return The suffix of the full name.
     */
    String getNameSuffix();


    /**
     * @return The main given name of the person.
     */
    NamePart getGivenName();

    /**
     * @return additional given names of the person.
     */
    List<NamePart> getAdditionalGivenNames();

    /**
     * @return the surname of the person.
     */
    NamePart getSurName();

    /**
     * @return all honorific titles of the person.
     */
    List<HonorificTitles> getHonorificTitles();

    /**
     * @return all heraldic titles of the person.
     */
    List<HeraldicTitles> getHeraldicTitles();
}
