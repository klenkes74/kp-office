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

package de.kaiserpfalzEdv.office.contacts.address.phone;

import de.kaiserpfalzEdv.commons.BuilderValidationException;
import de.kaiserpfalzEdv.office.contacts.address.AddressType;
import de.kaiserpfalzEdv.office.contacts.address.AddressUsage;
import de.kaiserpfalzEdv.office.tenants.NullTenant;
import de.kaiserpfalzEdv.office.tenants.TenantDO;
import org.apache.commons.lang3.builder.Builder;

import javax.validation.constraints.NotNull;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class PhoneNumberBuilder implements Builder<PhoneNumber> {
    private UUID id;
    private UUID tenantId;

    private AreaCode areaCode;
    private SubscriberNumber subscriberNumber;
    private Extension extension;

    private PhoneNumberType kind;
    private AddressType type;
    private AddressUsage usage;


    public PhoneNumber build() {
        setDefaults();
        validate();

        return new PhoneNumberDO(id, areaCode, subscriberNumber, extension, kind, type, usage, tenantId);
    }


    public void validate() {
        HashSet<String> reasons = new HashSet<>();

        generateValidationErrorList(reasons);

        if (!reasons.isEmpty()) {
            throw new BuilderValidationException("Can't build street address!", reasons);
        }
    }


    protected void setDefaults() {
        if (id == null) id = UUID.randomUUID();
        if (tenantId == null) tenantId = NullTenant.INSTANCE.getId();

        if (kind == null) kind = PhoneNumberType.TELEPHONE;
        if (type == null) type = AddressType.DEFAULT;
        if (usage == null) usage = AddressUsage.DEFAULT;
    }


    protected void generateValidationErrorList(@NotNull Collection<String> reasons) {
        if (areaCode == null)
            reasons.add("Can't create a phone number without an area code!");

        if (subscriberNumber == null)
            reasons.add("Can't create a phone number without a subscriber number!");
    }


    public PhoneNumberBuilder withPhoneNumber(@NotNull final PhoneNumber orig) {
        withId(orig.getId());
        withAreaCode(orig.getAreaCode());
        withSubscriberNumber(orig.getSubscriberNumber());
        withExtension(orig.getExtension());
        withKind(orig.getKind());
        withType(orig.getType());
        withUsage(orig.getUsage());

        return this;
    }


    public UUID getId() {
        return id;
    }

    public PhoneNumberBuilder withId(@NotNull final UUID id) {
        this.id = id;

        return this;
    }

    public PhoneNumberBuilder clearId() {
        this.id = null;

        return this;
    }


    public UUID getTenantId() {
        return tenantId;
    }

    public PhoneNumberBuilder withTenant(@NotNull final UUID tenantId) {
        this.tenantId = tenantId;

        return this;
    }

    public PhoneNumberBuilder withTenant(@NotNull final TenantDO tenant) {
        this.tenantId = tenant.getId();

        return this;
    }


    public PhoneNumberBuilder withAreaCode(@NotNull final AreaCode areaCode) {
        this.areaCode = areaCode;

        return this;
    }


    public PhoneNumberBuilder withSubscriberNumber(@NotNull final SubscriberNumber subscriberNumber) {
        this.subscriberNumber = subscriberNumber;

        return this;
    }


    public PhoneNumberBuilder withExtension(@NotNull final Extension extension) {
        this.extension = extension;

        return this;
    }


    public PhoneNumberBuilder withKind(@NotNull final PhoneNumberType kind) {
        this.kind = kind;

        return this;
    }


    public PhoneNumberBuilder withType(@NotNull final AddressType type) {
        this.type = type;

        return this;
    }


    public PhoneNumberBuilder withUsage(@NotNull final AddressUsage usage) {
        this.usage = usage;

        return this;
    }
}
