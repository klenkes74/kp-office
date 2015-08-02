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

package de.kaiserpfalzEdv.office.ui.web.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import de.kaiserpfalzEdv.office.commons.jackson.VersionableJacksonModule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@Configuration
@ConfigurationProperties("spring.rabbitmq")
public class QueueCommunicationConfiguration {
    private static Logger LOG = LoggerFactory.getLogger(QueueCommunicationConfiguration.class);

    private String                       host;
    private int                          port;
    private String                       virtual;
    private String                       username;
    private String                       password;
    private CachingConnectionFactory     connectionFactory;
    private Jackson2JsonMessageConverter amqpConverter;
    private ObjectMapper                 jsonMapper;


    public QueueCommunicationConfiguration() {
        LOG.trace("***** Created: {}", this);
    }

    @PostConstruct
    public void init() {
        LOG.debug("***** Initialized: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this);
    }


    @Bean
    public synchronized ConnectionFactory connectionFactory() throws Exception {
        if (connectionFactory == null) {
            connectionFactory = new CachingConnectionFactory(host, port);
            connectionFactory.setVirtualHost(virtual);
            connectionFactory.setUsername(username);
            connectionFactory.setPassword(password);

            connectionFactory.afterPropertiesSet();
        }

        return connectionFactory;
    }

    @Bean
    public synchronized RabbitTemplate rabbitTemplate(final ConnectionFactory connectionFactory) {
        RabbitTemplate result = new RabbitTemplate();

        result.setConnectionFactory(connectionFactory);
        result.setMessageConverter(messageConverter());

        return result;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        if (amqpConverter != null) return amqpConverter;

        amqpConverter = new Jackson2JsonMessageConverter();
        amqpConverter.setJsonObjectMapper(objectMapper());

        return amqpConverter;
    }


    @Bean
    public ObjectMapper objectMapper() {
        if (jsonMapper != null) return jsonMapper;

        jsonMapper = new ObjectMapper();
        jsonMapper.findAndRegisterModules();
        jsonMapper.registerModule(new VersionableJacksonModule());

        return jsonMapper;
    }


    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;

        LOG.trace("*   *   set host: {}", host);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;

        LOG.trace("*   *   set port: {}", port);
    }

    public String getVirtual() {
        return virtual;
    }

    public void setVirtual(String virtual) {
        this.virtual = virtual;

        LOG.trace("*   *   set virtual: {}", virtual);
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;

        LOG.trace("*   *   set username: {}", username);
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;

        LOG.trace("*   *   set password: {}", password);
    }
}