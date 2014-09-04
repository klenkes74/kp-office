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

package de.kaiserpfalzEdv.commons.session;

import de.kaiserpfalzEdv.commons.security.ActingSystem;
import de.kaiserpfalzEdv.commons.security.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author klenkes
 * @since 2014Q
 */
public class SessionInformationStore implements Serializable {
    private static final Logger LOG = LoggerFactory.getLogger(SessionInformationStore.class);

    private final HashMap<String, Object> sessionInformation = new HashMap<>();

    private Subject subject;
    private ActingSystem system;


    public void store(final String key, final Object data) {
        sessionInformation.put(key, data);
    }


    public Object retrieve(final String key) {
        return sessionInformation.get(key);
    }
}
