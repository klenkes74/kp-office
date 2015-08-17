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

package de.kaiserpfalzEdv.office.commons.amqp.session;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Named;
import javax.validation.constraints.NotNull;
import java.util.UUID;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 14:30
 */
@Named
public class AmqpRequest {
    private static final Logger LOG = LoggerFactory.getLogger(AmqpRequest.class);

    private static final String FIXED_MESSAGE_PROPERTIES = "MESSAGE_PROPERTIES";

    private static final RequestDataHolder fixedMap   = new RequestDataHolder();
    private static final RequestDataHolder requestMap = new RequestDataHolder();


    @Value("${amqp.ticketName}")
    private String securityTicketHeaderName;

    public AmqpRequest() {
        LOG.trace("Created: {}", this);
    }


    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        fixedMap.remove();
        requestMap.remove();

        LOG.trace("Destroyed: {}", this);
    }


    public Object get(@NotNull final String key) {
        return requestMap.get(key, null);
    }

    public void set(@NotNull final String key, @NotNull final String value) {
        requestMap.set(key, value);
    }

    public void unset(@NotNull final String key) {
        requestMap.unset(key);
    }


    public void startRequest(MessageProperties properties) {
        LOG.debug("Initializing request to request map: {}", properties);

        fixedMap.remove();
        requestMap.remove();

        if (properties != null) {
            fixedMap.set(FIXED_MESSAGE_PROPERTIES, properties);
        } else {
            LOG.warn("No message given vor initialization. No message properties and no security ticket will be available!");
        }
    }

    public void endRequest() {
        try {
            LOG.debug("Removed request from request map: {}", getMessageProperties());
        } catch (AmqpSessionException e) {
            LOG.warn("No valid request found. Ok, will fix it thought ...");
        }

        fixedMap.remove();
        requestMap.remove();
    }


    public MessageProperties getMessageProperties() {
        LOG.trace("Get message properties from request map.");

        return (MessageProperties) fixedMap.get(FIXED_MESSAGE_PROPERTIES);
    }

    public UUID getUserId() {
        try {
            return UUID.fromString((String) getMessageProperties().getHeaders().get(securityTicketHeaderName));
        } catch (AmqpSessionException | NullPointerException e) {
            return (UUID) fixedMap.get("user", null);
        }
    }

    public void setUserId(final UUID id) {
        if (isInitialized()) {
            getMessageProperties().getHeaders().put(securityTicketHeaderName, id.toString());
        } else {
            fixedMap.set("user", id);
        }
    }

    public UUID getCorrelationId() {
        if (getMessageProperties().getCorrelationId() != null) {
            return UUID.fromString((String) getMessageProperties().getHeaders().get(securityTicketHeaderName));
        } else {
            return UUID.randomUUID();
        }
    }


    public boolean isInitialized() {
        return fixedMap.contains(FIXED_MESSAGE_PROPERTIES);
    }
}
