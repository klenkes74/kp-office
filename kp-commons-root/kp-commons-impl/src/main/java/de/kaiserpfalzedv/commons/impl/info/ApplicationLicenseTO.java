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

package de.kaiserpfalzedv.commons.impl.info;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.github.zafarkhaja.semver.Version;
import de.kaiserpfalzedv.commons.api.data.types.ValidityDuration;
import de.kaiserpfalzedv.commons.api.data.types.VersionRange;
import de.kaiserpfalzedv.commons.api.info.SoftwareLicense;
import de.kaiserpfalzedv.commons.api.licensing.ApplicationLicense;
import de.kaiserpfalzedv.commons.impl.license.LicenseBuilder;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-30
 */
public class ApplicationLicenseTO implements ApplicationLicense {
    /**
     * Our software is distributed as Apache 2.0 licensed. So we can use it as base license.
     */
    private static final SoftwareLicense baseLicense = new LicenseBuilder().apache2().build();

    /**
     * The licensed features.
     */
    private final HashSet<String> features = new HashSet<>();
    /**
     * The id of the license.
     */
    private UUID id;
    /**
     * The licensee granted this license.
     */
    private String licensee;
    /**
     * The issuer of the license.
     */
    private String issuer;
    /**
     * Creation date of the license.
     */
    private OffsetDateTime created;
    /**
     * The duration of validity for this license.
     */
    private ValidityDuration duration;
    /**
     * The versions this license is valid for.
     */
    private VersionRange range;


    ApplicationLicenseTO(
            @NotNull final UUID id,
            @NotNull final String licensee,
            @NotNull final String issuer,
            @NotNull final OffsetDateTime created,
            @NotNull final ValidityDuration duration,
            @NotNull final VersionRange versions,
            @NotNull final Collection<String> features
    ) {
        this.id = id;
        this.licensee = licensee;
        this.issuer = issuer;
        this.created = created;
        this.duration = duration;
        this.range = versions;

        this.features.addAll(features);
    }

    /**
     * @deprecated Only for JAXB, Json, ...
     */
    @Deprecated
    public ApplicationLicenseTO() {
    }

    @Override
    public String getTitle() {
        return baseLicense.getTitle();
    }

    @Override
    public String getDisclaimer() {
        return baseLicense.getDisclaimer();
    }

    @Override
    public String getFullText() {
        return baseLicense.getFullText();
    }

    @Override
    public boolean isOpenSource() {
        return baseLicense.isOpenSource();
    }

    @Override
    public String getLicensee() {
        return licensee;
    }

    @Override
    public String getLicensor() {
        return issuer;
    }

    @Override
    public OffsetDateTime getCreated() {
        return created;
    }

    @Override
    public ValidityDuration getDuration() {
        return duration;
    }

    @Override
    public VersionRange getVersions() {
        return range;
    }

    @Override
    public Set<String> getFeatures() {
        return Collections.unmodifiableSet(features);
    }

    @Override
    public boolean isFeatureLicences(String option) {
        return features.contains(option);
    }

    @Override
    public boolean isValid(Version version) {
        return isValidVersion(version) && isValidDuration();
    }

    @Override
    public boolean isValidDuration() {
        return isValidDuration(Instant.now());
    }

    @Override
    public boolean isValidDuration(Instant now) {
        return duration.isValid(now);
    }

    @Override
    public boolean isValidVersion(Version version) {
        return range.isValid(version);
    }

    @Override
    public UUID getId() {
        return id;
    }

    @Override
    public int hashCode() {
        return defaultHashCode();
    }

    @Override
    public boolean equals(Object other) {
        return defaultEquals(other);
    }

    @Override
    public String toString() {
        return defaultToString();
    }
}
