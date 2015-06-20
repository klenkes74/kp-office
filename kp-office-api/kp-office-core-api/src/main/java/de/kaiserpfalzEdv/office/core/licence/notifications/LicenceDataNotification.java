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

package de.kaiserpfalzEdv.office.core.licence.notifications;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonProperty;
import de.kaiserpfalzEdv.office.commons.notifications.Notification;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;
import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.NONE;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 22:13
 */
@JsonAutoDetect(fieldVisibility = NONE, getterVisibility = ANY, setterVisibility = NONE)
public class LicenceDataNotification implements Notification {
    private static final long serialVersionUID = 3287815572114343343L;


    private OfficeLicence licence;


    public LicenceDataNotification(@JsonProperty("licence") final OfficeLicence licence) {
        this.licence = licence;
    }


    @JsonProperty("licence")
    public OfficeLicence getLicence() {
        return licence;
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
        LicenceDataNotification rhs = (LicenceDataNotification) obj;
        return new EqualsBuilder()
                .append(this.licence, rhs.licence)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(licence)
                .toHashCode();
    }


    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append(licence)
                .toString();
    }
}
