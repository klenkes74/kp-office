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

import de.kaiserpfalzedv.office.common.api.data.Nameable;

/**
 * A legal license text.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-10-31
 */
public interface License extends Nameable, Serializable {
    /**
     * @return The disclaimer for the license as defined within the license text for display in the software.
     */
    String getDisclaimer();

    /**
     * @return The full text of the license.
     */
    String getText();
}
