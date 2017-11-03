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

package de.kaiserpfalzedv.commons.api.licensing;

import de.kaiserpfalzedv.commons.api.BaseSystemException;

/**
 * The feature is not licensed. Boom.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-31
 */
public class NotLicensedException extends BaseSystemException {
    private static final long serialVersionUID = -2022502356331232976L;

    /**
     * The license that has been checked.
     */
    private ApplicationLicense license;

    /**
     * The feature missing in the license.
     */
    private String feature;

    public NotLicensedException(final ApplicationLicense license, final String feature) {
        super("Feature '" + feature + "' is not licensed: " + license.getId());

        this.license = license;
        this.feature = feature;
    }


    /**
     * @return The license where the {@link #getFeature() feature} is missing from.
     */
    public ApplicationLicense getLicense() {
        return license;
    }

    /**
     * @return The feature that is not included in the {@link #getLicense() license}.
     */
    public String getFeature() {
        return feature;
    }
}
