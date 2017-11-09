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

package de.kaiserpfalzedv.commons.impl.license.test;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.validation.constraints.NotNull;

import com.github.zafarkhaja.semver.Version;
import de.kaiserpfalzedv.commons.api.data.types.ValidityDuration;
import de.kaiserpfalzedv.commons.api.data.types.VersionRange;
import de.kaiserpfalzedv.commons.api.licensing.ApplicationLicense;
import de.kaiserpfalzedv.commons.impl.info.ApplicationLicenseBuilder;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-30
 */
public class ApplicationLicenseTest {
    private static final Logger LOG = LoggerFactory.getLogger(ApplicationLicenseTest.class);

    private static final UUID ID = UUID.randomUUID();
    private static final String LICENSEE = "Roland T. Lichti";
    private static final String ISSUER = "Kaiserpfalz EDV-Service";
    private static final OffsetDateTime ISSUED = OffsetDateTime.now(ValidityDuration.UTC);
    private static final Version VERSION_FROM = Version.forIntegers(1);
    private static final Version VERSION_UNTIL = Version.forIntegers(2);
    private static final VersionRange VERSIONS = new VersionRange(VERSION_FROM, VERSION_UNTIL);
    private static final OffsetDateTime FROM = OffsetDateTime.parse("1974-11-05T00:00:00.000+01:00");
    private static final OffsetDateTime TILL = OffsetDateTime.parse("2024-11-06T00:00:00.000+01:00");
    private static final ValidityDuration DURATION = new ValidityDuration(FROM, TILL);
    private static final HashSet<String> FEATURES = new HashSet<>(5);

    static {
        Collections.addAll(FEATURES, "feature1", "feature2", "feature3", "feature4", "feature5");
    }

    private ApplicationLicense cut;

    @BeforeClass
    public static void setUpMDC() {
        MDC.put("test-class", "office-license-to");
    }

    @AfterClass
    public static void tearDownMDC() {
        MDC.remove("test");
        MDC.remove("test-class");
    }

    @Test
    public void shouldBeALicenseWhenAllFieldsAreDefined() {
        logMethod("full-data", "Checking the full data set of a license");

        assertNotNull(cut);
        assertEquals(ID, cut.getId());
        assertEquals(LICENSEE, cut.getLicensee());
        assertEquals(ISSUER, cut.getLicensor());
        assertEquals(ISSUED, cut.getCreated());
        assertEquals(VERSIONS, cut.getVersions());
        assertEquals(DURATION, cut.getDuration());
        assertArrayEquals(FEATURES.toArray(new String[1]), cut.getFeatures().toArray(new String[1]));
    }

    private void logMethod(final String shortName, final String message, Object... data) {
        MDC.put("test", shortName);

        LOG.info(message, data);
    }

    @Test
    public void shouldNotBeEqualWhenIdsDifferSameClass() {
        logMethod("different-id", "Checking if the equals() is working with different ids");

        ApplicationLicense other = new ApplicationLicenseBuilder()
                .withLicensee(LICENSEE)
                .withIssuer(ISSUER)
                .withCreated(ISSUED)
                .withVersions(VERSIONS)
                .withDuration(DURATION)
                .withFeatures(FEATURES)
                .build();

        assertFalse(cut.equals(other));
        assertFalse(other.equals(cut));
    }

    @Test
    public void shouldBeEqualWhenIdsAreEqualSameClass() {
        logMethod("equal-id", "Checking if the equals() is working with the same ids but different data");

        ApplicationLicense other = new ApplicationLicenseBuilder()
                .withId(ID)
                .withLicensee(LICENSEE + "_other")
                .withIssuer(ISSUER + "_other")
                .withCreated(ISSUED.plusDays(1))
                .withVersions(VERSIONS)
                .withDuration(DURATION)
                .withFeatures(FEATURES)
                .build();

        assertTrue(cut.equals(other));
        assertTrue(other.equals(cut));
    }

    @Test
    public void shouldNotBeEqualWhenIdsDifferOtherClass() {
        logMethod("different-id-different-class", "Checking if the equals() is working with different ids");

        ApplicationLicense other = new OtherApplicationLicenseImplementation(
                new ApplicationLicenseBuilder()
                        .withLicensee(LICENSEE)
                        .withIssuer(ISSUER)
                        .withCreated(ISSUED)
                        .withVersions(VERSIONS)
                        .withDuration(DURATION)
                        .withFeatures(FEATURES)
                        .build()
        );

        assertFalse(cut.equals(other));
        assertFalse(other.equals(cut));
    }

    @Test
    public void shouldBeEqualWhenIdsAreEqualOtherClass() {
        logMethod("equal-id-different-class", "Checking if the equals() is working with the same ids but different data");

        ApplicationLicense other = new OtherApplicationLicenseImplementation(
                new ApplicationLicenseBuilder()
                        .withId(ID)
                        .withLicensee(LICENSEE)
                        .withIssuer(ISSUER)
                        .withCreated(ISSUED)
                        .withVersions(VERSIONS)
                        .withDuration(DURATION)
                        .withFeatures(FEATURES)
                        .build()
        );

        assertTrue(cut.equals(other));
        assertTrue(other.equals(cut));
    }

    @Before
    public void setUp() {
        cut = new ApplicationLicenseBuilder()
                .withId(ID)
                .withLicensee(LICENSEE)
                .withIssuer(ISSUER)
                .withCreated(ISSUED)
                .withVersions(VERSIONS)
                .withDuration(DURATION)
                .withFeatures(FEATURES)
                .build();
    }

    @After
    public void tearDown() {
        MDC.remove("test");
    }

}

class OtherApplicationLicenseImplementation implements ApplicationLicense {
    private ApplicationLicense data;

    OtherApplicationLicenseImplementation(@NotNull final ApplicationLicense data) {
        this.data = data;
    }

    @Override
    public String getTitle() {
        return data.getTitle();
    }

    @Override
    public String getDisclaimer() {
        return data.getDisclaimer();
    }

    @Override
    public String getFullText() {
        return data.getFullText();
    }

    @Override
    public boolean isOpenSource() {
        return false;
    }

    @Override
    public String getLicensee() {
        return data.getLicensee();
    }

    @Override
    public String getLicensor() {
        return data.getLicensor();
    }

    @Override
    public OffsetDateTime getCreated() {
        return data.getCreated();
    }

    @Override
    public ValidityDuration getDuration() {
        return data.getDuration();
    }

    @Override
    public VersionRange getVersions() {
        return data.getVersions();
    }

    @Override
    public Set<String> getFeatures() {
        return data.getFeatures();
    }

    @Override
    public boolean isFeatureLicences(String option) {
        return data.isFeatureLicences(option);
    }

    @Override
    public boolean isValid(Version version) {
        return data.isValid(version);
    }

    @Override
    public boolean isValidDuration() {
        return data.isValidDuration();
    }

    @Override
    public boolean isValidDuration(Instant now) {
        return data.isValidDuration(now);
    }

    @Override
    public boolean isValidVersion(Version version) {
        return data.isValidVersion(version);
    }

    @Override
    public UUID getId() {
        return data.getId();
    }

    @Override
    public int hashCode() {
        return data.defaultHashCode();
    }

    @SuppressWarnings("EqualsWhichDoesntCheckParameterClass") // The checks are delegated, so please be quiet!
    @Override
    public boolean equals(Object obj) {
        return data.defaultEquals(obj);
    }

    @Override
    public String toString() {
        return data.defaultToString();
    }
}
