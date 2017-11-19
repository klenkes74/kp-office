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

package de.kaiserpfalzedv.commons.client.messaging;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.Message;

import de.kaiserpfalzedv.commons.api.BuilderException;
import de.kaiserpfalzedv.commons.api.messaging.MessageInfo;
import de.kaiserpfalzedv.commons.api.messaging.MessageSender;
import de.kaiserpfalzedv.commons.api.messaging.MessagingCore;
import de.kaiserpfalzedv.commons.api.messaging.NoBrokerException;
import de.kaiserpfalzedv.commons.ejb.messaging.JEEContainerMessagingCore;
import de.kaiserpfalzedv.commons.impl.messaging.MessageInfoBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;
import static org.apache.commons.lang3.Validate.notNull;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2017-08-18
 */
public class MessageSenderImpl<T extends Serializable, R extends Serializable> implements MessageSender<T, R> {
    private static final Logger LOG = LoggerFactory.getLogger(MessageSenderImpl.class);
    private final HashMap<String, String> customHeaders = new HashMap<>();
    private JEEContainerMessagingCore core;
    private T payload;
    private String destination;
    private String messageId;
    private String workflowId;
    private String actionId;
    private String actionType;
    private String correlationId;
    private boolean response = true;
    private long ttl = -1L;
    private boolean persistentDelivery = false;
    private int priority = -1;


    /**
     * Creates a message sender.
     *
     * @param core The messaging core to work with.
     */
    public MessageSenderImpl(final MessagingCore core) {
        notNull(core, "Need a messaging core to work with!");

        this.core = (JEEContainerMessagingCore) core;
    }

    @Override
    public MessageInfo<R> sendMessage() throws NoBrokerException {
        setDefaults();
        validate();

        JMSContext context = core.getJMSContext();
        JMSProducer producer = null;
        try {
            Message message;
            try {
                message = context.createTextMessage((String) payload);
            } catch (ClassCastException e) {
                message = context.createObjectMessage(payload);
            }

            setMessageIdToMessage(message);
            setCorrelationIdToMessage(message);
            setReplyToToMessage(message);
            setExpirationToMessage(message);
            setCustomHeadersToMessage(message);
            setDeliveryModeToMessage(message);
            setPriorityToMessage(message);
            setWorkflowIdToMessage(message);
            setActionIdToMessage(message);
            setActionTypeToMessage(message);

            producer = context.createProducer();
            Destination target = context.createQueue(destination);
            producer.send(target, message);

            //noinspection unchecked
            return new MessageInfoBuilder()
                    .withCorrelationId(correlationId)
                    .withMultiplexer(core.getMultiplexer())
                    .build();
        } catch (Exception e) {
            throw new NoBrokerException(e);
        }
    }


    private void setDefaults() {
        if (isBlank(correlationId)) {
            correlationId = UUID.randomUUID().toString();
        }

        if (isBlank(workflowId)) {
            workflowId = UUID.randomUUID().toString();
        }

        if (isBlank(actionId)) {
            actionId = UUID.randomUUID().toString();
        }

        if (isBlank(actionType) && payload != null) {
            actionType = payload.getClass().getCanonicalName();
        }
    }


