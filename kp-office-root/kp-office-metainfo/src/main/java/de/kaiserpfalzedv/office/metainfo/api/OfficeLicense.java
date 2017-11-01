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

package de.kaiserpfalzedv.office.metainfo.api;

import java.io.Serializable;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Set;

import com.github.zafarkhaja.semver.Version;
import de.kaiserpfalzedv.office.common.api.data.Identifiable;
import de.kaiserpfalzedv.office.common.api.data.ValidityDuration;
import de.kaiserpfalzedv.office.common.api.data.VersionRange;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * The logical view on a license.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-15
 */
public interface OfficeLicense extends Identifiable, Serializable {
    /**
     * @return to whom the software has been licensed.
     */
    String getLicensee();

    /**
     * @return who licensed the software.
     */
    String getLicensor();

    /**
     * @return When the license has been given.
     */
    OffsetDateTime getCreated();

    /**
     * @return The validity range of the license.
     */
    ValidityDuration getDuration();

    /**
     * @return the valid versions for this license.
     */
    VersionRange getValidVersions();

    /**
     * @return the licensed options.
     */
    Set<String> getOptions();

    /**
     * Checks if there is a license for the option specified.
     *
     * @param option the option to be checked.
     *
     * @return TRUE, if the option is listed as licensed.
     */
    boolean isLicensed(String option);

    /**
     * @return TRUE, if the license is valid (for version and duration). A combination of {@link #isValidDuration()}
     * &amp;&amp; {@link #isValid(Version)}.
     */
    boolean isValid(Version version);

    /**
     * @return TRUE, if the license is currently valid.
     */
    boolean isValidDuration();

    boolean isValidDuration(Instant now);

    /**
     * @param version the version to be checked for this license.
     *
     * @return TRUE, if the given version is licensed.
     */
    boolean isValidVersion(Version version);


    default boolean defaultEquals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        if (!OfficeLicense.class.isAssignableFrom(obj.getClass())) {
            return false;
        }
        OfficeLicense rhs = (OfficeLicense) obj;
        return new EqualsBuilder()
                .append(getId(), rhs.getId())
                .isEquals();
    }

    default int defaultHashCode() {
        return new HashCodeBuilder()
                .append(getId())
                .toHashCode();
    }


    default String defaultToString() {
        return new ToStringBuilder(this)
                .append("id", getId())
                .append("licensee", getLicensee())
                .append("versions", getValidVersions())
                .append("duration", getDuration())
                .toString();
    }
}
