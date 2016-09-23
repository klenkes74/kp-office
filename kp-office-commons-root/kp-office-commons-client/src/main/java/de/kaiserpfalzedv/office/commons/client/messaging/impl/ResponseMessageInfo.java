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
import de.kaiserpfalzedv.office.commons.client.messaging.MessageMultiplexer;
import de.kaiserpfalzedv.office.commons.client.messaging.NoResponseException;
import de.kaiserpfalzedv.office.commons.client.messaging.ResponseOfWrongTypeException;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import java.io.Serializable;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-22
 */
public class ResponseMessageInfo<T extends Serializable> implements MessageInfo<T> {
    private MessageMultiplexer multiplexer;
    private String correlationId;

    private Message message;


    ResponseMessageInfo(final MessageMultiplexer multiplexer, final String correlationId) {
        this.multiplexer = multiplexer;
        this.correlationId = correlationId;

        multiplexer.register(correlationId, this);
    }


    @Override
    public void close() {
        multiplexer.unregister(correlationId);
    }

    @Override
    public boolean hasResponse() {
        return message != null;
    }

    @Override
    public T retrieveResponse() throws NoResponseException, ResponseOfWrongTypeException {
        if (message != null) {
            try {
                //noinspection unchecked
                return (T) ((ObjectMessage) message).getObject();
            } catch (JMSException e) {
                throw new NoResponseException(correlationId);
            } catch (ClassCastException e) {
                throw new ResponseOfWrongTypeException(correlationId, e);
            }
        }

        throw new NoResponseException(correlationId);
    }

    @Override
    public void onMessage(Message message) {
        this.message = message;
    }
}
