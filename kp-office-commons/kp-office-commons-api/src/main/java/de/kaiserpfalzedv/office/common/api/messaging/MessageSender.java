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

package de.kaiserpfalzedv.office.common.api.messaging;

import java.io.Serializable;
import java.util.Map;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-22
 */
public interface MessageSender<T extends Serializable, R extends Serializable> {
    MessageInfo<R> sendMessage() throws NoBrokerException;

    MessageSender<T, R> withPayload(T payload);

    MessageSender<T, R> withDestination(final String destination);

    MessageSender<T, R> withCorrelationId(final String correlationId);

    MessageSender<T, R> withMessageId(String messageId);

    MessageSender<T, R> withPersistentDelivery(boolean persistentDelivery);

    MessageSender<T, R> withPriority(int priority);

    MessageSender<T, R> withTTL(final long ttl);

    MessageSender<T, R> withCustomHeaders(final Map<String, String> headers);

    MessageSender<T, R> withCustomHeader(final String header, final String value);

    MessageSender<T, R> removeCustomHeader(final String header);

    MessageSender<T, R> withResponse();

    MessageSender<T, R> withoutResponse();

    String getCorrelationId();
}
