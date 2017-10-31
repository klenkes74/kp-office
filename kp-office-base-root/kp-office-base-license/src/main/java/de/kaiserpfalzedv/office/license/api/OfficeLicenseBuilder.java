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

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.common.api.BuilderException;
import de.kaiserpfalzedv.office.common.api.data.ValidityDuration;
import de.kaiserpfalzedv.office.common.api.data.VersionRange;
import org.apache.commons.lang3.builder.Builder;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * This builder generates the {@link OfficeLicenseTO transport implementation} of the {@link OfficeLicense}.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @see OfficeLicenseTO
 * @see de.kaiserpfalzedv.office.license.impl.OfficeLicenseImpl
 * @since 2017-10-30
 */
public class OfficeLicenseBuilder implements Builder<OfficeLicense> {
    private final HashSet<String> features = new HashSet<>();
    private UUID id;
    private String licensee;
    private String issuer;
    private OffsetDateTime created;
    private ValidityDuration duration;
    private VersionRange versions;

    @Override
    public OfficeLicense build() {
        setDefaultValues();
        validate();

        return new OfficeLicenseTO(
                id, licensee,
                issuer, created, duration,
                versions, features
        );
    }

    private void setDefaultValues() {
        if (id == null) {
            id = UUID.randomUUID();
        }

        if (isBlank(licensee)) {
            licensee = "./.";
        }

        if (isBlank(issuer)) {
            issuer = "./.";
        }

        if (created == null) {
            created = OffsetDateTime.now(ValidityDuration.UTC);
        }
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>(4);


        if (features.isEmpty()) {
            failures.add("No Features are licensed. Please check with customer support.");
        }

        if (duration == null) {
            failures.add("No duration given for this license.");
        }

        if (versions == null) {
            failures.add("No versions given for this license.");
        }


        if (failures.size() > 0) {
            throw new BuilderException(OfficeLicenseTO.class, failures);
        }
    }

    public OfficeLicenseBuilder withLicense(@NotNull final OfficeLicense license) {
        withId(license.getId());
        withLicensee(license.getLicensee());
        withIssuer(license.getLicensor());
        withCreated(license.getCreated());
        withDuration(license.getDuration());
        withVersions(license.getValidVersions());
        withFeatures(license.getOptions());

        return this;
    }

    public OfficeLicenseBuilder withId(@NotNull final UUID id) {
        this.id = id;
        return this;
    }

    public OfficeLicenseBuilder withLicensee(@NotNull final String licensee) {
        this.licensee = licensee;
        return this;
    }

    public OfficeLicenseBuilder withIssuer(@NotNull final String issuer) {
        this.issuer = issuer;
        return this;
    }

    public OfficeLicenseBuilder withCreated(@NotNull final OffsetDateTime created) {
        this.created = created;
        return this;
    }

    public OfficeLicenseBuilder withDuration(@NotNull final ValidityDuration duration) {
        this.duration = duration;
        return this;
    }

    public OfficeLicenseBuilder withVersions(@NotNull final VersionRange versions) {
        this.versions = versions;
        return this;
    }

    public OfficeLicenseBuilder withFeatures(@NotNull final Collection<String> features) {
        this.features.clear();
        this.features.addAll(features);
        return this;
    }

    public OfficeLicenseBuilder addFeatures(@NotNull final Collection<String> features) {
        this.features.addAll(features);
        return this;
    }

    public OfficeLicenseBuilder removeFeatures(@NotNull final Collection<String> features) {
        this.features.removeAll(features);
        return this;
    }

    public OfficeLicenseBuilder addFeature(@NotNull final String feature) {
        features.add(feature);
        return this;
    }

    public OfficeLicenseBuilder removeFeature(@NotNull final String feature) {
        features.remove(feature);
        return this;
    }
}
