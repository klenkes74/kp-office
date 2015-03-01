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

import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 08.02.15 22:09
 */
public class FeatureNotLicencedException extends LicencingException {
    private static final long         serialVersionUID     = 4645921405459785870L;
    private static final ErrorMessage FEATURE_NOT_LICENSED = ErrorMessage.LICENSE_4005;

    private final String featureId;
    private final UUID   licenseId;

    public FeatureNotLicencedException(final OfficeLicence license, final String featureId) {
        super(FEATURE_NOT_LICENSED, "Feature '" + featureId + "' not licensed in licence: " + license.getId());

        this.featureId = featureId;
        this.licenseId = license.getId();
    }


    public UUID getLicenseId() {
        return licenseId;
    }

    public String getFeatureId() {
        return featureId;
    }
}
