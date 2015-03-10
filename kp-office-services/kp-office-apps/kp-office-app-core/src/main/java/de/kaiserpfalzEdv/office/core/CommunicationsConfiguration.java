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

package de.kaiserpfalzEdv.office.core;

import de.kaiserpfalzEdv.office.core.i18n.impl.TranslationServer;
import de.kaiserpfalzEdv.office.core.licence.impl.LicenceServer;
import de.kaiserpfalzEdv.office.core.security.impl.SecurityServer;
import de.kaiserpfalzEdv.office.core.tenants.impl.TenantServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessageListener;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 02.03.15 22:23
 */
@Configuration
public class CommunicationsConfiguration {
    private static final Logger LOG = LoggerFactory.getLogger(CommunicationsConfiguration.class);

    @Value("${amqp.host}")
    private String host;
    @Value("${amqp.port}")
    private String port;
    @Value("${amqp.user}")
    private String user;
    @Value("${amqp.password}")
    private String password;

    @Value("${amqp.domain}")
    private String domain;
    @Value("${amqp.virtual}")
    private String virtual;


    @Inject
    private SecurityServer    securityServer;
    @Inject
    private TenantServer      tenantServer;
    @Inject
    private TranslationServer i18nServer;
    @Inject
    private LicenceServer     licenceServer;

    private CachingConnectionFactory amqpConnectionFactory;


    @PostConstruct
    public void init() {
        this.amqpConnectionFactory = connectionFactory();

        LOG.trace("Initialized: {}", this);
        LOG.trace("   security server: {}", this.securityServer);
        LOG.trace("   tenant server: {}", this.tenantServer);
        LOG.trace("   i18n server: {}", this.i18nQueue());
        LOG.trace("   licence server: {}", this.licenceServer);
    }


    @Bean
    public SimpleMessageListenerContainer securityListener() {
        return generateContainer(securityQueue(), securityServer);
    }

    @Bean
    public SimpleMessageListenerContainer tenantsListener() {
        return generateContainer(tenantQueue(), tenantServer);
    }

    @Bean
    public SimpleMessageListenerContainer i18nListener() {
        return generateContainer(i18nQueue(), i18nServer);
    }

    @Bean
    public SimpleMessageListenerContainer licenceListener() {
        return generateContainer(licenceQueue(), licenceServer);
    }

    private SimpleMessageListenerContainer generateContainer(final Queue queue, final MessageListener listener) {
        SimpleMessageListenerContainer result = new SimpleMessageListenerContainer();
        result.setConnectionFactory(connectionFactory());
        result.setQueues(queue);
        result.setMessageListener(listener);
        return result;
    }


    @Bean
    public synchronized CachingConnectionFactory connectionFactory() {
        if (amqpConnectionFactory != null) return amqpConnectionFactory;


        amqpConnectionFactory = new CachingConnectionFactory(host, Integer.parseInt(port, 10));
        amqpConnectionFactory.setUsername(user);
        amqpConnectionFactory.setPassword(password);
        amqpConnectionFactory.setVirtualHost(virtual);

        return amqpConnectionFactory;
    }


    @Bean
    public Queue securityQueue() {
        return buildQueue("hermes.core.security", "kpo.dead-letter-queue", "core.security");
    }

    @Bean
    public Queue tenantQueue() {
        return buildQueue("hermes.core.tenants", "kpo.dead-letter-queue", "core.tenants");
    }

    @Bean
    public Queue i18nQueue() {
        return buildQueue("hermes.core.i18n", "kpo.dead-letter-queue", "core.i18n");
    }

    @Bean
    public Queue licenceQueue() {
        return buildQueue("hermes.core.licence", "kpo.dead-letter-queue", "core.licence");
    }


    private Queue buildQueue(final String name, final String dlqExchange, final String dlqRoutingKey) {
        Map<String, Object> parameters = generateDlqParameters(dlqExchange, dlqRoutingKey);
        return new Queue(name, true, false, false, parameters);
    }

    private Map<String, Object> generateDlqParameters(final String dlqExchange, final String dlqRoutingKey) {
        HashMap<String, Object> result = new HashMap<>(2);

        result.put("x-dead-letter-exchange", dlqExchange);
        result.put("x-dead-letter-routing-key", dlqRoutingKey);

        return result;
    }
}
