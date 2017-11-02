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

package de.kaiserpfalzedv.office.metainfo.impl;

import com.github.zafarkhaja.semver.Version;
import com.verhas.licensor.License;
import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.commons.api.data.ValidityDuration;
import de.kaiserpfalzedv.commons.api.data.VersionRange;
import de.kaiserpfalzedv.office.metainfo.api.OfficeLicense;
import org.apache.commons.lang3.builder.Builder;
import org.bouncycastle.openpgp.PGPException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-30
 */
public class OfficeLicenseBuilder implements Builder<OfficeLicense> {
    private static final Logger LOG = LoggerFactory.getLogger(OfficeLicenseBuilder.class);
    private static final String KEY_RING = "/pubring.gpg";
    private final byte[] KEY_DIGEST = new byte[]{
            (byte) 0xF6,
            (byte) 0x4F, (byte) 0x3C, (byte) 0x1C, (byte) 0xA7, (byte) 0x20, (byte) 0xD5, (byte) 0x46, (byte) 0xF6,
            (byte) 0x56, (byte) 0x6A, (byte) 0x89, (byte) 0x2C, (byte) 0xCF, (byte) 0xFF, (byte) 0xFB, (byte) 0x86,
            (byte) 0xFE, (byte) 0xA1, (byte) 0xA0, (byte) 0xC9, (byte) 0x37, (byte) 0x7B, (byte) 0xE2, (byte) 0xF6,
            (byte) 0x4A, (byte) 0x45, (byte) 0x5A, (byte) 0x56, (byte) 0xE3, (byte) 0xCB, (byte) 0x8D, (byte) 0x89,
            (byte) 0xFB, (byte) 0x22, (byte) 0xD4, (byte) 0x15, (byte) 0xFF, (byte) 0x8E, (byte) 0xA1, (byte) 0x68,
            (byte) 0x69, (byte) 0x85, (byte) 0xCC, (byte) 0xA6, (byte) 0xCA, (byte) 0x61, (byte) 0xCF, (byte) 0xF6,
            (byte) 0xAD, (byte) 0xF4, (byte) 0xDD, (byte) 0x96, (byte) 0x76, (byte) 0xA9, (byte) 0xAF, (byte) 0x53,
            (byte) 0x3B, (byte) 0xBC, (byte) 0x11, (byte) 0x6A, (byte) 0xF6, (byte) 0x96, (byte) 0xB2,
    };
    private String licenseFileName;

    private ValidityDuration duration;
    private VersionRange versions;


    @Override
    public OfficeLicense build() {
        License license = generateBaseLicense();

        setDefaultValues(license);
        validate(license);

        return new OfficeLicenseImpl(license, duration, versions);
    }

    private License generateBaseLicense() {
        License license = new License();

        InputStream licensor = null;
        try {
            licensor = getClass().getResourceAsStream(KEY_RING);
            license.loadKeyRing(licensor, KEY_DIGEST);
        } catch (IOException | IllegalArgumentException e) {
            throw new BuilderException(OfficeLicenseImpl.class, new String[]{"Can't load licensor file. Did you play with the library files? Reason: " + e
                    .getMessage()});
        } finally {
            if (licensor != null) {
                try {
                    licensor.close();
                } catch (IOException e) {
                    LOG.error("Can't close license manager public key ring file: {}", KEY_RING);
                }
            }
        }

        InputStream licenseFile = null;
        try {
            URL uri = ClassLoader.getSystemResource(licenseFileName);
            licenseFile = uri.openStream();
            license.setLicenseEncoded(licenseFile);
        } catch (IOException e) {
            throw new BuilderException(OfficeLicenseImpl.class, new String[]{" Can't load license file: " + licenseFile});
        } catch (PGPException e) {
            throw new BuilderException(OfficeLicenseImpl.class, new String[]{"Can't decode and validate license file: " + licenseFile});
        }

        return license;
    }

    private void setDefaultValues(@NotNull final License license) {
        calculateDurationFromLicense(license);
        calculateVersionFromLicense(license);
    }

    private void validate(@NotNull final License license) {
        ArrayList<String> failures = new ArrayList<>(4);


        if (!license.isVerified()) {
            failures.add("License is not verified. Did you tamper with the files?");
        }

        if ("".equals(license.getFeature("features"))) {
            failures.add("License contains no feature list. Please call customer support.");
        }

        if ("".equals(license.getFeature("id"))) {
            failures.add("License contains no id. Please call custoemr support.");
        }

        if (duration == null) {
            failures.add("No duration given for this license.");
        }

        if (versions == null) {
            failures.add("No versions given for this license.");
        }


        if (failures.size() > 0) {
            throw new BuilderException(OfficeLicenseImpl.class, failures);
        }
    }

    private void calculateDurationFromLicense(@NotNull final License license) {
        if (duration == null && license != null) {
            try {
                OffsetDateTime from = LocalDate.parse(license.getFeature("valid-from"))
                                               .atStartOfDay(ValidityDuration.UTC)
                                               .toOffsetDateTime();
                OffsetDateTime till = LocalDate.parse(license.getFeature("valid-until"))
                                               .atStartOfDay(ValidityDuration.UTC)
                                               .toOffsetDateTime();

                duration = new ValidityDuration(from, till);
            } catch (NullPointerException e) {
                LOG.warn("The license file contains no valid duration ('valid-from' and 'valid-until')");
            }
        }
    }

    private void calculateVersionFromLicense(@NotNull final License license) {
        if (versions == null && license != null) {
            try {
                Version from = Version.valueOf(license.getFeature("version-from"));
                Version till = Version.valueOf(license.getFeature("version-until"));

                versions = new VersionRange(from, till);
            } catch (IllegalArgumentException e) {
                LOG.warn("The license file contains no valid version ('version-from' and 'version-until')");
            }
        }
    }

    public OfficeLicenseBuilder withLicenseFile(String licenseFile) {
        this.licenseFileName = licenseFile;

        return this;
    }
}
