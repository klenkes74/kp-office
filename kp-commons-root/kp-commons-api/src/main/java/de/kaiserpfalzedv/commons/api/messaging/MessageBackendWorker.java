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

package de.kaiserpfalzedv.commons.api.messaging;

import de.kaiserpfalzedv.commons.api.MessageInfo;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-11-17
 */
public interface MessageBackendWorker {
    /**
     * This method works on a request.
     *
     * @param info    Metadata for this request.
     * @param message the message as UTF-8 string.
     *
     * @throws Throwable since it could be a {@link de.kaiserpfalzedv.commons.api.BaseBusinessException} or one of its
     *                   subclasses or some system exceptions depending on the messaging system (like a JMSException).
     */
    void workOn(MessageInfo info, String message) throws Throwable;
}
