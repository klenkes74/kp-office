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

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import de.kaiserpfalzEdv.office.contacts.address.phone.AreaCode;
import de.kaiserpfalzEdv.office.contacts.address.postal.PostCode;
import de.kaiserpfalzEdv.office.core.KPOEntity;

import java.util.Collection;
import java.util.Set;

/**
 * @author klenkes
 * @since 2014Q
 */
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY, property = "@class", defaultImpl = CityDTO.class)
public interface City extends KPOEntity {
    Country getCountry();

    Set<PostCode> getPostCode();

    void setPostCodes(Collection<? extends PostCode> postCode);

    void addPostCode(PostCode postCode);

    void removePostCode(PostCode postCode);

    Set<AreaCode> getAreaCodes();

    void setAreaCodes(Collection<? extends AreaCode> areaCodes);

    void addAreaCode(AreaCode areaCode);

    void removeAreaCode(AreaCode areaCode);
}
