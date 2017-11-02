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

package de.kaiserpfalzedv.commons.impl.license;

import com.github.zafarkhaja.semver.Version;
import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.commons.api.data.ValidityDuration;
import de.kaiserpfalzedv.commons.api.data.VersionRange;
import de.kaiserpfalzedv.commons.api.metainfo.SoftwareLicense;
import org.apache.commons.lang3.builder.Builder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class LicenseBuilder implements Builder<SoftwareLicense> {
    private static final Logger LOG = LoggerFactory.getLogger(LicenseBuilder.class);

    private String licensee = "--- ./. ---";
    private String key = "--- ./. ---";
    private Version lowerVersion = Version.forIntegers(Integer.MIN_VALUE);
    private Version upperVersion = Version.forIntegers(Integer.MAX_VALUE);
    private OffsetDateTime from = OffsetDateTime.MIN;
    private OffsetDateTime until = OffsetDateTime.MAX;
    private OpenSourceLicense baseLicense;

    @Override
    public SoftwareLicense build() {
        validate();

        SourceLicense result = new SourceLicense(
                baseLicense,
                licensee, key,
                new ValidityDuration(from, until),
                new VersionRange(lowerVersion, upperVersion)
        );

        return result;
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (baseLicense == null) {
            failures.add("No base license defined. Please specify one.");
        }

        if (failures.size() > 0) {
            throw new BuilderException(SoftwareLicense.class, failures);
        }
    }


    public LicenseBuilder withLicensee(@NotNull String licensee) {
        this.licensee = licensee;
        return this;
    }

    public LicenseBuilder withKey(@NotNull String key) {
        this.key = key;
        return this;
    }

    public LicenseBuilder withLowerVerison(@NotNull Version lowerVerison) {
        this.lowerVersion = lowerVerison;
        return this;
    }

    public LicenseBuilder withUpperVersion(@NotNull Version upperVersion) {
        this.upperVersion = upperVersion;
        return this;
    }

    public LicenseBuilder withFrom(@NotNull LocalDate from, ZoneId zone) {
        this.from = from.atStartOfDay(zone).toOffsetDateTime();
        return this;
    }

    public LicenseBuilder withUntil(@NotNull LocalDate until, ZoneId zone) {
        this.until = until.atStartOfDay(zone).plusDays(1L).minusNanos(1L).toOffsetDateTime();
        return this;
    }

    public LicenseBuilder withBaseLicense(@NotNull OpenSourceLicense baseLicense) {
        this.baseLicense = baseLicense;
        return this;
    }

    public LicenseBuilder apache2() {
        return withBaseLicense(OpenSourceLicense.APACHEv2_0);
    }
}
