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

package de.kaiserpfalzEdv.office.core.licence;

import de.kaiserpfalzEdv.commons.service.VersionRange;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.UUID;

/**
 * The software licence as used within the licence management.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 14.02.15 15:56
 */
public interface OfficeLicense extends Serializable {
    /**
     * The UUID of the licence is generated by the issuer and is used to identify the licence.
     *
     * @return The unique ID of the licence.
     */
    UUID getId();

    /**
     * @return The date when the licence has been issued.
     */
    LocalDate getIssueDate();

    /**
     * Checks if the given licence is valid for the software in the given version.
     *
     * @param software The name of the software.
     * @param range    The version range to check against.
     *
     * @return If the licence is valid.
     */
    boolean isValid(final String software, final VersionRange range);

    String getIssuer();

    String getLicensee();

    LocalDate getStart();

    LocalDate getExpiry();

    String getSoftware();

    VersionRange getVersionRange();

    boolean containsFeature(final String feature);
}
