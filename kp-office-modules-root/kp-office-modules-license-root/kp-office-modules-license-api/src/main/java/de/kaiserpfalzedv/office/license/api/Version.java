/*
 * Copyright 2017 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.license.api;

import java.io.Serializable;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import static org.apache.commons.lang3.StringUtils.isEmpty;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-15
 */
public class Version implements Serializable {
    private static final long serialVersionUID = 5898724799706228743L;


    private final Long[] version;
    private final String versionAddition;


    public Version(final Version version) {
        this.version = version.version;
        this.versionAddition = version.versionAddition;
    }

    public Version(final Long... version) {
        this.version = version;

        this.versionAddition = "";
    }

    public Version(final String versionAddition, final Long... version) {
        this.version = version;

        this.versionAddition = versionAddition;
    }

    public boolean isOlderAs(final Version other) {
        return !isNewerAs(other);
    }

    public boolean isNewerAs(final Version other) {
        for (int i = 0; i < version.length && i < other.version.length; i++) {
            if (version[i] > other.version[i]) {
                return true;
            } else if (version[i] < other.version[i]) {
                return false;
            }
        }

        if (other.version.length > version.length) {
            for (int i = version.length; i < other.version.length; i++) {
                if (other.version[i] > 0) {
                    return false;
                }
            }
        }

        return versionAddition.compareTo(other.versionAddition) >= 0;

    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder()
                .append(version)
                .append(versionAddition)
                .toHashCode();
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
        Version rhs = (Version) obj;
        return new EqualsBuilder()
                .append(this.version, rhs.version)
                .append(this.versionAddition, rhs.versionAddition)
                .isEquals();
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();

        for (Long l : version) {
            result.append(".").append(l);
        }

        if (!isEmpty(versionAddition)) {
            result.append("-").append(versionAddition);
        }

        return result.substring(1);
    }
}
