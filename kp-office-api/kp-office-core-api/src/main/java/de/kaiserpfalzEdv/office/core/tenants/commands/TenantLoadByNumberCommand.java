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

package de.kaiserpfalzEdv.office.core.tenants.commands;

import de.kaiserpfalzEdv.office.commons.notifications.Notification;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 21:38
 */
public class TenantLoadByNumberCommand implements TenantCommand {
    private static final long serialVersionUID = -2409773643752824377L;


    private String number;


    public TenantLoadByNumberCommand(final String number) {
        this.number = number;
    }


    public String getNumber() {
        return number;
    }


    @Override
    public Notification execute(TenantCommandExecutor executor) {
        return executor.execute(this);
    }

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
        TenantLoadByNumberCommand rhs = (TenantLoadByNumberCommand) obj;
        return new EqualsBuilder()
                .append(this.number, rhs.number)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(number)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(number)
                .toString();
    }
}
