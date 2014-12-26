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

package de.kaiserpfalzEdv.commons.correlation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;

/**
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
public abstract class CDICorrelationInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(CDICorrelationInterceptor.class);


    @AroundInvoke
    public Object interceptGet(InvocationContext ctx) throws Exception {
        LOG.debug("Correlating {} {}", new Object[]{ctx.getMethod(), ctx.getParameters()});

        final Class<? extends Object> runtimeClass = ctx.getTarget().getClass();
        LOG.trace("Runtime extended classes: {}", (Object[]) runtimeClass.getClasses());
        LOG.trace("Runtime implemented interfaces: {}", (Object[]) runtimeClass.getInterfaces());
        LOG.trace("Runtime annotations ({}): {}", runtimeClass.getAnnotations().length, runtimeClass.getAnnotations());
        final Class<?> declaringClass = ctx.getMethod().getDeclaringClass();
        LOG.trace("Declaring class: {}", declaringClass);
        LOG.trace("Declaring extended classes: {}", (Object[]) declaringClass.getClasses());
        LOG.trace("Declaring annotations ({}): {}", declaringClass.getAnnotations().length, declaringClass.getAnnotations());
        String entityName;
        try {
            RequestCorrelated namedResource = runtimeClass.getAnnotation(RequestCorrelated.class);
            entityName = namedResource.value();
            if ("".equals(entityName)) throw new NullPointerException();

            LOG.trace("Got @Correlate={}", entityName);
        } catch (NullPointerException e) {
            entityName = declaringClass.getSimpleName().toLowerCase(); // TODO: should be lowerFirst camelCase
            LOG.warn("@Correlate not named, inferred from declaring classname: {} -> {}", declaringClass.getSimpleName(), entityName);
        }

        Correlation correlation = getCorrelation(ctx);
        MDC.put("correlationId", correlation.getId().toString());

        putCorrelationIntoMDC(correlation);

        try {
            LOG.info("Working on request: {}", correlation.toString());

            return ctx.proceed();
        } finally {
            MDC.remove("correlationId");
            MDC.remove("requestId");
            MDC.remove("responseId");
        }
    }


    public abstract Correlation getCorrelation(final InvocationContext ctx);

    public abstract <T extends Correlation> void putCorrelationIntoMDC(T correlation);
}
