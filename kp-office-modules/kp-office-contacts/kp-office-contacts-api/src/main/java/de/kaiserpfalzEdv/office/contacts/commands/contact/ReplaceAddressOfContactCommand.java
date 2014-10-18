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

package de.kaiserpfalzEdv.office.contacts.commands.contact;

import de.kaiserpfalzEdv.office.contacts.address.Address;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public class ReplaceAddressOfContactCommand extends UpdateContactCommand {
    private static final long serialVersionUID = 1L;


    private UUID addressId;
    private Address address;


    @SuppressWarnings({"deprecation", "UnusedDeclaration"})
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public ReplaceAddressOfContactCommand() {
    }

    @SuppressWarnings("deprecation")
    public ReplaceAddressOfContactCommand(@NotNull final UUID id, @NotNull final UUID addressId, @NotNull final Address address) {
        super(id);

        setAddressId(addressId);
        setAddress(address);
    }


    public UUID getAddressId() {
        return addressId;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setAddressId(UUID addressId) {
        this.addressId = addressId;
    }

    public Address getAddress() {
        return address;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setAddress(Address address) {
        this.address = address;
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("addressId", addressId)
                .append(address)
                .toString();
    }
}
