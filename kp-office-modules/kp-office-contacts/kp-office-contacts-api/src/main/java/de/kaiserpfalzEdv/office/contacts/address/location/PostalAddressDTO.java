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

import de.kaiserpfalzEdv.office.contacts.address.AddressType;
import de.kaiserpfalzEdv.office.contacts.address.AddressUsage;
import de.kaiserpfalzEdv.office.core.KPOEntityDTO;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public abstract class PostalAddressDTO extends KPOEntityDTO implements PostalAddress {
    private static final Logger LOG = LoggerFactory.getLogger(PostalAddressDTO.class);

    private City city;
    private PostCode postCode;

    private AddressType type;
    private AddressUsage usage;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B or JPA!
    public PostalAddressDTO() {
    }


    PostalAddressDTO(@NotNull final UUID id,
                     @NotNull final String name,
                     @NotNull final String number,
                     @NotNull final City city,
                     @NotNull final PostCode postCode,
                     @NotNull final AddressType type, @NotNull final AddressUsage usage) {
        super(id, name, number);

        setCity(city);
        setPostCode(postCode);
        setType(type);
        setUsage(usage);
    }


    @Override
    public PostCode getPostCode() {
        return postCode;
    }

    void setPostCode(final PostCode code) {
        this.postCode = code;
    }


    @Override
    public Country getCountry() {
        return postCode.getCountry();
    }


    @Override
    public City getCity() {
        return city;
    }

    void setCity(final City city) {
        this.city = city;
    }


    @Override
    public AddressType getType() {
        return type;
    }

    void setType(final AddressType type) {
        this.type = type;
    }


    @Override
    public AddressUsage getUsage() {
        return usage;
    }

    void setUsage(final AddressUsage usage) {
        this.usage = usage;
    }


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append(getCity())
                .append(getPostCode())
                .append(getType())
                .append(getUsage())
                .build();
    }
}
