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

package de.kaiserpfalzedv.iam.tenant.impl;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import de.kaiserpfalzedv.commons.api.MessageInfo;
import de.kaiserpfalzedv.commons.api.action.CommandExecutionException;
import de.kaiserpfalzedv.commons.ejb.ResponseSender;
import de.kaiserpfalzedv.iam.tenant.api.TenantCommandExecutor;
import de.kaiserpfalzedv.iam.tenant.api.commands.TenantBaseCommand;
import de.kaiserpfalzedv.iam.tenant.api.replies.TenantBaseReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-27
 */
@Dependent
public class TenantWorkerImpl implements TenantWorker {
    private static final Logger LOG = LoggerFactory.getLogger(TenantWorker.class);

    private UUID tenantWorkerId = UUID.randomUUID();

    private TenantCommandExecutor executor;

    private ResponseSender responseSender;
    private ObjectMapper mapper = new ObjectMapper();

    private MessageInfo info;


    @Inject
    public TenantWorkerImpl(
            @NotNull final TenantCommandExecutor executor,
            @NotNull final ResponseSender responseSender
    ) {
        this.executor = executor;
        this.responseSender = responseSender;
    }

    @PostConstruct
    public void init() {
        LOG.debug("Tenant Worker created: {}", tenantWorkerId);
    }

    @PreDestroy
    public void close() {
        LOG.debug("Tenant Worker destroyed: {}", tenantWorkerId);
    }


    @Override
    public void workOn(final MessageInfo info, final String message) throws JMSException, JsonProcessingException {
        this.info = info;

        String actionType = info.getActionType();
        LOG.trace("Worker ({}): actionType={}", tenantWorkerId, actionType);

        try {
            TenantBaseCommand command = mapper.readValue(message, claszOfAction(actionType));

            Optional<? extends TenantBaseReply> reply = command.execute(executor);

            if (reply.isPresent()) {
                responseSender.sendReply(info, mapper.writeValueAsString(reply.get()));
            }
        } catch (CommandExecutionException | IOException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            String error = mapper.writeValueAsString(e);

            responseSender.sendReply(info, error);
        }
    }

    private Class<? extends TenantBaseCommand> claszOfAction(final String actionType) {
        try {
            return Class.forName(actionType).asSubclass(TenantBaseCommand.class);
        } catch (ClassNotFoundException e) {
            throw new IllegalStateException("No valid message type: " + actionType);
        }
    }
}
