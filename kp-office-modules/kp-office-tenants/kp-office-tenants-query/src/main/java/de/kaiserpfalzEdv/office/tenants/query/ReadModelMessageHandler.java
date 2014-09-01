/*
 * Copyright (c) 2014 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzEdv.office.tenants.query;

import de.kaiserpfalzEdv.office.OfficeSystemException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.PersistenceException;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Named
public class ReadModelMessageHandler implements MessageListener {
    private static final Logger LOG = LoggerFactory.getLogger(ReadModelMessageHandler.class);

    @Inject
    private ReadModelHandler handler;



    @PostConstruct
    public void init() {
        LOG.trace("***** Created: {}", this.toString());
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this.toString());
    }


    @Override
    public void onMessage(final Message message) {
        try {
            LOG.debug("Received message: {} - {}", message.getMessageProperties().getMessageId(), message);


        } catch (PersistenceException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new OfficeSystemException(e.getMessage());
            // No exception chaining to remove dependency from base frameworks for caller.
        }
    }
}
