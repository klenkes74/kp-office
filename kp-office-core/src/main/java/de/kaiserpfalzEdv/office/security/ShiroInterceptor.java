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

package de.kaiserpfalzEdv.office.security;

import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

/**
 * @author Hendy Irawan
 * @author klenkes &lt;rlichti@kaiserpfalz-edv.de&gt;
 * @since 0.1.0
 */
@Secured @Interceptor
public class ShiroInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(ShiroInterceptor.class);

    @Inject
    Subject subject;

    @Inject
    SecurityManager securityManager;


    @AroundInvoke
    public Object interceptGet(InvocationContext ctx) throws Exception {
        LOG.trace("Securing {} {}", new Object[] { ctx.getMethod(), ctx.getParameters() });
        LOG.trace("Principal is: {}", subject.getPrincipal());

        final Class<? extends Object> runtimeClass = ctx.getTarget().getClass();
        LOG.trace("Runtime extended classes: {}", runtimeClass.getClasses());
        LOG.trace("Runtime implemented interfaces: {}", runtimeClass.getInterfaces());
        LOG.trace("Runtime annotations ({}): {}", runtimeClass.getAnnotations().length, runtimeClass.getAnnotations());
        final Class<?> declaringClass = ctx.getMethod().getDeclaringClass();
        LOG.trace("Declaring class: {}", declaringClass);
        LOG.trace("Declaring extended classes: {}", declaringClass.getClasses());
        LOG.trace("Declaring annotations ({}): {}", declaringClass.getAnnotations().length, declaringClass.getAnnotations());
        String entityName;
        try {
            NamedResource namedResource = runtimeClass.getAnnotation(NamedResource.class);
            entityName = namedResource.value();
            LOG.trace("Got @NamedResource={}", entityName);
        } catch (NullPointerException e) {
            entityName = declaringClass.getSimpleName().toLowerCase(); // TODO: should be lowerFirst camelCase
            LOG.warn("@NamedResource not annotated, inferred from declaring classname: {} -> {}", declaringClass.getSimpleName(), entityName);
        }
        String action = "admin";
        if (ctx.getMethod().getName().matches("get[A-Z].*"))
            action = "view";
        if (ctx.getMethod().getName().matches("set[A-Z].*"))
            action = "edit";
        String permission = String.format("%s:%s", action, entityName);
        LOG.debug("Checking permission '{}' for user '{}'", permission, subject.getPrincipal());
        try {
            subject.checkPermission(permission);
        } catch (Exception e) {
            LOG.error("Access denied - {}: {}", e.getClass().getName(), e.getMessage());
            throw e;
        }
        return ctx.proceed();
    }
}
