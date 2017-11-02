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

package de.kaiserpfalzedv.commons.impl.messaging;

import de.kaiserpfalzedv.commons.api.init.InitializationException;
import de.kaiserpfalzedv.commons.api.messaging.MessageMultiplexer;
import de.kaiserpfalzedv.commons.api.messaging.NoCorrelationInMessageException;
import de.kaiserpfalzedv.commons.api.messaging.NoListenerForCorrelationId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Singleton;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-22
 */
@Singleton
public class MessageMultiplexerImpl implements MessageMultiplexer {
    private static final Logger LOG = LoggerFactory.getLogger(MessageMultiplexerImpl.class);

    private final HashMap<String, MessageListener> listeners = new HashMap<>();


    @Override
    public void init() throws InitializationException {

    }

    @Override
    public void init(Properties properties) throws InitializationException {

    }

    @Override
    public void close() {
        listeners.forEach((key, listener) -> LOG.warn("No response received for correlation-id: {}", key));
        listeners.clear();
    }


    public void multiplex(final Message message) throws NoCorrelationInMessageException, NoListenerForCorrelationId {
        LOG.trace("Received message: {}", message);

        try {
            String correlationId = message.getJMSCorrelationID();

            if (correlationId == null) {
                throw new NoCorrelationInMessageException(message);
            }

            MessageListener listener = listeners.get(correlationId);
            if (listener == null) {
                throw new NoListenerForCorrelationId(correlationId, message);
            }

            listener.onMessage(message);

            LOG.debug("Displatched message with correlation-id '{}' to listener: {}", correlationId, listener);
        } catch (JMSException e) {
            throw new NoCorrelationInMessageException(message);
        }
    }

    @Override
    public void register(String correlationId, MessageListener listener) {
        LOG.debug("Registering correlation-id '{}' for listener: {}", correlationId, listener);
        listeners.put(correlationId, listener);
    }

    @Override
    public void unregister(String correlationId) {
        LOG.debug("Unregistering correlation-id: {}", correlationId);
        listeners.remove(correlationId);
    }
}
