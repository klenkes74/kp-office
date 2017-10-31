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

package de.kaiserpfalzedv.office.common.impl.messaging;

import java.io.Serializable;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.TextMessage;

import de.kaiserpfalzedv.office.common.api.BaseSystemException;
import de.kaiserpfalzedv.office.common.api.messaging.MessageInfo;
import de.kaiserpfalzedv.office.common.api.messaging.MessageMultiplexer;
import de.kaiserpfalzedv.office.common.api.messaging.NoResponseException;
import de.kaiserpfalzedv.office.common.api.messaging.ResponseOfWrongTypeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-22
 */
public class ResponseMessageInfo<T extends Serializable> implements MessageInfo<T> {
    private static final Logger LOG = LoggerFactory.getLogger(ResponseMessageInfo.class);

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
    public String getCorrelationId() {
        return correlationId;
    }

    @Override
    public String getWorkflowId() {
        try {
            return message.getStringProperty("workflowId");
        } catch (JMSException e) {
            throw new BaseSystemException(e);
        }
    }

    @Override
    public String getActionId() {
        try {
            return message.getStringProperty("actionId");
        } catch (JMSException e) {
            throw new BaseSystemException(e);
        }
    }

    @Override
    public String getActionType() {
        try {
            return message.getStringProperty("actionType");
        } catch (JMSException e) {
            throw new BaseSystemException(e);
        }
    }

    @Override
    public T retrieveResponse() throws NoResponseException, ResponseOfWrongTypeException {
        if (message != null) {
            try {
                if (TextMessage.class.isAssignableFrom(message.getClass())) {
                    //noinspection unchecked
                    return (T) ((TextMessage) message).getText();
                } else {
                    //noinspection unchecked
                    return (T) ((ObjectMessage) message).getObject();
                }
            } catch (JMSException e) {
                throw new NoResponseException(correlationId);
            } catch (ClassCastException e) {
                throw new ResponseOfWrongTypeException(correlationId, e);
            }
        }

        throw new NoResponseException(correlationId);
    }

    public synchronized T waitForResponse() throws InterruptedException, ResponseOfWrongTypeException, NoResponseException {
        while (message == null) {
            wait();
        }

        notify();
        return retrieveResponse();
    }

    @Override
    public synchronized void onMessage(final Message message) {
        LOG.trace("Response({}): message received: {}", correlationId, message);

        this.message = message;
        notify();
    }
}
