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

import de.kaiserpfalzEdv.office.contacts.address.AddressType;
import de.kaiserpfalzEdv.office.contacts.address.AddressUsage;
import de.kaiserpfalzEdv.office.contacts.location.City;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
@Entity
@DiscriminatorValue("POSTBOX")
public class PostboxAddressDTO extends PostalAddressDTO implements PostboxAddress {
    private static final long serialVersionUID = 2370903484590437001L;

    private String postboxId;


    @SuppressWarnings("deprecation")
    @Deprecated // Only for Jackson, JAX-B and JPA!
    public PostboxAddressDTO() {
    }

    PostboxAddressDTO(@NotNull final UUID id,
                      @NotNull final String name,
                      @NotNull final String number,
                      @NotNull final City city,
                      @NotNull final PostCode postCode,
                      @NotNull final AddressType type,
                      @NotNull final AddressUsage usage,
                      @NotNull final String postboxId,
                      @NotNull final UUID tenantId) {
        super(id, name, number, city, postCode, type, usage, tenantId);

        setPostboxId(postboxId);
    }

    @Override
    public String getPostboxId() {
        return postboxId;
    }

    void setPostboxId(@NotNull final String postboxId) {
        this.postboxId = postboxId;
    }


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .appendSuper(super.toString())
                .append("postbox", postboxId)
                .build();
    }
}
