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

package de.kaiserpfalzedv.commons.api.metainfo;

import de.kaiserpfalzedv.commons.api.data.ValidityDuration;
import de.kaiserpfalzedv.commons.api.data.VersionRange;

import java.io.Serializable;

/**
 * A software license. Used for displaying it to the user.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public interface SoftwareLicense extends Serializable {
    /**
     * The official name of the license. For the "big" licenses it should be quite easy to be defined.
     *
     * @return The name of the software license.
     */
    String getTitle();

    /**
     * The disclaimer is the normally quite short message that the licensor requires to be displayed within
     * the software.
     *
     * @return The generic (short) disclaimer to be shown in splashscreens.
     */
    String getDisclaimer();

    /**
     * The full text of the license. Normally ASCII text.
     *
     * @return The full text of the license.
     */
    String getFullText();

    /**
     * A small hint if the license is considered open source.
     *
     * @return If the license is open source compatible.
     */
    boolean isOpenSource();

    /**
     * @return The current license key.
     */
    String getKey();

    /**
     * @return To whom this software is licensed.
     */
    String getLicensee();

    /**
     * @return The timespan this license is valid.
     */
    ValidityDuration getDuration();

    /**
     * @return The software versions this license is valid.
     */
    VersionRange getVersions();
}
