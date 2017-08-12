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

package de.kaiserpfalzedv.office.common.client.messaging;

import java.util.ArrayList;

import javax.jms.Connection;

import de.kaiserpfalzedv.office.common.api.BaseSystemException;
import de.kaiserpfalzedv.office.common.api.BuilderException;
import de.kaiserpfalzedv.office.common.api.init.InitializationException;
import de.kaiserpfalzedv.office.common.api.messaging.MessageListener;
import de.kaiserpfalzedv.office.common.api.messaging.MessageMultiplexer;
import org.apache.commons.lang3.builder.Builder;
import org.apache.commons.pool2.ObjectPool;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-23
 */
public class MessageListenerBuilder implements Builder<MessageListener> {

    private ObjectPool<Connection> connectionPool;
    private MessageMultiplexer multiplexer;


    @Override
    public MessageListener build() {
        validate();

        MessageListener result = new MessageListenerImpl(connectionPool, multiplexer);

        try {
            result.init();
        } catch (InitializationException e) {
            throw new BaseSystemException(e);
        }

        return result;
    }

    public void validate() {
        ArrayList<String> failures = new ArrayList<>(3);

        if (connectionPool == null) {
            failures.add("No JMS connection pool defined!");
        }

        if (multiplexer == null) {
            failures.add("No jms message multiplexer!");
        }

        if (!failures.isEmpty()) {
            throw new BuilderException(MessageListener.class, failures.toArray(new String[1]));
        }
    }

    public MessageListenerBuilder withConnectionPool(ObjectPool<Connection> connectionPool) {
        this.connectionPool = connectionPool;
        return this;
    }

    public MessageListenerBuilder withMultiplexer(MessageMultiplexer multiplexer) {
        this.multiplexer = multiplexer;
        return this;
    }
}
