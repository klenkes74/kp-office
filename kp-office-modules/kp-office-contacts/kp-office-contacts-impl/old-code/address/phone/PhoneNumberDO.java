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

import de.kaiserpfalzEdv.office.contacts.address.AddressDO;
import de.kaiserpfalzEdv.office.contacts.address.AddressType;
import de.kaiserpfalzEdv.office.contacts.address.AddressUsage;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
@Entity
@Table(name = "PHONES", schema = "CONTACTS")
@DiscriminatorValue("PHONE")
public class PhoneNumberDO extends AddressDO implements PhoneNumber {
    private static final long serialVersionUID = 3033773346196798937L;

    private AreaCodeDO areaCode;

    private NumberPartDO subscriberNumber;
    private NumberPartDO extension;

    private PhoneNumberType kind;

    @SuppressWarnings({"deprecation", "UnusedDeclaration"})
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public PhoneNumberDO() {
    }

    PhoneNumberDO(@NotNull final UUID id,
                  @NotNull final AreaCode areaCode,
                  @NotNull final SubscriberNumber subscriberNumber,
                  final Extension extension,
                  @NotNull final PhoneNumberType kind,
                  @NotNull final AddressType type,
                  @NotNull final AddressUsage usage,
                  @NotNull final UUID tenantId) {
        super(id, formatNumber(areaCode, subscriberNumber), formatNumber(areaCode, subscriberNumber), type, usage, tenantId);

        setExtension(extension);
        setKind(kind);
    }

    private static String formatNumber(@NotNull final AreaCode areaCode, @NotNull final SubscriberNumber subscriberNumber) {
        return areaCode.getCode() + "-" + subscriberNumber.getDisplayNumber();
    }


    @Override
    public CountryCode getCountryCode() {
        return areaCode.getCountryCode();
    }

    @Override
    public AreaCode getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(@NotNull final AreaCode areaCode) {
        this.areaCode = (AreaCodeDO) areaCode;

        updateDisplayNumber();
    }


    @Override
    public SubscriberNumber getSubscriberNumber() {
        return subscriberNumber;
    }

    public void setSubscriberNumber(@NotNull final SubscriberNumber subscriberNumber) {
        this.subscriberNumber = (NumberPartDO) subscriberNumber;

        updateDisplayNumber();
    }

    @Override
    public Extension getExtension() {
        return extension;
    }

    public void setExtension(@NotNull final Extension extension) {
        this.extension = (NumberPartDO) extension;

        updateDisplayNumber();
    }


    @SuppressWarnings("deprecation")
    private void updateDisplayNumber() {
        setDisplayNumber(formatNumber(areaCode, subscriberNumber) + (extension != null ? "-" + extension.getDisplayNumber() : ""));
        setDisplayName(getDisplayNumber());
    }


    public PhoneNumberType getKind() {
        return kind;
    }

    public void setKind(@NotNull final PhoneNumberType kind) {
        this.kind = kind;
    }
}
