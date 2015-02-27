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

package de.kaiserpfalzEdv.office.contacts.address.postal;

import de.kaiserpfalzEdv.commons.util.BuilderValidationException;
import de.kaiserpfalzEdv.office.contacts.address.AddressType;
import de.kaiserpfalzEdv.office.contacts.address.AddressUsage;
import de.kaiserpfalzEdv.office.contacts.location.City;
import de.kaiserpfalzEdv.office.core.tenants.NullTenant;
import de.kaiserpfalzEdv.office.core.tenants.impl.TenantDO;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class PostalAddressBuilder implements Builder<PostalAddress> {
    private static final Logger LOG = LoggerFactory.getLogger(PostalAddressBuilder.class);

    private UUID id;
    private String name;
    private String number;
    private UUID tenantId;

    private City city;
    private PostCode postCode;
    private AddressType type;
    private AddressUsage usage;


    private String postBox;

    private String co;
    private String street;
    private String houseNumber;
    private String houseNumberAdd;


    @Override
    public PostalAddress build() {
        setDefaults();
        validate();

        PostalAddress result;

        if (isNotBlank(postBox)) {
            result = new PostboxAddressDTO(getId(), getName(), getNumber(), getCity(), getPostCode(), getType(), getUsage(),
                    getPostBox(), getTenantId());
        } else {
            result = new StreetAddressDTO(getId(), getName(), getNumber(), getCity(), getPostCode(), getType(), getUsage(),
                    getCo(), getStreet(), getHouseNumber(), getHouseNumberAdd(), getTenantId());
        }

        LOG.trace("Created via {}: {}", this, result);
        return result;
    }


    protected void setDefaults() {
        if (id == null)
            id = UUID.randomUUID();

        if (tenantId == null)
            id = NullTenant.INSTANCE.getId();

        if (name == null)
            name = number;

        if (number == null)
            number = name = id.toString();
    }

    public void validate() {
        HashSet<String> reasons = new HashSet<>();

        generateValidationErrorList(reasons);

        if (!reasons.isEmpty()) {
            throw new BuilderValidationException("Can't build street address!", reasons);
        }
    }

    protected void generateValidationErrorList(@NotNull Collection<String> reasons) {
        if (isBlank(street) && isBlank(postBox))
            reasons.add("Either a street or a post box identifier is needed for a postal address!");

        if (isNotBlank(street) && isNotBlank(postBox))
            reasons.add("Can't give postbox and street when building a postal address. Choose one.");
    }


    public PostalAddressBuilder withPostalAddress(@NotNull final PostalAddress original) {
        LOG.debug("Copying address: {}", original);

        withId(original.getId());
        withName(original.getDisplayName());
        withNumber(original.getDisplayNumber());

        withCity(original.getCity());
        withPostCode(original.getPostCode());
        withType(original.getType());
        withUsage(original.getUsage());

        return this;
    }


    String getCo() {
        return co;
    }

    public PostalAddressBuilder withContactOver(String co) {
        this.co = co;

        return this;
    }

    String getStreet() {
        return street;
    }

    public PostalAddressBuilder withStreet(String street) {
        this.street = street;

        if (isNotBlank(postBox)) {
            this.postBox = null;
            LOG.debug("Set street to postal address. Removed postbox!");
        }

        return this;
    }

    String getHouseNumber() {
        return houseNumber;
    }

    public PostalAddressBuilder withHouseNumber(String houseNumber) {
        this.houseNumber = houseNumber;

        return this;
    }

    String getHouseNumberAdd() {
        return houseNumberAdd;
    }

    public PostalAddressBuilder withHouseNumberAdd(String houseNumberAdd) {
        this.houseNumberAdd = houseNumberAdd;

        return this;
    }


    String getPostBox() {
        return postBox;
    }

    public PostalAddressBuilder withPostBox(String postBox) {
        this.postBox = postBox;

        if (isNotBlank(street)) {
            co = null;
            street = null;
            houseNumber = null;
            houseNumberAdd = null;

            LOG.debug("Set post box to postal address. Removed street data!");
        }

        return this;
    }


    public PostalAddressBuilder withId(final UUID id) {
        this.id = id;

        return this;
    }


    public PostalAddressBuilder withName(final String name) {
        this.name = name;

        return this;
    }


    public PostalAddressBuilder withNumber(final String number) {
        this.number = number;

        return this;
    }


    public PostalAddressBuilder withTenant(final UUID tenantId) {
        this.tenantId = tenantId;

        return this;
    }


    public PostalAddressBuilder withTenant(final TenantDO tenant) {
        this.tenantId = tenant.getId();

        return this;
    }


    public PostalAddressBuilder withCity(final City city) {
        this.city = city;

        return this;
    }


    public PostalAddressBuilder withPostCode(final PostCode postCode) {
        this.postCode = postCode;

        return this;
    }


    public PostalAddressBuilder withType(final AddressType type) {
        this.type = type;

        return this;
    }


    public PostalAddressBuilder withUsage(final AddressUsage usage) {
        this.usage = usage;

        return this;
    }


    UUID getId() {
        return id;
    }

    String getName() {
        return name;
    }

    String getNumber() {
        return number;
    }

    UUID getTenantId() {
        return tenantId;
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
