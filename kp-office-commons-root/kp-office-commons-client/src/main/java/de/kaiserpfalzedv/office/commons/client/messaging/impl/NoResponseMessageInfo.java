/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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
 *
 */

package de.kaiserpfalzedv.office.commons.client.messaging.impl;

import de.kaiserpfalzedv.office.commons.client.messaging.MessageInfo;
import de.kaiserpfalzedv.office.commons.client.messaging.NoResponseException;

import javax.jms.Message;
import java.io.Serializable;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-23
 */
public class NoResponseMessageInfo implements MessageInfo {
    private String correlationId;

    public NoResponseMessageInfo(final String correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean hasResponse() {
        return false;
    }

    @Override
    public Serializable retrieveResponse() throws NoResponseException {
        throw new NoResponseException(correlationId);
    }

    @Override
    public void onMessage(Message message) {
        throw new UnsupportedOperationException("This method should never have been called!");
    }
}
