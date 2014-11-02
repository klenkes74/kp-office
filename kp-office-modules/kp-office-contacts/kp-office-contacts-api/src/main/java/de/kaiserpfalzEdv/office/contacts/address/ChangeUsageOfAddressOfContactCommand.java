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

package de.kaiserpfalzEdv.office.contacts.address;

import de.kaiserpfalzEdv.office.contacts.commands.contact.UpdateContactCommand;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class ChangeUsageOfAddressOfContactCommand extends UpdateContactCommand {
    private static final long serialVersionUID = 1L;


    private UUID addressId;
    private AddressUsage addressUsage;


    @SuppressWarnings({"deprecation", "UnusedDeclaration"})
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public ChangeUsageOfAddressOfContactCommand() {
    }

    @SuppressWarnings("deprecation")
    public ChangeUsageOfAddressOfContactCommand(@NotNull final UUID id, @NotNull final UUID addressId, @NotNull final AddressUsage addressUsage) {
        super(id);

        setAddressId(addressId);
        setAddressUsage(addressUsage);
    }


    public UUID getAddressId() {
        return addressId;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setAddressId(@NotNull final UUID addressId) {
        this.addressId = addressId;
    }


    public AddressUsage getAddressUsage() {
        return addressUsage;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setAddressUsage(@NotNull final AddressUsage addressUsage) {
        this.addressUsage = addressUsage;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("addressId", addressId)
                .append(addressUsage)
                .toString();
    }
}
