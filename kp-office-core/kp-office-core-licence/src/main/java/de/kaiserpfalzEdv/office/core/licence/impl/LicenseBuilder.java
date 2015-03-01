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

package de.kaiserpfalzEdv.office.core.licence.impl;

import com.verhas.licensor.License;
import de.kaiserpfalzEdv.commons.service.VersionRange;
import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.commons.util.BuilderException;
import de.kaiserpfalzEdv.office.core.licence.OfficeLicence;
import org.apache.commons.lang3.builder.Builder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 14.02.15 21:26
 */
public class LicenseBuilder implements Builder<OfficeLicence> {
    private static final String LICENSE_ID            = "id";
    private static final String LICENSE_ISSUED        = "issued";
    private static final String LICENSE_ISSUER        = "issuer";
    private static final String LICENSE_LICENSEE      = "licensee";
    private static final String LICENSE_START         = "starts";
    private static final String LICENSE_EXPIRY        = "expires";
    private static final String LICENSE_SOFTWARE      = "software";
    private static final String LICENSE_VERSION_START = "version";
    private static final String LICENSE_VERSION_END   = "lastVersion";
    private static final String LICENSE_FEATURES      = "features";
    private final ArrayList<String> modules = new ArrayList<>(10);
    private UUID      id;
    private LocalDate issued;
    private String    issuer;
    private String    licensee;
    private LocalDate starts;
    private LocalDate expires;
    private String      software;
    private VersionRange versionRange;

    @Override
    public OfficeLicence build() {
        validate();

        return new LicenceImpl(
                id,
                issued,
                issuer,
                licensee,
                starts,
                expires,
                software,
                versionRange,
                modules.toArray(new String[1])
        );
    }

    private void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (id == null) failures.add("No valid licence id is given!");
        if (issued == null) failures.add("No valid issue date is given!");
        if (isBlank(issuer)) failures.add("No issuer for this licence is given!");
        if (isBlank(licensee)) failures.add("No licensee for this licence is given!");
        if (starts == null) failures.add("No start date for licence validity!");
        if (expires == null) failures.add("No expiry date for licence!");
        if (isBlank(software)) failures.add("No software name for this licence is given!");
        if (versionRange == null) failures.add("No validity range for the version for this licence!");
        if (modules.size() == 0) failures.add("No modules licensed!");

        if (failures.size() > 0) {
            throw new BuilderException(failures);
        }
    }
    
    
    public LicenseBuilder withLicense(final License license) {
        id = getUuidFromLicense(license, LICENSE_ID);
        issued = getDateFromLicense(license, LICENSE_ISSUED);
        issuer = license.getFeature(LICENSE_ISSUER);
        licensee = license.getFeature(LICENSE_LICENSEE);
        
        starts = getDateFromLicense(license, LICENSE_START);
        expires = getDateFromLicense(license, LICENSE_EXPIRY);
        
        software = license.getFeature(LICENSE_SOFTWARE);
        versionRange = getVersionRangeFromLicense(license, LICENSE_VERSION_START, LICENSE_VERSION_END);
        
        modules.addAll(getModulesFromLicense(license));
        
        return this;
    }
    
    private UUID getUuidFromLicense(final License license, final String feature) {
        return UUID.fromString(license.getFeature(feature));
    }
    
    private LocalDate getDateFromLicense(final License license, final String feature) {
        try {
            return LocalDate.parse(license.getFeature(feature));
        } catch (NullPointerException e) {
            throw new IllegalArgumentException("License does not contain feature '" + feature + "'.");
        }
    }

    private VersionRange getVersionRangeFromLicense(final License license, final String featureStart, final String featureEnd) {
        Versionable start = getVersionFromLicense(license, featureStart);
        Versionable end = getVersionFromLicense(license, featureEnd);
        
        return new SoftwareVersionRange(start, end);
    }
    
    private Versionable getVersionFromLicense(final License license, final String feature) {
        return new SoftwareVersion(license.getFeature(feature));
    }
    
    
    private Set<String> getModulesFromLicense(final License license) {
        HashSet<String> result = new HashSet<>(10);
        
        String modules = license.getFeature(LICENSE_FEATURES);
        try {
            Collections.addAll(result, modules.split(","));
        } catch (NullPointerException e) {
            // OK, there are no modules in there ...
        }
        
        return result;
    }
}
