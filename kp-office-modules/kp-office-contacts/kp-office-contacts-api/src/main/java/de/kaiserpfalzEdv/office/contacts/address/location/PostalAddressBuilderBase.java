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

import de.kaiserpfalzEdv.commons.ValidationException;
import de.kaiserpfalzEdv.office.contacts.address.AddressType;
import de.kaiserpfalzEdv.office.contacts.address.AddressUsage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public abstract class PostalAddressBuilderBase<T extends PostalAddress> {
    private static final Logger LOG = LoggerFactory.getLogger(PostalAddressBuilderBase.class);

    private UUID id;
    private String name;
    private String number;
    private City city;
    private PostCode postCode;
    private AddressType type;
    private AddressUsage usage;


    public void validate() throws ValidationException {
        HashSet<String> reasons = new HashSet<>();

        generateValidationErrorList(reasons);

        if (!reasons.isEmpty()) {
            throw new ValidationException("No valid data set for a postal address!", reasons);
        }
    }

    protected void generateValidationErrorList(@NotNull Collection<String> reasons) {
        if (city == null) reasons.add("No city for this address given!");
        if (postCode == null) reasons.add("No post code for this address given!");
    }


    public <X extends PostalAddressBuilderBase<T>> X withPostalAddress(@NotNull final PostalAddress original) {
        LOG.debug("Copying address: {}", original);

        withId(original.getId());
        withName(original.getDisplayName());
        withNumber(original.getDisplayNumber());

        withCity(original.getCity());
        withPostCode(original.getPostCode());
        withType(original.getType());
        withUsage(original.getUsage());

        return (X) this;
    }


    public <X extends PostalAddressBuilderBase<T>> X withId(final UUID id) {
        this.id = id;

        return (X) this;
    }


    public <X extends PostalAddressBuilderBase<T>> X withName(final String name) {
        this.name = name;

        return (X) this;
    }


    public <X extends PostalAddressBuilderBase<T>> X withNumber(final String number) {
        this.number = number;

        return (X) this;
    }


    public <X extends PostalAddressBuilderBase<T>> X withCity(final City city) {
        this.city = city;

        return (X) this;
    }


    public <X extends PostalAddressBuilderBase<T>> X withPostCode(final PostCode postCode) {
        this.postCode = postCode;

        return (X) this;
    }


    public <X extends PostalAddressBuilderBase<T>> X withType(final AddressType type) {
        this.type = type;

        return (X) this;
    }


    public <X extends PostalAddressBuilderBase<T>> X withUsage(final AddressUsage usage) {
        this.usage = usage;

        return (X) this;
    }


    UUID getId() {
        if (id == null) {
            id = UUID.randomUUID();

            LOG.debug("Generated uuid for postal address: {}", id.toString());
        }

        return id;
    }

    String getName() {
        if (isBlank(name)) {
            name = getId().toString();
        }

        return name;
    }

    String getNumber() {
        if (isBlank(number)) {
            number = getId().toString();
        }

        return number;
    }

    City getCity() {
        return city;
    }

    PostCode getPostCode() {
        return postCode;
    }

    AddressType getType() {
        if (type == null) {
            type = AddressType.NONE;
        }

        return type;
    }

    AddressUsage getUsage() {
        if (usage == null) {
            usage = AddressUsage.NONE;
        }

        return usage;
    }
}
