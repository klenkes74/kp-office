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

package de.kaiserpfalzEdv.commons.security;

import de.kaiserpfalzEdv.office.security.OfficeTicket;
import de.kaiserpfalzEdv.office.security.OfficeTicketDTO;
import de.kaiserpfalzEdv.office.security.SecurityClient;
import de.kaiserpfalzEdv.office.security.shiro.ticketAuthorization.OfficeTicketToken;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.apache.shiro.subject.SubjectContext;
import org.apache.shiro.subject.support.DefaultSubjectContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import java.util.UUID;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public class RabbitShiroInterceptor implements MethodInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(RabbitShiroInterceptor.class);


    private SecurityClient securityClient;

    @Inject
    public RabbitShiroInterceptor(
            final SecurityClient securityClient
    ) {
        this.securityClient = securityClient;

        LOG.trace("Created: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Override
    public Object invoke(MethodInvocation ctx) throws Throwable {
        Object result;

        try {
            Message msg = (Message) ctx.getArguments()[0];

            MessageProperties properties = msg.getMessageProperties();
            UUID ticketId = UUID.fromString(properties.getUserId());
            OfficeTicket ticket = new OfficeTicketDTO(ticketId);


            OfficeTicketToken token = new OfficeTicketToken(ticket);
            SubjectContext sc = new DefaultSubjectContext();
            sc.setAuthenticationToken(token);
        } catch (ClassCastException e) {
            // do nothing ...
        }

        try {
            result = ctx.proceed();
        } catch (RuntimeException e) {
            throw e;
        }

        return result;
    }
}