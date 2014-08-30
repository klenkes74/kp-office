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

package de.kaiserpfalzEdv.office.tenant;

import java.util.UUID;

import static com.google.common.base.Preconditions.checkArgument;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class TenantDTO implements Tenant {
    private UUID id;

    private String displayNumber;
    private String displayName;


    /**
     * @deprecated Only for JPA!
     */
    @Deprecated
    protected TenantDTO() {}


    /**
     * A copy-constructor.
     * @param orig The original tenant to be copied.
     */
    public TenantDTO(final Tenant orig) {
        checkArgument(orig != null, "An original tenant to copy is needed!");

        setId(orig.getId());
        setDisplayNumber(orig.getDisplayNumber());
        setDisplayName(orig.getDisplayName());
    }


    public TenantDTO(final String number, final String name) {
        checkArgument(isNotBlank(number), "The tenant has to have a unique code!");
        checkArgument(isNotBlank(name), "The tenant has to have a unique name!");

        setId(UUID.randomUUID());
        setDisplayNumber(number);
        setDisplayName(name);
    }


    @Override
    public UUID getId() {
        return id;
    }

    private void setId(final UUID id) {
        checkArgument(id != null, "Can't unset the id!");

        this.id = id;
    }

    @Override
    public String getDisplayNumber() {
        return displayNumber;
    }

    private void setDisplayNumber(final String displayNumber) {
        this.displayNumber = displayNumber;
    }


    @Override
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
}
