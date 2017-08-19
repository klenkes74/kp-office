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

package de.kaiserpfalzedv.office.common.impl.messaging;

import java.io.Serializable;

import javax.jms.Message;

import de.kaiserpfalzedv.office.common.api.messaging.MessageInfo;
import de.kaiserpfalzedv.office.common.api.messaging.NoResponseException;
import de.kaiserpfalzedv.office.common.api.messaging.ResponseOfWrongTypeException;

/**
 * @author rlichti {@literal <rlichti@kaiserpfalz-edv.de>}
 * @since 2016-09-23
 */
public class NoResponseMessageInfo implements MessageInfo {
    private String correlationId;
    private String workflowId;
    private String actionId;
    private String actionType;


    public NoResponseMessageInfo(
            final String correlationId,
            final String workflowId,
            final String actionId,
            final String actionType
    ) {
        this.correlationId = correlationId;
        this.workflowId = workflowId;
        this.actionId = actionId;
        this.actionType = actionType;
    }

    @Override
    public void close() {

    }

    @Override
    public boolean hasResponse() {
        return false;
    }

    @Override
    public String getCorrelationId() {
        return correlationId;
    }

    @Override
    public String getWorkflowId() {
        return workflowId;
    }

    @Override
    public String getActionId() {
        return actionId;
    }

    @Override
    public String getActionType() {
        return actionType;
    }

    @Override
    public Serializable retrieveResponse() throws NoResponseException {
        throw new NoResponseException(correlationId);
    }

    @Override
    public Serializable waitForResponse() throws NoResponseException, ResponseOfWrongTypeException, InterruptedException {
        return retrieveResponse();
    }

    @Override
    public void onMessage(Message message) {
        throw new UnsupportedOperationException("This method should never have been called!");
    }
}
