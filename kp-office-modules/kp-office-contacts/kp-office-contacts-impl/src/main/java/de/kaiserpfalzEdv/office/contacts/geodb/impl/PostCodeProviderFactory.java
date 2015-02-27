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

package de.kaiserpfalzEdv.office.contacts.geodb.impl;

import de.kaiserpfalzEdv.office.contacts.geodb.PostCodeProviderLoader;
import de.kaiserpfalzEdv.office.contacts.geodb.spi.PostCodeProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.annotation.PreDestroy;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.HashSet;
import java.util.ServiceLoader;
import java.util.Set;

/**
 * A factory for creating spring managed {@link de.kaiserpfalzEdv.office.contacts.geodb.spi.PostCodeProvider}
 * instances. For searching available instances, the {@link java.util.ServiceLoader} is used.
 *
 * @author klenkes
 * @version 2015Q1
 * @since 26.02.15 08:02
 */
@Named
public class PostCodeProviderFactory implements PostCodeProviderLoader, ApplicationContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(PostCodeProviderFactory.class);


    private ApplicationContext applicationContext;


    @Inject
    public PostCodeProviderFactory(final ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;

        LOG.trace("Created/Initialized: {}", this);
        LOG.trace("  application context: {}", this.applicationContext);
    }

    @PreDestroy
    public void close() {
        LOG.trace("Destroyed: {}", this);
    }


    /**
     * Located available {@link de.kaiserpfalzEdv.office.contacts.geodb.spi.PostCodeProvider} via the standard
     * java {@link java.util.ServiceLoader} and returns any of the found implementation as spring bean.
     *
     * @return The first found provider retrieved from the current application context as spring bean.
     *
     * @throws java.lang.IllegalStateException If there is no
     *                                         {@link de.kaiserpfalzEdv.office.contacts.geodb.spi.PostCodeProvider} available.
     */
    public PostCodeProvider getInstance() {
        ServiceLoader<PostCodeProvider> providers = ServiceLoader.load(PostCodeProvider.class);

        if (!providers.iterator().hasNext()) {
            throw new IllegalStateException("No PostCodeProvider available!");
        }

        return applicationContext.getBean(providers.iterator().next().getClass());
    }

    @Override
    public Set<PostCodeProvider> loadAllProvider() {
        HashSet<PostCodeProvider> result = new HashSet<>();
        ServiceLoader<PostCodeProvider> providers = ServiceLoader.load(PostCodeProvider.class);

        if (!providers.iterator().hasNext()) {
            throw new IllegalStateException("No PostCodeProvider available!");
        }

        providers.forEach(result::add);

        return result;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
