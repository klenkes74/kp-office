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

package de.kaiserpfalzEdv.office.contacts.location;

import de.kaiserpfalzEdv.office.commons.Entity;
import de.kaiserpfalzEdv.office.contacts.address.phone.CountryCode;

/**
 * @author klenkes
 * @since 2014Q
 */
public interface Country extends Entity {
    public String getIso2();

    public String getIso3();

    public String getPhoneCountryCode();

    public CountryCode getCountryCode();

    public String getPostalPrefix();
}
