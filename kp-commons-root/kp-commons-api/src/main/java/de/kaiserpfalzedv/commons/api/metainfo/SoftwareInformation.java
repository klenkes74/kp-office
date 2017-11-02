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

import java.util.Collection;
import java.util.List;

/**
 * Information to the database schema of the software. Yes, its the data provided by
 * {@link http://www.liquibase.org Liquibase}.
 *
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 1.0.0
 */
public interface SoftwareInformation {
    /**
     * @return The name of the software.
     */
    String getTitle();

    /**
     * @return The description of the software (could be used on a splash screen).
     */
    String getDescription();

    /**
     * @return The license of the software.
     */
    SoftwareLicense getSoftwareLicense();

    /**
     * @return A list of used libraries.
     */
    Collection<SoftwareLibrary> getLibraries();

    /**
     * @return The database changes.
     */
    List<DataSchemaChange> getChanges();
}
