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
import de.kaiserpfalzEdv.office.cli.executor.impl.ModuleConfigurator;
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
    private static String configFile = "/beans.xml";


    public static void main(String[] args) {
        EnvironmentLogger.log();

        ApplicationContext context = new ClassPathXmlApplicationContext(configFile);

        ModuleConfigurator configurator = context.getBean(ModuleConfigurator.class);
        configurator.initialializeModules(args);

        EventBusHandler bus = context.getBean(EventBusHandler.class);

        bus.post(new PreparationCommand(Application.class));

        bus.post(new ExecutionCommand(Application.class));

        return;
    }
}
