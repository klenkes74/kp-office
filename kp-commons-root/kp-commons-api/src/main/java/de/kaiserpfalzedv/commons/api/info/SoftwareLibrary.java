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

package de.kaiserpfalzedv.commons.api.info;

import java.io.Serializable;

/**
 * Used libraries may have other licenses and may require additional disclaimers.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public interface SoftwareLibrary extends Serializable {
    /**
     * @return The name of the library
     */
    String getLibraryName();

    /**
     * @return The copyright notice string of the author/owner of the software.
     */
    String getCopyrightNotice();

    /**
     * Sometimes the author or owner of the library requires a disclaimer to be displayed. This is the disclaimer.
     * For easier usage it defaults to the disclaimer of the license.
     *
     * @return The disclaimer the licensor of the library requires to be displayed.
     */
    default String getDisclaimer() {
        return getLicense().getDisclaimer();
    }

    /**
     * @return The license of the library.
     */
    SoftwareLicense getLicense();
}
