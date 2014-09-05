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

package de.kaiserpfalzEdv.office.security.query;

import de.kaiserpfalzEdv.office.tenants.TenantNotificationException;
import de.kaiserpfalzEdv.office.tenants.notifications.CreateTenantNotification;
import de.kaiserpfalzEdv.office.tenants.notifications.DeleteTenantNotification;
import de.kaiserpfalzEdv.office.tenants.notifications.SyncTenantNotification;
import de.kaiserpfalzEdv.office.tenants.notifications.UpdateTenantNotification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.AmqpRejectAndDontRequeueException;

import javax.annotation.PreDestroy;
import javax.inject.Inject;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class QueryMessageHandlerImpl implements QueryMessageHandler {
    private static final Logger LOG = LoggerFactory.getLogger(QueryMessageHandlerImpl.class);


    private QueryModelModifier modifier;
    private QueryExecutor queryExecutor;

    @Inject
    public QueryMessageHandlerImpl(
            final QueryModelModifier modifier,
            final QueryExecutor queryExecutor
    ) {
        this.modifier = modifier;
        this.queryExecutor = queryExecutor;

        LOG.trace("Created: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this.toString());
    }


    @Override
    public void handle(CreateTenantNotification command)  {
        try {
            modifier.handle(command);
        } catch (RuntimeException | TenantNotificationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

    @Override
    public void handle(UpdateTenantNotification command)  {
        try {
            modifier.handle(command);
        } catch (RuntimeException | TenantNotificationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

    @Override
    public void handle(DeleteTenantNotification command) {
        try {
            modifier.handle(command);
        } catch (RuntimeException | TenantNotificationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new AmqpRejectAndDontRequeueException(e);
        }
    }

    @Override
    public void handle(SyncTenantNotification command) {
        try {
            modifier.handle(command);
        } catch (RuntimeException | TenantNotificationException e) {
            LOG.error(e.getClass().getSimpleName() + " caught: " + e.getMessage(), e);

            throw new AmqpRejectAndDontRequeueException(e);
        }
    }
}
