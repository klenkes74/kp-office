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

import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.commons.api.data.ValidityDuration;
import de.kaiserpfalzedv.commons.api.data.VersionRange;
import de.kaiserpfalzedv.commons.api.licensing.ApplicationLicense;
import org.apache.commons.lang3.builder.Builder;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * This builder generates the {@link ApplicationLicenseTO transport implementation} of the
 * {@link ApplicationLicense}.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @see ApplicationLicenseTO
 * @since 2017-10-30
 */
public class ApplicationLicenseBuilder implements Builder<ApplicationLicense> {
    private final HashSet<String> features = new HashSet<>();
    private UUID id;
    private String licensee;
    private String issuer;
    private OffsetDateTime created;
    private ValidityDuration duration;
    private VersionRange versions;

    @Override
    public ApplicationLicense build() {
        setDefaultValues();
        validate();

        return new ApplicationLicenseTO(
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
            throw new BuilderException(ApplicationLicenseTO.class, failures);
        }
    }

    public ApplicationLicenseBuilder withLicense(@NotNull final ApplicationLicense license) {
        withId(license.getId());
        withLicensee(license.getLicensee());
        withIssuer(license.getLicensor());
        withCreated(license.getCreated());
        withDuration(license.getDuration());
        withVersions(license.getVersions());
        withFeatures(license.getFeatures());

        return this;
    }

    public ApplicationLicenseBuilder withId(@NotNull final UUID id) {
        this.id = id;
        return this;
    }

    public ApplicationLicenseBuilder withLicensee(@NotNull final String licensee) {
        this.licensee = licensee;
        return this;
    }

    public ApplicationLicenseBuilder withIssuer(@NotNull final String issuer) {
        this.issuer = issuer;
        return this;
    }

    public ApplicationLicenseBuilder withCreated(@NotNull final OffsetDateTime created) {
        this.created = created;
        return this;
    }

    public ApplicationLicenseBuilder withDuration(@NotNull final ValidityDuration duration) {
        this.duration = duration;
        return this;
    }

    public ApplicationLicenseBuilder withVersions(@NotNull final VersionRange versions) {
        this.versions = versions;
        return this;
    }

    public ApplicationLicenseBuilder withFeatures(@NotNull final Collection<String> features) {
        this.features.clear();
        this.features.addAll(features);
        return this;
    }

    public ApplicationLicenseBuilder addFeatures(@NotNull final Collection<String> features) {
        this.features.addAll(features);
        return this;
    }

    public ApplicationLicenseBuilder addFeature(@NotNull final String feature) {
        features.add(feature);
        return this;
    }

    public ApplicationLicenseBuilder clearFeatures() {
        features.clear();
        return this;
    }

    public ApplicationLicenseBuilder removeFeatures(@NotNull final Collection<String> features) {
        this.features.removeAll(features);
        return this;
    }

    public ApplicationLicenseBuilder removeFeature(@NotNull final String feature) {
        features.remove(feature);
        return this;
    }
}
