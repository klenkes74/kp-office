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

package de.kaiserpfalzEdv.office.commons.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;

import javax.inject.Named;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 28.03.15 23:34
 */
@Named
public class PrintMessage implements MessagePostProcessor {
    private static final Logger LOG = LoggerFactory.getLogger(PrintMessage.class);

    @Override
    public Message postProcessMessage(Message message) throws AmqpException {
        LOG.trace("Message: {}", message);

        return message;
    }
}
