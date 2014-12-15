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

package de.kaiserpfalzEdv.office.contacts.address.phone;

import de.kaiserpfalzEdv.office.contacts.commands.contact.UpdateContactCommand;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class ChangeKindOfPhoneNumberOfContactCommand extends UpdateContactCommand {
    private static final long serialVersionUID = 1L;


    private UUID phoneNumberId;
    private PhoneNumberType phoneNumberType;


    @SuppressWarnings({"deprecation", "UnusedDeclaration"})
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public ChangeKindOfPhoneNumberOfContactCommand() {
    }

    @SuppressWarnings("deprecation")
    public ChangeKindOfPhoneNumberOfContactCommand(@NotNull final UUID id, @NotNull final UUID phoneNumberId, @NotNull final PhoneNumberType phoneNumberType) {
        super(id);

        setPhoneNumberId(phoneNumberId);
        setPhoneNumberType(phoneNumberType);
    }


    public UUID getPhoneNumberId() {
        return phoneNumberId;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setPhoneNumberId(@NotNull final UUID phoneNumberId) {
        this.phoneNumberId = phoneNumberId;
    }


    public PhoneNumberType getPhoneNumberType() {
        return phoneNumberType;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setPhoneNumberType(@NotNull final PhoneNumberType phoneNumberType) {
        this.phoneNumberType = phoneNumberType;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("phoneNumberId", phoneNumberId)
                .append(phoneNumberType)
                .toString();
    }
}