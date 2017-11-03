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

package de.kaiserpfalz.licensing.impl;

import com.github.zafarkhaja.semver.Version;
import com.verhas.licensor.License;
import de.kaiserpfalzedv.commons.api.data.ValidityDuration;
import de.kaiserpfalzedv.commons.api.data.VersionRange;
import de.kaiserpfalzedv.commons.api.info.SoftwareLicense;
import de.kaiserpfalzedv.commons.api.licensing.ApplicationLicense;
import de.kaiserpfalzedv.commons.impl.license.LicenseBuilder;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

/**
 * Implementation of the OfficeLicense on basis of the License3j library.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-30
 */
public class ApplicationLicenseImpl implements ApplicationLicense {
    /**
     * Our software is distributed as Apache 2.0 licensed. So we can use it as base license.
     */
    private static final SoftwareLicense baseLicense = new LicenseBuilder().apache2().build();

    /**
     * The base license.
     */
    private License license;

    /**
     * The duration of validity for this license.
     */
    private ValidityDuration duration;

    /**
     * The versions this license is valid for.
     */
    private VersionRange range;


    ApplicationLicenseImpl(final License license, final ValidityDuration duration, final VersionRange versions) {
        this.license = license;
        this.duration = duration;
        this.range = versions;
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
        return license.getFeature("licensee");
    }

    @Override
    public String getLicensor() {
        return license.getFeature("issuer");
    }

    @Override
    public OffsetDateTime getCreated() {
        return OffsetDateTime.parse(license.getFeature("created"));
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
        HashSet<String> result = new HashSet<>();

        String features = license.getFeature("features");
        Collections.addAll(result, features.split(","));

        return result;
    }

    @Override
    public boolean isFeatureLicences(String option) {
        return license.getFeature(option) == "true";
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
        return UUID.fromString(license.getFeature("id"));
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
