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

import de.kaiserpfalzEdv.office.commons.DisplayNumberHolder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * @author klenkes
 * @since 2014Q
 */
@Embeddable
public class NumberPartDO implements DisplayNumberHolder, SubscriberNumber, Extension {
    @Column(length = 20, name="NUMBER_")
    private String number;


    @Deprecated // Only for Jackson, JAX-B and JPA!
    public NumberPartDO() {
    }

    @SuppressWarnings("deprecation")
    public NumberPartDO(final String number) {
        setNumber(number);
    }


    @Deprecated // Only for Jackson, JAX-B and JPA!
    public String getNumber() {
        return number;
    }

    @Deprecated // Only for Jackson, JAX-B and JPA!
    public void setNumber(String number) {
        this.number = number;
    }


    @Override
    public String getDisplayNumber() {
        return number;
    }


    @SuppressWarnings("deprecation")
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
        NumberPartDO rhs = (NumberPartDO) obj;
        return new EqualsBuilder()
                .append(this.getNumber(), rhs.getNumber())
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(number)
                .toHashCode();
    }


    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE).append(number).build();
    }
}
