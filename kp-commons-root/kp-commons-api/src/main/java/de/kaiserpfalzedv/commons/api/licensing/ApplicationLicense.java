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

package de.kaiserpfalzedv.commons.api.licensing;

import com.github.zafarkhaja.semver.Version;
import de.kaiserpfalzedv.commons.api.data.Identifiable;
import de.kaiserpfalzedv.commons.api.info.SoftwareLicense;

import java.io.Serializable;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Set;

/**
 * The logical view on a license.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-15
 */
public interface ApplicationLicense extends Identifiable, SoftwareLicense, Serializable {
    /**
     * Default implementation to return the UUID of the license as key.
     *
     * @return the UUID of the license as key.
     */
    default String getKey() {
        return getId().toString();
    }

    /**
     * @return who licensed the software.
     */
    String getLicensor();

    /**
     * @return When the license has been given.
     */
    OffsetDateTime getCreated();

    /**
     * @return the licensed options.
     */
    Set<String> getFeatures();

    /**
     * Checks if there is a license for the option specified.
     *
     * @param option the option to be checked.
     *
     * @return TRUE, if the option is listed as licensed.
     */
    boolean isFeatureLicences(String option);

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
}
