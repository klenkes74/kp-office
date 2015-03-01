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

package de.kaiserpfalzEdv.office.commons.amqp;

import org.apache.commons.lang3.SerializationUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.support.converter.MessageConversionException;
import org.springframework.amqp.support.converter.MessageConverter;

import javax.inject.Named;
import java.io.Serializable;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 13:13
 */
@Named
public class SerializedMessageConverter implements MessageConverter {
    private static final Logger LOG = LoggerFactory.getLogger(SerializedMessageConverter.class);

    @Override
    public Message toMessage(Object object, MessageProperties messageProperties) throws MessageConversionException {
        byte[] payload = SerializationUtils.serialize((Serializable) object);

        return new Message(payload, messageProperties);
    }

    @Override
    public Object fromMessage(Message message) throws MessageConversionException {
        return SerializationUtils.deserialize(message.getBody());
    }
}
