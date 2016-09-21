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

package de.kaiserpfalzedv.office.common.data;

import java.util.UUID;

/**
 * The interface for writing and retrieving identification information.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 27.12.15 11:31
 */
public interface WritableIdentifyable extends Identifyable, WriteableTenantable {
    void setId(final UUID id);

    void setDisplayName(final String displayName);

    void setFullName(final String fullName);
}