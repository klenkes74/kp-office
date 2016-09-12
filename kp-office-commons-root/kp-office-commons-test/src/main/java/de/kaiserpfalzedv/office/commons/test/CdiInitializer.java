/*
 * Copyright 2016 Kaiserpfalz EDV-Service, Roland T. Lichti
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

package de.kaiserpfalzedv.office.commons.test;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.AnnotatedType;
import javax.enterprise.inject.spi.BeanManager;
import javax.enterprise.inject.spi.CDI;
import javax.enterprise.inject.spi.InjectionTarget;

import org.jboss.weld.environment.se.Weld;
import org.jboss.weld.environment.se.WeldContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author klenkes {@literal <rlichti@kaiserpfalz-edv.de>}
 * @version 1.0.0
 * @since 2016-09-04
 */
public class CdiInitializer {
    private static final Logger LOG = LoggerFactory.getLogger(CdiInitializer.class);

    private static final CdiInitializer INSTANCE = new CdiInitializer();

    private final WeldContainer cdi;

    public CdiInitializer() {
        cdi = new Weld().initialize();

        LOG.info("Started Weld Container: {}", cdi);
    }


    public static <T> void addToCDI(T object) {
        // CDI is not activated by default?
        BeanManager beanManager = CDI.current().getBeanManager();

        addToCDI(object, beanManager);
    }

    @SuppressWarnings("unchecked")
    public static <T> void addToCDI(T object, BeanManager beanManager) {
        AnnotatedType<T> annotatedType = beanManager.createAnnotatedType((Class<T>) object.getClass());
        InjectionTarget<T> injectionTarget = beanManager.createInjectionTarget(annotatedType);
        CreationalContext<T> crContext = beanManager.createCreationalContext(null);
        injectionTarget.inject(object, crContext);

        LOG.trace("Added class {} to CDI context {}.", annotatedType.getClass().getCanonicalName(), crContext);
    }
}
