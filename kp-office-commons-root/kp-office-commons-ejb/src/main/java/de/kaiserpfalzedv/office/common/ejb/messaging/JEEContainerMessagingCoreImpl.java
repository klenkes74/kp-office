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

package de.kaiserpfalzedv.office.common.ejb.messaging;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSContext;
import javax.validation.constraints.NotNull;

import de.kaiserpfalzedv.office.common.api.config.ConfigReader;
import de.kaiserpfalzedv.office.common.api.init.InitializationException;
import de.kaiserpfalzedv.office.common.api.messaging.MessageMultiplexer;
import org.apache.commons.pool2.ObjectPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-22
 */
@ApplicationScoped
public class JEEContainerMessagingCoreImpl implements JEEContainerMessagingCore {
    static private final Logger LOG = LoggerFactory.getLogger(JEEContainerMessagingCoreImpl.class);


    private JMSContext context;
    private MessageMultiplexer multiplexer;

    @Inject
    public JEEContainerMessagingCoreImpl(
            @NotNull JMSContext context,
            @NotNull MessageMultiplexer multiplexer
    ) {
        this.context = context;
        this.multiplexer = multiplexer;
    }

    public void init(final ConfigReader config) throws InitializationException {
        init();
    }

    @PostConstruct
    @Override
    public void init() throws InitializationException {
        LOG.info("Started messaging core: {}", this);
    }

    @PreDestroy
    @Override
    public void close() {
        LOG.info("Stopped messaging core: {}", this);
    }

    @Override
    public void init(final Properties config) throws InitializationException {
        init();
    }

    @Override
    public JMSContext getJMSContext() {
        return context;
    }

    @Override
    public ObjectPool<Connection> getConnectionPool() {
        return null;
    }

    @Override
    public MessageMultiplexer getMultiplexer() {
        return multiplexer;
    }

    @Override
    public Destination getReplyTo() {
        return null;
    }

    @Override
    public String getClientId() {
        return context.getClientID();
    }


}
