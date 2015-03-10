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

package de.kaiserpfalzEdv.office.cli.executor.results;

import de.kaiserpfalzEdv.office.cli.CliModuleInfo;
import de.kaiserpfalzEdv.office.cli.executor.impl.CliResultCode;
import de.kaiserpfalzEdv.office.commons.data.DisplayNameHolder;
import de.kaiserpfalzEdv.office.commons.data.DisplayNumberHolder;
import de.kaiserpfalzEdv.office.commons.data.IdentityHolder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 02.03.15 23:38
 */
public abstract class CliResult implements IdentityHolder, DisplayNumberHolder, DisplayNameHolder, Serializable {
    private static final long serialVersionUID = -1775469851141314493L;


    private UUID          id;
    private CliModuleInfo moduleInfo;

    private String        displayNumber;
    private String        displayName;
    private CliResultCode messageKey;


    public CliResult(final UUID id, final CliModuleInfo moduleInfo, final CliResultCode key, final String displayNumber, final String displayName) {
        this.id = id;
        this.moduleInfo = moduleInfo;
        this.messageKey = key;
        this.displayNumber = displayNumber;
        this.displayName = displayName;
    }

    public CliResult(CliModuleInfo moduleInfo, CliResultCode key) {
        this(UUID.randomUUID(), moduleInfo, key, key.getCode(), key.getMessage());
    }


    @Override
    public UUID getId() {
        return id;
    }

    public CliModuleInfo getModuleInfo() {
        return moduleInfo;
    }

    @Override
    public String getDisplayNumber() {
        return displayNumber;
    }

    @Override
    public String getDisplayName() {
        return displayName;
    }


    public CliResultCode getMessageKey() {
        return messageKey;
    }


    public abstract boolean isFailure();

    public abstract boolean isSuccess();


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (obj.getClass() != getClass()) {
            return false;
        }
        CliResult rhs = (CliResult) obj;
        return new EqualsBuilder()
                .append(this.id, rhs.id)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(id)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
                .append("@" + System.identityHashCode(this))
                .append(id)
                .append(messageKey)
                .append("displayNumber", displayNumber)
                .append("displayName", displayName)
                .toString();
    }
}
