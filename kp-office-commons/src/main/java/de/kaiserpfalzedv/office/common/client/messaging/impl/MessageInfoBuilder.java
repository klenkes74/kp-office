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

package de.kaiserpfalzedv.office.common.client.messaging.impl;

import de.kaiserpfalzedv.office.common.api.messaging.MessageInfo;
import de.kaiserpfalzedv.office.common.client.messaging.MessageMultiplexer;
import org.apache.commons.lang3.builder.Builder;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-23
 */
public class MessageInfoBuilder implements Builder<MessageInfo> {
    private MessageMultiplexer multiplexer;
    private String correlationId;
    private String workflowId;
    private String actionId;
    private String actionType;

    @Override
    public MessageInfo build() {
        if (multiplexer != null)
            return new ResponseMessageInfo(multiplexer, correlationId);
        else
            return new NoResponseMessageInfo(correlationId, workflowId, actionId, actionType);
    }

    public MessageInfoBuilder withMultiplexer(MessageMultiplexer multiplexer) {
        this.multiplexer = multiplexer;
        return this;
    }

    public MessageInfoBuilder withCorrelationId(String correlationId) {
        this.correlationId = correlationId;
        return this;
    }

    public MessageInfoBuilder withWorkflowId(String workflowId) {
        this.workflowId = workflowId;
        return this;
    }

    public MessageInfoBuilder withActionId(String actionId) {
        this.actionId = actionId;
        return this;
    }

    public MessageInfoBuilder withActionType(String actionType) {
        this.actionType = actionType;
        return this;
    }
}
