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

package de.kaiserpfalzEdv.office.cli.executor.impl;

import de.kaiserpfalzEdv.commons.jee.eventbus.EventBusHandler;
import de.kaiserpfalzEdv.commons.service.Versionable;
import de.kaiserpfalzEdv.office.cli.CliModule;
import de.kaiserpfalzEdv.office.cli.CliModuleInfo;
import de.kaiserpfalzEdv.office.cli.executor.CliModuleRegistrar;
import de.kaiserpfalzEdv.office.cli.executor.CliModuleScanner;
import de.kaiserpfalzEdv.office.cli.executor.events.ShutdownCommand;
import de.kaiserpfalzEdv.office.cli.executor.results.CliResult;
import de.kaiserpfalzEdv.office.cli.executor.results.CliSuccessResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * @author klenkes
 * @version 2015Q1
 * @since 03.03.15 00:28
 */
@Named
public class RegistrarImpl implements CliModuleInfo, CliModuleRegistrar, CliModuleScanner, ApplicationContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(RegistrarImpl.class);

    private String contextFileName;

    private ApplicationContext context;
    private boolean initialized = false;


    private EventBusHandler bus;


    public RegistrarImpl(final String contextFileName) {
        this.contextFileName = contextFileName;
    }

    @Inject
    public RegistrarImpl(final ApplicationContext context) {
        setApplicationContext(context);
    }


    @PostConstruct
    @Override
    public CliResult init() {
        if (context == null) {
            setApplicationContext(new ClassPathXmlApplicationContext(contextFileName));
        }

        bus = context.getBean(EventBusHandler.class);

        initialized = true;
        LOG.info("Initialized: {}", this);
        return new CliSuccessResult(this, CliResultCode.OK);
    }


    @PreDestroy
    @Override
    public CliResult close() {
        if (initialized) {
            ShutdownCommand command = new ShutdownCommand(this);
            bus.post(command);
        }

        initialized = false;

        return new CliSuccessResult(this, CliResultCode.OK);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        LOG.debug("Changing application context: {} -> {}", context, applicationContext);
        this.context = applicationContext;
    }


    @Override
    public Set<CliModule> scan() {
        LOG.debug("Scanning for CLI modules ...");

        HashSet<CliModule> result = new HashSet<>();

        ServiceLoader<CliModule> loader = ServiceLoader.load(CliModule.class);
        loader.forEach(
                m -> {
                    LOG.trace("Found CLI Module: {}", m.getDisplayName());

                    CliModule toAdd = context.getBean(m.getClass());
                    result.add(toAdd);

                    LOG.trace("Added CLI Module: {}", toAdd.getDisplayName());
                }
        );

        return result;
    }


    @Override
    public Versionable getVersion() {
        return new Versionable.Builder().withMajor(0).withMinor(2).withPatchlevel(0).build();
    }

    @Override
    public String getShortDescription() {
        return "Module Registrar";
    }

    @Override
    public String getDescription() {
        return "Internal module for working with the different modules.";
    }

    @Override
    public boolean isInitialized() {
        return initialized;
    }

    @Override
    public String getDisplayName() {
        return RegistrarImpl.class.getSimpleName();
    }
}
