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

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.kaiserpfalzEdv.office.core.KPOEntity;

/**
 * @author klenkes
 * @since 2014Q
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class", defaultImpl = CountryDTO.class)
public interface Country extends KPOEntity {
    String getIso2();

    String getIso3();

    String getPhoneCountryCode();

    String getPostalPrefix();
}