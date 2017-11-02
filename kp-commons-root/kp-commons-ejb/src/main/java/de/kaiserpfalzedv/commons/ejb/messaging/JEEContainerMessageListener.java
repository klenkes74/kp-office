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

package de.kaiserpfalzedv.commons.ejb.messaging;

import de.kaiserpfalzedv.commons.api.init.InitializationException;
import de.kaiserpfalzedv.commons.api.messaging.MessageListener;
import de.kaiserpfalzedv.commons.api.messaging.MessageMultiplexer;
import de.kaiserpfalzedv.commons.api.messaging.NoCorrelationInMessageException;
import de.kaiserpfalzedv.commons.api.messaging.NoListenerForCorrelationId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.jms.Destination;
import javax.jms.Message;
import javax.validation.constraints.NotNull;
import java.util.Properties;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2017-08-18
 */
public class JEEContainerMessageListener implements MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(JEEContainerMessageListener.class);


    private MessageMultiplexer multiplexer;

    @Resource(lookup = "java:comp/env/jms/input")
    private Destination input;


    @Inject
    public JEEContainerMessageListener(
            @NotNull final MessageMultiplexer multiplexer
    ) {
        this.multiplexer = multiplexer;
    }

    @Override
    public void init() throws InitializationException {
    }

    @Override
    public void init(Properties properties) throws InitializationException {
    }

    @Override
    public void close() {

    }


    @Override
    public Destination getDestination() {
        return input;
    }

    @Override
    public MessageMultiplexer getMultiplexer() {
        return multiplexer;
    }

    @Override
    public void setMultiplexer(@NotNull final MessageMultiplexer multiplexer) {
        this.multiplexer = multiplexer;
    }

    @Override
    public void onMessage(Message message) {
        try {
            multiplexer.multiplex(message);
        } catch (NoCorrelationInMessageException | NoListenerForCorrelationId e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new RuntimeException(e);
        }
    }
}
