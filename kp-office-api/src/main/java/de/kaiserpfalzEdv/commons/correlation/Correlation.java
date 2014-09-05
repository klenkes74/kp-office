/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.commons.correlation;

import de.kaiserpfalzEdv.commons.security.ActingSystem;
import de.kaiserpfalzEdv.office.security.OfficeSubject;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author klenkes
 * @since 2014Q
 */
public interface Correlation extends Serializable {
    public UUID getSessionId();
    public UUID getId();

    public long getSequence();

    public OfficeSubject getRequester();
    public ActingSystem getSystem();

    public boolean isRequest();
    public boolean isResponse();
}
