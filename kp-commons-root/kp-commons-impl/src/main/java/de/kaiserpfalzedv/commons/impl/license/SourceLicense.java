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

import de.kaiserpfalzedv.commons.api.data.ValidityDuration;
import de.kaiserpfalzedv.commons.api.data.VersionRange;
import de.kaiserpfalzedv.commons.api.info.SoftwareLicense;
import org.apache.commons.io.IOUtils;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public class SourceLicense implements SoftwareLicense {
    private static final String FILE_ENCODING = "UTF-8";

    /**
     * The base software license.
     */
    private final OpenSourceLicense baseLicense;

    /**
     * The person or organization that the software is licensed to.
     */
    private String licensee;

    /**
     * The license key displayable to the end user.
     */
    private String key;

    /**
     * The timespan this license is valid. Defaults to {@link OffsetDateTime#MIN} to {@link OffsetDateTime#MAX}
     */
    private ValidityDuration duration;

    /**
     * The version range this license is valid. Defaults to versions from {@link Integer#MIN_VALUE} to
     * {@link Integer#MAX_VALUE}.
     */
    private VersionRange range;


    SourceLicense(
            @NotNull final OpenSourceLicense baseLicense,
            @NotNull final String licensee,
            @NotNull final String key,
            @NotNull final ValidityDuration duration,
            @NotNull final VersionRange range
    ) {
        this.baseLicense = baseLicense;
        this.licensee = licensee;
        this.key = key;
        this.duration = duration;
        this.range = range;
    }

    @Override
    public String getTitle() {
        return baseLicense.getTitle();
    }

    @Override
    public String getDisclaimer() {
        InputStream stream = getClass().getResourceAsStream(String.format("/licenses/%s.disclaimer",
                baseLicense.toString()));

        String result = readStream(stream);

        return result;
    }

    private String readStream(InputStream stream) {
        try {
            String result = IOUtils.toString(stream, FILE_ENCODING);
            IOUtils.closeQuietly(stream);

            return result;
        } catch (IOException e) {
            throw new IllegalStateException("Can't read the license '" + baseLicense.toString() + "': " + e
                    .getMessage());
        }
    }

    @Override
    public String getFullText() {
        InputStream stream = getClass().getResourceAsStream(String.format("/licenses/%s.txt",
                baseLicense.toString()));

        String result = readStream(stream);

        return result;
    }

    @Override
    public boolean isOpenSource() {
        return baseLicense.isOpenSource();
    }

    @Override
    public String getKey() {
        return key;
    }

    void setKey(final String key) {
        this.key = key;
    }

    @Override
    public String getLicensee() {
        return licensee;
    }

    void setLicensee(final String licensee) {
        this.licensee = licensee;
    }

    @Override
    public ValidityDuration getDuration() {
        return duration;
    }

    void setDuration(final ValidityDuration duration) {
        this.duration = duration;
    }

    @Override
    public VersionRange getVersions() {
        return range;
    }

    void setVersion(final VersionRange range) {
        this.range = range;
    }
}
