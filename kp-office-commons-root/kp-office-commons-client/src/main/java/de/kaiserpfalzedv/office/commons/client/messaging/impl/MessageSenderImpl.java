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

import de.kaiserpfalzedv.office.common.BuilderException;
import de.kaiserpfalzedv.office.commons.client.messaging.*;
import org.apache.commons.pool2.ObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-22
 */
public class MessageSenderImpl<T extends Serializable, R extends Serializable> implements MessageSender<T, R> {
    private static final Logger LOG = LoggerFactory.getLogger(MessageSenderImpl.class);

    private ObjectPool<Connection> connectionPool;

    private T payload;
    private String destination;
    private MessageMultiplexer multiplexer;
    private String correlationId;

    private final HashMap<String, String> customHeaders = new HashMap<>();
    private long ttl = -1L;

    /**
     * Creates a message sender.
     * @param connectionPool The pool used for sending messages.
     */
    public MessageSenderImpl(final ObjectPool<Connection> connectionPool) {
        notNull(connectionPool, "Need a connection pool to retrieve the JMS connection from!");

        this.connectionPool = connectionPool;
    }

    @Override
    public MessageInfo<R> sendMessage() throws NoBrokerException {
        validate();

        if (isBlank(correlationId))
            correlationId = UUID.randomUUID().toString();

        Connection connection = null;
        Session session = null;
        MessageProducer producer = null;
        try {
            connection = connectionPool.borrowObject();
            session = connection.createSession(false, 0);

            Destination target = session.createQueue(destination);

            ObjectMessage message = session.createObjectMessage(payload);
            setCorrelationIdToMessage(message);
            setReplyToToMessage(message);
            setExpirationToMessage(message);
            setCustomHeadersToMessage(message);

            producer = session.createProducer(target);
            producer.send(message);

            //noinspection unchecked
            return new MessageInfoBuilder()
                    .withCorrelationId(correlationId)
                    .withMultiplexer(multiplexer)
                    .build();
        } catch (Exception e) {
            throw new NoBrokerException(e);
        } finally {
            closeProducer(producer);
            closeSession(session);
            returnConnection(connection);
        }
    }

    private void setCorrelationIdToMessage(ObjectMessage message) throws JMSException {
        message.setJMSCorrelationID(correlationId);
    }

    private void setReplyToToMessage(ObjectMessage message) throws JMSException {
        if (multiplexer != null) {
            message.setJMSReplyTo(multiplexer.getReplyTo());
        }
    }

    private void setExpirationToMessage(ObjectMessage message) throws JMSException {
        if (ttl != -1) {
            message.setJMSExpiration(ttl);
        }
    }

    private void setCustomHeadersToMessage(ObjectMessage message) {
        customHeaders.forEach((k,v) -> {
            try {
                message.setStringProperty(k, v);
            } catch (JMSException e) {
                LOG.error(
                        "Could not set property '{}' to message with correlation-id '{}': {}",
                        k, correlationId, v
                );
            }
        });
    }

    private void closeProducer(MessageProducer producer) {
        if (producer != null) {
            try {
                producer.close();
            } catch (JMSException e) {
                LOG.error("JMS message producer could not be closed: {}", producer);
            }
        }
    }

    private void closeSession(Session session) {
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                LOG.error("JMS session could not be closed: {}", session);
            }
        }
    }

    private void returnConnection(Connection connection) {
        if (connection != null) {
            try {
                connectionPool.returnObject(connection);
            } catch (Exception e) {
                try {
                    connectionPool.invalidateObject(connection);
                } catch (Exception e1) {
                    LOG.error("Can't return connection to the connection pool: {}", connection);

                    try {
                        connection.close();
                    } catch (JMSException e2) {
                        LOG.error("Can't even close JMS connection: {}", connection);
                    }
                }
            }
        }
    }

    public void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (isBlank(destination)) {
            failures.add("Can't send a message without destination!");
        }

        if (payload == null) {
            failures.add("Can't send a message without payload!");
        }

        if (!failures.isEmpty()) {
            throw new BuilderException(MessageSenderImpl.class, failures.toArray(new String[1]));
        }
    }


    @Override
    public MessageSender<T,R> withPayload(T payload) {
        this.payload = payload;

        return this;
    }

    @Override
    public MessageSender<T,R> withDestination(String destination) {
        this.destination = destination;

        return this;
    }

    @Override
    public MessageSender<T,R> withCorrelationId(String correlationId) {
        this.correlationId = correlationId;

        return this;
    }

    @Override
    public MessageSender<T,R> withTTL(long ttl) {
        this.ttl = ttl;

        return this;
    }

    @Override
    public MessageSender<T,R> withCustomHeaders(final Map<String, String> headers) {
        customHeaders.clear();
        customHeaders.putAll(headers);

        return this;
    }

    @Override
    public MessageSender<T,R> withCustomHeader(String header, String value) {
        customHeaders.put(header, value);

        return this;
    }

    @Override
    public MessageSender<T,R> removeCustomHeader(String header) {
        customHeaders.remove(header);

        return this;
    }

    @Override
    public MessageSender<T,R> withResponse(MessageMultiplexer multiplexer) {
        this.multiplexer = multiplexer;

        return this;
    }

    @Override
    public MessageSender<T,R> withoutResponse() {
        this.multiplexer = null;

        return this;
    }

    @Override
    public String getCorrelationId() {
        return correlationId;
    }
}
