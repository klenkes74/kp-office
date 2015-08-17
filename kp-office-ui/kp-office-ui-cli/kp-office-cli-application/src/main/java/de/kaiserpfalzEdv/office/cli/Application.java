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

package de.kaiserpfalzEdv.office.cli;

import de.kaiserpfalzEdv.commons.jee.EnvironmentLogger;
import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import de.kaiserpfalzEdv.office.cli.executor.events.ExecutionCommand;
import de.kaiserpfalzEdv.office.cli.executor.events.PreparationCommand;
import de.kaiserpfalzEdv.office.cli.executor.events.ShutdownCommand;
import de.kaiserpfalzEdv.office.cli.executor.impl.ModuleConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.aspectj.EnableSpringConfigured;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.Serializable;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @version 0.1.0
 * @since 0.1.0
 */
@EnableSpringConfigured
public class Application implements Serializable {
    private static Logger LOG = LoggerFactory.getLogger(Application.class);
    private static String configFile = "/beans.xml";

    private static Application runner = new Application();

    public static void main(String[] args) {
        runner.run(args);

        System.exit(0);
    }

    private void run(String[] args) {
        LOG.info("Starting kp-office-cli: {}", args);
        EnvironmentLogger.log();

        ApplicationContext context = new ClassPathXmlApplicationContext(configFile);

        LOG.debug("Initializing application ...");
        ModuleConfigurator configurator = context.getBean(ModuleConfigurator.class);
        configurator.initialializeModules(args);

        EventBusHandler bus = context.getBean(EventBusHandler.class);

        LOG.debug("Preparing commands ...");
        bus.post(new PreparationCommand(Application.class));

        LOG.debug("Executing commands ...");
        bus.post(new ExecutionCommand(Application.class));

        LOG.info("Stopping kp-office-cli");
        bus.post(new ShutdownCommand(Application.class));
    }
}
