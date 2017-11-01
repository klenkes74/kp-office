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

package de.kaiserpfalzedv.office.metainfo.api;

import java.io.Serializable;
import java.util.Optional;

import de.kaiserpfalzedv.office.common.api.data.Identifiable;
import de.kaiserpfalzedv.office.common.api.data.Nameable;

/**
 * A bigger module of the software. May come with a {@link License software license} of its own and therefore with an
 * own {@link OfficeLicense technical license}.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @see OfficeLicense
 * @see License
 * @since 2017-10-31
 */
public interface OfficeModule extends Identifiable, Nameable, Serializable {
    /**
     * @return The license of this module.
     */
    License getLicenseText();

    /**
     * @return The software license enforcement.
     */
    Optional<OfficeLicense> getLicense();
}
