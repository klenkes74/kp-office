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

package de.kaiserpfalzEdv.office.commons.amqp.services;

import de.kaiserpfalzEdv.office.commons.amqp.session.AmqpRequest;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.03.15 16:12
 */
@Aspect
@Component
public class AmqpReceiverAspect {
    private static final Logger LOG = LoggerFactory.getLogger(AmqpReceiverAspect.class);

    @Inject
    private AmqpRequest request;

    @Inject
    private AmqpHeaderInjectorService service;


    public AmqpReceiverAspect() {
        LOG.trace("Created: {}", this);
    }

    @PostConstruct
    public void init() {
        LOG.trace("Initialized: {}", this);
        LOG.trace("  amqp request: {}", request);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    @Pointcut("execution(* de.kaiserpfalzEdv..*(org.springframework.amqp.core.Message))")
    public void messageReceiver() {}

    @Pointcut("execution(* org.springframework.amqp.rabbit.core.RabbitTemplate.send(java.lang.String, java.lang.String, org.springframework.amqp.core.Message, org.springframework.amqp.rabbit.support.CorrelationData))")
    public void messageSender() {}


    @Before("messageReceiver()")
    public void extractMessages(final JoinPoint invocation) {
        LOG.trace("Starting request {} ...", invocation.getTarget().getClass().getName());

        Message message = (Message) invocation.getArgs()[0];

        request.startRequest(message.getMessageProperties());
    }


    @Before("messageSender()")
    public void injectHeaderInMessage(final JoinPoint invocation) {
        Message message = (Message) invocation.getArgs()[2];

        service.postProcessMessage(message);

        request.endRequest();
    }
}
