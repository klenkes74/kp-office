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

package de.kaiserpfalzEdv.office.ui.web;

import de.kaiserpfalzEdv.office.ui.web.configuration.QueueCommunicationConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.boot.SpringApplication;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.logging.Level;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 01.08.15 05:23
 */
public class Application {
    private static final Logger LOG = LoggerFactory.getLogger(Application.class);


    static {
        if (!SLF4JBridgeHandler.isInstalled()) {
            LOG.info("Redirecting java.util.logging to SLF4J ...");

            java.util.logging.LogManager.getLogManager().reset();
            SLF4JBridgeHandler.install();
            java.util.logging.Logger.getLogger("global").setLevel(Level.FINEST);
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(QueueCommunicationConfiguration.class, args);
    }

    @PostConstruct
    public void init() {
        LOG.trace("***** Created: {}", this);
    }

    @PreDestroy
    public void close() {
        LOG.trace("***** Destroyed: {}", this);
    }
}