    public void validate() {
        ArrayList<String> failures = new ArrayList<>();

        if (0 > priority || priority > 9) {
            failures.add("JMS priority is defined as interval [0,9]. " + priority
                                 + " does not fall inside this interval.");
        }

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

    private void setMessageIdToMessage(Message message) throws JMSException {
        if (isNotBlank(messageId)) {
            LOG.trace("JMS({}): Setting message-id to: {}", correlationId, messageId);

            message.setJMSMessageID("ID:" + messageId);
        }
    }

    private void setCorrelationIdToMessage(Message message) throws JMSException {
        LOG.trace("JMS({}): Setting correlation-id.", correlationId);

        message.setJMSCorrelationID(correlationId);
    }

    private void setReplyToToMessage(Message message) throws JMSException {
        if (response) {
            LOG.trace("JMS({}): Setting reply to: {}", correlationId, core.getReplyTo());

            message.setJMSReplyTo(core.getReplyTo());
        }
    }

    private void setExpirationToMessage(Message message) throws JMSException {
        if (ttl != -1) {
            LOG.trace("JMS({}): Setting expiration: {}", correlationId, ttl);
            message.setJMSExpiration(ttl);
        }
    }

    private void setCustomHeadersToMessage(Message message) {
        customHeaders.forEach((k, v) -> {
            try {
                LOG.trace("JMS({}): Setting custom header: {}={}", correlationId, k, v);

                message.setStringProperty(k, v);
            } catch (JMSException e) {
                LOG.error(
                        "Could not set property '{}' to message with correlation-id '{}': {}",
                        k, correlationId, v
                );
            }
        });
    }

    private void setDeliveryModeToMessage(Message message) throws JMSException {
        LOG.trace("JMS({}): Setting persistent delivery to {}.", correlationId, persistentDelivery);

        message.setJMSDeliveryMode(persistentDelivery ? DeliveryMode.PERSISTENT : DeliveryMode.NON_PERSISTENT);
    }

    private void setPriorityToMessage(Message message) throws JMSException {
        if (priority >= 0 && priority <= 9) {
            LOG.trace("JMS({}): Setting priority to: {}", correlationId, priority);

            message.setJMSPriority(priority);
        }
    }

    private void setWorkflowIdToMessage(Message message) throws JMSException {
        message.setStringProperty("workflowId", workflowId);
    }

    private void setActionIdToMessage(Message message) throws JMSException {
        message.setStringProperty("actionId", actionId);
    }

    private void setActionTypeToMessage(Message message) throws JMSException {
        message.setStringProperty("actionType", payload.getClass().getSimpleName());
    }


    @Override
    public MessageSender<T, R> withPayload(final T payload) {
        this.payload = payload;

        return this;
    }

    @Override
    public MessageSender<T, R> withDestination(final String destination) {
        this.destination = destination;

        return this;
    }

    @Override
    public MessageSender<T, R> withCorrelationId(final String correlationId) {
        this.correlationId = correlationId;

        return this;
    }

    @Override
    public MessageSender<T, R> withMessageId(String messageId) {
        this.messageId = messageId;
        return this;
    }

    @Override
    public MessageSender<T, R> withPersistentDelivery(boolean persistentDelivery) {
        this.persistentDelivery = persistentDelivery;
        return this;
    }

    @Override
    public MessageSender<T, R> withPriority(int priority) {
        this.priority = priority;
        return this;
    }

    @Override
    public MessageSender<T, R> withTTL(final long ttl) {
        this.ttl = ttl;

        return this;
    }

    @Override
    public MessageSender<T, R> withCustomHeaders(final Map<String, String> headers) {
        customHeaders.clear();
        customHeaders.putAll(headers);

        return this;
    }

    @Override
    public MessageSender<T, R> withCustomHeader(final String header, final String value) {
        customHeaders.put(header, value);

        return this;
    }

    @Override
    public MessageSender<T, R> removeCustomHeader(final String header) {
        customHeaders.remove(header);

        return this;
    }

    @Override
    public MessageSender<T, R> withResponse() {
        response = true;

        return this;
    }


    @Override
    public MessageSender<T, R> withoutResponse() {
        response = false;

        return this;
    }

    @Override
    public String getCorrelationId() {
        return correlationId;
    }

    @Override
    public void close() {
        if (correlationId != null) {
            core.unregister(correlationId);
        }
    }

    public MessageSenderImpl withWorkflowId(String workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    public MessageSenderImpl withActionId(String actionId) {
        this.actionId = actionId;
        return this;
    }

    public MessageSenderImpl withActionType(String actionType) {
        this.actionType = actionType;
        return this;
    }
}
